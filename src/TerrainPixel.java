import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class TerrainPixel extends Sprite {
  public static final String img_file = "ground.png";
  private Color color;
  public boolean exists;
  private static final int width = 5;
  private static final int height = 5;
  private static BufferedImage img;

  public TerrainPixel(Color color, int posX, int posY, 
                      int field_width, int field_height) {
    super(0, 0, posX, posY, width, height, field_width, field_height);
    this.color = color;
    exists = true;
    
   /* try {
      if (img == null) {
        img = ImageIO.read(new File(img_file));
      }
    } catch (IOException e) {
      System.out.println("Internal Error:" + e.getMessage());
    }*/
  }
  
  public boolean exists(){
    return exists;
  }
  
  public int getX(){
    return posX;
  }
  
  public int getY(){
    return posY;
  }
  
  
  @Override
  public void draw(Graphics g){
    if(exists) {
      g.setColor(color);
      g.fillOval(posX, posY, width, height);
    }
  }
}
