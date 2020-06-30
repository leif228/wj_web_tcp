package com.wujie.app.framework.util;

import cn.hutool.json.XML;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wujie.app.business.enums.ErrorEnum;
import com.wujie.app.framework.exception.CustomException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {



    private static String getContent(HttpServletRequest request) throws UnsupportedEncodingException {
        String method = request.getMethod();
        if (StrKit.isBlank(method)) {
            return null;
        }
        if ("POST".equals(method)) {
            return getPostParameter(request);
        } else {
            return getGetParameter(request);
        }
    }


    private static String getGetParameter(HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println(request.getRequestURL());
        String queryString = URLDecoder.decode(request.getQueryString(), "utf-8");
        String[] split = queryString.split("&");
        Map<String, Object> map = new HashMap<>();

        for (String s : split) {
            if (StrKit.notBlank(s) && s.contains("=")) {
                String[] val1 = s.split("=");
                if (val1.length == 2) {
                    String key = val1[0];
                    String value = val1[1];
                    if (StrKit.notBlank(key) && StrKit.notBlank(value))
                        map.put(key, value);
                }
            }
        }
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toJSONString();
    }
//    private static String getPostParameter(HttpServletRequest request){
//        BufferedReader br = null;
//        try {
//            br = request.getReader();
//            String line = br.readLine();
//            String var4;
//            if (line == null) {
//                var4 = "";
//                return var4;
//            } else {
//                StringBuilder ret = new StringBuilder();
//                ret.append(line);
//
//                while((line = br.readLine()) != null) {
//                    ret.append('\n').append(line);
//                }
//
//                var4 = ret.toString();
//                return var4;
//            }
//        } catch (IOException var14) {
//            throw new RuntimeException(var14);
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException var13) {
//                    throw new BaseException(var13, -5,"关闭IO出错。");
//                }
//            }
//
//        }
//
//    }

    private static String getPostParameter(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        Map<String, String[]> map = request.getParameterMap();
        if (map.isEmpty()) {
            String applicationJSONStr = getApplicationJSON(request);
            json = StrKit.notBlank(applicationJSONStr) ? JSON.parseObject(applicationJSONStr) : json;
        } else {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String[] strings = map.get(paramName);
                json.put(paramName, "".equals(strings[0]) ? null : strings[0]);
            }
        }
        return json.toJSONString();

//        Set set=map.entrySet();
//        Map.Entry[] entry=(Map.Entry[])set.toArray(new Map.Entry[0]);
//        for(int i=0;i<entry.length;i++)
//        {
//            line.append(entry[i].getKey());
//            String[] value = (String[]) entry[i].getValue();
//            for(int j=0;j<value.length;j++)
//            {
//                line.append(value[j]);
//            }
//        }
//        return line.toString();

//        try {
//            br = ThreadParameterUtil.getRequest().getReader();
//            String line = br.readLine();
//            String var4;
//            if (line == null) {
//                var4 = "";
//                return var4;
//            } else {
//                StringBuilder ret = new StringBuilder();
//                ret.append(line);
//
//                while((line = br.readLine()) != null) {
//                    ret.append('\n').append(line);
//                }
//
//                var4 = ret.toString();
//                return var4;
//            }
//        } catch (IOException var14) {
//            throw new RuntimeException(var14);
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException var13) {
//                    throw new BaseException(var13, -5,"关闭IO出错。");
//                }
//            }
//
//        }
    }


    private static String getApplicationJSON(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            br = request.getReader();
            String line = br.readLine();
            String var4;
            if (line == null) {
                var4 = "";
                return var4;
            } else {
                StringBuilder ret = new StringBuilder();
                ret.append(line);

                while ((line = br.readLine()) != null) {
                    ret.append('\n').append(line);
                }

                var4 = ret.toString();
                return var4;
            }
        } catch (IOException var14) {
            throw new RuntimeException(var14);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException var13) {
                    throw new CustomException(ErrorEnum.IO_CLOSE_ERR);
                }
            }

        }
    }

    /* *//**
     * 获取请求Body
     *
     * @param request
     * @return
     *//*
    public String getBodyString(final ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = cloneInputStream(request.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    */

    /**
     * 复制输入流
     *
     * @return</br>
     *//*
    public InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }*/
    public static JSONObject getData(HttpServletRequest request) throws UnsupportedEncodingException {
        String content = getContent(request);

        if (StrKit.notBlank(content)) {
            JSONObject data;
            try {
                data = JSON.parseObject(content);
            } catch (Exception var7) {
                try {
                    data = JSONObject.parseObject(XML.toJSONObject(content).toString());
                } catch (JSONException var6) {
                    throw new CustomException(ErrorEnum.TO_JSON_ERR);
                }
            }
            return data;
        } else {
            return null;
        }
    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            return ip.split(",")[0];
        } else {
            return ip;
        }
    }


}
