package org.dyndns.delphyne.k8spelling.model

import groovy.util.logging.Slf4j

import org.dyndns.delphyne.k8spelling.TestBase
import org.junit.BeforeClass
import org.junit.Test

@Slf4j
class WordListTest extends TestBase {
    static words = ["who", "what", "when", "where", "why"]
    
    @BeforeClass
    static void setup() {
        Word.withTransaction {
            words.each {
                new Word(word:it).save()
            }
        }
        
        WordList.withTransaction {
            new WordList(name:"alphabetical", words:Word.list().sort { a, b -> a.word <=> b.word }).save()
            new WordList(name:"size", words:Word.list().sort { it.word.size() }).save()
        }
    }
    
    @Test
    void testListsRetrievedAsInput() {
        assert WordList.findByName("alphabetical").words*.word == words.sort()
        assert WordList.findByName("size").words*.word == words.sort { it.size() }
    }
    
    @Test
    void testModifications() {
        WordList.withTransaction {
            new WordList(name:"myTest", words:[
                    new Word(word:"one"),
                    new Word(word:"two")
                ]).save()
        }
        assert WordList.findByName("myTest").words.find { it -> it.word == "one" }
        
        WordList.withTransaction {
            WordList.findByName("myTest").removeFromWords(Word.findByWord("one")).save()
        }
                
        assert !WordList.findByName("myTest").words.find { it -> it.word == "one" }
    }
}
