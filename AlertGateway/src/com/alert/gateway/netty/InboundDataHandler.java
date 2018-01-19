/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: InboundDataHandler.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.netty;

import com.alert.gateway.business.BaseBusiness;
import com.alert.gateway.message.FrameEncodeMessageObject;
import com.alert.gateway.message.FrameObject;
import com.alert.gateway.utils.Constants;
import com.alert.gateway.utils.DataUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class InboundDataHandler extends ChannelInboundHandlerAdapter {

    private static volatile Logger LOGGER;

    public InboundDataHandler(String safeHandler) {
        LOGGER = Logger.getLogger(safeHandler + ".Handler");
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error(cause.getMessage());
        ctx.close();
        ctx.channel().close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //TODO kiem tra neu ban tin ping thi tra lai ngay
        //neu khong thi dat vao queue de xu ly 
        FrameObject frameObject = (FrameObject) msg;
        String messageType = frameObject.getMessageType();
        if (Constants.MESSAGE_TYPE_ZTE.HEART_BEAT_TYPE.equals(messageType)) {
            if (ctx.channel().isActive()) {
                ctx.channel().writeAndFlush(frameObject);
            }
        } else {
            //dat ban tin vao queue de xu ly
            DataUtil.getRequestMessageQueue().put(frameObject);
        }
    }

    private void responseToClient(FrameEncodeMessageObject frameEncodeMessageObject, Channel channel) {
        if (channel.isActive()) {
            channel.writeAndFlush(frameEncodeMessageObject);
        } else {
            LOGGER.info("channel is not active");
        }
    }
}
