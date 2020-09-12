package com.lisong;

import java.awt.*;

/**
 * @author lenovo
 * @package com.lisong
 * @date 2020/9/11 23:40
 * @Description
 */
public class Energy {
        int x,y; //能量块的坐标位置
        int width = 20;//设置能量块的宽度     能量块的中心位置在 (x+5,y+5)
        boolean tag = true;
        public Energy(int x, int y) {
            // TODO Auto-generated constructor stub
            this.x = x;
            this.y = y;
        }
        public void draw(Graphics g) {
            if(this.tag) {
                g.setColor(Color.green);
                g.fillOval(x, y, width, width);
            }
        }

        public void hit(Tank tank) {
            if((Math.pow(((this.x+5)-(tank.x+15)),2)+Math.pow(((this.y+5)-(tank.y+15)),2))<700) {
                if(tank.life<40) {
                    tank.life=40;
                    this.tag = false;
                }
            }
        }

}
