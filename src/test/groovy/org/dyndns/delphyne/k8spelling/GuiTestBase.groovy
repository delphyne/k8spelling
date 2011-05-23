package org.dyndns.delphyne.k8spelling

import groovy.swing.SwingBuilder

import java.awt.BorderLayout

import javax.swing.JFrame

import org.dyndns.delphyne.k8spelling.view.GuiPanel
import org.dyndns.delphyne.k8spelling.view.StatusPanel
import org.junit.BeforeClass
import org.uispec4j.UISpec4J

class GuiTestBase extends TestBase {
    static SwingBuilder swing = new SwingBuilder()

    @BeforeClass
    static void guiTestBaseClassSetup() {
        UISpec4J.init()
    }
    
    JFrame frame
        
    void buildGui(GuiPanel panel, StatusPanel status) {
        swing.edt {
            frame = frame(title:this.class.simpleName, defaultCloseOperation:JFrame.DISPOSE_ON_CLOSE) {
                borderLayout()
            }
            frame.contentPane.add(panel.widget, BorderLayout.CENTER)
            frame.contentPane.add(status.widget, BorderLayout.SOUTH)
            
            frame.pack()
            frame.show()
            
            frame.rootPane.defaultButton = panel.defaultButton
            panel.defaultFocus.requestFocusInWindow()
        }
        
        status.message = "Ready"
    }
}
