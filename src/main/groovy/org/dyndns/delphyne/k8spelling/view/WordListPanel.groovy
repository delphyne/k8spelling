package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.awt.Component

import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JSplitPane;

class WordListPanel implements GuiPanel {
    JPanel widget
    JButton defaultButton
    
    WordListPanel() {
        SwingBuilder swing = new SwingBuilder()
        
        widget = swing.panel {
            borderLayout()
            panel(constraints:BorderLayout.NORTH, border:emptyBorder(3)) {
                borderLayout()
                comboBox(constraints:BorderLayout.CENTER)
                button(constraints:BorderLayout.EAST, text:"+")
            }
            panel(constraints:BorderLayout.CENTER) {
                borderLayout()
                panel(constraints:BorderLayout.CENTER) {
                    borderLayout()
                    splitPane(
                            constraints:BorderLayout.CENTER,
                            orientation:JSplitPane.HORIZONTAL_SPLIT,
                            leftComponent:list(),
                            rightComponent:list()
                        )
                }
                panel(constraints:BorderLayout.SOUTH) {
                    button(text:"<<")
                    button(text:"<")
                    button(text:">")
                    button(text:">>")
                }
            }
            panel(constraints:BorderLayout.SOUTH, border:emptyBorder(3)) {
                borderLayout()
                textField(constraints:BorderLayout.CENTER)
                label(constraints:BorderLayout.WEST, text:"New word:")
                button(constraints:BorderLayout.EAST, text:"Add Word")
            }
        }
    }
    
    Component getDefaultFocus() { }
}
