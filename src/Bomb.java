

import java.awt.Color;
import java.awt.Graphics;


/** A Bomb sprite is created by a tank.  */

public class Bomb extends Sprite {
  
  public int damage = (int) (Math.random() * 10) + 3;
  public int bombRadius = 5;
  public int damageRadius = 20;
  private static final int WIDTH = 15;
  private static final int HEIGHT = 15;
  public Tank shooter;
  
  /** Constructor */
  public Bomb(int vx, int vy, int px, int py, 
      int maxX, int maxY, Tank shooter) {
    super(vx, vy, px, py, WIDTH, HEIGHT, maxX, maxY);
    this.shooter = shooter;
  }
  
  @Override
  public void move() {
    posX += velX;
    posY += velY;
  }
 
  @Override
  public void draw(Graphics g) {
    g.setColor(Color.CYAN);
    g.fillOval(posX, posY, WIDTH, HEIGHT);
  }

}