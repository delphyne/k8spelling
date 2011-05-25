package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity
import groovy.transform.Canonical

@Canonical
@Entity
class Student implements Atom, Comparable<Student> {
    String data
    
	static constraints = {
		data(blank: false)
	}
    
    String toString() { data }
    int compareTo(Student other) { data <=> other?.data }
}
