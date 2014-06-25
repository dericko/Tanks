import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A player object, extends sprite
 * 
 * 
 */
public class Tank extends Sprite {
  // Tank-specific variables
  public static final String img_file = "frogtank_blue.png";
  public boolean isAlive;
  public int life;
  public boolean hasShot;
  public boolean isTurn;

  public int aimAngle;
  public int power;
  public Color color;
  public boolean faceRight = true;
  public double xComp;
  public double yComp;
  public boolean winner;
  public String name;
  /**
   * TODO - implement special public boolean useSpecial;
   */

  // Image
  private static BufferedImage img;

  // Initialization variable
  private static final int INIT_VEL_X = 0;
  private static final int INIT_VEL_Y = 0;
  private static final int INIT_HP = 100;
  private static final int SIZE = 20;

  /** Constructor */
  public Tank(Color color, String name, int init_x, int maxX, int maxY) {
    super(INIT_VEL_X, INIT_VEL_Y, init_x, -SIZE, SIZE, SIZE, maxX, maxY);
    this.color = color;
    this.name=name;
    hasShot = false;
    life = INIT_HP;
    power = 0;
    aimAngle = 45;
    xComp = Math.sin(aimAngle * Math.PI / 180);
    yComp = Math.cos(aimAngle * Math.PI / 180);
    winner = false;
    isAlive = true;

    try {
      if (img == null) {
        img = ImageIO.read(new File(img_file));
      }
    } catch (IOException e) {
      System.out.println("Internal Error:" + e.getMessage());
    }
  }

  public void updateAngle() {
    xComp = Math.sin(aimAngle * Math.PI / 180);
    yComp = Math.cos(aimAngle * Math.PI / 180);
  }

  public Bomb shoot(int maxX, int maxY) {
    hasShot = true;

    double vY = yComp * power;
    double vX = xComp * power;

    Bomb b = new Bomb((int) vX, -(int) vY, posX, posY, maxX, maxY, this);

    if (faceRight) {
      b.velX = Math.abs(b.velX);
      b.posX += (SIZE + SIZE / 4);
    } else {
      b.velX = -Math.abs(b.velX);
      b.posX -= SIZE;
    }

    return b;
  }

  public void winGame() {
    winner = true;
  }
  
  @Override
  public void takeDamage(Bomb bomb) {
    if (this.intersects(bomb)) {
      /** Adjust damage according to direct hit */
      life -= bomb.damage;
    }
  }
  
  @Override
  public void move() {
    posX += velX;
    posY += velY;
  }

  public void isKilled(Tank killer) {
    /** TODO - determine killer */
  }

  public boolean isBelowFloor() {
    return posY > maxY;
  }

  @Override
  public void draw(Graphics g) {
 // Win frog tank
    if (winner) {
      g.drawImage(img, posX, posY, width, height, null);
    } else {
    if (isAlive) {
      // Tank
      g.setColor(color);
      g.fillOval(posX, posY, width, height);

      // Display life
      g.drawString(Integer.toString(life), posX - 2, posY - 5);

      // Turn Status
      if (isTurn) {
        g.setColor(Color.ORANGE);
        g.fillRect(posX, posY - 25, 20, 6);
        g.setColor(color);
      } else {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(posX, posY - 25, 20, 6);
        g.setColor(color);
      }
      
      g.setColor(Color.MAGENTA);
      if (faceRight) {
        g.fillOval((int) ((posX + SIZE / 4) + (25 * xComp)),
            (int) ((posY + SIZE / 2) - (25 * yComp)), 7, 7);
      } else {
        g.fillOval((int) ((posX + SIZE / 4) - (25 * xComp)),
            (int) ((posY + SIZE / 2) - (25 * yComp)), 7, 7);
      }
      g.setColor(color);
    } else {
      g.setColor(Color.GRAY);
      g.drawString("x_x", posX, posY);
      g.fillOval(posX, posY, width, height);
    }
      
    }
  }
}
