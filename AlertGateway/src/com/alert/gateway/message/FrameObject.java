/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: FrameObject.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.message;

/**
 *
 * @author taipa
 */
public class FrameObject {

    private String TransactionID; 
    private String messageType;
    private String[] messageRequest;

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String TransactionID) {
        this.TransactionID = TransactionID;
    }

    public FrameObject() {
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String[] getMessageRequest() {
        return messageRequest;
    }

    public void setMessageRequest(String[] messageRequest) {
        this.messageRequest = messageRequest;
    }

}
