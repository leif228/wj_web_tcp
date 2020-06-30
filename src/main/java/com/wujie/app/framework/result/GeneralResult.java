package com.wujie.app.framework.result;

import com.wujie.app.framework.page.PageAction;

import java.io.Serializable;


/**
 * 
 *************************************************
 * describe:返回的结果集                  
 * @author  Mr.Chen                   
 * @version 1.0                
 * @date    2018年10月19日 创建文件                                             
 * @see                        
 *************************************************
 */
public class GeneralResult implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** 返回码 **/
	private Integer code = 0;

	/** 返回异常消息 **/
	private String message;

	/** 返回的结果 **/
	private transient Object result;

	/** 分页实体 **/
	private transient PageAction pageAction;

	/** 防止重复提交的Token **/
	private String token;

	private static GeneralResult g = new GeneralResult();

	public static GeneralResult ok(){
		try {
			return (GeneralResult) g.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return new GeneralResult();
	}

	public GeneralResult() {
		super();
	}

	public GeneralResult(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public GeneralResult(int code, Object result) {
		super();
		this.code = code;
		this.result = result;
	}

	public GeneralResult(PageAction pageAction, Object result) {
		super();
		this.pageAction = pageAction;
		this.result = result;
	}

	public GeneralResult(int code, String message, Object result) {
		super();
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public PageAction getPageAction() {
		return pageAction;
	}

	public void setPageAction(PageAction pageAction) {
		this.pageAction = pageAction;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	//异常返回
	public static GeneralResult fail(String msg) {
		return new GeneralResult(-1, msg);
	}
	public static GeneralResult fail(int code,String msg) {
		return new GeneralResult(code, msg);
	}

	@Override
	public String toString() {
		return "GeneralResult [code=" + code + ", message=" + message + ", result=" + result + ", pageAction="
				+ pageAction + ", token=" + token + "]";
	}

}
