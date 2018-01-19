/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: OutboundFrameEncoder.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.netty;


import com.alert.gateway.message.FrameEncodeMessageObject;
import com.alert.gateway.utils.Constants;
import com.alert.gateway.utils.DataUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class OutboundFrameEncoder extends MessageToByteEncoder<Object> {

    private static volatile Logger LOGGER = Logger.getLogger(OutboundFrameEncoder.class);

    public OutboundFrameEncoder(String safeFrameEncoder) {
        LOGGER = Logger.getLogger(safeFrameEncoder + ".FrameEncoder");
    }
    
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof FrameEncodeMessageObject) {
            Integer aesType = ((FrameEncodeMessageObject) msg).getAesType();
            byte[] aesKey = Constants.masterKeyAES.getBytes();
            if(1!=aesType){
                aesKey = DataUtil.GetMapChanenelKey().get(ctx.channel().remoteAddress().toString());
            }
            
            byte[] message = ((FrameEncodeMessageObject) msg).processMessage(aesKey);
            //byte[] message = ((FrameEncodeMessageObject) msg).getMessage();
            
            ByteBuf out = ctx.alloc().buffer(message.length);
            out.writeBytes(message);
            ctx.writeAndFlush(out);
            //SmartLog.getInstance().logInfo(LOGGER, ((FrameEncodeMessageObject) msg).getImei() + " | Gui xuong client: " + new String(((FrameEncodeMessageObject) msg).getMessage()));
            LOGGER.info(((FrameEncodeMessageObject) msg).getImei() + " | Gui xuong client PlaintText: " + new String(((FrameEncodeMessageObject) msg).getMessage()));
            LOGGER.info(((FrameEncodeMessageObject) msg).getImei() + " | Gui xuong client: " + new String(message));
        }
    }
    
    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}
