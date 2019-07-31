package com.neuedu.runtime;

import com.neuedu.base.BaseSprite;
import com.neuedu.base.Drawable;
import com.neuedu.base.Moveable;
import com.neuedu.constant.FrameConstant;
import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;
import com.neuedu.util.ImageMap;

import java.awt.*;
import java.util.Random;

public class EnemyPlane extends BaseSprite implements Moveable, Drawable {

    private Image image;

    private int enemyPlaneSpeed = FrameConstant.GAME_SPEED;

    private Random random = new Random();

    public EnemyPlane() {
    }

    public EnemyPlane(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, getX(), getY(),image.getWidth(null),image.getHeight(null), null);
        move();
        fire();
        borderTesting();
    }

    @Override
    public void move() {
        setY(getY() + enemyPlaneSpeed);
    }

    //飞机的边缘检测方法 (要在移动方法调用的后面加上该检测方法)
    public void borderTesting(){
        if (getY() > FrameConstant.FRAME_HEIGHT ) {
            GameFrame gameFrame = DataStore.get("gameFrame");
            gameFrame.enemyPlaneList.remove(this);
        }
    }

    //敌方开火
    public void fire(){
        GameFrame gameFrame = DataStore.get("gameFrame");
        if (random.nextInt(1000) > 990) {
            gameFrame.enemyBulletList.add(new EnemyBullet(
                    getX()+(image.getWidth(null)/2)
                            -(ImageMap.get("epb01").getWidth(null)/2),
                    getY()+(image.getHeight(null)),
                    ImageMap.get("epb01")));
        }
    }

    //构建敌方飞机的矩形框，用于与我方子弹的碰撞检测
    @Override
    public Rectangle getRectangle(){
        return new Rectangle(getX(), getY(), image.getWidth(null), image.getHeight(null));
    }
}
