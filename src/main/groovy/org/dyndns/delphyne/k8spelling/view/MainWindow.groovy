package org.dyndns.delphyne.k8spelling.view

import java.awt.BorderLayout

import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList

import groovy.swing.SwingBuilder

class MainWindow {
    MainWindow() {
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
                            widget(id: "wordStatusPanel", constraints: BorderLayout.CENTER, new WordStatusPanel())
                        }
                        panel(name: "Words", border: emptyBorder(3)) {
                            borderLayout()
                            widget(id: "wordListEditorPanel", constraints: BorderLayout.CENTER, new ListEditorPanel(Word, WordList, status))
                        }
                        panel(name: "Students", border: emptyBorder(3)) {
                            borderLayout()
                            widget(id: "studentListEditorPanel", constraints: BorderLayout.CENTER, new ListEditorPanel(Student, StudentList, status))
                        }
                    }
                    tabs.addChangeListener([stateChanged: { ChangeEvent e ->
                            /*
                             * Even though we added WordStatusPanel and ListEditorPanel directly to the JTabbedPane,
                             * the widget() call in the SwingBuilder dsl wraps them in another panel.  as such,
                             * to get at the actual component, we need to look at the first item in the components
                             * array of that panel.
                             */
                            e.source.visComp.components[0].onFocus()
                        }] as ChangeListener)

                    widget(id: "statusPanel", constraints: BorderLayout.SOUTH, status)
                }
            }
        }
        status.message = "Ready"
    }
}
