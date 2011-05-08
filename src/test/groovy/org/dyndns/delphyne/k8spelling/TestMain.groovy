package org.dyndns.delphyne.k8spelling

import javax.persistence.Persistence
import javax.persistence.Query

import org.dyndns.delphyne.k8spelling.model.Student
import org.junit.Test

class TestMain {
    @Test
    void testSomeStuff() {
        System.properties["derby.system.home"] = new File(".").canonicalPath 
        
        def factory = Persistence.createEntityManagerFactory("default")
        def em = factory.createEntityManager()

//        em.getTransaction().begin()
//        ["Brian", "Kate", "Sam"].each {
//            em.persist(new Student(name: it))
//            em.flush()
//        }
//        em.getTransaction().commit()
//        
//        Query q = em.createQuery("select s from Student s")
//        q.resultList.each {
//            println it.dump()
//        }

        em.close()
        factory.close()
    }
}
