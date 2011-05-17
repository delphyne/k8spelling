package org.dyndns.delphyne.k8spelling

import groovy.transform.WithReadLock;
import groovy.util.logging.Slf4j

@Slf4j
class Config {
    private ConfigObject config
    private ConfigSlurper slurper
    private long lastModified
    
    private Config() {
        this("production")
    }
    
    private Config(String env) {
        slurper = new ConfigSlurper(env)
        URL configUrl = Thread.currentThread().getContextClassLoader().getResource("k8spelling.config")
        loadConfig(configUrl)
    }
    
    @WithReadLock
    private void loadConfig(URL configUrl = config.configFile) {
        log.info "Loading ${slurper.environment} configuration data from ${configUrl}"
        this.config = slurper.parse(configUrl)
        if (realFile) {
            lastModified = new File(config.configFile.toURI()).lastModified()
            log.info "Configuration last updated on ${new Date(lastModified)}"
        }
    }
    
    /**
     * @return true if the file is an actual file on disk
     */
    private boolean isRealFile() {
        config.configFile.protocol == "file"
    }
    
    /**
     * @return true if the file has been modified since the config was last loaded
     */
    private boolean isConfigUpdated() {
        if (realFile) {
            lastModified < new File(config.configFile.toURI()).lastModified()
        }
    }
    
    /**
     * Poor man's delegation
     */
    def propertyMissing(String name) {
        if (configUpdated) {
            loadConfig()
        }
        
        config[name]
    }
}
