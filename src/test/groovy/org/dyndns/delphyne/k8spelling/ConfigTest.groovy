package org.dyndns.delphyne.k8spelling

import groovy.util.logging.Slf4j

import org.junit.Test

@Slf4j
class ConfigTest {
    @Test
    void testConfigDelegation() {
        Config config = new Config()
        assert !config.jdbc.url.contains("mem")
        
        config = new Config("test")
        assert config.jdbc.url.contains("mem")
    }
    
    @Test
    void testConfigReloads() {
        File tempFile = File.createTempFile("ConfigReloadTest", ".config")
        
        tempFile.withWriter {
            it << '''
                my.value = 1
            '''
        }
        
        Config config = new Config()
        config.config.configFile = tempFile.toURL()
        assert config.my.value == 1
        
        tempFile.withWriter {
            it << '''
                my.value = 2
            '''
        }
        
        Thread.sleep(1500)
        assert config.my.value == 2
    }
}
