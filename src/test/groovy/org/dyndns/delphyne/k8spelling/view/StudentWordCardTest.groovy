package org.dyndns.delphyne.k8spelling.view

import javax.swing.JFrame

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordState
import org.dyndns.delphyne.k8spelling.model.WordStatus
import org.dyndns.delphyne.k8spelling.print.StudentWordCard

import groovy.swing.SwingBuilder

class StudentWordCardTest {
    
    static void main(String[] args) {
        WordStatus.metaClass.static.findByStudentAndState << { Student student, WordState state ->
            ['who', 'what', 'when', 'where', 'why', 'how', 'a', 'an', 'then', 'the'].collect { new Word(data: it) }
        }
        
        Student s = new Student(data: "Jackson")
        
        SwingBuilder.edtBuilder {
            frame(title: StudentWordCardTest.simpleName, defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE, show:true, size: [StudentWordCard.WIDTH, StudentWordCard.HEIGHT * 4]) {
                gridLayout(columns: 1, rows: 4)
                4.times { widget(new StudentWordCard(student: s)) }
            }
        }
    }
}
