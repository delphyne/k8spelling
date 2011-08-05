package org.dyndns.delphyne.k8spelling

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext;

class TestBase {
    static ApplicationContext appCtx = new ClassPathXmlApplicationContext('applicationContext.xml')
}
