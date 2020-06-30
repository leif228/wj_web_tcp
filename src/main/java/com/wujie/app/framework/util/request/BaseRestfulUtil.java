package com.wujie.app.framework.util.request;

import java.io.IOException;

import com.wujie.app.business.util.system.SystemConfig;
import com.wujie.app.framework.result.GeneralResult;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;


/**
* @Title:com.duoqio.module.util.restful.BaseRestfulUtil
* @Description：用于发起HTTP请求的工具类    
* @author Mr.Chen-> WoXinFeiXiang
* @version 1.0
* @date 2019年3月7日 下午2:26:30
*/
public class BaseRestfulUtil {
	
	/**
	* @Title:postForEntityRestful
	* @Description： 携带参数发起Post请求的工具类
	* @author WoXinFeiXiang
	* @date 2019年3月7日 下午2:27:46
	* @param url
	* @param param
	* @return ResponseEntity<GeneralResult>
	*/
	public static ResponseEntity<GeneralResult> postForEntityRestful(String url, MultiValueMap<String, Object> param) {
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(param,headers);
		ResponseEntity<GeneralResult> result = template.postForEntity(SystemConfig.getHostDomain() + url, httpEntity, GeneralResult.class);
		/* * 请求状态
		200（OK）- 如果现有资源已被更改
		201（created）- 如果新资源被创建
		202（accepted）- 已接受处理请求但尚未完成（异步处理）
		301（Moved Permanently）- 资源的URI被更新
		303（See Other）- 其他（如，负载均衡）
		400（bad request）- 指代坏请求
		404 （not found）- 资源不存在
		406 （not acceptable）- 服务端不支持所需表示
		409 （conflict）- 通用冲突
		412 （Precondition Failed）- 前置条件失败（如执行条件更新时的冲突）
		415 （unsupported media type）- 接受到的表示不受支持
		500 （internal server error）- 通用错误响应
		503 （Service Unavailable）- 服务当前无法处理请求*/
		int requestStatus = result.getStatusCodeValue();
		if(requestStatus != 200){
			result.getBody().setCode(-1);
			result.getBody().setMessage("请求错误");
		}
		return result;
	}
	
	/**
	* @Title:getForEntityRestful
	* @Description： 携带参数发起Get请求的工具类
	* @author WoXinFeiXiang
	* @date 2019年3月7日 下午2:28:03
	* @param url
	* @param param
	* @return ResponseEntity<GeneralResult>
	*/
	public static ResponseEntity<GeneralResult> getForEntityRestful(String url, MultiValueMap<String, Object> param) {
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(param,headers);
		ResponseEntity<GeneralResult> result = template.getForEntity(SystemConfig.getHostDomain() + url, GeneralResult.class, httpEntity);
		
		/* * 请求状态
		 * 200（OK） - 表示已在响应中发出
		 * 204（无内容） - 资源有空表示
		 * 301（Moved Permanently） - 资源的URI已被更新
		 * 303（See Other） - 其他（如，负载均衡）
		 * 304（not modified）- 资源未更改（缓存）
		 * 400 （bad request）- 指代坏请求（如，参数错误）
		 * 404 （not found）- 资源不存在
		 * 406 （not acceptable）- 服务端不支持所需表示
		 * 500 （internal server error）- 通用错误响应
		 * 503 （Service Unavailable）- 服务端当前无法处理请求*/
		int requestStatus = result.getStatusCodeValue();
		if(requestStatus != 200){
			result.getBody().setCode(-1);
			result.getBody().setMessage("请求错误");
		}
		
		return result;
	}
	
	/**
	 * 调用第三方接口
	 * @param url
	 * @return
	 */
	public static JSONObject getInterfaceJsonObj(String url) {
		RestTemplate template = new RestTemplate();
		JSONObject result = template.getForObject(url, JSONObject.class);
		return result;
	}
	
	/**
	 * 调用第三方接口
	 * @param url
	 * @return
	 */
	public static JSONObject getInterfaceJsonObjByMessageConverter(String url) {
		RestTemplate template = new RestTemplate();
		// 解决微信不能处理Content-Type为text/plain类型的Json返回值的情况
		//template.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
		JSONObject result = template.getForObject(url, JSONObject.class);
		return result;
	}
	
	
	/**
	 * httpClient-get请求
	 * @param url
	 * @param charset
	 * @return
	 */
	public static JSONObject sendGet(String url,String charset){
		//新建客户端
		HttpClient httpclient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
		try {
			httpclient.executeMethod(getMethod);
			String responseMsg = getMethod.getResponseBodyAsString();
			JSONObject result = JSON.parseObject(responseMsg, JSONObject.class);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/***
     * httpClient-Post请求
     * @param url 请求地址
     * @param params post参数
     * @return
     * @throws Exception
     */
    public static JSONObject httpClientPost(String url, String params) {
        HttpClient client = new HttpClient();
        client.getParams().setContentCharset("UTF-8");
        PostMethod httpPost = new PostMethod(url);
        try {
            RequestEntity requestEntity = new ByteArrayRequestEntity(params.getBytes("utf-8"));
            httpPost.setRequestEntity(requestEntity);
            client.executeMethod(httpPost);
            String response = httpPost.getResponseBodyAsString();
            JSONObject result = JSON.parseObject(response, JSONObject.class);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            httpPost.releaseConnection();
        }
    }
	
	/*
		 //模拟请求
		@RequestMapping("/getSowingPic")
		public @ResponseBody GeneralResult getSowingPic() {
			//添加请求参数 add 进map集合  然后参数值一定要转为String
			MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
			param.add("page", "0");
			//ResponseEntity<GeneralResult> 表示一定要返回同类型对象
			ResponseEntity<GeneralResult> result = BaseRestfulUtil.postForEntityRestful("/sowing/getSowingPic", param);
			return result.getBody();
		}
	 
	 */
	
}
