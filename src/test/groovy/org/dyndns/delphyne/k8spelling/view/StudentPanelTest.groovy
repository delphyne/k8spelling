package org.dyndns.delphyne.k8spelling.view

import groovy.util.logging.Slf4j

import org.dyndns.delphyne.k8spelling.GuiTestBase
import org.dyndns.delphyne.k8spelling.model.Student
import org.junit.BeforeClass
import org.junit.Test
import org.uispec4j.Panel
import org.uispec4j.Table
import org.uispec4j.UISpec4J

@Slf4j
class StudentPanelTest extends GuiTestBase {
    static {
        Student.withTransaction {
            new Student(name:"Brian", active:false).save()
            new Student(name:"Kate", active:false).save()
            new Student(name:"Sam", active:true).save()
            new Student(name:"Mos", active:true).save()
        }
    }
    
    @Test
    void testStudentPanel() {
        Panel panel = new Panel(new StudentPanel().widget)
        Table table = panel.getTable()
        assert table.contentEquals([
                ["Brian", false],
                ["Kate", false],
                ["Sam", true],
                ["Mos", true]
            ] as Object[][]).isTrue()
    }
    
    public static void main(String[] args) {
        new StudentPanelTest().buildGui(new StudentPanel())
    }
}
