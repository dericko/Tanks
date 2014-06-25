import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Leaves extends Sprite{

  public static final String img2_file = "leaves.png";
  public static final String img1_file = "leaves2.png";
  public boolean exists;
  private static final int width = 75;
  private static final int height = 75;
  private int life;
  private static BufferedImage img2;
  private static BufferedImage img1;

  public Leaves(int posX, int posY, 
               int field_width, int field_height) {
    super(0, 0, posX, posY, width, height, field_width, field_height);
    exists = true;
    life = 2;
    
    try {
      if (img2 == null) {
        img2 = ImageIO.read(new File(img2_file));
      }
      if (img1 == null) {
        img1 = ImageIO.read(new File(img1_file));
      }
    } catch (IOException e) {
      System.out.println("Internal Error:" + e.getMessage());
    }
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
    if (life == 2) g.drawImage(img2, posX, posY, width, height, null);
    else if (life == 1) g.drawImage(img1, posX, posY, width, height, null);
  }
}
