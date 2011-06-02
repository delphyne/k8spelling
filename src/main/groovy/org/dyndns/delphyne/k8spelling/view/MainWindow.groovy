package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.awt.Color

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
                    menu(text: "File", mnemonic: 'F') {
                        menuItem(text: "Import", mnemonic: 'I', actionPerformed: {})
                        menuItem(text: "Export", mnemonic: 'E', actionPerformed: {})
                        menuItem(text: "Print", mnemonic: 'P', actionPerformed: {})
                        menuItem(text: "Quit", mnemonic: 'Q', , actionPerformed: { mainWindow.dispose() })
                    }
                    menu(text: "Help", mnemonic: 'H') {
                        menuItem(text: "About", mnemonic: 'A', actionPerformed: { optionPane(message: "k8spelling, by Brian M. Carr", optionType: JOptionPane.CLOSED_OPTION).createDialog("About k8spelling").show() })
                    }
                }
                panel {
                    borderLayout()
                    tabbedPane(id: "tabs", constraints: BorderLayout.CENTER) {
                        panel(name: "Status", border: emptyBorder(3)) {
                            borderLayout()
                            widget(id: "wordStatusPanel", constraints: BorderLayout.CENTER, new WordStatusPanel().widget)
                        }
                        panel(name: "Words", border: emptyBorder(3)) {
                            borderLayout()
                            widget(id: "wordListEditorPanel", constraints: BorderLayout.CENTER, new ListEditorPanel(Word, WordList, status).widget)
                        }
                        panel(name: "Students", border: emptyBorder(3)) {
                            borderLayout()
                            widget(id: "studentListEditorPanel", constraints: BorderLayout.CENTER, new ListEditorPanel(Student, StudentList, status).widget)
                        }
                    }
                    panel(constraints: BorderLayout.SOUTH, border: emptyBorder(3)) {
                        borderLayout()
                        widget(id: "statusPanel", constraints: BorderLayout.CENTER, status.widget)
                    }
                }
            }
        }
        status.message = "Ready"
    }
}
