package org.dyndns.delphyne.k8spelling.print

import java.awt.GridLayout;

import javax.swing.JPanel

import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.WordList

class StudentWordCardPage extends JPanel {
    
    final static int DPI = 72
    final static int WIDTH = DPI * 8.5
    final static int HEIGHT = DPI * 11
    
    StudentWordCardPage(StudentList students, WordList words, int page) {
        this.layout = new GridLayout(4, 1)
        students.items.drop(page * 4).take(4).each {
            add(new StudentWordCard(student: it, words: words))
        }
    }
}
