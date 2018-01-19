/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: SafeConfig.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.utils;

import java.io.IOException;
import java.util.logging.Level;

public class SafeConfig extends Config {

    private static final String configFile = "../etc/mtr_config.conf";

    private static volatile SafeConfig config;

    public SafeConfig(String cnfFile) {
        super(cnfFile);
    }

    public static SafeConfig getInstance() {
        if (config == null) {
            config = new SafeConfig(configFile);
            try {
                config.loadCnfFile();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(SafeConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }

    public int getServerPort() {
        return config.getIntParam("serverPort");
    }

    public String getWebServiceAddress() {
        return config.getParam("webServiceAddress", "http://localhost:9999/calex");
    }

    public int getSleepTime() {
        return config.getIntParam("sleepTime", 30);
    }

    public long getThreadSleepTime() {
        return config.getIntParam("threadSleepTime", 1000);
    }

    public long getTimeSleep() {
        return config.getIntParam("timeSleep", 10);
    }

    public int getEnableSOAP() {
        return config.getIntParam("enableSOAP", 1);
    }

    public int getTimeOut() {
        return config.getIntParam("timeOut", 500);
    }

    public int getDebugMode() {
        return config.getIntParam("debug", 0);
    }

    public String getDeviceTypeWithoutConfigSleep() {
        return config.getParam("deviceType", "1261,1361,1500");
    }

    public int getServerMaxBossGroupThread() {
        return config.getIntParam("server.bossgroup.thread", 500);
    }

    public int getServerMaxWorkGroupThread() {
        return config.getIntParam("server.workergroup.thread", 500);
    }

    public int getLimitTranningRecord() {
        return config.getIntParam("limit_tranning_record", 10);
    }

    public int getNodeId() {
        return config.getIntParam("node_id", -1);
    }

    public int getTotalAgent() {
        return config.getIntParam("total_agent", -1);
    }

    public int getSmsTemplateId() {
        return config.getIntParam("sms_template_id", 0);
    }

    public Long getTimeSynchDB() {
        Long ret = 1000L;
        ret = Long.valueOf(config.getParam("time_synch_db", "1000"));
        return ret;
    }

    public String getAppCode() {
        return config.getParam("APP_CODE", "GATEWAY_");
    }

    public String getSmsCallServiceAddress() {
        return config.getParam("sms_call_service_address", "http://localhost:8712/pcccService/smsCallService");
    }

    public String getServiceAuthorization() {
        return config.getParam("service_authorization", "admin:Pccc@114");

    }
}
