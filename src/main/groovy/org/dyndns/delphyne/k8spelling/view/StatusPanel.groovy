package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j

import java.awt.BorderLayout
import java.awt.Component
import java.awt.FontMetrics

import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

@Slf4j
class StatusPanel implements GuiPanel {
    JPanel widget
    Component defaultFocus
    JButton defaultButton

    private JLabel label

    private SwingBuilder swing

    StatusPanel() {
        swing = new SwingBuilder()

        widget = swing.build {
            panel(border: compoundBorder(outer: loweredBevelBorder(), inner: emptyBorder(3))) {
                borderLayout()
                label = label(constraints: BorderLayout.CENTER)
            }
        }
    }

    void setMessage(String msg) {
        FontMetrics metrics = label.getFontMetrics(label.getFont())

        int maxWidth = label.width - 10
        int strWidth = metrics.stringWidth(msg)

        if (strWidth > maxWidth) {
            label.toolTipText = msg
        } else {
            label.toolTipText = null
        }

        label.text = msg
        log.info msg
    }
}
