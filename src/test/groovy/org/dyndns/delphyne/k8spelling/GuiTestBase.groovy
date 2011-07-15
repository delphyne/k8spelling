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

    void buildGui(GuiPanel panel, StatusPanel status) {
        swing.edt {
            frame(id: "testWindow", title: this.class.simpleName, size: [640, 480], show: true, defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE) {
                borderLayout()
                widget(id: "testedPanel", constraints: BorderLayout.CENTER, panel)
                widget(id: "status", constraints: BorderLayout.SOUTH, status.widget)
            }
        }

        def defaultButton = panel.defaultButton
        if (defaultButton) {
            swing.testWindow.rootPane.defaultButton = defaultButton
        }
        
        if (panel.defaultFocus) {
            panel.defaultFocus.requestFocusInWindow()
        }

        status.message = "Ready"
    }
}
