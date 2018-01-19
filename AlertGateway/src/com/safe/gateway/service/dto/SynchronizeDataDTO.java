/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.gateway.service.dto;

/**
 *
 * @author hoahv5
 */
public class SynchronizeDataDTO {

    private String objectName;
    private String synchronizeType;
    private Long timeRequest;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getSynchronizeType() {
        return synchronizeType;
    }

    public void setSynchronizeType(String synchronizeType) {
        this.synchronizeType = synchronizeType;
    }

    public Long getTimeRequest() {
        return timeRequest;
    }

    public void setTimeRequest(Long timeRequest) {
        this.timeRequest = timeRequest;
    }
}
