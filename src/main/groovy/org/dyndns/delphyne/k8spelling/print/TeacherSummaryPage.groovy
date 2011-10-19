package org.dyndns.delphyne.k8spelling.print

import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.RenderingHints

import javax.swing.JPanel

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList
import org.dyndns.delphyne.k8spelling.model.WordState
import org.dyndns.delphyne.k8spelling.model.WordStatus

class TeacherSummaryPage extends JPanel {
    StudentList students
    WordList words

    final static int DPI = 72
    final static int WIDTH = DPI * 8.5
    final static int HEIGHT = DPI * 11

    Font headerFont = new Font('Helvertica', Font.BOLD, 24)
    Font studentFont = new Font('Helvetica', Font.BOLD, 12)
    Font wordFont = new Font('Helvetica', Font.PLAIN, 12)

    TeacherSummaryPage() {
        super()
        background = Color.WHITE
    }

    void paintComponent(Graphics g) {
        super.paintComponent(g)

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        g.font = headerFont
        g.drawString("${students} / ${words} (${new Date().format('MMMMM d, yyyy')})", DPI * 0.5, DPI * 0.5)

        students.items.eachWithIndex { Student student, int i ->
            int voff = DPI + 0.3 * DPI * i
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
