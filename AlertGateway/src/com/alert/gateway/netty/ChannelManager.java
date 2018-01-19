/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: SafeOneRequest.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.netty;


import com.alert.gateway.message.MessageObject;
import com.alert.gateway.utils.DataUtil;
import com.safe.gateway.service.dto.DeviceConfigSOAPObject;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class ChannelManager {

    private static final Logger LOGGER = Logger.getLogger(ChannelManager.class.getSimpleName());

    public static void serverRequestDevice(MessageObject message) {
        Channel channel = DataUtil.GetMapImeiChannelHandler().get(message.getImei());
        if (channel.isActive()) {
            channel.write(message);
        } else {
            LOGGER.info(message.getImei() + " | Thiet bi offline");
        }
    }
    public static void serverRequestDevice(DeviceConfigSOAPObject message) {
        Channel channel = DataUtil.GetMapImeiChannelHandler().get(message.getImei());
        if (channel.isActive()) {
            channel.write(message);
        } else {
            LOGGER.info(message.getImei() + " | Thiet bi offline");
        }
    }
}
