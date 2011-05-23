package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity
import groovy.transform.Canonical

@Canonical
@Entity
class Student implements Atom {
    String data
    String toString() { data }
}
