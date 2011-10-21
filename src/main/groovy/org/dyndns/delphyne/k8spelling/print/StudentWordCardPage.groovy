package org.dyndns.delphyne.k8spelling.print

import groovy.util.logging.Log4j

import java.awt.Graphics

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.WordList

@Log4j
class StudentWordCardPage {

    final static int DPI = 72
    final static int WIDTH = DPI * 8.5
    final static int HEIGHT = DPI * 11

    StudentList students
    WordList words
    int page

    void paint(Graphics graphics) {
        students.items.drop(page * 4).take(4).eachWithIndex { Student student, int index ->
            log.trace "printing word card for $student"
            def swc = new StudentWordCard(student: student, words: words, index: index)
            swc.paint(graphics)
        }
	}

    void foo() { apple.awt.CPrinterGraphics }
}
