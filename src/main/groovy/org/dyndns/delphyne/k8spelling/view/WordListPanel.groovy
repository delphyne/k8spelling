package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j

import java.awt.BorderLayout
import java.awt.Component
import java.awt.event.ActionEvent

import javax.swing.DefaultComboBoxModel
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JList
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JSplitPane

import org.dyndns.delphyne.k8spelling.model.WordList

@Slf4j
class WordListPanel implements GuiPanel {
    JPanel widget
    JButton defaultButton

    JComboBox listSelection
    
    JList left
    JList right

    WordListPanel() {
        SwingBuilder swing = new SwingBuilder()

        JOptionPane nameInput = new JOptionPane(
                null,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION
                )

        widget = swing.panel {
            borderLayout()
            panel(constraints: BorderLayout.NORTH, border: emptyBorder(3)) {
                borderLayout()
                listSelection = comboBox(
                        constraints: BorderLayout.CENTER,
                        action: action(closure:{ ActionEvent event ->
                            updateLists()
                        }),
                        model: new DefaultComboBoxModel(WordList.list() as Object[])
                        )
                button(
                        constraints: BorderLayout.EAST,
                        action: action(
                        name: "+",
                        closure: {
                            def newName = nameInput.showInputDialog(
                                    "Enter a name for the new list:",
                                    "New List")
                            if (newName) {
                                WordList newList
                                WordList.withTransaction {
                                    newList = new WordList(name: newName, words: []).save()
                                }
                                listSelection.model.addElement(newList)
                                listSelection.model.selectedItem = newList
                            }
                        })
                        )
            }
            panel(constraints: BorderLayout.CENTER) {
                borderLayout()
                panel(constraints: BorderLayout.CENTER) {
                    borderLayout()
                    splitPane(
                            constraints: BorderLayout.CENTER,
                            orientation: JSplitPane.HORIZONTAL_SPLIT,
                            resizeWeight: 0.5,
                            leftComponent: scrollPane {
                                left = list()
                            },
                            rightComponent: scrollPane {
                                right = list()
                            },
                            )
                }
                panel(constraints: BorderLayout.SOUTH) {
                    button(
                        action(
                            name: ">>",
                            closure:{
                            WordList.withTransaction {
                                def wl = listSelection.model.selectedItem
                                wl.words = []
                                wl.save()
                            }
                            updateLists()
                            })
                        )
                    button(text: ">")
                    button(text: "<")
                    button(text: "<<")
                }
            }
            panel(constraints: BorderLayout.SOUTH, border: emptyBorder(3)) {
                borderLayout()
                textField(constraints: BorderLayout.CENTER)
                label(constraints: BorderLayout.WEST, text: "New word:")
                button(constraints: BorderLayout.EAST, text: "Add Word")
            }
        }
        
        updateLists()
    }

    Component getDefaultFocus() {
    }
    
    void updateLists() {
        def leftValues = listSelection.model.selectedItem.words
        def allValues = WordList.default.words
        def rightValues = allValues - leftValues
        
        left.model = new DefaultComboBoxModel(leftValues as Object[])
        right.model = new DefaultComboBoxModel(rightValues as Object[])
    }
}