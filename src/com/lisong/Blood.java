package com.lisong;

import java.awt.*;
import java.util.Random;

/**
 * @author lenovo
 * @package com.lisong
 * @date 2020/9/11 19:05
 * @Description
 */
public class Blood {
    public static final int WIDTH = 15, HEIGHT = 15;
    private int x,y;
    private Color color = new Color(0x41, 0x14, 0x45);
    private int[][] path = {{350,300}, {350, 305}, {350, 310}, {350, 315}, {350,320}, {350, 330}, {350, 340}};
    private boolean live = true;
    private Random r = new Random();

    public Blood() {
        int key = r.nextInt(path.length);
        this.x = path[key][0];
        this.y = path[key][1];
    }

    /**
     *
     * @param g  graphics window
     */
    public void draw(Graphics g) {
        if(!this.live) return;

        Color c = g.getColor();
        g.setColor(this.color);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(c);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void setLive() {
        this.live = false;
    }

    public boolean getLive(){
        return this.live;
    }
}
