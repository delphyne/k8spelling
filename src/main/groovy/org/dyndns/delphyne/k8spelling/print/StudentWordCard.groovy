package org.dyndns.delphyne.k8spelling.print

import groovy.util.logging.Slf4j

import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics
import java.awt.RenderingHints

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList
import org.dyndns.delphyne.k8spelling.model.WordState
import org.dyndns.delphyne.k8spelling.model.WordStatus

@Slf4j
class StudentWordCard {

    Student student
    WordList words
    int index // which position on the page is this card, 0-4

    final static Font headerFont = new Font('Helvetica', Font.PLAIN, 24)
    final static Font wordFont = headerFont.deriveFont(16)

    final static int width = 72 * 8.5
    final static int height = 72 * 10.25 / 4

    void paint(Graphics g) {
        log.trace 'painting'

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        drawStudentName(g)

        log.trace 'finding words for student'
        words.items.findAll { Word word ->
            WordStatus.withCriteria {
                and {
                    eq('word', word)
                    eq('student', student)
                    eq('state', WordState.Assigned)
                }
            }
        }.eachWithIndex { Word word, int index ->
            log.trace word.toString()
            drawWord(g, word, index)
        }

        g.drawLine(0 + 36, adjustVerticalOffsetForIndex(height - 1), width - 72, adjustVerticalOffsetForIndex(height - 1))
    }

    private void drawStudentName(Graphics g) {
        int axis = width / 2
        int voff = 0.5 * 72
        drawString(g, headerFont, student.data, axis, voff)
    }

    private void drawWord(Graphics g, Word word, int index) {
        int axis = (index % 2) ? width / 4 : width * 3 / 4
        int voff = (0.35 * Math.floor(index / 2) * 72)
        int baseline = 1 * 72

        drawString(g, wordFont, word.data, axis, baseline + voff)
    }

    private void drawString(Graphics g, Font f, String str, int axis, int verticalOffset) {
        g.font = f
        FontMetrics fm = g.getFontMetrics(f)
        int strWidth = fm.stringWidth(str)
        int leftEdge = axis - strWidth / 2

        log.trace "${str} [axis: $axis, offset: $verticalOffset, leftEdge: $leftEdge]"
        g.drawString(str, leftEdge, adjustVerticalOffsetForIndex(verticalOffset))
    }

    private int adjustVerticalOffsetForIndex(int verticalOffset) {
        verticalOffset + (index * height)
    }
}
