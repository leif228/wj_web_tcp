package com.wujie.app.framework.util.request;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 
 *************************************************
 * 公用的请求类
 * @author  MengDaNai                   
 * @version 1.0                
 * @date    2019年6月18日 创建文件                                            
 * @See                            
 *************************************************
 */
public class RequestUtil {

	RequestUtil(){}
	
	/**
	 * post请求</br>
	 * String url = "http://192.168.1.233:9091/SystemController/findXsCirculation";</br>
	 * MultiValueMap<String, String> map = new LinkedMultiValueMap<>();</br>
	 * map.add("email", "844072586@qq.com");</br>
	 * @param url 地址
	 * @param map 参数
	 * @return
	 */
	public static String post(String url,MultiValueMap<String, String> map) {
		RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
	    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
	    try {
			String string = new String(response.getBody().getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(string);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return response.getBody();
	}
}
