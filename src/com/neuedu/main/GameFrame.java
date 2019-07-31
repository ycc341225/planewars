package com.neuedu.main;

import com.neuedu.constant.FrameConstant;
import com.neuedu.runtime.*;
import com.neuedu.util.ImageMap;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameFrame extends Frame {

    Scanner scanner = new Scanner(System.in);

    //创建背景图片对象
    private Background background = new Background();

    //创建游戏状态
    public boolean gameOver = false;

    //创建我的飞机对象
    private Plane plane = new Plane();

    //创建我的子弹对象集合
    public final List<Bullet>  bulletList= new CopyOnWriteArrayList<>();

    //创建敌方飞机对象集合
    public final List<EnemyPlane>  enemyPlaneList= new CopyOnWriteArrayList<>();

    //创建敌方子弹对象集合
    public final List<EnemyBullet>  enemyBulletList = new CopyOnWriteArrayList<>();

    @Override
    public void paint(Graphics g){

        if ( !gameOver) {

        background.draw(g); //画背景
        plane.draw(g);  //画我的飞机

        for (Bullet bullet : bulletList) {
            bullet.draw(g);     //画我的飞机的子弹
        }

        //在窗口显示文字信息 （查看子弹出界面后状态）
        g.setColor(Color.RED);      //设置字体颜色为红色
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString("我方子弹数量："+"\n"+ bulletList.size(), 100, 100);

        //在窗口显示文字信息 （查看敌机出界面后状态）
        g.setColor(Color.YELLOW);      //设置字体颜色为黄色
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString("敌机数量："+"\n"+ enemyPlaneList.size(), 100, 200);

        //在窗口显示文字信息 （查看敌方子弹出界面后状态）
        g.setColor(Color.BLUE);      //设置字体颜色为蓝色
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString("敌方子弹数量："+"\n"+ enemyBulletList.size(), 100, 300);


        for (EnemyPlane enemyPlane : enemyPlaneList) {
            enemyPlane.draw(g);     //画敌方飞机
        }

        for (EnemyBullet enemyBullet : enemyBulletList) {
            enemyBullet.draw(g);    //画敌方子弹
        }


        //这里是碰撞检测之后再重画一次敌机
        for (Bullet bullet : bulletList) {
            bullet.collisionTesting(enemyPlaneList);
        }

        //这里调用敌方子弹与我方飞机的碰撞检测方法
        for (EnemyBullet enemyBullet : enemyBulletList) {
            enemyBullet.collisionTesting(plane);
                if (gameOver) {
                    //在窗口显示文字信息 （游戏结束提示!）
                    g.setColor(Color.RED);      //设置字体颜色为红色
                    g.setFont(new Font("行书", Font.BOLD, 100));
                    g.drawString("GAME OVER!", 90, 400);
                }
            }

        }

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

        //设置窗口不可改变大小（最大化也不行了）
        setResizable(false);

        //设置窗口关闭时终止进程
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        //插入按键监听 (用来控制飞机等的移动)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                plane.keyPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                plane.keyReleased(e);
            }
        });

        //多线程
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    repaint();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        //游戏初始时添加一些敌方飞机
        enemyPlaneList.add(new EnemyPlane(20, 20, ImageMap.get("ep01")));
        enemyPlaneList.add(new EnemyPlane(150, 40, ImageMap.get("ep01")));
        enemyPlaneList.add(new EnemyPlane(300, 60, ImageMap.get("ep01")));
        enemyPlaneList.add(new EnemyPlane(450, 80, ImageMap.get("ep01")));
        enemyPlaneList.add(new EnemyPlane(700, 100, ImageMap.get("ep01")));

        setVisible(true);      //显示窗口
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
