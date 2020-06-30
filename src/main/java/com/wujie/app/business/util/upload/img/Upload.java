package com.wujie.app.business.util.upload.img;

import com.duoqio.file.OssUpload;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.wujie.app.business.enums.ErrorEnum;
import com.wujie.app.business.util.system.SystemConfig;
import com.wujie.app.business.vo.ResultVo;
import com.wujie.app.framework.util.base.impl.BaseServiceImpl;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ************************************************
 * 公用的图片上传类
 *
 * @author MengDaNai
 * @version 1.3
 * @date 2019年1月29日 创建文件
 * @See ************************************************
 */
@RestController
@RequestMapping("/Upload")
public class Upload extends BaseServiceImpl {

    /**
     * ************************************************
     * oss上传
     *
     * @param oneFile  文件流
     * @param fileName 文件名
     * @return 路径
     * @throws Exception
     * @author MengDaNai
     * @date 2019年3月26日 创建文件
     * ************************************************
     */
    public static String ossUpload(MultipartFile oneFile, String fileName) throws Exception {
        // MultipartFile转换为InputStream
        CommonsMultipartFile cFile = (CommonsMultipartFile) oneFile;
        DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();
        InputStream is = fileItem.getInputStream();

        fileName = UUID.randomUUID().toString() + fileName.substring(fileName.length() - 4);
        Map<String, Object> resultMap = ossUploadBack(fileName, is);
        if (resultMap.get("url").toString().trim().length() > 0) {
            fileName = resultMap.get("url").toString().trim();
        }
        return fileName;
    }

    /**
     * oss上传 公司api返还图片全路径
     *
     * @param newFileName 图片路径
     * @param input       图片流文件
     * @return
     * @throws Exception
     */
    public static Map<String, Object> ossUploadBack(String newFileName, InputStream input) throws Exception {
        return OssUpload.putObject(72135762L, newFileName, input, (long) input.available());
    }


    /**
     * ************************************************
     * oss上传
     *
     * @param file 文件流
     * @return 路径
     * @throws Exception
     * @author MengDaNai
     * @date 2019年3月26日 创建文件
     * ************************************************
     */
    @PostMapping(value = "/uploadImg")
    public static ResultVo uploadImg(MultipartFile file) throws Exception {
		/*CommonsMultipartFile cFile = (CommonsMultipartFile) file;
        DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();
        InputStream is = fileItem.getInputStream();*/
        InputStream is = file.getInputStream();
        /*Map<String, Object> resultMap = ossUploadBack(UUID.randomUUID().toString() + ".png", is);
        if (resultMap.get("url").toString().trim().length() > 0) {
            return ResultVo.ok(resultMap.get("url").toString().trim());
        }*/
        return ResultVo.ok(qiniuyunUploadImg(is));
        // return ResultVo.err(ErrorEnum.GATEWAY_ERROR);
    }

    @PostMapping(value="/uploadImgList")
    public static ResultVo uploadImgList(@RequestParam(value="files") MultipartFile[] files) throws Exception {
        if(ObjectUtils.isEmpty(files)){
            return ResultVo.err(ErrorEnum.DATA_IS_ILLEGAL);
        }
        List<String> fileUrlList = new ArrayList<>();
        for(MultipartFile multipartFile : files){
            fileUrlList.add(qiniuyunUploadImg(multipartFile.getInputStream()));
        }
        return ResultVo.ok(fileUrlList);
    }

    /**
     * ue/um上传控件支持函数
     *
     * @param map
     * @param upfile upfile 是百度编辑器自定义的file名字,请勿修改
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("up")
    public String up(MultipartFile upfile, Map<String, Object> map) throws Exception {
        String originalFilename = upfile.getOriginalFilename();
        String newFileName = changFileName(originalFilename);
        InputStream is = upfile.getInputStream();
        String resultPath = qiniuyunUploadImg(is);
        String mapJson = "<script>window.name='{\"name\":\"" + originalFilename + "\", \"originalName\": \"" + newFileName + "\", \"size\": " + upfile.getSize() + ", \"state\": \"SUCCESS\", \"type\": \"" + upfile.getContentType() + "\", \"url\": \"" + resultPath + "\"}' </script>";
        return mapJson;
       /* String resultPath = "";
        // 上传到阿里云oss
        Map<String, Object> resultMap = ossUploadBack(newFileName, is);
        if (resultMap.get("url").toString().trim().length() > 0) {
            resultPath = resultMap.get("url").toString();
        }
        if (StringUtils.hasText(resultPath)) {
            map.put("state", "SUCCESS");
            map.put("name", originalFilename);
            map.put("originalName", newFileName);
            map.put("size", upfile.getSize());
            map.put("type", upfile.getContentType());
            map.put("url", resultPath);
        } else {
            map.put("state", "error");
        }
        // 董璨2019-06-14：这是原本的写法，下面用来代替的代码是因为前后端分离跨域
        //String mapJson = JsonUtil.map2json(map);
        String mapJson = "<script>window.name='{\"name\":\"" + originalFilename + "\", \"originalName\": \"" + newFileName + "\", \"size\": " + upfile.getSize() + ", \"state\": \"SUCCESS\", \"type\": \"" + upfile.getContentType() + "\", \"url\": \"" + resultPath + "\"}' </script>";
        return mapJson;*/
    }

    public static String changFileName(String uploadName) {
        NameByIPTime bit = new NameByIPTime();
        String extStr = uploadName.substring(uploadName.lastIndexOf("."));
        // 拼接文件名称
        return bit.getIPTimeRand() + extStr;
    }


    public static String qiniuyunUploadImg(InputStream inputStream) {
        //构造一个带指定 Region 对象的配置类
        Zone z = Zone.autoZone();
        Configuration cfg = new Configuration(z);
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String bucket = "jingjiajia-dqo";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] bytes = toByteArray(inputStream);
            Auth auth = Auth.create(SystemConfig.getQiNiuYunAccessKey(), SystemConfig.getQiNiuYunSecretKey());
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return "http://qa23qacbo.bkt.clouddn.com/" + putRet.key;
        } catch (QiniuException ex) {

        } catch (IOException e) {
        }
        return null;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }


}
