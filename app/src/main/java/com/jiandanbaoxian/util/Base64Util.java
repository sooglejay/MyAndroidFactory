package com.jiandanbaoxian.util;
/**
 * Created by JammyQtheLab on 2015/10/23.
 */
public class Base64Util {
    static private String BaseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    static private char[] BaseChars = BaseString.toCharArray();

    //    static public byte[]getParams(List<String> arg_name,List<String>arg_value)
//    {
//        String params = "";
//        int size = arg_name.size();
//        for(int i = 0 ;i<size;i++)
//        {
//            params+=arg_name.get(i)+"="+arg_value.get(i);
//            if(i<size-1)
//            {
//                params+="&";
//            }
//        }
//        return params.getBytes();
//    }
    static public String encode(byte[] source) {
        StringBuffer ret = new StringBuffer("");
        int len = source.length;
        int knot = len / 3;
        int i = 0;
        int reg;
        for (; i < knot; i++) {
            reg = i * 3;
            ret.append(BaseChars[(source[reg] & 0x0fc) >>> 2])
                    .append(BaseChars[((source[reg] & 0x03) << 4) + ((source[reg + 1] & 0x00f0) >>> 4)])
                    .append(BaseChars[((source[reg + 1] & 0x0f) << 2) + ((source[reg + 2] & 0x0c0) >>> 6)])
                    .append(BaseChars[source[reg + 2] & 0x03f]);
        }
        reg = i * 3;
        if (reg < len) {
            if (reg + 1 == len) {
                ret.append(BaseChars[(source[reg] & 0x0fc) >>> 2])
                        .append(BaseChars[(source[reg] & 0x03) << 4]).append('=').append('=');
            } else {
                ret.append(BaseChars[(source[reg] & 0x0fc) >>> 2])
                        .append(BaseChars[((source[reg] & 0x03) << 4) + ((source[reg + 1] & 0x0f0) >>> 4)])
                        .append(BaseChars[(source[reg + 1] & 0x0f) << 2]).append('=');
            }
        }
        return ret.toString();
    }

    static public byte[] decode(String encodedString) {
        if (encodedString.length() < 4) {
            return null;
        }
        char[] source = encodedString.toCharArray();
        int knot = source.length / 4;
        int len = knot * 3;
        if (source[source.length - 1] == '=') {
            if (source[source.length - 2] == '=') {
                len -= 2;
            } else {
                len--;
            }
        }
        int[] reg = new int[4];
        if (knot * 4 != source.length) {
            return null;
        }
        byte[] ret = new byte[len];
        int i, j;
        for (i = 0; i < knot; i++) {
            for (j = 0; j < 4; j++) {
                reg[j] = BaseString.indexOf(source[i * 4 + j]);
            }
            ret[i * 3] = (byte) ((reg[0] << 2) + ((reg[1] & 0x30) >>> 4));
            if (reg[2] != -1) {
                ret[i * 3 + 1] = (byte) (((reg[1] & 0x0f) << 4) + ((reg[2] & 0x3c) >>> 2));
            }
            if (reg[3] != -1) {
                ret[i * 3 + 2] = (byte) (((reg[2] & 0x03) << 6) + reg[3]);
            }

        }
        return ret;
    }
//    public static void main(String[] args) {
//        byte[] b="phone=13678054215".getBytes();
//        System.out.println(encode(b));
//
//
//        byte[] ret=decode(encode(b));
//        System.out.println(new String(ret));
//        System.out.println(new String(decode("VXNlcm5hbWU6")));
//        System.out.println(new String(decode("")));
//    }

}
