/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alert.gateway.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author quanpv2
 */
public final class Configurations {

    private Properties properties = null;
    private static Configurations instance = null;
    private static Logger LOGGER;

    private String serverAddress;
    private String port;
    private String masterKeyAES;
    private String urlWarning;
    private String urlState;
    private String urlStateHa;
    private String urlWarningHa;

    public Properties getProperties() {
        return properties;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public String getPort() {
        return port;
    }

    public String getMasterKeyAES() {
        return masterKeyAES;
    }

    public String getUrlWarning() {
        return urlWarning;
    }

    public String getUrlWarningHa() {
        return urlWarningHa;
    }

    public String getUrlState() {
        return urlState;
    }

    public void setUrlState(String urlState) {
        this.urlState = urlState;
    }

    public String getUrlStateHa() {
        return urlStateHa;
    }

    public void setUrlStateHa(String urlStateHa) {
        this.urlStateHa = urlStateHa;
    }

    /**
     * Private constructor
     */
    private Configurations() {
        Properties sysProperties = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream("../etc/mtr_config.conf");
            sysProperties.load(is);
            serverAddress = sysProperties.getProperty("serverAddress", "");
            port = sysProperties.getProperty("serverPort", "");
            masterKeyAES = sysProperties.getProperty("masterKeyAES").trim();
            urlWarning = sysProperties.getProperty("urlWarning").trim();
            urlWarningHa = sysProperties.getProperty("urlWarning_ha").trim();
            urlState = sysProperties.getProperty("urlState").trim();
            urlStateHa = sysProperties.getProperty("urlStateHa").trim();
        } catch (IOException | NumberFormatException ex) {
            SmartLog.getInstance().logError(LOGGER, ex);

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    SmartLog.getInstance().logError(LOGGER, ex);
                }
            }
        }
    }

    /**
     * Creates the instance is synchronized to avoid multithreads problems
     */
    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new Configurations();
        }
    }

    /**
     * Get the properties instance. Uses singleton pattern
     *
     * @return
     */
    public static Configurations getInstance() {
        // Uses singleton pattern to guarantee the creation of only one instance
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

//     /** Get a property of the property file */
//     public String getProperty(String key){
//         String result = null;
//         if(key !=null && !key.trim().isEmpty()){
//             result = this.properties.getProperty(key);
//         }
//         return result;
//     }
    /**
     * Override the clone method to ensure the "unique instance" requeriment of
     * this class
     *
     * @return
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
