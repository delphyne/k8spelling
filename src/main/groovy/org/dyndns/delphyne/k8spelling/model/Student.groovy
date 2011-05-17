package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity

@Entity
class Student {
    String name
    boolean active
    
    static constraints = {
        name(unique:true)
    }
    
    String toString() {
        "$name ($active)"
    }
}
