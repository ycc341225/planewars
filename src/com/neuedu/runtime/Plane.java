package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Plane extends BaseSprite implements Moveable, Drawable {

    //给飞机创建图片属性
    private Image image;

    //给飞机创建方向属性
    private boolean up,right,down,left;

    //创建飞机速度的属性
    private int myPlaneSpeed = FrameConstant.GAME_SPEED*3;

    //给飞机创建开火属性
    private boolean fire;

    public Plane() {
        this((FrameConstant.FRAME_WIDTH - ImageMap.get("my01").getWidth(null)) / 2,
                FrameConstant.FRAME_HEIGHT - ImageMap.get("my01").getHeight(null),
                ImageMap.get("my01"));
    }

    public Plane(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    //重写的画出飞机的方法
    @Override
    public void draw(Graphics g) {
        g.drawImage(image, getX(), getY(),image.getWidth(null),image.getHeight(null), null);

        //调用飞机的移动方法
        move();

        //调用飞机的边缘检测方法
        borderTesting();

        //调用飞机开火的方法
        fire();

        if (fire) {
            count++;
            if (count > 15) {   //刷新15次之后才能发出下一颗子弹
                count = 0;
            }
        }
    }

    //飞机重写的移动方法
    @Override
    public void move() {
        if (up) {
            setY(getY() - myPlaneSpeed);
        }
        if (right) {
            setX(getX() + myPlaneSpeed);
        }
        if (down) {
            setY(getY() + myPlaneSpeed);
        }
        if (left) {
            setX(getX() - myPlaneSpeed);
        }
    }


    //按键按下控制飞机移动、设计等
    public  void keyPressed(KeyEvent e){
        if (e .getKeyCode() == KeyEvent.VK_W ) {
            up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = true;
        }
        if (e .getKeyCode() == KeyEvent.VK_S ) {
            down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            fire = true;
        }
    }

    //按键释放控制飞机停止移动、停止射击等
    public  void keyReleased(KeyEvent e){
        if (e .getKeyCode() == KeyEvent.VK_W ) {
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = false;
        }
        if (e .getKeyCode() == KeyEvent.VK_S ) {
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            fire = false;
        }
    }


    //飞机的边缘检测方法 (要在移动方法调用的后面加上该检测方法)
    public void borderTesting(){
        if (getX() < 0) {
            setX(0);
        }
        if (getX() > FrameConstant.FRAME_WIDTH -  image.getWidth(null)) {
            setX(FrameConstant.FRAME_WIDTH -  image.getWidth(null));
        }
        if (getY() < 30) {
            setY(30);
        }
        if (getY() > FrameConstant.FRAME_HEIGHT - image.getHeight(null)) {
            setY(FrameConstant.FRAME_HEIGHT - image.getHeight(null));
        }
    }

    //添加一个计时变量，控制开火频率
    private int count = 0;
    //创建飞机开火的方法
    public void fire(){
        if (fire && count == 0) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.bulletList.add(new Bullet(
                    getX()+(image.getWidth(null)/2)-(ImageMap.get("mb01").getWidth(null)/2),
                    getY()-ImageMap.get("mb01").getHeight(null),
                    ImageMap.get("mb01")
            ));
        }
    }

    //构建我方飞机的矩形框，用于与敌机的子弹进行碰撞检测
    @Override
    public Rectangle getRectangle(){
        return new Rectangle(getX(), getY(), image.getWidth(null), image.getHeight(null));
    }


}
