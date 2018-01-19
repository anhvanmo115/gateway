/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: ChannelStateWriteHandle.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.netty;


import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class ChannelStateWriteHandle extends ChannelDuplexHandler {

    protected static final ChannelGroup allConnected = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final Logger LOGGER = Logger.getLogger("ChannelStateHandle");

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

//         LOGGER.info("MyHandler.userEventTriggered " + evt.toString());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
//                ctx.close();
//                 ctx.channel().close();
//                 LOGGER.info("Time out: IdleState.READER_IDLE");
            } else if (e.state() == IdleState.WRITER_IDLE) {
                 ctx.close();
                 ctx.channel().close();
//                 LOGGER.info("Time out: IdleState.WRITER_IDLE");
            }
        }
    }
}
