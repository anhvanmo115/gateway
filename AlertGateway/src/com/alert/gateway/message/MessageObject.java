/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: MessageObject.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.message;

import java.util.Date;

/**
 *
 * @author taipa
 */
public class MessageObject {

    private Date gpsCreateDate;
    private int deviceType;
    private String version;
    private String imei; // id cua thiet bi
    private int transportId;
    private boolean hasSensorCarMotor;
    private int useType;
    private int gpsNoise;
    
    private String typePccc;
    private String contentPccc;
    private String clientTimePccc;
    
    private String TransactionID;

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String TransactionID) {
        this.TransactionID = TransactionID;
    }

    public MessageObject() {
    }

    public boolean isHasSensorCarMotor() {
        return hasSensorCarMotor;
    }

    public void setHasSensorCarMotor(boolean hasSensorCarMotor) {
        this.hasSensorCarMotor = hasSensorCarMotor;
    }

    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }

    public int getGpsNoise() {
        return gpsNoise;
    }

    public void setGpsNoise(int gpsNoise) {
        this.gpsNoise = gpsNoise;
    }

    public Date getGpsCreateDate() {
        return gpsCreateDate;
    }

    public void setGpsCreateDate(Date gpsCreateDate) {
        this.gpsCreateDate = gpsCreateDate;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getTransportId() {
        return transportId;
    }

    public void setTransportId(int transportId) {
        this.transportId = transportId;
    }

    public String getTypePccc() {
        return typePccc;
    }

    public void setTypePccc(String typePccc) {
        this.typePccc = typePccc;
    }

    public String getContentPccc() {
        return contentPccc;
    }

    public void setContentPccc(String contentPccc) {
        this.contentPccc = contentPccc;
    }

    public String getClientTimePccc() {
        return clientTimePccc;
    }

    public void setClientTimePccc(String clientTimePccc) {
        this.clientTimePccc = clientTimePccc;
    }
    
    
}
