package org.dyndns.delphyne.k8spelling.view

import org.dyndns.delphyne.k8spelling.GuiTestBase
import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList

class WordStatusPanelTest extends GuiTestBase {
    static {
        StudentList.withTransaction {
            new StudentList(name:"1st grade", items: [new Student(data:"Brian"), new Student(data:"Kate"), new Student("Sam")] as SortedSet).save()
            new StudentList(name:"2nd grade", items: [new Student(data:"Angie"), new Student(data:"Joy")] as SortedSet).save()
        }
        
        WordList.withTransaction {
            new WordList(name:"1st grade", items: [new Word(data: "who"), new Word(data: "what"), new Word(data: "when")] as SortedSet).save()
            new WordList(name:"2nd grade", items: [new Word(data: "where"), new Word(data: "why"), new Word(data: "how")] as SortedSet).save()
        }
    }
    
    static void main(String[] args) {
        StatusPanel status = new StatusPanel()
        new WordStatusPanelTest().buildGui(new WordStatusPanel(status), status)
    }
}
