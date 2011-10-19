package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder

import javax.swing.JFrame

import org.dyndns.delphyne.k8spelling.TestBase
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.WordList
import org.dyndns.delphyne.k8spelling.print.TeacherSummaryPage

class TeacherSummaryPageTest extends TestBase {
    static void main(String[] args) {
        TeacherSummaryPage tsp = new TeacherSummaryPage(students: StudentList.default, words: WordList.default)

        SwingBuilder.edtBuilder {
            frame(title: TeacherSummaryPageTest.simpleName, defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE, show: true, size: [TeacherSummaryPage.WIDTH, TeacherSummaryPage.HEIGHT]) {
                widget(tsp)
            }
        }
    }
}
