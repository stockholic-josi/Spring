package com.taxholic.core.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;


public class Captcha extends HttpServlet {
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int width = 140;
        int height = 40;
        int fontSize = 24;
        int xGap = 12 ;
        int yGap = 15 ;
        String fontName = "Comic Sans MS" ;
        Color gradiantStartColor = new Color(223, 223, 223); // dark grey
        Color gradiantEndColor = new Color(239, 239, 239); // light grey
        Color textColor =  new Color(0, 102, 216);
        
        //랜덤 단어생성
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 5; i++) {
        	char ch = (char) ((Math.random() * 26) + 97);
        	sb.append(ch);
         }

        BufferedImage bufferedImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        GradientPaint gp = new GradientPaint(0, 0, gradiantStartColor , 0, height / 2, gradiantEndColor, true);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        Random r = new Random();

        for (int i = 0; i < width - 10; i = i + 25) {
            int q = Math.abs(r.nextInt()) % width;
            int colorIndex = Math.abs(r.nextInt()) % 200;
            g2d.setColor(new Color(colorIndex, colorIndex, colorIndex));
            g2d.drawLine(i, q, width, height);
            g2d.drawLine(q, i, i, height);
        }

        g2d.setColor(textColor);

        String captcha = sb.toString();
       // request.getSession().setAttribute("captcha", captcha);
        Cookie cookie = new Cookie("captcha", captcha);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);

        int x = 0;
        int y = 0;

        for (int i = 0; i <captcha.length(); i++) {
            Font font = new Font(fontName , Font.BOLD, fontSize);
            g2d.setFont(font);
            x += xGap + (Math.abs(r.nextInt()) % 15);
            y = yGap + Math.abs(r.nextInt()) % 20;

            g2d.drawChars(captcha.toCharArray(), i, 1, x, y);
        }

        for (int i = 0; i < width - 10; i = i + 25) {
            int p = Math.abs(r.nextInt()) % width;
            int q = Math.abs(r.nextInt()) % width;
            int colorIndex = Math.abs(r.nextInt()) % 200;
            g2d.setColor(new Color(colorIndex, colorIndex, colorIndex));
            g2d.drawLine(p, 0, i + p, q);
            g2d.drawLine(p, 0, i + 25, height);
        }

        g2d.dispose();

        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        os.close();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
