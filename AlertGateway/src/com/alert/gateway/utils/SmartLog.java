/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: SmartLog.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.utils;

import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class SmartLog {

    private boolean DEBUG;

    private static final SmartLog instance = new SmartLog();

    public static SmartLog getInstance() {
        return instance;
    }

    public boolean isDebug() {
        return DEBUG;
    }

    public void setDebugMode(int mode) {
        DEBUG = mode == 1;
    }

    public void logInfo(Logger logger, Object info) {
        if (DEBUG) {
            logger.info(info);
        }
    }

    public void logDebug(Logger logger, Object info) {
        if (DEBUG) {
            logger.debug(info);
        }
    }

    public void logError(Logger logger, Object info) {
        if (DEBUG) {
            logger.error(info);
        }
    }

    public void logWarn(Logger logger, Object info) {
        if (DEBUG) {
            logger.warn(info);
        }
    }
}
