package org.dyndns.delphyne.k8spelling

import groovy.swing.SwingBuilder

import java.awt.Component

import javax.swing.JFrame

class GuiTestBase extends TestBase {
    static SwingBuilder sb = new SwingBuilder()
    
    static void buildGui(List<Component> widgets) {
        sb.edt {
            frame(title:'test', size:[640,480], show:true, defaultCloseOperation:JFrame.DISPOSE_ON_CLOSE) {
                widgets.each {
                    widget(it)
                }
            }
        }
    }
    
    static void buildGui(Component widget) {
        buildGui([widget])
    }
}
