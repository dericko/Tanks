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
		final JFrame frame = new JFrame("" + "tanks");
		
	// Status panel variables
    final JPanel status_panel = new JPanel();
    final JLabel playerStatus = new JLabel("Get Ready!");
    final JLabel timer = new JLabel("");
    final JSlider powerbar = new JSlider(0, 20, 0);

		// Main playing area
		field = new GameField(playerStatus, timer, powerbar);
		frame.add(field, BorderLayout.CENTER);
		
		// Add frame and status panel functionality
    frame.setLocation(500, 500);
    frame.add(status_panel, BorderLayout.SOUTH);
    
    status_panel.setLayout(new FlowLayout());
    status_panel.add(timer);
    status_panel.add(playerStatus);
		
		powerbar.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        
      }
		  
		});
    status_panel.add(powerbar);
		
		// Control Panel
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.PAGE_START);

	// Pause/Resume Buttons
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				field.reset();
			}
		});
		control_panel.add(reset);
		
		// Quit Button
		final JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
		    System.exit(0);
		  }
		});
		control_panel.add(quit);
		

		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game with instructions page;
		field.playing = false;
		field.reset();
		
		/** ADD INSTRUCTIONS
		field.instructions = true;
	  */
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
