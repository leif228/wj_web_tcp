package com.wujie.app.business.util.pay.wx;

import com.github.wxpay.sdk.WXPayConfig;
import com.wujie.app.business.util.system.SystemConfig;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 *************************************************
 * 微信的支付配置类
 * @author  MengDaNai                   
 * @version 1.0                
 * @date    2019年6月19日 创建文件                                            
 * @See                            
 *************************************************
 */
@Data
@Log4j2
@Configuration
public class WxPayConfig implements WXPayConfig {

    private byte [] certData;

    public WxPayConfig() throws IOException{
        Resource resource = new ClassPathResource("/cert/wxpay/apiclient_cert.p12");
        InputStream inputStream = resource.getInputStream();
        this.certData = IOUtils.toByteArray(inputStream);
        inputStream.close();
    }
    
    @Override
    public String getAppID() {
        return SystemConfig.getWxAppId();
    }

    @Override
    public String getMchID() {
        return SystemConfig.getWxMchId();
    }

    @Override
    public String getKey() {
        return SystemConfig.getWxPartnerKey();
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }

}
