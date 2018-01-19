/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: InboundFrameDecoder.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.netty;


import com.alert.gateway.message.FrameObject;
import com.alert.gateway.service.ServiceManager;
import com.alert.gateway.utils.AesManager;
import com.alert.gateway.utils.Constants;
import com.alert.gateway.utils.DataUtil;
import com.alert.gateway.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 *
 * @author duypn4
 */
public class InboundFrameDecoder extends ByteToMessageDecoder {

    private static volatile Logger LOGGER;

    private final int timeOutFw;
    private volatile Channel outboundFWChannel;
       
    protected static final ChannelGroup allConnected = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    protected Map<String, String> mapDeviceKey;
    ConcurrentHashMap<String, byte[]> mapChanenelKey;
    protected List<String> listDevice;

    public InboundFrameDecoder(String safeFrameDecoder) {
        LOGGER = Logger.getLogger(safeFrameDecoder + ".FrameDecoder");

        timeOutFw = 500;
        mapDeviceKey = ServiceManager.getInstance().getMapDeviceKey();
        mapChanenelKey = DataUtil.GetMapChanenelKey();
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx); //To change body of generated methods, choose Tools | Templates.
        if (outboundFWChannel != null && outboundFWChannel.isActive()) {
            outboundFWChannel.close();
        }
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {

        /**
         * ***********LinhNV10 bo sung ma hoa ngay
         * 17/05/2017*************************** - Them header phan lam 2 loại
         * cho login va khong phai login - them ma hoa AES
         */
        //TODO tach ra class xy ly ma hoa
        int readables = buffer.readableBytes();
        if (readables < Constants.FrameHelper.PACKAGE_START_MARK.getBytes().length + Constants.FrameHelper.PACKAGE_END_MARK.getBytes().length) {
            return;
        }
        byte[] arrByteBuf = new byte[readables];
        byte[] keyAES = Constants.masterKeyAES.getBytes();//Arrays.copyOf(masterKeyAES, masterKeyAES.length);

        buffer.getBytes(0, arrByteBuf);

        //SmartLog.getInstance().logInfo(LOGGER, "IP: " + ctx.channel().remoteAddress() + " | " + new String(arrByteBuf));
        String cipheText = new String(arrByteBuf);
        buffer.markReaderIndex();

        byte[] tmpMsgData;
        byte[] dataPlaintext;
        Utils ulti = new Utils();
        if (arrByteBuf[0] == 0x5B) {
            keyAES = mapChanenelKey.get(ctx.channel().remoteAddress().toString());
            if (keyAES == null || keyAES.length != 16 || arrByteBuf[arrByteBuf.length - 1] != 0x5D) {
                LOGGER.info("Chua khai Key cho thiet bi hoac key bi sai dinh dang AES128 -> dong ket noi");
                ctx.close();
                return;
            }
           LOGGER.info("Doi key theo tung thiet bi vi khong phai login");
        } else if (arrByteBuf[0] != 0x7B || arrByteBuf[arrByteBuf.length - 1] != 0x7D) {
            LOGGER.info("Chua khai Key cho thiet bi hoac key bi sai dinh dang AES128 -> dong ket noi");
            buffer.discardReadBytes();
            buffer.resetReaderIndex();
            ctx.close();
            return;
        }
        tmpMsgData = new byte[arrByteBuf.length - 2];
        buffer.skipBytes(Constants.FrameHelper.PACKAGE_START_MARK.getBytes().length);
        buffer.readBytes(tmpMsgData);
        buffer.skipBytes(Constants.FrameHelper.PACKAGE_END_MARK.getBytes().length);
        //Kiem tra CRC-16 cho msg
        String strtmpMsgData = new String(tmpMsgData);
        if (tmpMsgData.length < 6) {
            buffer.discardReadBytes();
            buffer.resetReaderIndex();
            LOGGER.info("RECEIVE MESSAGE: Len qua nho" + new String(tmpMsgData));
            ctx.close();
        }
        String messageInput = strtmpMsgData.substring(0, tmpMsgData.length - 5);
        String crcMessage = strtmpMsgData.substring(tmpMsgData.length - 5);
        String crc = ulti.calcularCRC16(messageInput);
        if (!crcMessage.equals(crc)) {
            //crcMessage.length()
            buffer.discardReadBytes();
            buffer.resetReaderIndex();
            LOGGER.info("RECEIVE MESSAGE: Sai ma CRC-16 " + new String(tmpMsgData));
            ctx.close();
            return;
        }
        if (tmpMsgData.length < 6) {
            buffer.discardReadBytes();
            buffer.resetReaderIndex();
            LOGGER.info("RECEIVE MESSAGE: Len qua nho" + new String(tmpMsgData));
            ctx.close();
        }

        //Giai ma tmpMsgData
        //Base64
        //giai ma
        Base64 decoder = new Base64();
        byte[] decodedBytes = decoder.decode(messageInput);
        if (decodedBytes.length % 16 != 0) {
            buffer.discardReadBytes();
            buffer.resetReaderIndex();
            LOGGER.info("Ban tin khong giai ma duoc " + new String(tmpMsgData));
            ctx.close();
            return;
        }
        AesManager aesObject = new AesManager();
        dataPlaintext = aesObject.decryptByte(decodedBytes, keyAES);
        if (dataPlaintext == null) {
            buffer.discardReadBytes();
            buffer.resetReaderIndex();
            ctx.close();
            return;
        }

        if (dataPlaintext.length < 6) {
            buffer.discardReadBytes();
            buffer.resetReaderIndex();
            ctx.close();
            return;
        }
        String strDataPlainText = new String(dataPlaintext);

        LOGGER.info("RECEIVE MESSAGE: " + strDataPlainText);
        String[] msgString = (new String(dataPlaintext)).split(Constants.FrameHelper.PACKAGE_SPLIT_MARK);

        FrameObject frameObject = new FrameObject();
        frameObject.setMessageType(msgString[4]);
        //LinhNV10 them transactionID
        frameObject.setTransactionID(msgString[msgString.length - 1]);

        frameObject.setMessageRequest(msgString);

        buffer.discardReadBytes();
        out.add(frameObject);
        String ipSource = ctx.channel().remoteAddress().toString();

        String idDevice = msgString[3];
        //Kiem tra idDevice co ton tai thi thuc hien khong co thi dong ket noi luon
        listDevice = ServiceManager.getInstance().getListDevice();
        if (!listDevice.contains(idDevice)) {
            LOGGER.info("Thiet bi [" + idDevice + "] chua duoc khai bao cho login: ");
            ctx.close();
        }

        //luu lai message tu thiet bi
        //TODO insert database
//        SafeOneDAO.getInstance().insertLogMessage(msgString[3], msgString[4], "", "T", ipSource, strDataPlainText, cipheText, msgString[msgString.length - 1]);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {       
        ctx.close();

        if (outboundFWChannel != null && outboundFWChannel.isActive()) {
            outboundFWChannel.close();
        }
    }

}
