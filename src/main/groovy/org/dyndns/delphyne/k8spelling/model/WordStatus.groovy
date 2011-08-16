package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity

@Entity
class WordStatus {
    Student student
    Word word
    WordState state
    Date masteredDate
    Date assignedDate
    
    static constraints = {
        state(nullable:true)
        masteredDate(nullable:true)
        assignedDate(nullable:true)
    }
    
    static mapping = { word(lazy:false) }
    
    String toString() {
        state
    }
}
