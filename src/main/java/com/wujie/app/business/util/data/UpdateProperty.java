package com.wujie.app.business.util.data;

import com.wujie.app.framework.exception.CustomException;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UpdateProperty {
    //用户推荐码生成底数
    public static final int CODE_NUM = 1880000000;

    public static int getCodeNum(){
        return CODE_NUM;
    };


    //批量修改参数
    public static Object setResult(Object up, Object data){
        Field[] fs = up.getClass().getDeclaredFields();//得到属性集合
        Field[] fs1 = data.getClass().getDeclaredFields();//得到属性集合
        try {
            for (Field f : fs) {//遍历属性
                f.setAccessible(true);//设置属性可见
                if(Modifier.isFinal(f.getModifiers())) {continue;}//过滤final属性的变量
                Object val = f.get(up);// 得到此属性的值
                if(val != null) {
                    for(Field f1 :fs1) {
                        f1.setAccessible(true);//设置属性可见
                        Field fd = f;
                        if(f.equals(f1)) {
                            fd.set(data, val);//将修改的值传入
                        }
                    }
                }
            }
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * @Title 参数过滤器
     * @Description filter
     * @author huangJun
     * @date 2019/12/9 9:46
     * @param objects
     * @return: void
     */
    public static void filter(Object... objects){
        if(null != objects){
            for(Object o:objects){
                if(null==o){
                    throw new CustomException("您缺少必要参数!");
                }
                if("" == o){
                    throw new CustomException("参数不能为空值!");
                }
            }
        }
    }

    public static void check(Object... objects){
        Arrays.stream(objects).filter(e -> Objects.isNull(e) || !StringUtils.hasText(e.toString())).forEach(e -> {
            throw new CustomException("您缺少必要参数!");
        });
    }



    //生成事务号
    public static String getTransactionId(){
        Date date = new Date();
        return "HMQ"+new SimpleDateFormat("yyyy").format(date)+date.getTime();
    }

    /**
     * @Title 深度复制集合  (自行处理异常)
     * @Description deepCopy
     * @author huangJun
     * @date 2020/1/23 10:32
     * @param src
     * @return: java.util.List<T>
    */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

}
