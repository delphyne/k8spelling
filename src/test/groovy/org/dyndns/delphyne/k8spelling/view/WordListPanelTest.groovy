package org.dyndns.delphyne.k8spelling.view

import org.dyndns.delphyne.k8spelling.GuiTestBase
import org.dyndns.delphyne.k8spelling.model.Word
import org.dyndns.delphyne.k8spelling.model.WordList

class WordListPanelTest extends GuiTestBase {
    static {
        WordList.withTransaction {
            def wl1 = new WordList(name:"First Grade", words: []).save()
            (1..99).each { wl1.addToWords(new Word(word:(it as String))) }
            wl1.save()
            new WordList(name:"Second Grade", words: [new Word(word:"Three"), new Word(word:"Four")]).save()
        }
    }
    
    static void main(String[] args) {
        new WordListPanelTest().buildGui(new WordListPanel())
    }
}
