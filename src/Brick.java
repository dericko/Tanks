import java.awt.Color;
import java.awt.Graphics;


public class Brick extends Sprite {

  public boolean exists;
  private static final int width = 150;
  private static final int height = 10;
  private int life;

  public Brick(int posX, int posY, 
               int field_width, int field_height) {
    super(0, 0, posX, posY, width, height, field_width, field_height);
    exists = true;
    life = 2;
  }
  
  @Override
  public void takeDamage(Bomb bomb) {
    if (this.intersects(bomb)) {
      life --;
      if (life <= 0) isAlive = false;
    }
  }

  @Override
  public void draw(Graphics g) {
    if (life == 2) {
      g.setColor(Color.BLACK);
      g.fillRect(posX, posY, width, height);
    } else if (life == 1) {
      g.setColor(Color.GRAY);
      g.fillRect(posX, posY, width, height);
    }
  }
}