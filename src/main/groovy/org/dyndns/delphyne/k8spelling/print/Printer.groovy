package org.dyndns.delphyne.k8spelling.print

import groovy.util.logging.Log4j

import java.awt.Graphics
import java.awt.print.PageFormat
import java.awt.print.Printable
import java.awt.print.PrinterException
import java.awt.print.PrinterJob

import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.WordList

@Log4j
class Printer implements Printable {

    StudentList students
    WordList words

    Printer(StudentList students, WordList words) {
        this.students = students
        this.words = words
    }

    int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        try {
            log.trace "print requested, page $pageIndex"
            if (pageIndex > Math.ceil(students.items.size() / 4)) {
                log.trace 'NO_SUCH_PAGE'
                NO_SUCH_PAGE
            } else {
                def currentPage

                if (pageIndex == 0) {
                    log.debug 'setting current page to summary'
                    currentPage = new TeacherSummaryPage(students: students, words: words)
                } else {
                    int wcIndex = pageIndex - 1
                    log.debug "setting current page to student word card page $wcIndex"
                    currentPage = new StudentWordCardPage(students: students, words: words, page: wcIndex)
                }

                graphics.translate(pageFormat.imageableX, pageFormat.imageableY)
                log.debug "rendering page of type ${currentPage.class.simpleName}"
                currentPage.paint(graphics)
                log.trace 'PAGE_EXISTS'
                PAGE_EXISTS
            }
        } catch (ex) {
            log.error '', ex
        }
    }

    void print() {
        PrinterJob printJob = PrinterJob.printerJob
        printJob.printable = this
        if (printJob.printDialog()) {
            try {
                printJob.print()
            } catch (ex) {
                log.error '', ex
            }
        }
    }
}
