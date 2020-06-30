package com.wujie.app.business.util.xcx;


import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

/**
 * @ProjectName: 201909_advert_web
 * @Package: com.duoqio.boot.business.util.xcx
 * @ClassName: XcxTelDecrypt
 * @Author: Administrator
 * @Description: 小程序电话号码解密
 * @Date: 2019/10/31 16:14
 * @copyright: 重庆物界
 * @website: www.wujie.com
 */
public class XcxTelDecrypt {
    private static final String WATERMARK = "watermark";
    private static final String APPID = "appid";



    /**
     * @Title decrypt
     * @Description xcx解密电话号码
     * @Author yangf
     * @Date 2019/11/1 9:14
     * @param: appId 微信小程序APPID
     * @param: encryptedData 加密数据
     * @param: sessionKey 微信用户sessionKey
     * @param: iv 加密串
     * @Return java.lang.String
     */
    public static String decrypt(String appId, String encryptedData, String sessionKey, String iv){

        String result = "";
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                result = new String(WxPKCS7Encoder.decode(resultByte));
                JSONObject jsonObject = JSONObject.fromObject(result);
                String decryptAppid = jsonObject.getJSONObject(WATERMARK).getString(APPID);
                if(!appId.equals(decryptAppid)){
                    result = "";
                }
            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args){
        String appId = "wx1ee627376272a1a4";
        String encryptedData = "n2wWaOi7Wv1EaKVEbcjr5nm2jDnYezFK1YBK9e/qPyaUoxV0ZdZ5I92bQ9eX6bwW6Ku/fFhD0eLieyjBKgZR6q1DxOSUjBeiyzwzZe5Cb4Ze2z23lz0FTXEJ6RO71LVPpBfgverZn565SuwTJWuDKDLkM3cp3ekSVIwHL93GMYtT+r3JXklD1F+UKl5HFfZJ4qBH0a54BgI18CHV1Fd83Q==";
        String sessionKey = "dOxlmOb+HqQT4vp/9izImg==";
        String iv = "LkOXuGT4SMYWF/tCnidM4g==";
        // {"phoneNumber":"18723914027","purePhoneNumber":"18723914027","countryCode":"86","watermark":{"timestamp":1572511770,"appid":"wx1ee627376272a1a4"}}
        System.out.println(decrypt(appId, encryptedData, sessionKey, iv));
    }
}
