

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
public abstract class Tank extends Sprite {
  // Tank-specific variables
  public boolean isAlive = true;
  public int life;
  public boolean hasShot;
  public boolean isTurn;
  public int aim;
  public int aimFaceX;
  public int aimOrigin;
  public int aimAngle;
  public int power;
  public Color color;
  public boolean faceRight = true;
  
  // Image
  private static BufferedImage img;
  
  // Initialization variable
  private static final int INIT_VEL_X = 0;
  private static final int INIT_VEL_Y = 0;
  private static final int SIZE = 20;

  /** Constructor */
  public Tank(Color color, int init_x, int maxX, int maxY) {
    super(INIT_VEL_X, INIT_VEL_Y, init_x, -SIZE, 
          SIZE, SIZE, maxX, maxY);
    this.color = color;
    life = 1000;
    hasShot = false;
    aim = 0;
    aimFaceX = SIZE;
    aimOrigin = 0;
    aimAngle = 45;
    power = 0;
  }
  
  public void faceLeft() {
    faceRight = false;
    aimFaceX = -SIZE;
    aimOrigin = 180;
    aimAngle = -Math.abs(aimAngle);
  }
  
  public void faceRight() {
    faceRight = true;
    aimFaceX = SIZE-7;
    aimOrigin = 0;
    aimAngle = Math.abs(aimAngle);
  }
  
  public int aimUp() {
    return aimAngle += aim;
  }
  
  public int aimDown() {
    return aimAngle -= aim;
  }
  
  public Bomb shoot(int maxX, int maxY) {
    hasShot = true;
    Bomb b = new Bomb(1, -2,
        posX, posY, maxX, maxY, this);
    if (faceRight) { 
      b.velX *= power;
      b.velY *= power;
      b.posX += (SIZE + SIZE/4);
    } else { 
      b.velX *= -power;
      b.velY *= power;
      b.posX -= SIZE;
      }
    return b;
  }
  
  public void takeDamage(Bomb bomb) {
    if (this.intersects(bomb)) {
      /** Adjust damage according to direct hit */
      life -= bomb.damage;
    }
  }
    
  @Override
  public void draw(Graphics g) {
    
    if (isAlive) {
      // Tank
      g.setColor(color);
      g.fillOval(posX, posY, width, height);
      
  
      // Front end
      if (faceRight)
        g.drawArc(posX + aimFaceX, posY-SIZE/2, 25, 25, aimOrigin, 50);
      else
        g.drawArc(posX + aimFaceX, posY-SIZE/2, 25, 25, aimOrigin, -50);
      
      // Display life
      g.drawString(Integer.toString(life), posX, posY);
      
      // Turn Status
      if (isTurn) {
        g.setColor(Color.YELLOW);
        g.fillOval(posX, posY, 10, 10);
        g.setColor(color);
      } 
    } else {
      g.setColor(color.GRAY);
      g.drawString("x_x", posX, posY);
      g.fillOval(posX, posY, width, height);
    }
    
    
    /** STUB -- ADD IMAGES
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
