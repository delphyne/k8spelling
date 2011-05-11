package org.dyndns.delphyne.k8spelling.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id
    
    String name
    
    boolean active
}
