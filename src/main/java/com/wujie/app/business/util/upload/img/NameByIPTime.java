package com.wujie.app.business.util.upload.img;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

/**
 * 类说明：IP时间戳操作类
 * 为上传文件命名
 * @author dujian
 * @version 2013/10/15
 */
public class NameByIPTime {
	private SimpleDateFormat sdf = null;
	private String ip;
	public NameByIPTime(){
		
	}
	/*
	 * 统一ip格式
	 * 
	 */
	public String checkIp(HttpServletRequest request){
		ip = request.getHeader("x-forwarded-for");   
	    if (!checkIP(ip)) {   
	        ip = request.getHeader("Proxy-Client-IP");   
	    }   
	    if (!checkIP(ip)) {   
	        ip = request.getHeader("WL-Proxy-Client-IP");   
	    }   
	    if (!checkIP(ip)) {   
	        ip = request.getRemoteAddr();   
	    }   
	    return ip;
	}
	/*
	 * 判断ip是ip4 还IP6
	 */
	private boolean checkIP(String ip) {   
	    if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip)   
	            || ip.split(".").length != 4) {   
	        return false;   
	    }   
	    return true;   
	} 
	
	public NameByIPTime(String ip){
		this.ip = ip;
	}
	/*
	 * 取得IP地址+时间戳+3位随机数
	 */
	public String getIPTimeRand(){
		StringBuffer sb = new StringBuffer();
		if(this.ip!=null){
			String s[] = ip.split("\\.");
			for(int i=0;i<s.length;i++){
				sb.append(this.addZero(s[i], 3));
			}
		}
		sb.append(this.getTimeStamp());
		Random r = new Random();
		for(int i=0;i<3;i++){
			sb.append(r.nextInt(10));
		}
		return sb.toString();
	}
	/*
	 * 补0操作
	 */
	private String addZero(String str,int len){
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		while(sb.length()<len)
			sb.insert(0, "0");
		return sb.toString();
	}
	//取得时间戳
	private String getTimeStamp(){
		sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}
}
