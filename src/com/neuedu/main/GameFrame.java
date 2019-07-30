package com.neuedu.main;

import com.neuedu.constant.FrameConstant;
import com.neuedu.runtime.Background;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends Frame {

    private Background background = new Background();

    @Override
    public void paint(Graphics g){
        background.draw(g);
    }

    /**
     * 使用这个方法初始化窗口
     */
    public void init(){
        //设置窗口尺寸
        setSize(FrameConstant.FRAME_WIDTH,FrameConstant.FRAME_HEIGHT);

        //设置窗口在电脑上居中显示
        setLocationRelativeTo(null);

        //启动窗口时不加载图片
        enableInputMethods(false);

        //设置窗口关闭时终止进程
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);      //显示窗口

        //多线程
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    repaint();
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.start();
    }

    //双缓冲
    private Image OffScreenImage = null;//创建双缓冲区

    @Override
    public void update(Graphics g){
        if (OffScreenImage == null) {
            OffScreenImage = this.createImage(FrameConstant.FRAME_WIDTH,FrameConstant.FRAME_HEIGHT);
        }
        Graphics gOff = OffScreenImage.getGraphics();//创建离线图片的实例，在图片缓冲区绘图
        paint(gOff);
        g.drawImage(OffScreenImage, 0, 0, null);
    }






}
