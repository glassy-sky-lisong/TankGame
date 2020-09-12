package com.lisong;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import org.w3c.dom.css.Rect;

import javax.xml.stream.FactoryConfigurationError;
import java.awt.*;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 * @author lenovo
 * @package com.lisong
 * @date 2020/9/11 16:42
 * @Description
 */
public class Missile {
    public static final int WIDTH = 6,  HEIGHT = 6;
    public static final int SPEED = 7;
    public static final int KILL = 10;
    private int x, y;
    private Direction dir;
    private TankClient tc;
    private boolean live = true;
    private boolean role;
    private Color color;

    public Missile(int x, int y, boolean role, Direction gunDir, TankClient tc) {
        this.x = x;
        this.y = y;
        this.role = role;
        this.dir = gunDir;
        this.tc = tc;
        this.color = this.role ? new Color(0xff, 0xce, 0x0e) : new Color(0x9d, 0x90, 0x87);
    }

    /**
     *
     * @param g draw missile
     */
    public void draw(Graphics g) {
        if (!this.live) {
            this.tc.missiles.remove(this);
            return;
        }
        Color c = g.getColor();
        g.setColor(this.color);
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);

        this.move();
    }

    /**
     *
     *   cross  boundary check
     */
    public void crossCheck() {
        if (this.x < 0 || this.x > TankClient.WIDTH || this.y < 0 || this.y > TankClient.HEIGHT) {
            this.live = false;
        }
    }

    /**
     *  missile move
     */
    public void move() {
        if (!this.live) return;
        switch (this.dir) {
            case U:
                this.y -= SPEED;
                break;
            case L:
                this.x -= SPEED;
                break;
            case LU:
                this.x -= SPEED;
                this.y -= SPEED;
                break;
            case LD:
                this.y += SPEED;
                this.x -= SPEED;
                break;
            case R:
                this.x += SPEED;
                break;
            case RU:
                this.x += SPEED;
                this.y -= SPEED;
                break;
            case RD:
                this.x += SPEED;
                this.y += SPEED;
                break;
            case D:
                this.y += SPEED;
            default:
                break;
        }

        this.crossCheck();
    }

    /**
     *
     * @return missile shape
     */
    public Rectangle getRect() {
        return new Rectangle(this.x, this.y, WIDTH, HEIGHT);
    }

    /**
     *  hit tank
     * @param t hit tank
     * @return is success
     */
    public boolean hitTank(Tank t) {
        if (this.live && t.isLive() && this.role != t.getRole()
          && this.getRect().intersects(t.getRect())) {
            t.setLife();

            /* tank life boundary calculate */
            if (t.getLife() <= 0) {
                t.setLife();
                t.setLive();
            }
            /* missile has lose efficacy */
            this.live = false;

            /* explode effect */
            Explode explode = new Explode(this.x, this.y, this.tc);
            tc.explodes.add(explode);

            return true;
        }
        return false;
    }

    /**
     *  hit a lot of tanks
     * @param tanks tanks List
     * @return All is success
     */
    public boolean hitTanks(ArrayList<Tank> tanks) {
        for (int i=0; i < tanks.size(); i++) {
            if (hitTank(tanks.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     *  handle boundary problem when missile move wall
     * @param w wall
     * @return is lose efficacy
     */
    public boolean againstWall(Wall w) {
        if (this.getRect().intersects(w.getRect())) {
            /* it is lose efficacy */
            this.live = false;
            return true;
        }
        return false;
    }
}
