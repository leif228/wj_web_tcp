package com.wujie.app.business.util.code;

import com.wujie.app.framework.result.GeneralResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @ProjectName: foh
 * @Package: com.duoqio.boot.business.util.verification.code
 * @ClassName: GeneratingPictureVerificationCode
 * @Author: yangf
 * @Description: 生成图片验证码
 * @Date: 2019/8/14 14:22
 * @copyright: 重庆物界
 * @website: www.wujie.com
 */
@Slf4j
public class GeneratingPictureVerificationCode {

    /**
     * @Title getVerificationCode
     * @Description 生成图片验证码
     * @Author yangf
     * @Date 2019/8/14 14:28
     * @param: response
     * @param: request
     * @Return void
     */
    public static GeneralResult getVerificationCode() {
        int width = 200;
        int height = 69;
        ByteArrayOutputStream baos = null;
        String base64Img = "";
        try {
            // 生成验证码字符并加上噪点，干扰线，返回值为验证码字符
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 生成对应宽高的初始图片
            String randomText = drawRandomText(width, height, verifyImg);
            baos = new ByteArrayOutputStream();//io流
            ImageIO.write(verifyImg, "jpg", baos);
            base64Img = Base64Utils.encodeToString(baos.toByteArray());
            base64Img = "data:image/jpg;base64," + base64Img.toString();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }finally {
            if(baos != null){
                try {
                    baos.flush();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new GeneralResult(0, (Object) base64Img);
    }

    /*生成验证码字符并加上噪点，干扰线，返回值为验证码字符*/
    public static String drawRandomText(int width, int height, BufferedImage verifyImg) {

        Graphics2D graphics = (Graphics2D) verifyImg.getGraphics();
        graphics.setColor(Color.WHITE);//设置画笔颜色-验证码背景色
        graphics.fillRect(0, 0, width, height);//填充背景
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 40));
        //数字和字母的组合
        String baseNumLetter = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
        StringBuffer sBuffer = new StringBuffer();
        int x = 10;  //旋转原点的 x 坐标
        String ch = "";
        Random random = new Random();
        // 生成的验证码
        String vCode = "";
        for (int i = 0; i < 4; i++) {
            graphics.setColor(getRandomColor());
            //设置字体旋转角度
            int degree = random.nextInt() % 30;  //角度小于30度
            int dot = random.nextInt(baseNumLetter.length());
            ch = baseNumLetter.charAt(dot) + "";
            vCode += ch;
            sBuffer.append(ch);
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, 45);
            graphics.drawString(ch, x, 45);
            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, 45);
            x += 48;
        }

        //画干扰线
        for (int i = 0; i < 6; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());
            // 随机画线
            graphics.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height));
        }

        //添加噪点
        for (int i = 0; i < 30; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 2, 2);
        }
        // TODO
        return sBuffer.toString();
    }

    /**
     * 随机取色
     */
    private static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
        return color;
    }
}
