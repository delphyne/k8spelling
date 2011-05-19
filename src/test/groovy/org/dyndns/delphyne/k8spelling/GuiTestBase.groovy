package org.dyndns.delphyne.k8spelling

import groovy.swing.SwingBuilder

import java.awt.Component

import javax.swing.JFrame

import org.junit.BeforeClass
import org.uispec4j.UISpec4J

class GuiTestBase extends TestBase {
    static SwingBuilder swing = new SwingBuilder()

    @BeforeClass
    static void guiTestBaseClassSetup() {
        UISpec4J.init()
    }
        
    void buildGui(List<Component> widgets) {
        swing.edt {
            frame(title:this.class.simpleName, pack:true, show:true, defaultCloseOperation:JFrame.DISPOSE_ON_CLOSE) {
                widgets.each { widget(it) }
            }
        }
    }
    
    void buildGui(Component widget) {
        buildGui([widget])
    }
}
