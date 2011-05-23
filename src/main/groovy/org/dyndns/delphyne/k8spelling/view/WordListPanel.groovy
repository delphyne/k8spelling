package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j

import java.awt.BorderLayout
import java.awt.Component
import java.awt.event.ActionEvent

import javax.swing.Action
import javax.swing.DefaultComboBoxModel
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JList
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JSplitPane

import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList

@Slf4j
class WordListPanel implements GuiPanel {
    JPanel widget
    JButton defaultButton
    Component defaultFocus

    private JComboBox listSelection
    private JList left
    private JList right

    private SwingBuilder swing

    WordListPanel() {
        swing = new SwingBuilder()

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
                                                action: action(closure:{ ActionEvent event -> updateLists() }),
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
                                                        listSelection.model << newList
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
                                                    leftComponent: scrollPane { left = list() },
                                                    rightComponent: scrollPane { right = list() },
                                                    )
                }
                panel(constraints: BorderLayout.SOUTH) {
                    button(action(name: ">>", closure: {
                        def wl = listSelection.model.selectedItem
                        WordList.withTransaction { wl.words = []}
                        updateLists()
                    })
                    )
                    button(action(name: ">", closure: {
                        def wl = listSelection.model.selectedItem
                        def toBeRemoved = left.selectedValues
                        WordList.withTransaction {
                            wl.words = wl.words - toBeRemoved
                            wl.save()
                        }
                        updateLists()
                    }))
                    button(action(name: "<", closure: {
                        def wl = listSelection.model.selectedItem
                        def jumpToIndex = wl.words.size()
                        def toBeAdded = right.selectedValues
                        WordList.withTransaction {
                            wl.words.addAll(toBeAdded)
                            wl.save()
                        }
                        updateLists()
                        left.ensureIndexIsVisible(jumpToIndex)
                    }))
                    button(action(name: "<<", closure: {
                        def wl = listSelection.model.selectedItem
                        def jumpToIndex = wl.words.size()
                        def toBeAdded = WordList.default.words - wl.words
                        WordList.withTransaction {
                            wl.words.addAll(toBeAdded)
                            wl.save()
                        }
                        updateLists()
                        left.ensureIndexIsVisible(jumpToIndex)
                    }))
                }
            }
            panel(constraints: BorderLayout.SOUTH, border: emptyBorder(3)) {
                borderLayout()
                Action addWord = action(name: "Add Word",
                                                mnemonic: "A",
                                                closure: {
                                                    def wl = listSelection.model.selectedItem
                                                    def jumpToIndex = wl.words.size()
                                                    Word.withTransaction {
                                                        Word newWord = new Word(word: defaultFocus.text)
                                                        log.debug "Adding '$newWord' to Word"
                                                        try {
                                                            newWord.save()
                                                        } catch (Exception e) {
                                                            log.error "Failed to persist $newWord", e
                                                        }
                                                        wl.addToWords(newWord)
                                                    }
                                                    updateLists()
                                                    left.ensureIndexIsVisible(jumpToIndex)
                                                    defaultFocus.selectAll()
                                                })
                defaultFocus = textField(constraints: BorderLayout.CENTER, action: addWord)
                label(constraints: BorderLayout.WEST, text: "New word:", labelFor: defaultFocus, displayedMnemonic: "W")
                defaultButton = button(constraints: BorderLayout.EAST, action: addWord)
            }
        }

        updateLists()
    }

    void updateLists() {
        def leftValues = listSelection.model.selectedItem.words
        def allValues = WordList.default.words
        def rightValues = allValues - leftValues

        left.listData = leftValues as Object[]
        right.listData = rightValues.sort() as Object[]
    }
}