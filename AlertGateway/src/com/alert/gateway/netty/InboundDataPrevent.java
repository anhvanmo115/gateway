/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: InboundDataPrevent.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.netty;

import com.alert.gateway.message.MessageObject;
import com.alert.gateway.utils.DataUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class InboundDataPrevent extends MessageToMessageDecoder<MessageObject> {

    private static volatile Logger LOGGER;
    private String deviceId;
    private static Logger logger = Logger.getLogger(InboundDataPrevent.class);
    public InboundDataPrevent(String safeDataPrevent) {
        LOGGER = Logger.getLogger(safeDataPrevent + ".DataPrevent");
    }

    @Override
    protected void decode(ChannelHandlerContext chc, MessageObject msg, List<Object> out) throws Exception {
        if (msg == null) {
            return;
        }

        String imei = null;

        if (msg instanceof MessageObject) {
            imei = ((MessageObject) msg).getImei();
        }

        if (imei == null || imei.isEmpty()) {
            logger.info(" \\\"Cannot find imei in package!!!\\\"");
            return;
        }

        // De tranh phai xac thuc nhieu lan cho mot thiet bi -> kiem tra bien deviceId
        // Neu da duoc xac thuc thi deviceId se khac null va day luon du lieu sang channel tiep theo
        // Neu chua se choc vao DB de kiem tra sau do gan lai deviceId
        if (this.deviceId != null && !this.deviceId.isEmpty()) {
            ((MessageObject) msg).setImei(deviceId);
            out.add(msg);
            return;
        }
        
        // Xac thuc thiet bi
        //msg.setImei(deviceId);
        this.deviceId = imei;
        msg.setImei(deviceId);
        //kiem tra su ton tai cua thiet bi
        logger.info("Thiet bi [" + deviceId+ "] login thanh cong.");
        out.add(msg);                
        if (!DataUtil.GetMapImeiChannelHandler().containsValue(chc.channel())) {
            DataUtil.GetMapImeiChannelHandler().put(deviceId, chc.channel());           
            logger.info("Active");
        }
        
    }
}
