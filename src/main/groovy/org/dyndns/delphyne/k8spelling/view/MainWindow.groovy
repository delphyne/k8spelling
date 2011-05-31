package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder

import java.awt.BorderLayout

import javax.swing.JFrame
import javax.swing.JOptionPane

import org.dyndns.delphyne.k8spelling.Config
import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList

class MainWindow {
    MainWindow() {
        Config config = new Config("test")
        def status = new StatusPanel()
        new SwingBuilder().edt {
            frame(id: "mainWindow", title: "k8spelling", defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE, size: [640, 480], show: true) {
                menuBar {
                    menu(text: "Help", mnemonic: 'H') {
                        menuItem(text: "About", mnemonic: 'A', actionPerformed: { optionPane(message: "k8spelling, by Brian M. Carr", optionType: JOptionPane.CLOSED_OPTION).createDialog("About k8spelling").show() })
                    }
                }
                panel {
                    borderLayout()
                    tabbedPane(constraints: BorderLayout.CENTER) {
                        panel(name: "One") {
                            widget(new WordStatusPanel())
                        }
                        panel(name: "Words") {
                            widget(new ListEditorPanel(Word, WordList, status))
                        }
                        panel(name: "Students") {
                            widget(new ListEditorPanel(Student, StudentList, status))
                        }
                    }
                    panel(constraints: BorderLayout.SOUTH) {
                        widget(status)
                    }
                }
            }
        }
    }
}
