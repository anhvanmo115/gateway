/* 
 * Copyright (C) 2017 VIETTEL GROUP. All rights reserved.
 *
 * @Project name: Safe.One v2.0
 * @File name: VietnameseUtils.java (UTF-8)
 * @Author: hoahv5@viettel.com.vn
 * @Date created: 14-04-2017
 * Reproduction in any form is prohibited.
 */
package com.alert.gateway.utils;

public class VietnameseUtils {

    protected static final char[] EN_CHARS = new char[]{
        'a', 'd', 'e', 'i', 'o', 'u', 'y', 'A', 'D', 'E', 'I', 'O', 'U', 'Y'
    };
    protected static final String[] VN_CHARS = new String[]{
        "áàảãạâấầẩẫẫậăắằẳẵặ",
        "đ",
        "éèẻẽẹêếềểễệ",
        "íìỉĩị",
        "óòỏõọôốồổỗộơớờởỡợ",
        "úùủũụưứừửữự",
        "ýỳỷỹỵ",
        "ÁÀẢÃẠÂẤẦẨẪẪẬĂẮẰẲẴẶ",
        "Đ",
        "ÉÈẺẼẸÊẾỀỂỄỆ",
        "ÍÌỈĨỊ",
        "ÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢ",
        "ÚÙỦŨỤƯỨỪỬỮỰ",
        "ÝỲỶỸỴ"
    };

    private static char getEnglishStyle(char ch) {
        int length = VN_CHARS.length;
        for (int i = 0; i < length; i++) {
            if (VN_CHARS[i].indexOf(ch) >= 0) {
                return EN_CHARS[i];
            }
        }

        return ch;
    } // end of getEnglishStyle(ch)

    /**
     * Convert a Vietnamese string to "english-style". <br />
     * E.g: Việt Nam --> Viet Nam
     *
     * @param vnStr	vietnameses string
     * @return	english-style of input string
     */
    public static String getEnglishStyle(String vnStr) {
        // create string buffer
        int length = vnStr.length();
        StringBuilder sb = new StringBuilder(length);

        // iterate all the chars and convert into english style
        for (int i = 0; i < length; i++) {
            sb.append(getEnglishStyle(vnStr.charAt(i)));
        }

        return sb.toString();
    }

    private static String truncVietnamese(String vnStr) {
//        vnStr = vnStr.toLowerCase();
//        vnStr = vnStr.replaceAll(" ", "");
//        vnStr = getEnglishStyle(vnStr);
//        return vnStr;
        String output = "";
        vnStr = vnStr.toLowerCase().trim();
        vnStr = vnStr.replaceAll(" +", " ");
        String[] vnStrs = vnStr.split(" ");
        int myLength = vnStrs.length - 1;
        output = output + getEnglishStyle(vnStrs[myLength]);
        for (int i = 0; i < myLength; i++) {
            output = output + getEnglishStyle(vnStrs[i].substring(0, 1));
        }
        return output;
    }

    public static String createUsername(int objInfo, String province, String fullname) {
        String genUsername = truncVietnamese(fullname);
        if (objInfo == 1) {
            genUsername = province.toLowerCase() + "_" + genUsername;
        } else if (objInfo == 2) {
            genUsername = province.toLowerCase() + "_dn_" + genUsername;
        }
        return genUsername;
    }
}
