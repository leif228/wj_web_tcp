package com.wujie.app.business.util;

import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: 20190409_jingxiangjia_admin
 * @Package: com.duoqio.boot.business.util
 * @ClassName: WechatConstant
 * @Author: zhanglongfei
 * @Description: 微信常量
 * @Date: 2020/5/11 16:18
 * @copyright: 重庆物界
 * @website: www.wujie.com
 */
@ConfigurationProperties(prefix = "xcxwxpay")
@Data
@Component
public class WechatConstant {

    public static Integer OK_STATUS = 0;
    public static String SUCCESS = "SUCCESS";
    public static String URL_CODE2SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    public static String URL_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    public static String WX_PAY_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";


    //微信支付类型
    //MWEB--H5支付
    //APP -- app支付
    //NATIVE--原生支付
    //JSAPI--公众号支付-小程序支付
    public static final String TRADE_TYPE_NATIVE = "NATIVE";
    public static final String TRADE_TYPE_JSAPI = "JSAPI";
    public static final String TRADE_TYPE_MWEB = "MWEB";
    public static final String TRADE_TYPE_APP = "APP";

    public static final String PLATFORM_ENV_DEV = "dev";

    //生产或测试  dev | production
    private String projectEnv;
    private String serverDomain;
    private String appid;
    private String mchid;
    private String key;
    private String secret;


}
