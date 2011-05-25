package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity
import groovy.transform.Canonical


@Entity
@Canonical
class Word implements Atom, Comparable<Word> {
    String data
	static constraints = {
		data(blank: false)
	}
    
    String toString() { data }
    int compareTo(Word other) { data <=> other?.data }
}
