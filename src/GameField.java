import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * 
 */

/**
 * GameField
 * 
 * Holds primary game logic for object/sprite iteration
 */
public class GameField extends JPanel {

  /** Players */
  public Instructions instPage = new Instructions(500, 1000);
  public Tank currentPlayer; // Aliased current player
  private ArrayList<Tank> playerList; // all players, for drawing consistency
  private LinkedList<Sprite> fieldSprites; // to interact with players and bombs
  private Bomb bomb;
  private Slider powerBar;
  public int timeCount; // Turn timer's value
  private Timer timer;
  private Timer refresher;
  private Timer bombDelay;
  public int playerNum;
  private int currPlayer;
  private int playersLeft;
  private boolean winDelay;
  
  // Bricks
  private Brick brick1;
  private Brick brick2;
  private Brick brick3;
  private Brick brick4;
  private Brick brick5;
  private Brick brick6;
  private Brick brick7;
  private Brick brick8;
  private Brick brick9;
  private Brick brick10;

  
  // Dividers
  private VertDivider vert1;
  private VertDivider vert2;
  private VertDivider vert3;
  private HorizDivider horiz1;
  private HorizDivider horiz2;
  private HorizDivider horiz3;
  private HorizDivider horiz4;
  private HorizDivider horiz5;
  private HorizDivider horiz6;
  private HorizDivider horiz7;
  private HorizDivider horiz8;
  private HorizDivider horiz9;
  private HorizDivider horiz10;
  private HorizDivider horiz11;
  private HorizDivider horiz12;
  private HorizDivider horiz13;
  private HorizDivider horiz14;
  private HorizDivider horiz15;
  /** Game Status */
  public boolean playing = false;
  public boolean instructionMode = true;
  public boolean instructionMode2 = false;
  private JLabel turnTimer;
  private JLabel playerStatus;

  /** Game constants */
  public static final int FIELD_HEIGHT = 500;
  public static final int FIELD_WIDTH = 1000;
  public static final int TANK_VELOCITY = 2;
  public static final double G = 0.5;
  public static final int ONE_SECOND = 999; // milliseconds

  public static final int TURN_INTERVAL = 20; // seconds
  public static final int INTERVAL = 35; // Timer interval in milliseconds

  /** Constructor */
  public GameField(JLabel turnTimer, JLabel playerStatus) {

    // Create turn buffer and player set
    playerList = new ArrayList<Tank>();

    // Create border
    setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Create Refresher
    refresher = new Timer(INTERVAL, new ActionListener() {
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
          currentPlayer.aimAngle -= 3;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
          currentPlayer.aimAngle += 3;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
          if (!currentPlayer.hasShot) {
            currentPlayer.power += 2;
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
    playerNum = 2;
    this.turnTimer = turnTimer;
    this.playerStatus = playerStatus;
  }

  public void createPlayer(Color color, int i) {
    String name = "Player " + i;
    Tank p = new Tank(color, name, 
        (int) (Math.random() * FIELD_WIDTH), FIELD_WIDTH,FIELD_HEIGHT);
    playerList.add(p);
  }

  /**
   * (Re-)set game to initial state
   */
  public void reset() {
    if (!refresher.isRunning()) refresher.start();
    if (timer != null) timer.stop();
    // Create players
    winDelay = false;
    playersLeft = playerNum;
    playerList = new ArrayList<Tank>();
    for(int i = 0; i <= playerNum; i++) {
      switch(i) {
      case 1: createPlayer(Color.RED, 1); break;
      case 2: createPlayer(Color.BLUE, 2); break;
      case 3: createPlayer(Color.GREEN, 3); break;
      case 4: createPlayer(Color.PINK, 4); break;
      }
    }

    // Create powerbar
    powerBar = new Slider(0, FIELD_WIDTH, FIELD_HEIGHT);
    
    // Create field map
    fieldSprites = new LinkedList<Sprite>();
    
    // Create Bricks
    brick1 = new Brick(0, 
        FIELD_HEIGHT - (int) (Math.random() * FIELD_HEIGHT), 
        FIELD_WIDTH, FIELD_HEIGHT);
    brick2 = new Brick(150, 
        FIELD_HEIGHT - (int) (Math.random() * FIELD_HEIGHT), 
        FIELD_WIDTH, FIELD_HEIGHT);
    brick3 = new Brick(300, 
        FIELD_HEIGHT - (int) (Math.random() * FIELD_HEIGHT),  
        FIELD_WIDTH, FIELD_HEIGHT);
    brick4 = new Brick(450, 
        FIELD_HEIGHT - (int) (Math.random() * FIELD_HEIGHT), 
        FIELD_WIDTH, FIELD_HEIGHT);
    brick5 = new Brick(600, 
        FIELD_HEIGHT - (int) (Math.random() * FIELD_HEIGHT), 
        FIELD_WIDTH, FIELD_HEIGHT);
    brick6 = new Brick(750, 
        FIELD_HEIGHT - (int) (Math.random() * FIELD_HEIGHT),
        FIELD_WIDTH, FIELD_HEIGHT);
    brick7 = new Brick(900, 
        FIELD_HEIGHT - (int) (Math.random() * FIELD_HEIGHT), 
        FIELD_WIDTH, FIELD_HEIGHT);
    brick8 = new Brick((int) (Math.random() * FIELD_WIDTH),
        FIELD_HEIGHT - (int) (Math.random() * FIELD_HEIGHT), 
        FIELD_WIDTH, FIELD_HEIGHT);
    brick9 = new Brick((int) (Math.random() * FIELD_WIDTH),
        FIELD_HEIGHT - (int) (Math.random() * FIELD_HEIGHT), 
        FIELD_WIDTH, FIELD_HEIGHT);
    brick10 = new Brick((int) (Math.random() * FIELD_WIDTH),
        FIELD_HEIGHT - (int) (Math.random() * FIELD_HEIGHT), 
        FIELD_WIDTH, FIELD_HEIGHT);
    fieldSprites.add(brick1);
    fieldSprites.add(brick2);
    fieldSprites.add(brick3);
    fieldSprites.add(brick4);
    fieldSprites.add(brick5);
    fieldSprites.add(brick6);
    fieldSprites.add(brick7);
    fieldSprites.add(brick8);
    fieldSprites.add(brick9);
    fieldSprites.add(brick10);

    
    // Create Dividers
    vert1 = new VertDivider((int) (Math.random() * FIELD_WIDTH),
        15, FIELD_WIDTH, FIELD_HEIGHT);
    vert2 = new VertDivider((int) (Math.random() * FIELD_WIDTH),
        15, FIELD_WIDTH, FIELD_HEIGHT);
    vert3 = new VertDivider((int) (Math.random() * FIELD_WIDTH),
        15, FIELD_WIDTH, FIELD_HEIGHT);
    
    horiz1 = new HorizDivider(1, FIELD_HEIGHT - 24, 
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz2 = new HorizDivider(1, FIELD_HEIGHT - 22, 
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz3 = new HorizDivider(1, FIELD_HEIGHT - 20,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz4 = new HorizDivider(1, FIELD_HEIGHT - 18,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz5 = new HorizDivider(1, FIELD_HEIGHT - 16,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz6 = new HorizDivider(334, FIELD_HEIGHT - 74,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz7 = new HorizDivider(334, FIELD_HEIGHT - 72,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz8 = new HorizDivider(334, FIELD_HEIGHT - 70,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz9 = new HorizDivider(334, FIELD_HEIGHT - 68,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz10 = new HorizDivider(334, FIELD_HEIGHT - 66,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz11 = new HorizDivider(667, FIELD_HEIGHT - 24,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz12 = new HorizDivider(667, FIELD_HEIGHT - 22,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz13 = new HorizDivider(667, FIELD_HEIGHT - 20,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz14 = new HorizDivider(667, FIELD_HEIGHT - 18,
        FIELD_WIDTH, FIELD_HEIGHT);
    horiz15 = new HorizDivider(667, FIELD_HEIGHT - 16,
        FIELD_WIDTH, FIELD_HEIGHT);
    fieldSprites.add(vert1);
    fieldSprites.add(vert2);
    fieldSprites.add(vert3);
    fieldSprites.add(horiz1);
    fieldSprites.add(horiz2);
    fieldSprites.add(horiz3);
    fieldSprites.add(horiz4);
    fieldSprites.add(horiz5);
    fieldSprites.add(horiz6);
    fieldSprites.add(horiz7);
    fieldSprites.add(horiz8);
    fieldSprites.add(horiz9);
    fieldSprites.add(horiz10);
    fieldSprites.add(horiz11);
    fieldSprites.add(horiz12);
    fieldSprites.add(horiz13);
    fieldSprites.add(horiz14);
    fieldSprites.add(horiz15);
    
    // Begin first turn
    currPlayer = 0;
    currentPlayer = playerList.get(currPlayer);
    nextTurn();

    // Create Turn timer
    timer = new Timer(ONE_SECOND, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        turnTimer.setText(Integer.toString(timeCount));

        if (currentPlayer.isAlive) {
          playerStatus.setText(currentPlayer.name + " ");
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
  public void win(Tank winner) {
      timer.stop();
      refresher.stop();
    // Set component's focus on keyboard
    requestFocusInWindow();
  }

  public void endTurn() {

    bombDelay = new Timer(50, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if ((bomb.posX > FIELD_WIDTH || bomb.posX < 0) || 
            (bomb.posY > FIELD_HEIGHT)) {
          nextTurn();
        }
      }
    });
    bombDelay.start();
  }

  public void nextTurn() {
    if (bombDelay != null) {
      if (bombDelay.isRunning())
        bombDelay.stop();
    }
    // Initialize turn
    currentPlayer.hasShot = false;
    currentPlayer.isTurn = false;
    currentPlayer.velX = 0;
    currentPlayer.power = 0;
    powerBar.power = 0;

    if(currPlayer < playerNum-1) {
      currPlayer++;
    } else {
      currPlayer = 0;
    }
    currentPlayer = playerList.get(currPlayer);

    // Reset turn-timer
    timeCount = TURN_INTERVAL;

    // Set component's focus on keyboard
    requestFocusInWindow();
  }

  public void togglePause() {
    instructionMode = !instructionMode;
    playing = !playing;
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

      // Check if alive
      for (Tank p : playerList) {
        if(p.isAlive) {
          if (p.life <= 0) {
            p.isAlive = false;
            playersLeft--;
          }
        }
      }

      // Check for bomb hits after a shot
      if (currentPlayer.hasShot) {
        // Update bomb position
        bomb.move();
        bomb.velY = (int) Math.round(bomb.velY + G);
        // Bomb hits a tank
        for (Tank p : playerList) {
          if (p.intersects(bomb)) {
            p.takeDamage(bomb);
          }
        }
        for (Sprite brick : fieldSprites) {
          if (brick.isAlive) {
            if (bomb.intersects(brick)) {
              bomb.velX = 0;
              bomb.velY = 0;
              brick.takeDamage(bomb);
              nextTurn();
            }
          }
        }
      }

      // Check tank conditions
      for (Tank p : playerList) {
        // Check tanks for bunge
        if(p.isAlive) {
          if (p.isBelowFloor()) {
            p.life = 0;
            p.isAlive = false;
            playersLeft--;
            nextTurn();
            System.out.println("pLeft:" +playersLeft);
          }
        }
      }
   // Check tank on terrain
      for (Sprite brick: fieldSprites) {
        if (brick.isAlive) {
          if (currentPlayer.willIntersect(brick)) {
            currentPlayer.velY = 0;
          }
        }
      }
    }
    
    // Check for winning condition
    if (playersLeft == 1) {
      
      for (Tank p : playerList) {
        if (p.isAlive) {
          playerStatus.setText(p.name + "");
          p.winGame();
          if (winDelay) win(p);
          winDelay = true;
        }
      }
    }
    
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    if (instructionMode) {
      instPage.draw(g);
    } else {
   // Draw tanks
      for (Tank p : playerList) {
        p.draw(g);
      }
      
      // Draw power bar
      powerBar.draw(g);
      
      // Draw bomb
      if (currentPlayer.hasShot)
        bomb.draw(g);
  
      for (Sprite obj : fieldSprites) {
        if (obj.isAlive) {
          obj.draw(g);
        }
      }
    }
  
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
  }
}
