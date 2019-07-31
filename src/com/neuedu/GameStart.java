package com.neuedu;


import com.neuedu.main.GameFrame;
import com.neuedu.util.DataStore;

/**
 * 游戏的启动类
 */
public class GameStart {

    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();     //初始化
        DataStore.put("gameFrame",gameFrame);
        gameFrame.init();
    }

}
