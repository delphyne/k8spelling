package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity
import groovy.transform.Canonical

@Canonical
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
