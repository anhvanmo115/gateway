/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: HeartBeatMessageObject.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.message;

/**
 *
 * @author taipa
 */
public class HeartBeatMessageObject extends MessageObject {

    private int gpsLockNum;
    private int gsmSignal;
    private int batteryVolume;

    public HeartBeatMessageObject() {
    }

    public int getGpsLockNum() {
        return gpsLockNum;
    }

    public void setGpsLockNum(int gpsLockNum) {
        this.gpsLockNum = gpsLockNum;
    }

    public int getGsmSignal() {
        return gsmSignal;
    }

    public void setGsmSignal(int gsmSignal) {
        this.gsmSignal = gsmSignal;
    }

    public int getBatteryVolume() {
        return batteryVolume;
    }

    public void setBatteryVolume(int batteryVolume) {
        this.batteryVolume = batteryVolume;
    }

}
