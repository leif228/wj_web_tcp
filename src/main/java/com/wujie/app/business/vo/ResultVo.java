package com.wujie.app.business.vo;

import com.alibaba.fastjson.JSONObject;
import com.wujie.app.business.enums.ErrorEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: news
 * @Package: com.yang.utils
 * @ClassName: ErrorResult
 * @Author: fanYang
 * @Description: 异常返回实体类
 * @Date: 2020/3/16 19:53
 * @copyright: fanYang
 * @website: 1009983833@qq.com
 */
@Data
public class ResultVo<T> implements Serializable {

    private static String successCode = "0";
    private static String successMsg = "success";
    private final static JSONObject DEFAULT_RESULT = new JSONObject();

    private String code;

    private String msg;

    private T data;

    public ResultVo(){

    }

    public ResultVo(ErrorEnum errorEnum) {
        this.code = errorEnum.getErrCode();
        this.msg = errorEnum.getErrMsg();
    }

    public ResultVo(String code, String errMsg) {
        this.code = code;
        this.msg = errMsg;
    }
    public ResultVo(String code, String errMsg, T t) {
        this.code = code;
        this.msg = errMsg;
        this.data = t;
    }

    public ResultVo(T t) {
        this.code = "0";
        this.data = t;
    }

    public static ResultVo err(String code, String errMsg){
        return new ResultVo(code, errMsg);
    }

    public static ResultVo err(ErrorEnum errorEnum){
        return new ResultVo(errorEnum);
    }


    /**
     * 未登录返回结果
     */
    public static <T> ResultVo<T> unauthorized(T data) {
        return new ResultVo<T>(ErrorEnum.UNAUTHORIZED.getErrCode(), ErrorEnum.UNAUTHORIZED.getErrMsg(), data);
    }

    public static <JSONObject> ResultVo<JSONObject> ok() {
        return new ResultVo(successCode, successMsg, DEFAULT_RESULT);
    }

    public static <T> ResultVo<T> ok(T t) {
        return new ResultVo(successCode, successMsg, t);
    }
}
