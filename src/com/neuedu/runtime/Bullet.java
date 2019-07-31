package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.List;

public class Bullet extends BaseSprite implements Moveable, Drawable {

    private Image image;

    private int myBulletSpeed = FrameConstant.GAME_SPEED*5;

    public Bullet() {
        this(0,
                0,
                ImageMap.get("mb01"));
    }

    public Bullet(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, getX(), getY(), image.getWidth(null), image.getHeight(null), null);

        //调用子弹移动的方法
        move();

        //调用子弹的边缘检测方法
        borderTesting();
    }

    @Override
    public void move() {
        setY(getY() - myBulletSpeed);
    }


    //子弹的边缘检测方法 (要在移动方法调用的后面加上该检测方法)
    public void borderTesting(){
        if (getY() < 30 - image.getHeight(null)) {
           GameFrame gameFrame =  DataStore.get("gameFrame");
           gameFrame.bulletList.remove(this);
        }
    }

    //构建我方子弹的矩形框，用于与敌机的碰撞检测
    @Override
    public Rectangle getRectangle(){
        return new Rectangle(getX(), getY(), image.getWidth(null), image.getHeight(null));
    }

    //碰撞检测方法

    public void collisionTesting(List<EnemyPlane> enemyPlaneList){
            GameFrame gameFrame =  DataStore.get("gameFrame");
            for (EnemyPlane enemyPlane : enemyPlaneList) {
                if (enemyPlane.getRectangle().intersects(this.getRectangle())) {
                    enemyPlaneList.remove(enemyPlane);      //发生碰撞就消除碰撞的敌机
                    gameFrame.bulletList.remove(this);   //发生碰撞就消除一颗碰撞的子弹
                }
        }
    }
}
