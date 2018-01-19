/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: DevicePositionReportMessageObject.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.message;

/**
 *
 * @author taipa
 */
public class DevicePositionReportMessageObject extends MessageObject {

    private int gpsState;
    private float longtitude;
    private float latitude;
    private float gpsSpeed;
    private float gpsDirection;
    private int antiTheft;
    private int electricMotor;
    private int fuelSupply;
    private int doorState;
    private int carMotorState;
    private int mode;
    private String messageType;
    private StringBuilder status;
    
    public DevicePositionReportMessageObject() {
    }

    public int getFuelSupply() {
        return fuelSupply;
    }

    public void setFuelSupply(int fuelSupply) {
        this.fuelSupply = fuelSupply;
    }

    public int getDoorState() {
        return doorState;
    }

    public void setDoorState(int doorState) {
        this.doorState = doorState;
    }

    public StringBuilder getStatus() {
        return status;
    }

    public void setStatus(StringBuilder status) {
        this.status = status;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getGpsState() {
        return gpsState;
    }

    public void setGpsState(int gpsState) {
        this.gpsState = gpsState;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getGpsSpeed() {
        return gpsSpeed;
    }

    public void setGpsSpeed(float gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public float getGpsDirection() {
        return gpsDirection;
    }

    public void setGpsDirection(float gpsDirection) {
        this.gpsDirection = gpsDirection;
    }

    public int getAntiTheft() {
        return antiTheft;
    }

    public void setAntiTheft(int antiTheft) {
        this.antiTheft = antiTheft;
    }

    public int getElectricMotor() {
        return electricMotor;
    }

    public void setElectricMotor(int electricMotor) {
        this.electricMotor = electricMotor;
    }

    public int getCarMotorState() {
        return carMotorState;
    }

    public void setCarMotorState(int carMotorState) {
        this.carMotorState = carMotorState;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "DevicePositionReportMessageObject{" + "imei=" + getImei() + ", transportId=" + getTransportId() + ", gpsCreateDate=" + getGpsCreateDate() + ", gpsState=" + gpsState + ", longtitude=" + longtitude + ", latitude=" + latitude + ", gpsSpeed=" + gpsSpeed + ", gpsDirection=" + gpsDirection + ", antiTheft=" + antiTheft + ", fuelSupply=" + fuelSupply + ", electricMotor=" + electricMotor + ", carMotorState=" + carMotorState + ", mode=" + mode + ", status=" + status + '}';
    }

}
