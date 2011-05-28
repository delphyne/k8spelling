package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity
import groovy.transform.Canonical

@Canonical
@Entity
class WordList implements ListOfAtoms {
    String name
    List items
    
    static hasMany = [items: Word]
    static mapping = { items(lazy: false) }
    static transients = ["default"]
    
    static WordList getDefault() {
        new WordList(name: "Default", items: Word.list())
    }
    
    String toString() { name }
    
    static constraints = {
        name(blank: false)
    }
}
