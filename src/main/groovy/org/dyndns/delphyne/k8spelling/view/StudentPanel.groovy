package org.dyndns.delphyne.k8spelling.view

import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j

import java.awt.BorderLayout
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent

import javax.swing.Action
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.JTextField
import javax.swing.table.AbstractTableModel

import org.dyndns.delphyne.k8spelling.model.Student

@Slf4j
class StudentPanel {
    JPanel widget
    JTable studentTable
    JTextField inputField
    Action addStudentAction
    
    StudentPanel() {
        SwingBuilder sb = new SwingBuilder()
        
        addStudentAction = sb.action(name:"Add Student", 
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
        
        widget = sb.panel {
            borderLayout()
            scrollPane(constraints:BorderLayout.CENTER) {
                studentTable = table(model:new StudentTableModel(), autoCreateRowSorter:true, showGrid:true)
            }
            panel(constraints:BorderLayout.SOUTH, border:emptyBorder(3)) {
                borderLayout()
                inputField = textField(constraints:BorderLayout.CENTER, action:addStudentAction)
                label(constraints:BorderLayout.WEST, labelFor:inputField, text:"New Student:", displayedMnemonic:"S")
                button(constraints:BorderLayout.EAST, action:addStudentAction)
            }
        }
        
        inputField.requestFocusInWindow()
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