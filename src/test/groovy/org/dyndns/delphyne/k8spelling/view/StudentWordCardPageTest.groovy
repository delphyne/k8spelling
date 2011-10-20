package org.dyndns.delphyne.k8spelling.view

import javax.swing.JFrame

import org.dyndns.delphyne.k8spelling.TestBase
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.WordList
import org.dyndns.delphyne.k8spelling.print.StudentWordCardPage
import org.dyndns.delphyne.k8spelling.print.TeacherSummaryPage

import groovy.swing.SwingBuilder

class StudentWordCardPageTest extends TestBase {
    static void main(String[] args) {
        StudentWordCardPage swcp = new StudentWordCardPage(StudentList.default, WordList.default, 0)
        
        SwingBuilder.edtBuilder {
            frame(title: StudentWordCardPage.simpleName, defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE, show: true, size: [StudentWordCardPage.WIDTH, StudentWordCardPage.HEIGHT]) {
                widget(swcp)
            }
        }
    }
}
