package org.dyndns.delphyne.k8spelling.model

import org.dyndns.delphyne.k8spelling.Config
import org.junit.BeforeClass
import org.junit.Test


class WordTest {
    static List words = [
            "because",
            "of",
            "there",
            "where",
            "are",
            "they",
            "then",
            "when",
            "were",
            "here",
            "could",
            "would",
            "should",
            "why",
            "our",
            "said",
            "put",
            "have",
            "some",
            "come"
        ]
    
    static Config config
    
    @BeforeClass
    static void classSetup() {
        config = new Config("test")
        Word.withTransaction {
            words.each {
                new Word(word:it).save()
            }
        }
    }
    
    @Test
    void testAllWordsExist() {
        words.each {
            assert it == Word.findByWord(it).toString()
        }
    }
}
