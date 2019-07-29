package com.cjs.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class VerifyAction {
    /** 定义验证图片的宽度   */
    private static final int IMG_WIDTH = 60;

    /** 定义验证图片的高度   */
    private static final int IMG_HIGHT = 22;

    private static Random random = new Random();

    private static Font font = new Font("宋体", Font.BOLD, 18);

    @ResponseBody
    @RequestMapping("/createCode")
    public String createCode(HttpSession session, HttpServletResponse response) {
        response.setContentType("images/jpeg");
        BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.fillRect(0, 0, IMG_WIDTH, IMG_HIGHT);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0, 0, IMG_WIDTH, IMG_HIGHT);
        for (int i=0; i < 50; i++) {
            graphics.setColor(new Color(180 + random.nextInt(75),
                    180 + random.nextInt(75),
                    180 + random.nextInt(75)));
            int px1 = 2 + random.nextInt(IMG_HIGHT - 4);
            int py1 = 2 + random.nextInt(IMG_HIGHT - 4);
            int px2 = 2 + random.nextInt(IMG_HIGHT - 4);
            int py2 = 2 + random.nextInt(IMG_HIGHT - 4);
            graphics.drawLine(px1,py1,px2,py2);
        }

        graphics.setFont(font);
        String code = "";
        for (int i = 0; i < 4; i++) {
            String temp = generatorVerify();
            code += temp;
            graphics.setColor(new Color(random.nextInt(20),
                    random.nextInt(40),
                    random.nextInt(20)));
            int offsetLeft = transferForm(graphics);
            graphics.drawString(temp,13*i +offsetLeft,17);
        }
        System.out.println(code);
        session.setAttribute(CommonContains.VERIFY_SESSION,code);

        graphics.dispose();
        /**
         * 输出
         */
        try {
            ImageIO.write(image, "jpeg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 画笔倾斜方法
     * @param graphics
     * @return
     */
    private int transferForm(Graphics graphics) {
        Graphics2D gr = (Graphics2D) graphics;
        AffineTransform tr = gr.getTransform();

        double shx = Math.random();
        if (shx<0.25)shx = 0.25;
        if (shx>0.55)shx = 0.55;

        int temp = random.nextInt(2);
        int offsetLeft = 2;
        if (temp == 0) {
            shx = 0-shx;
            offsetLeft = 10;
        }
        tr.setToShear(shx,0);
        gr.setTransform(tr);
        return offsetLeft;
    }

    private String generatorVerify() {
        /** 随机生成0-3之间的数字 */
        int witch = (int)Math.round((Math.random() * 2));
        witch=2;
        switch (witch){
            case 0: // 生成大写字母(A-Z|65-90)
                long temp = Math.round(Math.random() * 25 + 65);
                return String.valueOf((char)temp);
            case 1: // 生成小写字母(a-z|97-122)
                temp = Math.round(Math.random() * 25 + 97);
                return String.valueOf((char)temp);
            case 2: // 生成数字(0-9)
                return String.valueOf(Math.round(Math.random() * 9));
            default: // 生成汉字(0x4E00-0x9FBF)
                temp = Math.round(Math.random() * 500 + 0x4E00);
                return String.valueOf((char)temp);
        }
    }
}
