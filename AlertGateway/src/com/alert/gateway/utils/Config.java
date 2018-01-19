/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: Config.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.utils;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

public  class Config {
    private static final Logger LOGGER = Logger.getLogger(Config.class.getSimpleName());
    private final String cnfFile;

    /**
     * quan ly cac tham so cau hinh
     */
    private Properties cnfParams;
    
    public Config(String cnfFile) {
        this.cnfFile = cnfFile;
    }

    public String getCurrentConfigPath() {
        return this.cnfFile;
    }

    /**
     * Tra ve so param trong config
     *
     * @return
     */
    public int getConfigPamramsNumber() {
        return cnfParams != null ? cnfParams.size() : 0;
    }

    /**
     * khoi tao doi tuong.
     *
     * @throws FileNotFoundException loi file cau hinh
     */
    public synchronized void loadCnfFile() throws IOException {
        // load ve cac thu vien native dung cho giao tiep hlr
        Properties cnfParamsTmp = new Properties();
        FileInputStream propsFile = null;
        try {
            propsFile = new FileInputStream(cnfFile);
            cnfParamsTmp.load(propsFile);
            // moi thu ok moi gan lai vao config cu
            cnfParams = cnfParamsTmp;

        } 
        
        finally {
            if (propsFile != null) {
                try {
                    propsFile.close();
                } catch (IOException e) {
                    // thong bao dong file loi
                    LOGGER.error("",e);
                }
            }
        }
    }

    /**
     * nhan ve gia tri cua 1 tham so cau hinh
     *
     * @param paramName ten tham so
     * @return gia tri tham so bieu dien duoi dang string, hoac null neu khong
     * ton tai
     */
    public String getParam(String paramName) {
        return cnfParams != null ? cnfParams.getProperty(paramName): null;
    }

    /**
     * nhan ve gia tri cua 1 tham so cau hinh
     *
     * @param paramName ten tham so
     * @param defaultValue gia tri default neu tham so khong duoc cau hinh
     * @return gia tri hoac gia tri default cua tham so bieu dien duoi dang
     * string
     */
    public String getParam(String paramName, String defaultValue) {
        return cnfParams != null ? cnfParams.getProperty(paramName, defaultValue) : defaultValue;
    }

    /**
     * Nhận v�? giá trị tham số cấu hình kiểu int
     *
     * @param paramName tên tham số
     * @param defaultValue giá trị mặc định
     * @return giá trị ứng với tên tham số <b>paramName</b>
     */
    public int getIntParam(String paramName, int defaultValue) {
        
        if(cnfParams == null) {
            return defaultValue;
        }
        
        String sValue = cnfParams.getProperty(paramName);
        if ((sValue == null) || (sValue.isEmpty())) {
            return defaultValue;
        }
        sValue = sValue.trim();
        return Integer.parseInt(sValue);
    }
    public int getIntParam(String paramName) {
        
        
        if(cnfParams == null) {
            return 0;
        }
        
        String sValue = this.cnfParams.getProperty(paramName);
        if ((sValue == null) || (sValue.isEmpty())) {
            return 0;
        }
        sValue = sValue.trim();
        return Integer.parseInt(sValue);
    }

    public boolean isContainsConfig(String paramName) {
        return cnfParams != null ? cnfParams.containsKey(paramName) : false;
    }

    public  boolean getBooleanParam(String paramName, boolean defaultValue) {
        return cnfParams != null ? Boolean.parseBoolean(this.cnfParams.getProperty(paramName, Boolean.toString(defaultValue))) : false;
    }

}
