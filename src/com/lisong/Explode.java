package com.lisong;

import java.awt.*;

/**
 * @author lenovo
 * @package com.lisong
 * @date 2020/9/11 21:56
 * @Description
 */
public class Explode {
    public static final int DIAMETER = 25;
    public static final int LIMIT = 200;
    private int x, y;
    private Color color;
    private TankClient tc;
    private int step;

    /**
     *  default constructor
     * @param x x zuobiao
     * @param y y zuobiao
     * @param tc tankClient
     */
    public Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
        this.color = Color.ORANGE;
        this.step = 1;
    }

    /**
     *
     * @param g window
     */
    public void draw(Graphics g) {
        if (this.step * DIAMETER > LIMIT) {
            this.tc.explodes.remove(this);
            return;
        }

        Color c = g.getColor();
        g.setColor(this.color);
        g.fillOval(this.x, this.y, this.step * DIAMETER, this.step * DIAMETER);
        g.setColor(c);

        this.step++;
    }
}


