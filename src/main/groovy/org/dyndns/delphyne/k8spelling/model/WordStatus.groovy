package org.dyndns.delphyne.k8spelling.model

import java.sql.Date

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class WordStatus {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id

    Student student
    
    Word word
    
    @Enumerated(EnumType.STRING)
    WordState wordState
    
    Date masteredDate
    
    Date assignedDate
}
