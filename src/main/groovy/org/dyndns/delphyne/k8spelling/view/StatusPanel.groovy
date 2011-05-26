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
class StatusPanel implements GuiPanel, Thread.UncaughtExceptionHandler {
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
        
        Thread.defaultUncaughtExceptionHandler = this
    }

    void setMessage(String msg, boolean writeLog = true) {
        FontMetrics metrics = label.getFontMetrics(label.getFont())

        int maxWidth = label.width - 10
        int strWidth = metrics.stringWidth(msg)

        if (strWidth > maxWidth) {
            label.toolTipText = msg
        } else {
            label.toolTipText = null
        }

        label.text = msg

        if (log) {
            log.info msg
        }
    }
    
    void uncaughtException(Thread t, Throwable e) {
        log.error "Uncaught Error", e
        setMessage("Error: ${e.message}", false)
    }
}
