package org.dyndns.delphyne.k8spelling

import org.dyndns.delphyne.k8spelling.view.MainWindow
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

import groovy.util.logging.Log4j

@Log4j
class Main {
    static void main(String[] args) {
        log.info 'Starting k8spelling'
        ApplicationContext appCtx = new ClassPathXmlApplicationContext('applicationContext.xml')
        new MainWindow()
        addShutdownHook { log.info 'Application shutdown' }
    }
}
