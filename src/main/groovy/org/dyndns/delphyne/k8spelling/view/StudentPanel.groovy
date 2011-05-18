package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.awt.Component

class StudentPanel {
    Component widget
    
    StudentPanel() {
        SwingBuilder sb = new SwingBuilder()
        widget = sb.panel {
            borderLayout()
            scrollPane(constraints:BorderLayout.CENTER) {
                textArea()
            }
            panel(constraints:BorderLayout.SOUTH) {
                borderLayout()
                textField(constraints: BorderLayout.CENTER)
                button(constraints:BorderLayout.EAST, text:"Add Student", mnemonic:"A")
            }
        }
    }
}
