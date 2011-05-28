package org.dyndns.delphyne.k8spelling.view

import org.dyndns.delphyne.k8spelling.GuiTestBase
import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.StudentList
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList

class WordStatusPanelTest extends GuiTestBase {
    static {
        StudentList.withTransaction {
            new StudentList("1st grade", [
                new Student("Brian"),
                new Student("Kate"),
                new Student("Sam"),
                new Student("Jenni"),
                new Student("Geoff"),
                new Student("Margaret"),
                new Student("Christen"),
                new Student("Carolyn"),
                new Student("Jerry"),
                new Student("Jeannie")
            ]).save()
            new StudentList("2nd grade", [
                new Student(data:"Angie"),
                new Student(data:"Joy")
            ]).save()
        }

        WordList.withTransaction {
            new WordList("1st grade", [
                new Word("who"),
                new Word("what"),
                new Word("when")
            ]).save()
            new WordList("2nd grade", [
                new Word("where"),
                new Word("why"),
                new Word("how")
            ]).save()
        }
    }

    static void main(String[] args) {
        StatusPanel status = new StatusPanel()
        new WordStatusPanelTest().buildGui(new WordStatusPanel(status), status)
    }
}
