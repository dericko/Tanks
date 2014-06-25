/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
  public void run() {

    final GameField field;

    // Top-level frame
    final JFrame frame = new JFrame("tanks");
    frame.setResizable(false);
    frame.setLocation(200, 200);
    frame.setBackground(Color.WHITE);

    // Status panel variables
    final JPanel status_panel = new JPanel();
    frame.add(status_panel, BorderLayout.SOUTH);
    status_panel.setBackground(Color.LIGHT_GRAY);
    final JLabel playerStatus = new JLabel("Get Ready!");
    final JLabel timer = new JLabel("");
    final JLabel score = new JLabel("");

    // Main playing area
    field = new GameField(playerStatus, timer);
    frame.add(field, BorderLayout.CENTER);

    // Add frame and status panel functionality
    frame.setLocation(500, 500);

    status_panel.setLayout(new FlowLayout());
    status_panel.add(timer);
    status_panel.add(playerStatus);
    status_panel.add(score);

    // Control Panel
    final JPanel control_panel_holder = new JPanel();
    final JPanel control_left = new JPanel();
    final JPanel control_right = new JPanel();
    control_panel_holder.setLayout(new GridLayout(1, 2));
    control_panel_holder.add(control_left);
    control_panel_holder.add(control_right);
    frame.add(control_panel_holder, BorderLayout.PAGE_START);

    // Reset Button
    final JButton reset = new JButton("Reset");

    // Instructions
    final JButton instructions = new JButton("Read More");

    // Player Modes
    final JButton twoPlayerMode = new JButton("Two Player");
    final JButton threePlayerMode = new JButton("Three Player");
    final JButton fourPlayerMode = new JButton("Four Player");

    twoPlayerMode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        field.playerNum = 2;
        field.instructionMode = false;
        field.instructionMode2 = false;
        instructions.setVisible(true);
        reset.setVisible(true);
        instructions.setText("Instructions");
        field.reset();
      }
    });

    threePlayerMode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        twoPlayerMode.setText("Two Player");
        fourPlayerMode.setText("Four Player");
        field.playerNum = 3;
        field.instructionMode = false;
        field.instructionMode2 = false;
        instructions.setVisible(true);
        reset.setVisible(true);
        instructions.setText("Instructions");
        field.reset();
      }
    });

    fourPlayerMode.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        field.playerNum = 4;
        field.instructionMode = false;
        field.instructionMode2 = false;
        instructions.setVisible(true);
        reset.setVisible(true);
        instructions.setText("Instructions");
        field.reset();
      }
    });

    // Reset function
    reset.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        field.instructionMode = false;
        field.instructionMode2 = false;
        instructions.setVisible(true);
        field.reset();
      }
    });
    reset.setVisible(false);

    // Instructions fuction
    instructions.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (field.playing) {
          field.togglePause();
          field.instPage.page1 = true;
          instructions.setText("ReadMore");
          reset.setVisible(false);
        } else {
          field.instructionMode2 = true;
          field.instPage.page1 = false;
          instructions.setVisible(false);
          instructions.setText("Instructions");
        }
      }
    });

    // Quit Button
    final JButton quit = new JButton("Quit");
    quit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    control_left.add(twoPlayerMode);
    control_left.add(threePlayerMode);
    control_left.add(fourPlayerMode);
    control_right.add(reset);
    control_right.add(instructions);
    control_right.add(quit);

    // Put the frame on the screen
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    // Start game with instructions page;
    field.playing = false;
    field.instructionMode = true;

  }

  /*
   * Main method run to start and run the game Initializes the GUI elements
   * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
   * this in the final submission of your game.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Game());
  }
}
