/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: SafeOneConnectionPool.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.database;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * Database connection pool. Its services are configuration, managing database
 * connection pool.
 *
 * Suite for using on all VtrackingServer instance internal business and
 * configuration loading.
 *
 * @author Nguyen The Nam <namnt24@viettel.com.vn>
 * @version $Revision: 2813 $
 */
public class SafeOneConnectionPool {

    // Init Log4J
    private static final Logger LOGGER = Logger.getLogger(SafeOneConnectionPool.class.getSimpleName());
    // Init Instance
    private static final SafeOneConnectionPool instance = new SafeOneConnectionPool();
    private ComboPooledDataSource cpds;
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    /**
     * Singleton instance for MTrackingConfig. Remember to call one (and
     * only one) initC3P0 method before using its getConnection() method
     *
     * @return Singleton instance for MTrackingConfig
     */
    public static SafeOneConnectionPool getInstance() {
        return instance;
    }

    /**
     * Prevent outsider from calling construction method.
     */
    private SafeOneConnectionPool() {
    }
    
    public void initC3P0() throws PropertyVetoException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {

        if (initialized.getAndSet(false)) {
            LOGGER.warn("Received initC3P0 request, but C3P0 are already initialized");
            return;
        }

        cpds = new ComboPooledDataSource();

        Properties sysProperties = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream("../etc/db_cfg.properties");

            sysProperties.load(is);
            Set<String> keys = sysProperties.stringPropertyNames();
            
            for (String key : keys) {
                if (key.startsWith("c3p0.")) {
                    String value = sysProperties.getProperty(key);
//                    String decryptValue = Crypto.decrypt(value);
                    String decryptValue = value;
                    BeanUtils.setProperty(cpds, key.substring("c3p0".length()), decryptValue);
//                    BeanUtils.setProperty(cpds, key.substring(PropertyName.C3P0_PREFIX.length()), value);
                }
            }

            for (String key : keys) {
                if (key.startsWith("c3p0Ext.")) {
                    BeanUtils.setProperty(cpds, key.substring("c3p0Ext.".length()), sysProperties.getProperty(key));
                }
            }
            
            LOGGER.info("C3P0 loaded...");

            // Check database driver            
            Class.forName(cpds.getDriverClass());

        } catch (IOException e) {
            LOGGER.error("Load database property configuration file error. Please check the C3P0 configuration part.", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("No JDBC driver found, please check the C3P0 configuration part.", e);
        } catch (IllegalAccessException ex) {
            LOGGER.error("IllegalAccessException", ex);
        } catch (InvocationTargetException ex) {
            LOGGER.error("InvocationTargetException", ex);
        } 
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    LOGGER.error(ex);
                }
            }
        }
    }

    /**
     * Get pooled database connection. After using it, just close as normally
     * use.
     *
     * @return Database connection
     */
    public Connection getConnection(){
        Connection c ;
        try{
         c = cpds.getConnection();
         return c;
        }catch(SQLException e){
            LOGGER.error(e);
            return null;
        }
    }
}
