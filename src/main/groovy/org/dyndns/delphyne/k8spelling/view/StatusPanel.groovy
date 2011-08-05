package org.dyndns.delphyne.k8spelling.view

import java.awt.BorderLayout
import java.awt.FontMetrics

import javax.swing.JLabel
import javax.swing.JPanel

import org.apache.log4j.Logger

import groovy.swing.SwingBuilder

class StatusPanel extends JPanel implements Thread.UncaughtExceptionHandler {

    private JLabel label

    private SwingBuilder swing

    private final static Logger log = Logger.getLogger(StatusPanel)

    StatusPanel() {
        swing = new SwingBuilder()

        swing.build {
            panel(id: "statusPanelWidget", border: compoundBorder(outer: loweredBevelBorder(), inner: emptyBorder(3))) {
                borderLayout()
                label = label(constraints: BorderLayout.CENTER)
            }
        }

        this.add(swing.statusPanelWidget)

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

        if (writeLog) {
            log.info msg
        }
    }

    void uncaughtException(Thread t, Throwable e) {
        log.error "Uncaught Error", e
        setMessage("Error: ${e.message}", false)
    }
}
