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

public class EnemyBullet extends BaseSprite implements Moveable, Drawable {

    private  Image image;

    private  int enemyBulletSpeed = FrameConstant.GAME_SPEED * 5;

    public EnemyBullet() {
        this(0, 0, ImageMap.get("epb01"));
    }

    public EnemyBullet(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, getX(), getY(),image.getWidth(null),image.getHeight(null), null);
        move();
        borderTesting();
    }

    @Override
    public void move() {
        setY(getY() + enemyBulletSpeed);
    }


    //敌方子弹的边缘检测方法
    public void borderTesting(){
        if (getY() > FrameConstant.FRAME_HEIGHT ) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.enemyBulletList.remove(this);
        }
    }


    //构建敌方子弹的矩形框，用于与我方飞机的碰撞检测
    @Override
    public Rectangle getRectangle(){
        return new Rectangle(getX(), getY(), image.getWidth(null), image.getHeight(null));
    }

    //碰撞检测方法
    public void collisionTesting(Plane plane){
        GameFrame gameFrame =  DataStore.get("gameFrame");
            if (plane.getRectangle().intersects(this.getRectangle())) {
                gameFrame.bulletList.remove(this);   //发生碰撞就消除一颗碰撞的子弹
                gameFrame.gameOver = true;
            }
        }

}
