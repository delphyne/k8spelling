package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j

import java.awt.BorderLayout
import java.awt.Component

import javax.swing.Action
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.JTextField
import javax.swing.table.AbstractTableModel

import org.dyndns.delphyne.k8spelling.model.Student

@Slf4j
class StudentPanel implements GuiPanel {
    JPanel widget
    JTable studentTable
    JTextField inputField
    JButton defaultButton
    Action addStudentAction
    
    StudentPanel() {
        SwingBuilder swing = new SwingBuilder()
        
        addStudentAction = swing.action(name:"Add Student", 
                defaultButton:true, 
                mnemonic:"A", 
                closure:{
                    Student.withTransaction {
                        Student newStudent = new Student(name:inputField.text, active:true)
                        log.debug "Adding '$newStudent' to Students"
                        try {
                            newStudent.save()
                            studentTable.model = new StudentTableModel()
                        } catch (Exception e) {
                            log.error "Failed to persist $newStudent", e
                        }
                        inputField.selectAll()
                        inputField.requestFocus()
                    }
            })
        
        widget = swing.panel {
            borderLayout()
            scrollPane(constraints:BorderLayout.CENTER) {
                studentTable = table(model:new StudentTableModel(), autoCreateRowSorter:true, showGrid:true)
            }
            panel(constraints:BorderLayout.SOUTH, border:emptyBorder(3)) {
                borderLayout()
                inputField = textField(constraints:BorderLayout.CENTER, action:addStudentAction)
                label(constraints:BorderLayout.WEST, labelFor:inputField, text:"New Student:", displayedMnemonic:"S")
                defaultButton = button(constraints:BorderLayout.EAST, action:addStudentAction)
            }
        }
        
        inputField.requestFocusInWindow()
    }
    
    Component getDefaultFocus() {
        inputField
    }
}

class StudentTableModel extends AbstractTableModel {
    List<String> properties = ["name", "active"] 
    List<Student> students = Student.list()
    
    int getColumnCount() { properties.size() }
    String getColumnName(int col) { properties[col].capitalize() }
    Class<?> getColumnClass(int col) { Student.metaClass.properties.find { it.name == properties[col] }.type }
    int getRowCount() { students.size() }
    boolean isCellEditable(int row, int col) { true }
    
    Object getValueAt(int row, int col) {
        Student.withTransaction { 
            students[row][properties[col]]
        } 
    }
    
    void setValueAt(Object value, int row, int col) {
        
    }
}