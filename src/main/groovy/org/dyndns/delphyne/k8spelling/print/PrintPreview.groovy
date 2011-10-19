package org.dyndns.delphyne.k8spelling.print

import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j

import java.awt.BorderLayout
import java.awt.Graphics
import java.awt.print.PageFormat
import java.awt.print.Printable
import java.awt.print.PrinterJob

import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.RepaintManager

import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.WordList

@Slf4j
class PrintPreview extends JFrame implements Printable {
    private SwingBuilder swing = new SwingBuilder()

    StudentList students
    WordList words

    def pageCache = [:].withDefault {
        if (it == 'summary') {
            new TeacherSummaryPage(students: students, words: words)
        } else {
            new StudentWordCardPage(students: students, words: words, page: it)
        }
    }

    PrintPreview() {
        super('Print Preview')

        swing.build {
            panel(id: 'content') {
                borderLayout()
                panel(id: 'page', constraints: BorderLayout.CENTER)
                panel(constraints: BorderLayout.SOUTH) {
                    checkBox(id: 'includeTeacherPage', selected: true, action: action(name: 'Include Teacher Summary:',
                        closure: {
                            log.info 'clicked'
                        }))
                    spinner(id: 'currentPageNum', model: spinnerNumberModel(minimum: 1, maximum: 5, stepSize: 1), value: 1)
                    button(action: action(name: 'Print', closure: {
                            PrinterJob printJob = PrinterJob.printerJob
                            printJob.printable = this
                            printJob.print()
                        }))
                }
            }
        }
        contentPane.add(swing.content)
    }

    void updatePreviewPane() {
        def page = getPage(swing.currentPageNum.value - 1)
    }

    JPanel getPage(int index) {
        if (swing.includeTeacherPage.selected) {
            index == 0 ? pageCache.summary : pageCache[index - 1]
        } else {
            pageCache[index]
        }
    }

    int print(Graphics graphics, PageFormat format, int index) {
        if (index >= swing.spinner.model.maximum) {
            NO_SUCH_PAGE
        } else {
            JPanel page = getPage(index)
            RepaintManager rm = RepaintManager.currentManager(page)

            graphics.translate(format.imageableX, format.imageableY)
            rm.doubleBufferingEnabled = false
            page.paint(graphics)
            rm.doubleBufferingEnabled = true

            PAGE_EXISTS
        }
    }
}
