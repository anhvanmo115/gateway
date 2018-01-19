/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: PackageUtil.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.utils;

import io.netty.handler.codec.CorruptedFrameException;

/**
 *
 * @author duypn4
 */
public class PackageUtil {
    public static void packageMarkCheck(byte[] data, int dataIndex, byte[] markToCheck) throws CorruptedFrameException {
        for (int i = 0; i < markToCheck.length; i++) {
            if (markToCheck[i] != data[i + dataIndex]) {
                String frameErrorMsg = String.format(
                        "Invalid package mark, expected 0x%x but received 0x%x.",
                        markToCheck[i],
                        data[i]);
                throw new CorruptedFrameException(frameErrorMsg);
            }
        }
    }
}
