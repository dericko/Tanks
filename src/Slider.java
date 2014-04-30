
import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 */

/** Power bar to show current shot power
 *
 */
public class Slider extends Sprite {
  private static final int VX = 0;
  private static final int VY = 0;
  private static final int posX = 0;
  private static final int posY = 0;
  public int power;
  private int sliderSpeed;
      
  public Slider(int power, int maxX, int maxY) {
    super(VX, VY, posX, posY, maxX, 30, maxX, maxY);
    this.power = power;
    sliderSpeed = maxX / 30;
  }
  
  @Override
  public void draw(Graphics g) {
    g.setColor(Color.BLACK);
    g.drawRect(posX, posY, maxX, 40);
   
    g.setColor(Color.RED);
    g.fillRect(posX, posY, power * sliderSpeed, 40);
  }
  
}
