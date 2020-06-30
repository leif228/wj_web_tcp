package com.wujie.app.business.util.upload.img;

import com.duoqio.file.OssUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

/**
 * @ProjectName: news
 * @Package: utils
 * @ClassName: UploadFile
 * @Author: fanYang
 * @Description: 上传文件公用类
 * @Date: 2020/3/24 17:17
 * @copyright: 重庆物界
 * @website: www.wujie.com
 */
@Slf4j
public class UploadFile {

    /**
     * @Title uploadQRCodeByInputStream
     * @Description 上传用户二维码
     * @Author yangf
     * @Date 2020/3/25 10:05
     * @param: inputStream 文件流数据
     * @param: folderName 文件夹名
     * @Return java.lang.String
     */
    public static String uploadQRCodeByInputStream(InputStream inputStream, String folderName) {
        try {
            String fileName = "QRCode-" + UUID.randomUUID().toString() + ".png";
            Map<String, Object> resultMap = ossUploadBack(fileName, folderName, inputStream);
            Object url = resultMap.get("url");
            if (!ObjectUtils.isEmpty(url)) {
                return url.toString();
            }
        } catch (Exception e) {
            log.error("OSS生成用户二维码异常:{}", e.getMessage());
        }
        return null;
    }

    /**
     * @Title uploadImgByBaseEncoder
     * @Description 上传图片接口
     * @Author yangf
     * @Date 2020/3/24 17:28
     * @param: imgBase base64数据
     * @param: folderName 文件夹名
     * @Return java.lang.String
     */
    public static String uploadImgByBaseEncoder(String imgBase, String folderName){
        String imgUrl = imgBase.substring(imgBase.indexOf(",") + 1);
        BASE64Decoder base64Encoder = new BASE64Decoder();
        InputStream is = null;
        try {
            // 上传图片到阿里云
            byte[] b = base64Encoder.decodeBuffer(imgUrl);
            for (int j = 0; j < b.length; ++j) {
                //调整异常数据
                if (b[j] < 0) {
                    b[j] += 256;
                }
            }
            is = new ByteArrayInputStream(b);
            String fileName = "code-" + UUID.randomUUID().toString() + ".png";
            Map<String, Object> resultMap = null;
            resultMap = ossUploadBack(fileName, folderName, is);
            Object url = resultMap.get("url");
            if (!ObjectUtils.isEmpty(url)) {
                imgBase = url.toString();
            }
            return imgBase;
        } catch (Exception e) {
            log.error("OSS上传图片异常:{}", e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Map<String, Object> ossUploadBack(String fileName, String folderName, InputStream is) throws IOException {
        return OssUpload.putObject(47646723L, folderName + fileName, is, (long) is.available());
    }


}
