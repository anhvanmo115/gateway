/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: FrameEncodeMessageObject.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.message;

import com.alert.gateway.utils.AesManager;
import com.alert.gateway.utils.Utils;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author taipa
 */
public class FrameEncodeMessageObject extends MessageObject {

    private byte[] message;
    private Integer aesType;

    public Integer getAesType() {
        return aesType;
    }

    public void setAesType(Integer aesType) {
        this.aesType = aesType;
    }

    public FrameEncodeMessageObject() {
        this.aesType = 0;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public byte[] processMessage(byte[] key) {

        if (message!=null) {
            if (message.length > 2) {
                byte[] messageNew;
                //kiem tra byte dau tien la gi
                byte header = message[0];
                byte end = message[message.length - 1];
                messageNew = Arrays.copyOfRange(message, 1, message.length - 1);
                //ma hoa tai day
                AesManager aesObject = new AesManager();
                messageNew = aesObject.encryptByte(messageNew, key);
                String base64Str = DatatypeConverter.printBase64Binary(messageNew);
                //Bo sung them CheckSum
                Utils u = new Utils();
                String crc = u.calcularCRC16(base64Str);
                base64Str = base64Str + crc;
                byte[] respondMsg = base64Str.getBytes();
                byte[] sendMsg = new byte[respondMsg.length + 2];
                int index = 0;
                sendMsg[index++] = header;
                for (int i = 0; i < respondMsg.length; i++) {
                    sendMsg[index++] = respondMsg[i];
                }
                sendMsg[index] = end;
                return sendMsg;
            }
        }
        return null;
    }

}
