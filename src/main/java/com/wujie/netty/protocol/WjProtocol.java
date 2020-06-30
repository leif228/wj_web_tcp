package com.wujie.netty.protocol;

import java.util.Arrays;

public class WjProtocol {
    private String header="$TCUB&";//6
    private short len;//2
    private char ver='1';//1
    private char encrypt='1';//1
    private short plat;//2
    private short maincmd;//2
    private short subcmd;//2
    private String format;//2
    private short back;//2
    private byte[] userdata;//n
    private char checkSum;//1

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public short getLen() {
        return len;
    }

    public void setLen(short len) {
        this.len = len;
    }

    public char getVer() {
        return ver;
    }

    public void setVer(char ver) {
        this.ver = ver;
    }

    public char getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(char encrypt) {
        this.encrypt = encrypt;
    }

    public short getPlat() {
        return plat;
    }

    public void setPlat(short plat) {
        this.plat = plat;
    }

    public short getMaincmd() {
        return maincmd;
    }

    public void setMaincmd(short maincmd) {
        this.maincmd = maincmd;
    }

    public short getSubcmd() {
        return subcmd;
    }

    public void setSubcmd(short subcmd) {
        this.subcmd = subcmd;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public short getBack() {
        return back;
    }

    public void setBack(short back) {
        this.back = back;
    }

    public byte[] getUserdata() {
        return userdata;
    }

    public void setUserdata(byte[] userdata) {
        this.userdata = userdata;
    }

    public char getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(char checkSum) {
        this.checkSum = checkSum;
    }

    public static void main(String[] args) {
        String header="$TCUB&";//6
       byte[] bytes = header.getBytes();
       System.out.println(bytes.length);
       System.out.println(new String(bytes));

    }
}
