package org.dyndns.delphyne.k8spelling.print

import groovy.util.logging.Slf4j

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics
import java.awt.RenderingHints

import javax.swing.JPanel

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList
import org.dyndns.delphyne.k8spelling.model.WordState
import org.dyndns.delphyne.k8spelling.model.WordStatus

@Slf4j
class StudentWordCard extends JPanel {

    Student student
    WordList words

    final static Font headerFont = new Font('Helvetica', Font.PLAIN, 24)
    final static Font wordFont = headerFont.deriveFont(16)

    final static int WIDTH = 72 * 8.5
    final static int HEIGHT = 72 * 11 / 4
    final static Dimension DIMENSION = new Dimension(WIDTH, HEIGHT)

    StudentWordCard() {
        super()
        background = Color.WHITE
    }

    void paintComponent(Graphics g) {
        super.paintComponent(g)

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        drawStudentName(g)

        // TODO: this needs to interrogate the wordlist...
        WordStatus.findByStudentAndState(student, WordState.Assigned).eachWithIndex { Word word, int index ->
            drawWord(g, word, index)
        }

        g.drawLine(0 + 36, height - 1, width - 72, height - 1)
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
        g.drawString(str, leftEdge, verticalOffset)
    }
}
