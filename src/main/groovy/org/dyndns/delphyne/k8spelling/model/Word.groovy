package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity

@Entity
class Word {
    String word
    
    static constraints = {
        word(unique:true)
    }
    
    String toString() {
        word
    }
}
