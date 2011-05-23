package org.dyndns.delphyne.k8spelling.view

import org.dyndns.delphyne.k8spelling.GuiTestBase
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList

class ListEditorPanelTest extends GuiTestBase {
    
    static {
        Word.withTransaction {
            ["foo","bar","baz"].each { new Word(data:it).save() }
        }
        
        WordList.withTransaction {
            new WordList(name:"one").save()
            def wl = new WordList(name:"two")
            def words = []
            5.times {
                words << new Word(data: it as String).save()
            }
            wl.items = words
            wl.save()
        }
    }
    
    static void main(String[] args) {
        StatusPanel status = new StatusPanel()
        new ListEditorPanelTest().buildGui(new ListEditorPanel(Word, WordList, status), status)
    }
}
