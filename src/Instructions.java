import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Instructions extends Sprite{
  public static final String img_file = "instructions.png";
  public static final String img2_file = "instructions2.png";
  private static final int posX = 0;
  private static final int posY = 0;
  private static final int width = 1000;
  private static final int height = 500;
  private static BufferedImage img;
  private static BufferedImage img2;
  public boolean page1;
  
  public Instructions(int field_width, int field_height) {
    super(0, 0, posX, posY, width, height, field_width, field_height);
    
    page1 = true;
    
    try {
      if (img == null) {
        img = ImageIO.read(new File(img_file));
      }
      if (img2 == null) {
        img2 = ImageIO.read(new File(img2_file));
      }
    } catch (IOException e) {
      System.out.println("Internal Error:" + e.getMessage());
    }
  }

  @Override
  public void draw(Graphics g) {
    if (page1) g.drawImage(img, posX, posY, width, height, null);
    else g.drawImage(img2, posX, posY, width, height, null);
  }
}