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

class ListEditorPanel implements GuiPanel {
    JPanel widget
    Component defaultFocus
    JButton defaultButton

    private JComboBox listSelection
    private JList left
    private JList right

    private Class singleType
    private Class listType

    private SwingBuilder swing

    ListEditorPanel(Class singleType, Class listType, StatusPanel status) {
        this.singleType = singleType
        this.listType = listType

        swing = new SwingBuilder()

        JOptionPane nameInput = new JOptionPane(null, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION)

        widget = swing.panel {
            borderLayout()
            panel(constraints: BorderLayout.NORTH, border: emptyBorder(3)) {
                borderLayout()
                listSelection = comboBox(
                                                constraints: BorderLayout.CENTER,
                                                action: action(closure:{ ActionEvent event -> updateLists() }),
                                                model: new DefaultComboBoxModel(listType.list() as Object[])
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
                                                        def newList
                                                        listType.withTransaction {
                                                            newList = listType.newInstance()
                                                            newList.name = newName
                                                            newList.items = []
                                                            newList.save()
                                                        }
                                                        listSelection.model << newList
                                                        listSelection.model.selectedItem = newList
                                                    }
                                                    status.message = "Created new list, 'newName'"
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
                    button(action(name: ">", closure: {
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
                    button(action(name: "<", closure: {
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
                    button(action(name: "<<", closure: {
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
                                                        newItem.data = defaultFocus.text
                                                        newItem.save()
                                                        l.addToItems(newItem)
                                                        l.save()
                                                        status.message = "Created '$newItem' and added to '$l'"
                                                    }
                                                    updateLists()
                                                    left.ensureIndexIsVisible(jumpToIndex)
                                                    defaultFocus.selectAll()
                                                })
                defaultFocus = textField(constraints: BorderLayout.CENTER, action: addItem)
                label(constraints: BorderLayout.WEST, text: "New ${singleType.simpleName.toLowerCase()}:", labelFor: defaultFocus, displayedMnemonic: singleType.simpleName[0])
                defaultButton = button(constraints: BorderLayout.EAST, action: addItem)
            }
        }

        updateLists()
    }

    void updateLists() {
        def leftValues = listSelection.model.selectedItem.items
        def allValues = listType.default.items
        def rightValues = allValues - leftValues

        left.listData = leftValues as Object[]
        right.listData = rightValues.sort() as Object[]
    }
}
