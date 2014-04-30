import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.HashSet;
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
  public Tank currentPlayer; // Aliased current player
  private LinkedList<Tank> turnBuffer;
  private HashSet<Tank> playerSet;
  private Bomb bomb;
  private Slider powerBar;
  public int timeCount; // Turn timer's value
  private Timer timer;
  private Timer bombDelay;
  
  /** Game Status */
  public boolean playing = false;
  private JLabel turnTimer;
  private JLabel playerStatus;
  
  /** Game constants */
  public static final int FIELD_HEIGHT = 500;
  public static final int FIELD_WIDTH = 1000;
  public static final int TANK_VELOCITY = 2;
  public static final double G = 0.5;
  public static final int ONE_SECOND = 1000; // milliseconds
  
  public static final int TURN_INTERVAL = 60; // seconds
  public static final int INTERVAL = 35; // Timer interval in milliseconds
  
  /** Constructor */
  public GameField(JLabel turnTimer, JLabel playerStatus) {
    
    // Create turn buffer and player set
    turnBuffer = new LinkedList<Tank>();
    playerSet = new HashSet<Tank>();
    
    
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
          currentPlayer.faceRight = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
          currentPlayer.velX = TANK_VELOCITY;
          currentPlayer.faceRight = true;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
          currentPlayer.aimAngle-=3;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          currentPlayer.aimAngle+=3;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          if (!currentPlayer.hasShot) {
            currentPlayer.power+=2;
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
            endTurn();
          }
      }
    });
    
    this.turnTimer = turnTimer;
    this.playerStatus = playerStatus;
  }
  
  public void createPlayer(Color color) {
    Tank p = new Tank(color,  (int) (Math.random() * FIELD_WIDTH), 
                    FIELD_WIDTH, FIELD_HEIGHT);
    turnBuffer.add(p);
    playerSet.add(p);
  }
  
  /**
   * (Re-)set game to initial state
   */
  public void reset() {    
    // Create players
    createPlayer(Color.RED);
    createPlayer(Color.BLUE);
    createPlayer(Color.GREEN);
    
    // Create powerbar
    powerBar = new Slider(0, FIELD_WIDTH, FIELD_HEIGHT);
    
    // Begin first turn
    currentPlayer = turnBuffer.remove();
    nextTurn();

    // Create Turn timer
    timer = new Timer(ONE_SECOND, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        turnTimer.setText(Integer.toString(timeCount));

        if (currentPlayer.isAlive) {
          currentPlayer.velX = 0;
          currentPlayer.isTurn = true;
          timeCount--;
          if (timeCount <= 0) {
            nextTurn();
          }
        } else {
          nextTurn();
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
    } else {
      timer.start();
    }
   // Set component's focus on keyboard
      requestFocusInWindow();
  }

  public void endTurn() {

    bombDelay = new Timer(50, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (((bomb.posX > FIELD_WIDTH || bomb.posX < 0) || 
            bomb.posY > FIELD_HEIGHT)) {
          nextTurn();
        }
      }
    });
    bombDelay.start();
  }
  
  public void nextTurn() {
    if (bombDelay != null ) {
      if (bombDelay.isRunning()) bombDelay.stop();
    }
    // Initialize turn
    currentPlayer.hasShot = false;
    currentPlayer.isTurn = false;
    currentPlayer.velX = 0;
    currentPlayer.power = 0;
    powerBar.power = 0;
    
    turnBuffer.add(currentPlayer);
    currentPlayer = turnBuffer.remove();
    
    // Reset turn-timer
    timeCount = TURN_INTERVAL;
    
   // Set component's focus on keyboard
    requestFocusInWindow();
  }
  
  /** Called each time timer triggers */
  void tick() {
    // Game in progress
    if (playing) {
      
      // update powerbar position
      powerBar.power = currentPlayer.power;
      
      // Update player position
      currentPlayer.move();
      currentPlayer.updateAngle();
      currentPlayer.velY = (int) Math.round(currentPlayer.velY + G);
      /** TODO - update all player positions for bunge */
      
      // Check if alive
      for (Tank p : turnBuffer){
        if (p.life <= 0) p.isAlive = false;
      }

      // Bomb in air
      if (currentPlayer.hasShot) {
        // Update bomb position
        bomb.move();
        bomb.velY = (int) Math.round(bomb.velY + G);
        
        for (Tank p : turnBuffer){
          if (p.intersects(bomb)) {
            p.takeDamage(bomb);
          }
        }
      }      
      
    }
    
    /** TODO - check bomb-land collision */
      
      repaint();
    }
  
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    for (Tank p : playerSet){
      p.draw(g);
    }
    powerBar.draw(g);
    if (currentPlayer.hasShot) bomb.draw(g);
    
    /** TODO - draw map */
  }
  
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
  }
}
