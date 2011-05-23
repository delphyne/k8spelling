package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity
import groovy.transform.Canonical


@Entity
@Canonical
class Word implements Atom {
    String data
    String toString() { data }
}
