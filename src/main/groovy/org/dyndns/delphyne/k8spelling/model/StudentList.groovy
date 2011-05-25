package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity
import groovy.transform.Canonical

@Canonical
@Entity
class StudentList implements ListOfAtoms {
    String name
    SortedSet items
    
    static hasMany = [items: Student]
    static transients = ["default"]
    static mapping = { items(lazy: false) }
    
    static StudentList getDefault() {
        new StudentList(name: "Default", students: Student.list() as SortedSet)
    }
    
    String toString() { name }
    
    static constraints = {
        name(blank: false)
    }
}
