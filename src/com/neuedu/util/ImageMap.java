package com.neuedu.util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ImageMap {

    private static final Map<String, Image> map = new HashMap<>();

    static {
        //加载背景图
        map.put("bg01", ImageUtil.getImage("com\\neuedu\\imgs\\bg\\bg1.png"));
        //加载飞机图
        map.put("my01", ImageUtil.getImage("com\\neuedu\\imgs\\plane\\my_1.png"));
        //加载我的子弹
        map.put("mb01", ImageUtil.getImage("com\\neuedu\\imgs\\bullet\\bullet.png"));
        //加载敌方飞机图
        map.put("ep01", ImageUtil.getImage("com\\neuedu\\imgs\\plane\\ep_1.png"));
        //加载敌方子弹图
        map.put("epb01", ImageUtil.getImage("com\\neuedu\\imgs\\bullet\\epb_1.png"));
    }

    public static Image get(String key){
        return map.get(key);
    }
}
