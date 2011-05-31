package org.dyndns.delphyne.k8spelling.controller

import java.lang.Thread.State;

import groovy.util.logging.Slf4j

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordState
import org.dyndns.delphyne.k8spelling.model.WordStatus

@Slf4j
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
}
