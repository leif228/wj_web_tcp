package com.wujie.netty.utils;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wujie.app.business.util.NumConvertUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class ByteUtils {
    private static ObjectMapper objectMapper=new ObjectMapper();
    private static List<String> datePattern=new ArrayList<String>();
    static {
        datePattern.add("yyyy-MM-dd hh/mm/ss");
        datePattern.add("yyyy-MM-dd hh:mm:ss");
        datePattern.add("yyyy/MM/dd hh:mm:dd");
    }
    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        DeserializationConfig config=objectMapper.getDeserializationConfig();
        for (String s : datePattern) {
            objectMapper.setDateFormat(new SimpleDateFormat(s));
        }
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }
    public  static ObjectMapper InstanceObjectMapper(){
        return  objectMapper;
    }
    public List<String> getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(List<String> datePattern) {
        this.datePattern = datePattern;
    }

    /**
     * byte数组转hex
     * @param bytes
     * @return
     */
    public static String byteToHex(byte[] bytes){
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }
    /**
     * hex转byte数组
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex){
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;
    }

    public static void main(String[] args) {
        byte[] bytes = hexToByte("00f5");
        System.out.println(ByteUtils.byteToHex(bytes));
        System.out.println( NumConvertUtil.HexStringToInt(ByteUtils.byteToHex(bytes)));
        System.out.println(NumConvertUtil.IntToHexStringLimit4(245));
    }
}
