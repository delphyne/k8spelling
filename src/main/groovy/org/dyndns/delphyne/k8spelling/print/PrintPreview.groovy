package org.dyndns.delphyne.k8spelling.print

import java.awt.BorderLayout

import javax.swing.JFrame

import groovy.beans.Bindable
import groovy.swing.SwingBuilder
import groovy.util.logging.Slf4j

@Slf4j
class PrintPreview extends JFrame {
    SwingBuilder swing = new SwingBuilder()
    
    def pageCache = [:]
    
    PrintPreview() {
        super('Print Preview')
        
        swing.build {
            panel {
                borderLayout()
                panel(constraints: BorderLayout.CENTER) {
                    panel(size: [640,480])
                }
                panel(constraints: BorderLayout.SOUTH) {
                    checkBox(id: 'includeTeacherPage', action: action(name: 'Include Teacher Page:', closure: {
                            log.info 'clicked'
                        }))
                    spinner(id: 'currentPageNum', value: 0, action: action(closure: { log.info swing.currentPageNum.value }))
                    button(action: action(name: 'Print', closure: { log.info 'print'}))
                }
            }
        }
        
        show()
    }
}
