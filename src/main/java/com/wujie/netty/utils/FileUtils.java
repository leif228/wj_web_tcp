package com.wujie.netty.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

public class FileUtils {
    /**
     * @param path 如 E:/config.properties
     */
    public static Properties readFile(String path) {
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));

            properties.load(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
