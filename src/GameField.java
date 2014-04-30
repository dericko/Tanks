import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.LinkedList;

/**
 * 
 */

/** GameField
 *
 * Holds primary game logic for object/sprite iteration
 */
public class GameField extends JPanel {
  
  /** Game Turn */
  //private Turn currentTurn;
  
  /** Players */
  private TankPlayer p1; // Player 1
  private TankPlayer p2; // Player 2
  private TankPlayer p3; // Player 3
  private TankPlayer p4; // Player 4
  public TankPlayer currentPlayer; // Aliased current player
  private Bomb bomb;
  private Slider powerBar;
  public int timeCount; // Turn timer's value
  private int pTurn;
  private Timer timer;
  
  /** Game Status */
  public boolean playing = false;
  private JLabel turnTimer;
  private JLabel playerStatus;
  private JSlider powerbar;
  
  /** Game constants */
  public static final int FIELD_HEIGHT = 500;
  public static final int FIELD_WIDTH = 1000;
  public static final int TANK_VELOCITY = 2;
  public static final double GRAVITY = 0.5;
  public static final int ONE_SECOND = 1000; // milliseconds
  
  public static final int TURN_INTERVAL = 6; // seconds
  public static final int INTERVAL = 20; // Timer interval in milliseconds
  
  /** Constructor */
  public GameField(JLabel turnTimer, JLabel playerStatus, JSlider powerbar) {
    
    // Create border
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
    // Create Refresher
    Timer refresher = new Timer(INTERVAL, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        tick();
      }
    });
    // Start Refresher
    refresher.start();   
    
    // Focus on keyboard
    setFocusable(true);
    
    // Add key listener to move tank, aim bomb, and shoot bomb
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
          currentPlayer.velX = -TANK_VELOCITY;
          currentPlayer.faceLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          currentPlayer.velX = TANK_VELOCITY;
          currentPlayer.faceRight();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
          currentPlayer.aim = currentPlayer.aimUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          currentPlayer.aim = currentPlayer.aimDown();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          if (!currentPlayer.hasShot) {
            currentPlayer.power++;
          }
        }
      }
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
          currentPlayer.velX = 0;
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
          currentPlayer.velX = 0;
        else if (e.getKeyCode() == KeyEvent.VK_SPACE)
          if (!currentPlayer.hasShot) {
            bomb = currentPlayer.shoot(FIELD_WIDTH, FIELD_HEIGHT);
          }
      }
    });
    
    this.turnTimer = turnTimer;
    this.playerStatus = playerStatus;
    this.powerbar = powerbar;
  }
  
  /**
   * (Re-)set game to initial state
   */
  public void reset() {    
    // Create players
    p1 = new TankPlayer(Color.BLUE,  (int) (Math.random() * 1000), 
        FIELD_WIDTH, FIELD_HEIGHT);
    p2 = new TankPlayer(Color.RED,  (int) (Math.random() * 1000), 
        FIELD_WIDTH, FIELD_HEIGHT);
    p3 = new TankPlayer(Color.GREEN,(int) (Math.random() * 1000), 
        FIELD_WIDTH, FIELD_HEIGHT);
    p4 = new TankPlayer(Color.BLACK, (int) (Math.random() * 1000), 
        FIELD_WIDTH, FIELD_HEIGHT);
    
    powerBar = new Slider(0, FIELD_WIDTH, FIELD_HEIGHT);
    
    currentPlayer = p1;
    pTurn = 0;
    nextTurn();
    
    // Create Turn timer
    timer = new Timer(ONE_SECOND, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        turnTimer.setText(Integer.toString(timeCount));
        
        /** TODO - use LinkedList<Tank> for player ordering */
          if (pTurn == 1) {
            if (p1.isAlive) {
              playerStatus.setText("Player One - ");
              currentPlayer.velX = 0;
              currentPlayer = p1;
              currentPlayer.isTurn = true;
              timeCount--;
              if (timeCount <= 0) {
                nextTurn();
              }
            } else {
              nextTurn();
            }
          } else if (pTurn == 2) {
            if (p2.isAlive) {
              playerStatus.setText("Player Two - ");
              currentPlayer.velX = 0;
              currentPlayer = p2;
              currentPlayer.isTurn = true;
              timeCount--;
              if (timeCount <= 0) {
                nextTurn();
              }
            } else {
              nextTurn();
            }
          } else if (pTurn == 3) {
            if (p3.isAlive) {
              playerStatus.setText("Player Three - ");
              currentPlayer.velX = 0;
              currentPlayer = p3;
              currentPlayer.isTurn = true;
              timeCount--;
              if (timeCount <= 0) {
                nextTurn();
              }
            } else {
              nextTurn();
            }
          } else {
            if (p4.isAlive) {
              playerStatus.setText("Player Four - ");
              currentPlayer.velX = 0;
              currentPlayer = p4;
              currentPlayer.isTurn = true;
              timeCount--;
              if (timeCount <= 0) {
                nextTurn();
              }
            } else {
              nextTurn();
            }
          }
        }
    });
    timer.start();
    
    playing = true;
    
    // Set component's focus on keyboard
    requestFocusInWindow();
  }
  
  /** Not currently working */
  public void pause() {
    if (timer.isRunning()) {
      timer.stop();
    } else
      timer.start();
  }

  /** TODO - implement endturn() AFTER bomb hits */
  
 /** public void endTurn() {
    timer.stop();
    // Turn Interval
    endInterval = new Timer(500, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("endingturn");
        playerStatus.setText("Next...");
        turnTimer.setText("");
        currentPlayer.hasShot = false;
        currentPlayer.velX = 0;
        currentPlayer.power = 0;
        
        nextTurn();
      }
    });
    endInterval.start();
  }
  */
  
  public void nextTurn() {
  /*  endInterval = new Timer(1500, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("endingturn");
        playerStatus.setText("Next...");
        turnTimer.setText("");
      }
    });
    endInterval.start();
    endInterval.setRepeats(false);
*/
    currentPlayer.hasShot = false;
    currentPlayer.isTurn = false;
    currentPlayer.velX = 0;
    currentPlayer.power = 0;
    powerBar.power = 0;
    
    if (pTurn < 4) pTurn++;
    else pTurn = 1;
    
    timeCount = TURN_INTERVAL;
  }
  
  /** Called each time timer triggers */
  void tick() {
    // Game in progress
    if (playing) {
      
      // update powerbar position
      powerbar.setValue(currentPlayer.power);
      powerBar.power = currentPlayer.power;
      
      // Update player position
      currentPlayer.move();
      currentPlayer.velY = (int) Math.round(currentPlayer.velY + GRAVITY);
      /** TODO - update all player positions for bunge */
      
      // Check if alive
      if (p1.life <= 0) p1.isAlive = false;
      if (p2.life <= 0) p2.isAlive = false;
      if (p3.life <= 0) p3.isAlive = false;
      if (p4.life <= 0) p4.isAlive = false;
      
      // Bomb in air
      if (currentPlayer.hasShot) {
        // Update bomb position
        bomb.move();
        bomb.velY = (int) Math.round(bomb.velY + GRAVITY);
        
        /** TODO - check bomb-tank collision */
        if (p1.intersects(bomb)) {
          p1.takeDamage(bomb);
          nextTurn();
        }
        
        if (p2.intersects(bomb)) {
          p2.takeDamage(bomb);
          nextTurn();
        }
        
        if (p3.intersects(bomb)) {
          p3.takeDamage(bomb);
          nextTurn();
        }
        
        if (p4.intersects(bomb)) {
          p4.takeDamage(bomb);
          nextTurn();
        }
      }
      
      
      
    }
    
    /** TODO - check bomb-land collision */
      
      repaint();
    }
  
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    p1.draw(g);
    p2.draw(g);
    p3.draw(g);
    p4.draw(g);
    powerBar.draw(g);
    if (currentPlayer.hasShot) bomb.draw(g);
    
    /** TODO - draw map */
  }
  
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
  }
}
