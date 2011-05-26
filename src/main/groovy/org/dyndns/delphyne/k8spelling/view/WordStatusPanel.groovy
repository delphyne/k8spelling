package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.awt.Component

import javax.swing.DefaultComboBoxModel
import javax.swing.JButton
import javax.swing.JPanel

import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.WordList

class WordStatusPanel implements GuiPanel {
    JPanel widget
    Component defaultFocus
    JButton defaultButton
    
    private StatusPanel status
    private SwingBuilder swing
    
    WordStatusPanel(StatusPanel status) {
        swing = new SwingBuilder()
        
        swing.panel {
            borderLayout()
            
            widget = panel(constraints: BorderLayout.NORTH) {
                label(text: "Students:")
                comboBox(model: new DefaultComboBoxModel(StudentList.list() as Object[]))
                label(text: "Words:")
                comboBox(model: new DefaultComboBoxModel(WordList.list() as Object[]))
            }
        }
    }
}
