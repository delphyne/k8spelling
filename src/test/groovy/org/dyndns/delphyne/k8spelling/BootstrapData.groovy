package org.dyndns.delphyne.k8spelling

import org.dyndns.delphyne.k8spelling.model.Student
import org.dyndns.delphyne.k8spelling.model.Word
import org.springframework.core.io.Resource

class BootstrapData extends TestBase {
    static void main(String[] args) {
        ['students.txt': Student, 'words.txt': Word].each { String file, Class clazz ->
            clazz.withTransaction {
                Resource r = appCtx.getResource(file)
                r.URL.withInputStream { stream ->
                    stream.eachLine { item ->
                        clazz.newInstance(data: item).save()
                    }    
                }
            }
        }
    }
}
