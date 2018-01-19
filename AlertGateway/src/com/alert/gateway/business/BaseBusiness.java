/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alert.gateway.business;

import com.alert.gateway.message.FrameEncodeMessageObject;
import com.alert.gateway.message.FrameObject;
import com.alert.gateway.message.MessageObject;

/**
 *
 * @author kid
 */
public abstract class BaseBusiness {

    private MessageObject messageObject;
    
    public BaseBusiness() {
        messageObject = new MessageObject();
    }

    /**
     * tach gia tri tu mang string nhan duoc va set vao messageObject
     *
     * @param frameObject
     * @return messageObject sau khi duoc set gia tri
     */
    public abstract void decode(FrameObject frameObject);

    /**
     * xu ly nghiep vu cho ban tin messageObject
     */
    public abstract void processRequest();

    /**
     * tu messageObject endcode thanh mang string de gui cho client
     * @return FrameEncodeMessageObject 
     */
    public abstract FrameEncodeMessageObject encode();

    public MessageObject getMessageObject() {
        return messageObject;
    }

    public void setMessageObject(MessageObject messageObject) {
        this.messageObject = messageObject;
    }

}
