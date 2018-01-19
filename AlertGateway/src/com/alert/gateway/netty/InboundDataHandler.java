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
        //TODO foward cho tien trinh routing
        //hoac routing ngay tai day
        FrameObject frameObject = (FrameObject) msg;
        //b1 routing, tim ra business 
        BaseBusiness bu = null;
        //b2 xu ly ban tin
        if (bu != null) {
            bu.decode(frameObject);
            bu.processRequest();
            FrameEncodeMessageObject frameEncodeMessageObject = bu.encode();
            
        }
    }
    
    private void responseToClient(FrameEncodeMessageObject frameEncodeMessageObject, Channel channel){
        if(channel.isActive()){
            channel.writeAndFlush(frameEncodeMessageObject);
        }else{
            LOGGER.info("channel is not active");
        }
    }
}
