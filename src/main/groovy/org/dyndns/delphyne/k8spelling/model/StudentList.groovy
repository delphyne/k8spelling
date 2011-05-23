package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity
import groovy.transform.Canonical

@Canonical
@Entity
class StudentList implements ListOfAtoms {
    String name
    List items
    
    static hasMany = [items: Student]
    static transients = ["default"]
    static mapping = { items(lazy: false) }
    
    static StudentList getDefault() {
        new StudentList(name: "Default", students: Student.list())
    }
    
    String toString() { name }
}
