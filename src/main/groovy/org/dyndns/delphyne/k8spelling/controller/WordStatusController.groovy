package org.dyndns.delphyne.k8spelling.controller

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList
import org.dyndns.delphyne.k8spelling.model.WordState
import org.dyndns.delphyne.k8spelling.model.WordStatus

import groovy.util.logging.Log4j

@Log4j
class WordStatusController {

    static WordStatus create(Word word, Student student, WordState state) {
        def result
        WordStatus.withTransaction {
            result = new WordStatus(word: word, student: student, state: state)
            if (state == WordState.Assigned) {
                result.assignedDate = new Date()
            } else if (state == WordState.Mastered) {
                result.masteredDate = new Date()
                result.assignedDate = result.masteredDate
            }
            result.save()
        }
    }

    static WordStatus update(WordStatus status, WordState state) {
        status.refresh()

        def assignedDate, masteredDate
        if (state == WordState.Assigned) {
            assignedDate = new Date()
            masteredDate = null
        } else if (state == WordState.Mastered) {
            masteredDate = new Date()
            assignedDate = status.assignedDate ?: masteredDate
        } else {
            assignedDate = masteredDate = null
        }

        WordStatus.withTransaction {
            status.state = state
            status.assignedDate = assignedDate
            status.masteredDate = masteredDate
            status.save()
        }
    }

    /**
     * Assigns up to numWordsToAssign words to a student in order from the beginning of the list.  Any words already
     *  assigned will be preserved, unless the student has more than 10 words assigned to them.  If more than 10 words
     *  are assigned, the extra words will be removed from the end of the list.
     * @param student The student to which new words are assigned
     * @param list The list from which to select new words
     * @param numWordsToAssign Up to this many words will be automatically assigned to the student.  Defaults to 10.
     * @return a List of changed word status objects
     */
    static List<WordStatus> autoAssign(Student student, WordList list, int numWordsToAssign = 10) {
        def assigned = []
        def unassigned = []
        def statuses = [:]
        def results = []
        
        for (word in list.items) {
            WordStatus currentStatus = WordStatus.findByStudentAndWord(student, word)
            if (currentStatus) {
                statuses[word] = currentStatus
                if (currentStatus.state == WordState.Assigned) {
                    assigned << word
                } else if (currentStatus?.state != WordState.Mastered) {
                    unassigned << word
                }
            } else {
                unassigned << word
            }
        }
        
        def toBeAssigned = []
        def toBeUnassigned = []
        if (assigned.size() < numWordsToAssign && unassigned.size() > 0) {
            toBeAssigned.addAll(unassigned[0..(Math.min(numWordsToAssign - assigned.size(), unassigned.size()))])
        } else if (assigned.size() > numWordsToAssign) {
            toBeUnassigned.addAll(assigned[(numWordsToAssign)..-1])
        }
        
        toBeAssigned.each { Word word ->
            WordStatus status = statuses[word]
            if (status) {
                results << update(status, WordState.Assigned)
            } else {
                results << create(word, student, WordState.Assigned)
            }
        }
        
        toBeUnassigned.each { Word word ->
            results << update(statuses[word], null)
        }
    }
}
