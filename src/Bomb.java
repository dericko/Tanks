

import java.awt.Color;
import java.awt.Graphics;
import java.util.EventListener;


/** A Bomb sprite is created by a tank. It contains a listener to see if it 
 *  intersects another sprite
 */
public class Bomb extends Sprite {
  
  public int damage = 250;
  public int bombRadius = 5;
  public int damageRadius = 20;
  private static final int WIDTH = 10;
  private static final int HEIGHT = 10;
  
  /** Constructor */
  public Bomb(int vx, int vy, int px, int py, 
      int maxX, int maxY, Tank shooter) {
    super(vx, vy, px, py, WIDTH, HEIGHT, maxX, maxY);
    
    shooter.hasShot = true;
  }
  
  @Override
  public void move() {
    posX += velX;
    posY += velY;
  }
 
  @Override
  public void draw(Graphics g) {
    g.setColor(Color.RED);
    g.fillOval(posX, posY, WIDTH, HEIGHT);
  }

}