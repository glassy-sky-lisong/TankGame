package com.lisong;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * @author lenovo
 * @package com.lisong
 * @date 2020/9/11 0:34
 * @Description
 */
public class TankClient extends Frame {
    public static final int GAME_X = 400, GAME_Y = 100;
    public static final int WIDTH = 800, HEIGHT = 600;
    public static final int ENEMY_SIZE = 6;
    int scoreTotal = 0;
    private Color backColor = new Color(0x65, 0xc2, 0x94);
    private Image offScreenImage = null;
    Tank heroTank = new Tank((WIDTH)/2, GAME_Y + HEIGHT - Tank.HEIGHT, true, Direction.STOP
    , this);
    ArrayList<Tank> enemyTanks = new ArrayList<Tank>();
    ArrayList<Missile> missiles = new ArrayList<Missile>();
    ArrayList<Explode> explodes = new ArrayList<Explode>();
    ArrayList<Wall> walls = new ArrayList<Wall>();
    Blood blood = new Blood();

    /**
     *  default constructor
     */
    public TankClient() {
        Wall wall1 = new Wall(300, 200, 10, 150);
        Wall wall2 = new Wall(300, 200, 200, 15);

        this.walls.add(wall1);
        this.walls.add(wall2);
    }

    /**
     *  generate enemyTank
     */
    public void generateEnemys() {
        for (int i=0; i < ENEMY_SIZE; i++) {
            int x,y;
            y = 50;
            x= (i + 10) * Tank.WIDTH;
            Tank enemyTank = new Tank(x, y, false, Direction.D,this);
            this.enemyTanks.add(enemyTank);
        }
    }

    /**
     *  double-buffer applied to avert screen flicker and flash
     *  利用双缓冲技术解决闪屏问题
     */
    public void update(Graphics g) {
        if (this.offScreenImage == null) {
            this.offScreenImage = this.createImage(WIDTH, HEIGHT);
        }
        Graphics goff = this.offScreenImage.getGraphics();
        Color c = goff.getColor();
        goff.setColor(this.backColor);
        goff.fillRect(0, 0, WIDTH, HEIGHT);
        goff.setColor(c);
        paint(goff);
        g.drawImage(this.offScreenImage, 0, 0, null);
    }

    /**
     *   draw all components
     * @param g window
     */
    public void paint(Graphics g) {
        //  left top banner
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹数量："+ this.missiles.size(), 10, 20);
        g.drawString("爆炸数量："+ this.explodes.size(), 10, 40);
        g.drawString("剩余敌方坦克数量：" + this.enemyTanks.size(), 10, 60);
        g.drawString("英雄坦克血量：" + this.heroTank.getLife(), 10, 80);
        g.drawString("英雄分数：" + this.scoreTotal, 10, 100);
        g.drawString("游戏键位：↑ 向上 ↓ 向下 ← 向左 → 向右 ", 10, 120);
        g.drawString(" 空格 打坦克 Ctrl 散弹 F2 复活" ,10, 140);
        g.setColor(c);

//      draw walls
        for (int i=0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            wall.draw(g);
        }

//        draw heroTank
        this.heroTank.draw(g);
        this.heroTank.eatBlood(this.blood);

//        enemy tanks
        if (this.enemyTanks.size() <= 0) {
            this.generateEnemys();
        }else {
            for (int m=0; m < enemyTanks.size(); m++) {
                Tank enemy = enemyTanks.get(m);
                enemy.againstTanks(this.enemyTanks);
                for (int j = 0; j < this.walls.size(); j++) {
                    enemy.againstWall(this.walls.get(j));
                }
                enemy.draw(g);
            }
        }

//        draw missiles
        for (int a = 0; a < missiles.size(); a++) {
            Missile mi = missiles.get(a);
            mi.hitTanks(this.enemyTanks);
            mi.hitTank(this.heroTank);
            for (int j = 0; j < this.walls.size(); j++) {
                mi.againstWall(this.walls.get(j));
            }
            mi.draw(g);
        }

//        draw explode
        for (int i=0; i < explodes.size(); i++) {
            this.explodes.get(i).draw(g);
        }

//        draw blood
        this.blood.draw(g);
    }

    public void launchFrame() {
        this.generateEnemys();

//        frame meta message
        this.setTitle("坦克大战");
        this.setBackground(this.backColor);
        this.setLocation(GAME_X, GAME_Y);
        this.setSize(WIDTH, HEIGHT);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new KeyMonitor());
        this.setResizable(false);
        this.setVisible(true);
        Thread tankThread = new Thread(new PaintThread());
        tankThread.start();
    }

    private class PaintThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {
        public void keyReleased(KeyEvent e) { heroTank.keyReleased(e); }
        public void keyPressed( KeyEvent e) { heroTank.keyPressed(e); }
    }
}
