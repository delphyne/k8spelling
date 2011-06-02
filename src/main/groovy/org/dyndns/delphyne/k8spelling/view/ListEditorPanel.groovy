package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder

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

class ListEditorPanel extends JPanel implements GuiPanel {
    JPanel widget
    Component defaultFocus

    private Class singleType
    private Class listType

    private SwingBuilder swing

    private toBeDisabled = []

    ListEditorPanel(Class singleType, Class listType, StatusPanel status) {
        super(new BorderLayout())

        this.singleType = singleType
        this.listType = listType

        swing = new SwingBuilder()

        swing.optionPane(id: "nameInput", messageType: JOptionPane.QUESTION_MESSAGE, optionType: JOptionPane.OK_CANCEL_OPTION)

        swing.panel(id: "listEditorPanel", constraints: BorderLayout.CENTER) {
            borderLayout()
            panel(constraints: BorderLayout.NORTH, border: emptyBorder(3)) {
                borderLayout()
                comboBox(
                                                id: "listSelection",
                                                constraints: BorderLayout.CENTER,
                                                action: action(closure:{ ActionEvent event -> updateLists() }),
                                                model: new DefaultComboBoxModel(listType.list() as Object[])
                                                )
                button(
                                                id: "addListButton",
                                                constraints: BorderLayout.EAST,
                                                action: action(
                                                name: "+",
                                                closure: {
                                                    def newName = nameInput.showInputDialog(
                                                                                    "Enter a name for the new list:",
                                                                                    "New List")
                                                    if (newName) {
                                                        def newList
                                                        listType.withTransaction {
                                                            newList = listType.newInstance()
                                                            newList.name = newName
                                                            newList.items = []
                                                            newList.save()
                                                        }
                                                        listSelection.model << newList
                                                        listSelection.model.selectedItem = newList
                                                        status.message = "Created new list, 'newName'"
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
                                                    leftComponent: scrollPane { list(id: "left") },
                                                    rightComponent: scrollPane { list(id: "right") },
                                                    )
                }
                panel(constraints: BorderLayout.SOUTH) {
                    button(id: "removeAll",
                                                    action(name: ">>", closure: {
                                                        def l = listSelection.model.selectedItem
                                                        l.refresh()
                                                        listType.withTransaction {
                                                            l.items = []
                                                            l.save()
                                                            status.message = "Removed all items from '$l'"
                                                        }
                                                        updateLists()
                                                    })
                                                    )
                    button(id: "removeOne",
                                                    action(name: ">", closure: {
                                                        def l = listSelection.model.selectedItem
                                                        l.refresh()
                                                        def toBeRemoved = left.selectedValues
                                                        listType.withTransaction {
                                                            toBeRemoved.each { l.removeFromItems(it) }
                                                            l.save()
                                                            status.message = "Removed $toBeRemoved from '$l'"
                                                        }
                                                        updateLists()
                                                    }))
                    button(id: "addOne",
                                                    action(name: "<", closure: {
                                                        def l = listSelection.model.selectedItem
                                                        l.refresh()
                                                        def jumpToIndex = l.items.size()
                                                        def toBeAdded = right.selectedValues
                                                        listType.withTransaction {
                                                            l.items.addAll(toBeAdded)
                                                            l.save()
                                                            status.message = "Added $toBeAdded to '$l'"
                                                        }
                                                        updateLists()
                                                        left.ensureIndexIsVisible(jumpToIndex)
                                                    }))
                    button(
                                                    id: "addAll",
                                                    action(name: "<<", closure: {
                                                        def l = listSelection.model.selectedItem
                                                        l.refresh()
                                                        def jumpToIndex = l.items.size()
                                                        def toBeAdded = listType.default.items - l.items
                                                        listType.withTransaction {
                                                            l.items.addAll(toBeAdded)
                                                            l.save()
                                                            status.message = "Added $toBeAdded to '$l'"
                                                        }
                                                        updateLists()
                                                        left.ensureIndexIsVisible(jumpToIndex)
                                                    }))
                }
            }
            panel(constraints: BorderLayout.SOUTH, border: emptyBorder(3)) {
                borderLayout()
                Action addItem = action(name: "Add ${singleType.simpleName}".toString(),
                                                mnemonic: "A",
                                                closure: {
                                                    def l = listSelection.model.selectedItem
                                                    l.refresh()
                                                    def jumpToIndex = l.items.size()
                                                    singleType.withTransaction {
                                                        def newItem = singleType.newInstance()
                                                        newItem.data = addItemText.text
                                                        newItem.save()
                                                        l.addToItems(newItem)
                                                        l.save()
                                                        status.message = "Created '$newItem' and added to '$l'"
                                                    }
                                                    updateLists()
                                                    left.ensureIndexIsVisible(jumpToIndex)
                                                    addItemText.selectAll()
                                                })
                textField(id: "addItemText", constraints: BorderLayout.CENTER, action: addItem)
                label(id: "addItemLabel", constraints: BorderLayout.WEST, text: "New ${singleType.simpleName.toLowerCase()}:", labelFor: addItemText, displayedMnemonic: singleType.simpleName[0])
                button(id: "addItemButton", constraints: BorderLayout.EAST, action: addItem)
            }
        }

        widget = swing.listEditorPanel
        defaultFocus = swing.addItemText

        swing.with {
            toBeDisabled << addItemText
            toBeDisabled << addItemButton
            toBeDisabled << addItemLabel
            toBeDisabled << addOne
            toBeDisabled << addAll
            toBeDisabled << removeOne
            toBeDisabled << removeAll
            toBeDisabled << listSelection
        }

        updateLists()

        this.add(widget)
    }

    void updateLists() {
        def list = swing.listSelection.model.selectedItem
        def leftValues = list?.items
        def allValues = listType.default.items
        def rightValues = allValues - leftValues

        if (list) {
            swing.left.listData = leftValues as Object[]
            toBeDisabled.each { it.enabled = true }
            defaultFocus.requestFocus()
        } else {
            toBeDisabled.each { it.enabled = false }
        }
        swing.right.listData = rightValues.sort() as Object[]
    }

    JButton getDefaultButton() {
        if (swing.listSelection.model.selectedItem) {
            swing.addListButton
        } else {
            swing.addItemButton
        }
    }
}
