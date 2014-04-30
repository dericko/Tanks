


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 *
 */
public class Tank extends Sprite {
  // Tank-specific variables
  public boolean isAlive = true;
  public int life;
  public boolean hasShot;
  public boolean isTurn;
  
  public int aimAngle;
  public int power;
  public Color color;
  public boolean faceRight = true;
  public double xComp;
  public double yComp;
/** TODO  public boolean useSpecial; */
  
  // Image
  private static BufferedImage img;
  
  // Initialization variable
  private static final int INIT_VEL_X = 0;
  private static final int INIT_VEL_Y = 0;
  private static final int INIT_HP = 100;
  private static final int SIZE = 20;

  /** Constructor */
  public Tank(Color color, int init_x, int maxX, int maxY) {
    super(INIT_VEL_X, INIT_VEL_Y, init_x, -SIZE/2, 
          SIZE, SIZE, maxX, maxY);
    this.color = color;
    hasShot = false;
    life = INIT_HP;
    power = 0;
    aimAngle = 45;
    xComp = Math.sin(aimAngle * Math.PI / 180);
    yComp = Math.cos(aimAngle * Math.PI / 180);
  }
  
  public void updateAngle() {
    xComp = Math.sin(aimAngle * Math.PI / 180);
    yComp = Math.cos(aimAngle * Math.PI / 180);
  }
  
  public Bomb shoot(int maxX, int maxY) {
    hasShot = true;
    
    double vY = yComp * power;
    double vX = xComp * power;

    Bomb b = new Bomb((int) vX, -(int) vY,
        posX, posY, maxX, maxY);
    
    if (faceRight) {
      b.velX = Math.abs(b.velX);
      b.posX += (SIZE + SIZE/4);
    } else {
      b.velX = -Math.abs(b.velX);
      b.posX -= SIZE;
      }
    
    
    return b;
  }
  
  public void takeDamage(Bomb bomb) {
    if (this.intersects(bomb)) {
      /** Adjust damage according to direct hit */
      life -= bomb.damage;
      System.out.println("hit!");
    }
  }
    
  @Override
  public void draw(Graphics g) {
    
    if (isAlive) {
      // Tank
      g.setColor(color);
      g.fillOval(posX, posY, width, height);

      // Display life
      g.drawString(Integer.toString(life), 
                  posX-10, posY-5);
      
      g.setColor(Color.MAGENTA);
      if (faceRight) {        
        g.fillOval( 
            (int)((posX+SIZE/2)+(25*xComp)), 
            (int)((posY+SIZE/2)-(25*yComp)), 7, 7);
      } else {
        g.fillOval( 
            (int)((posX+SIZE/2)-(25*xComp)), 
            (int)((posY+SIZE/2)-(25*yComp)), 7, 7);
      }
      g.setColor(color);
      
      // Aimer
      
      
      // Turn Status
      if (isTurn) {
        g.setColor(Color.YELLOW);
        g.fillOval(posX+5, posY+5, 10, 10);
        g.setColor(color);
      } 
    } else {
      g.setColor(Color.GRAY);
      g.drawString("x_x", posX, posY);
      g.fillOval(posX, posY, width, height);
    }
    
    
    /** TODO -- ADD IMAGES
    try {
      if (img == null) {
        img = ImageIO.read(new File(img_file));
      }
      
      
    } catch (IOException e) {
      System.out.println("Image reading error:" + e.getMessage());
    }
    */
  }
}
