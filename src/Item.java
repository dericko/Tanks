

/**
 * 
 */

/**
 * @author hkdaisyc2002
 *
 */
public abstract class Item extends Sprite {
//Initialization variables
 private static final int INIT_X = 0;
 private static final int INIT_Y = 0;
 private static final int INIT_VEL_X = 0;
 private static final int INIT_VEL_Y = 0;
 private static final int SIZE = 5;
  
  
  /** Constructor */
  public Item(int maxX, int maxY) {
    super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, 
        SIZE, SIZE, maxX, maxY);
  }

}
