///* 
// * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
// *
// * @Project name: Safe.One v2.0
// * @File name: InboundMessageDecoder.java (UTF-8)
// * @Author: hoahv5@viettel.com.vn
// * @Date created: 14-04-2017
// * Reproduction in any form is prohibited.
// */
//package com.alert.gateway.netty;
//
//import com.alert.gateway.message.FrameObject;
//import com.alert.gateway.message.MessageObject;
//import com.alert.gateway.utils.Constants;
//import com.alert.gateway.utils.DataUtil;
//import static com.alert.gateway.utils.DataUtil.convertHexToBinary;
//import static com.alert.gateway.utils.DataUtil.parseDate;
//import com.alert.gateway.utils.DeviceObject;
//import com.alert.gateway.utils.SmartLog;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToMessageDecoder;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.util.List;
//import org.apache.log4j.Logger;
//import com.alert.gateway.utils.Constants.*;
//
///**
// *
// * @author duypn4
// */
//public class InboundMessageDecoder extends MessageToMessageDecoder<FrameObject> {
//
//    private static volatile Logger LOGGER;
//    private final int isEnableAlwayForward;
//
//    public InboundMessageDecoder(String safeMessageDecoder) {
//        LOGGER = Logger.getLogger(safeMessageDecoder + ".MessageDecoder");
//        isEnableAlwayForward = 0;
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
//        ctx.channel().close();
//    }
//
//    @Override
//    protected void decode(ChannelHandlerContext chc, FrameObject msg, List<Object> out) throws Exception {
//        //giai ma o day
//        FrameObject frameObject = (FrameObject) msg;
//        out.add(processMessage(frameObject));      
//    }
//
//    //TODO viet ham xu ly cac ban tin frameobject
//    //co che routing vao business
//    private MessageObject processMessage(FrameObject frameObject) {
//        return null;
//    }
//}
