package org.dyndns.delphyne.k8spelling.print

import groovy.util.logging.Log4j

import java.awt.Font
import java.awt.Graphics
import java.awt.RenderingHints

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList
import org.dyndns.delphyne.k8spelling.model.WordState
import org.dyndns.delphyne.k8spelling.model.WordStatus

@Log4j
class TeacherSummaryPage {
    StudentList students
    WordList words

    final static int DPI = 72
    final static int WIDTH = DPI * 8.5
    final static int HEIGHT = DPI * 11

    Font headerFont = new Font('Helvertica', Font.BOLD, 24)
    Font studentFont = new Font('Helvetica', Font.BOLD, 12)
    Font wordFont = new Font('Helvetica', Font.PLAIN, 12)

    void paint(Graphics g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        g.font = headerFont
        g.drawString("${students} / ${words} (${new Date().format('MMMMM d, yyyy')})", DPI * 0.5, DPI * 0.5)

        students.items.eachWithIndex { Student student, int i ->
            int voff = DPI + 0.275 * DPI * i
            log.debug "finding words from $words assigned to $student"
            List assignedWords = words.items.findAll { Word word ->
                WordStatus.withCriteria {
                    and {
                        eq('word', word)
                        eq('student', student)
                        eq('state', WordState.Assigned)
                    }
                }
            }

            g.font = studentFont
            g.drawString(student as String, DPI * 0.5, voff)

            g.font = wordFont
            g.drawString(assignedWords.join(', '), DPI * 2, voff)
        }
    }
}
