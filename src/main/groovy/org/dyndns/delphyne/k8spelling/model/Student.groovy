package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity

@Entity
class Student {
    String name
    Boolean active
    
    static constraints = {
        name(unique:true, validator:{ val, obj -> val.trim().size() > 0 })
    }
    
    String toString() {
        "$name ($active)"
    }
}
