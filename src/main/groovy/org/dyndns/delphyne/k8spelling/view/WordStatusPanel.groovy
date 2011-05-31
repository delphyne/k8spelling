package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder

import java.awt.BorderLayout
import java.awt.Component

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.table.AbstractTableModel
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer
import javax.swing.table.TableColumn

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList
import org.dyndns.delphyne.k8spelling.model.WordState;
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

            scrollPane(constraints: BorderLayout.CENTER) { table(id: 'mappingTable') }
        }

        swing.mappingTable.model = new WordStatusDataModel(studentListPicker.model.selectedItem, wordListPicker.model.selectedItem)
        swing.mappingTable.columnModel.columns.each { TableColumn it -> it.headerRenderer = new VerticalTableHeaderCellRenderer() }
        swing.mappingTable.columnModel.columns.eachWithIndex { TableColumn col, int i ->
            if (i != 0) {
                col.cellEditor = new DefaultCellEditor(swing.comboBox(items: [null]+ (WordState.values() as List)))
                col.cellRenderer = new WordStatusCellRenderer()
            }
        }
    }
}

class WordStatusDataModel extends AbstractTableModel {
    StudentList students
    WordList words

    WordStatusDataModel(StudentList sList, WordList wList) {
        students = sList
        words = wList
    }

    int getColumnCount() {
        students.items.size() + 1
    }

    int getRowCount() {
        words.items.size()
    }

    public Object getValueAt(int row, int col) {
        if (col == 0) {
            words.items[row]
        } else {
            WordStatus.findByWordAndStudent(words.items[row], students.items[col - 1])
        }
    }

    @Override
    String getColumnName(int col) {
        col == 0 ? "" : students.items[col - 1]
    }

    @Override
    boolean isCellEditable(int row, int col) {
        col != 0
    }

    @Override
    void setValueAt(Object value, int row, int col) {
    }
}

class WordStatusCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
    int row, int col) {
        if (value) {
            toolTipText = "<html>Assigned: ${value.assignedDate?.format("MM/dd/yyyy") ?: ""}<br>Mastered: ${value.masteredDate?.format("MM/dd/yyyy") ?: ""}</html>"
        }
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col)
    }
}