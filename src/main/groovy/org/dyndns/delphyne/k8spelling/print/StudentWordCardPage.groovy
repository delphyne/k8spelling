package org.dyndns.delphyne.k8spelling.print

import javax.swing.JPanel

import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.WordList

class StudentWordCardPage extends JPanel {
    StudentList students
    WordList words

    /**
     * Zero-indexed page number
     */
    int page
}
