


import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * 
 */

/** An movable game object
 *  
 */
public abstract class Sprite {
  
  /** Position and velocity */
  public int posX;
  public int posY;
  public int velX;
  public int velY;
  
  /** Size in pixels */
  public int width;
  public int height;
  
  /** Gameplay boundary */
  public int maxX;
  public int maxY;
  
  /** Image for object */
  private ImageIcon img;
  
  
  /**
   * Constructor
   */
  public Sprite (int velX, int velY, int posX, int posY, 
                 int width, int height, 
                 int field_width, int field_height){
    this.velX = velX;
    this.velY = velY;
    this.posX = posX;
    this.posY = posY;
    this.width  = width;
    this.height = height;
    this.maxX = field_width  - width;
    this.maxY = field_height  - height;
  }
  
  
  /**
   * Moves the object by its velocity.  Ensures that the object does
   * not go outside its bounds by clipping.
   */
  public void move(){
    posX += velX;
    posY += velY;
    
    clip();
  }

  /**
   * Prevents the object from going outside of the bounds of the area 
   * designated for the object. (i.e. Object cannot go outside of the 
   * active area the user defines for it).
   */ 
  public void clip(){
    if (posX < 0) posX = 0;
    else if (posX > maxX) posX = maxX;

    if (posY < 0) posY = 0;
    else if (posY > maxY) posY = maxY;
  }
  
  /**
   * Determine if this sprite is intersecting another sprite
   * 
   * @param obj : other sprite
   * @return whether or not this sprite intersects other sprite
   */
 public boolean intersects(Sprite obj) {
   return (posX + width >= obj.posX
       && posY + height >= obj.posY
       && obj.posX + obj.width >= posX 
       && obj.posY + obj.height >= posY);
 }
 
 /**
  * Determine whether this game object will intersect another in the
  * next time step, assuming that both objects continue with their 
  * current velocity.
  * 
  * Intersection is determined by comparing bounding boxes. If the 
  * bounding boxes (for the next time step) overlap, then an 
  * intersection is considered to occur.
  * 
  * @param obj : other object
  * @return whether an intersection will occur.
  */
 public boolean willIntersect(Sprite obj){
   int next_x = posX + velX;
   int next_y = posY + velY;
   int next_obj_x = obj.posX + obj.velX;
   int next_obj_y = obj.posY + obj.velY;
   return (next_x + width >= next_obj_x
       && next_y + height >= next_obj_y
       && next_obj_x + obj.width >= next_x 
       && next_obj_y + obj.height >= next_y);
 }
 
 /** Determine whether the game object will hit a 
  *  wall in the next time step. If so, return the direction
  *  of the wall in relation to this game object.
  *  
  * @return direction of impending wall, null if all clear.
  */
 public Direction hitWall() {
   if (posX + velX < 0)
     return Direction.LEFT;
   else if (posX + velX > maxX)
     return Direction.RIGHT;
   if (posY + velY < 0)
     return Direction.UP;
   else if (posY + velY > maxY)
     return Direction.DOWN;
   else return null;
 }
 
 /**
  * Default draw method for how object should be drawn in GUI
  * 
  * @param g Graphics context for drawing the object
  */
 public void draw (Graphics g) {
 }
 
}
