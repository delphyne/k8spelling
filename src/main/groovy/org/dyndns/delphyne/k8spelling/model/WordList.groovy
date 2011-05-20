package org.dyndns.delphyne.k8spelling.model

import grails.persistence.Entity

@Entity
class WordList {
    String name
    List words
    
    static hasMany = [words:Word]
    
    static mapping = {
        words(lazy:false)
    }
    
    static constraints = {
        name(unique:true)
    }
    
    static transients = ["default"]
    
    static WordList getDefault() {
        new WordList(name:"Default", words:Word.list(sort:"id", order:"asc"))
    }
    
    String toString() {
        name
    }
}
