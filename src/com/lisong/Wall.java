package com.lisong;

import java.awt.*;

/**
 * @author lenovo
 * @package com.lisong
 * @date 2020/9/11 18:47
 * @Description
 */
public class Wall {
    int x, y;
    int width, height;
    private Color color;

    public Wall(int x, int y, int width, int height) {
//         fa5122
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = new Color(0xfa, 0x51, 0x22);
    }

    public Wall(int x, int y, int width, int height, Color c) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = c;
    }

    /**
     *
     * @param g window
     */
    public void draw(Graphics g) {
        Color c = g.getColor();
        g.setColor(this.color);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(c);
    }

    /**
     *
     * @return Rectangle
     */
    public Rectangle getRect() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

}
