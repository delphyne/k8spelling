package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity

@Entity
class WordStatus {
    Student student
    Word word
    WordState wordState
    Date masteredDate
    Date assignedDate
    
    static constraints = {
        wordState(nullable:true)
        masteredDate(nullable:true)
        assignedDate(nullable:true)
    }
    
    String toString() {
        wordState
    }
}
