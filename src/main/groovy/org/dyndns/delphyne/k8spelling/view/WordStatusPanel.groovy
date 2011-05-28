package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.awt.Component

import javax.swing.DefaultComboBoxModel
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableCellRenderer
import javax.swing.table.TableModel

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList
import org.dyndns.delphyne.k8spelling.model.WordStatus

import darrylbu.renderer.VerticalTableHeaderCellRenderer

class WordStatusPanel implements GuiPanel {
    JPanel widget
    Component defaultFocus
    JButton defaultButton

    private StatusPanel status
    private SwingBuilder swing

    private JComboBox studentListPicker
    private JComboBox wordListPicker

    private JTable mapping
    
    private TableCellRenderer renderer

    WordStatusPanel(StatusPanel status) {
        swing = new SwingBuilder()
        
        renderer = new VerticalTableHeaderCellRenderer()

        studentListPicker = swing.comboBox(model: new DefaultComboBoxModel(StudentList.list() as Object[]))
        wordListPicker = swing.comboBox(model: new DefaultComboBoxModel(WordList.list() as Object[]))

        widget = swing.panel {
            borderLayout()

            widget = panel(constraints: BorderLayout.NORTH) {
                label(text: "Students:", labelFor: studentListPicker)
                widget(studentListPicker)
                label(text: "Words:", labelFor: wordListPicker)
                widget(wordListPicker)
            }

            scrollPane(constraints: BorderLayout.CENTER) {
                table(id: 'mappingTable') {
                    tableModel {
                        propertyColumn(editable:false, propertyName: 'word', headerRenderer: renderer)
                        studentListPicker.model.selectedItem.items.each { student ->
                            propertyColumn(propertyName: 'foo', header: student, headerRenderer: renderer)
//                            closureColumn(
//                                header: student, 
//                                headerRenderer: renderer,
//                                read: {r -> r})
                        }
                    }
                }
            }
        }
        
        def foo = swing.mappingTable.model.rowsModel.value = [
            [word: "who", foo: "mastered"]
            ]
        println foo
    }
}