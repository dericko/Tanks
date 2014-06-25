import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 */

/** Builds a terrain map with 2D array of pixels
 *
 */
public class TerrainMap extends TerrainPixel{
  private TerrainPixel[][] base;
  private int baseX;
  private int baseY = 10;
  private TerrainPixel[][] center;
  private int centX = 50;
  private int centY = 400;
  private TerrainPixel[][] topL;
  private TerrainPixel[][] topR;
  private int topX = 50;
  private int topY = 200;
  private int field_width;
  private int field_height;
  
  public TerrainMap(Color color, int posX, int posY, 
      int field_width, int field_height) {
    super(color, posX, posY, field_width, field_height);
    baseX = field_width;
    base   = new TerrainPixel[baseX][baseY];
    //center = new TerrainPixel[centX][centY];
    //topL   = new TerrainPixel[topX][topY];
    //topR   = new TerrainPixel[topX][topY];
    fillTerrain(base);
    this.field_height = field_height;
    this.field_width = field_width;
  }
  
  public void fillTerrain(TerrainPixel[][] fill){
    for(int x = 0; x < baseX; x++) {
      for(int y = 0; y < baseY; y++) {
        base[x][y] = new TerrainPixel(Color.ORANGE, x, field_height - y, 
            field_width, field_height);
      }
    }
  }
  
  public TerrainPixel[][] getBase(){
    return base;
  }
  
  public TerrainPixel[][] getCenter(){
    return center;
  }
  
  public TerrainPixel[][] getTopL(){
    return topL;
  }
  
  public TerrainPixel[][] getTopR(){
    return topR;
  }
  
  @Override
  public void draw(Graphics g) {
    // Draw the base shape
    for(int x = 0; x < base.length; x++) {
      for(int y = 0; y < base[0].length; y++) {
          base[x][y].draw(g); // draw Terrain pixel
      }
    }
  }
}