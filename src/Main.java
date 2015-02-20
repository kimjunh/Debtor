import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Main initializes everything and starts the game.
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class Main extends JPanel implements ActionListener, KeyListener{
	
	// Contained objects
	private Timer clock; // Timer that refreshes the game
	private GameManager game; // The class that manages all of the game content
	private MenuManager menu; // The class that manages all of the menu content

	// Fields
	private boolean graphicsSet; // Has the GameManager and the MenuManager been initialized yet?
	private boolean atMenu; // Is the player at the Menu
	private int frameRate; // Set framerate
	private boolean coinPowerUnlocked; // Has the hero unlocked the coin power?
	private boolean speedPowerUnlocked; // Has the hero unlocked the speed power?
	private boolean invulPowerUnlocked; // Has the hero unlocked the invulnerability power?
	public static final long serialVersionUID = 24362462L;
	
	
	/**
	 * This constructor sets up the Timer to mock up a refresh rate for the application.
	 * Other field values for the game are also set.
	 */
	public Main(){
		graphicsSet = false;
		atMenu = true;
		frameRate = Globals.FRAME_RATE_0;
		coinPowerUnlocked = false;
		speedPowerUnlocked = false;
		invulPowerUnlocked = false;
		
		// Sets up the refresh (corresponding to the timer itself)
		clock = new Timer(frameRate, this);
		clock.setRepeats(false);
		clock.start();
	}
	
	/**
	 * This method initializes the drawing window and the various listeners for user input.
	 *
	 * @param args The standard string array in creating a Java application.
	 */
	public static void main(String[] args){
		// Set up the window, maximize the screen itself, and set up a window
    	JFrame w = new JFrame("Debtor");
	    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    w.setExtendedState(Frame.MAXIMIZED_BOTH);
	    w.setUndecorated(true);  
	    
	    // Creates and adds container within the window
	    Container c = w.getContentPane();
		c.add(new Main());
	    
	    // Creates a drawing panel in the container
	    JPanel panel = new Main();
	    panel.setBackground(Color.BLACK);
	    
	    // Adds the drawing panel
	    w.getContentPane().add(panel);
	    w.setVisible(true);
	    w.setResizable(false);
	    
	    // Add a KeyListener
	    w.addKeyListener((KeyListener)panel);
	}
	
	/**
	 * This method sends drawing instruction responsibility to a lower class (for organization purposes).
	 * It also converts the old Graphics class into the new Graphics2D class to help drawing.
	 *
	 * @param g The tool used to draw directly into a buffer, then onto a screen.
	 */
	public void paintComponent(Graphics g){
		// Sets up drawing
		super.paintComponent(g);
  		setOpaque(true);
  		
  		// Get the dimensions of the screen for drawing
  		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
  		
  		// Creates the lower level manager for drawing game content
  		if (!graphicsSet){
  			// This sets up the class that will have the drawing instruction responsibility
  			menu = new MenuManager(dim);
  			game = new GameManager(dim);
  			graphicsSet = true; // GameManager and MenuManager has been initialized
  		}
  		
  		// If at menu
  		if (atMenu){
  			// Draw the menu
  			menu.draw((Graphics2D) g);
  			
  			// If told to play game
  			if (menu.playGame){
  				// Lower and raise game speed depending on option selected
  				if (menu.mode == 0){
  					// Fast
  					frameRate = Globals.FRAME_RATE_0;
  		  		} else if(menu.mode == 1){
  					// Medium
  					frameRate = Globals.FRAME_RATE_2;
  		  		}else if(menu.mode == 2){
  		  			// Slow
  		  			frameRate = Globals.FRAME_RATE_4;	
  		  		}
  				// Exit menu and start game
  				game.startMusic();
  				atMenu = false;
  				
  			}
  		}else{
  			// Otherwise, draw the game itself
  			game.draw((Graphics2D)g);
  		}
			
  		// If the game is won, start up again
  		if (game.winner){
  			// Restart MenuManager and GameManager and send to menu
  			game.stopMusic();
  			
  			//Unlock achievements
  			if(game.achievedInvul())
  				invulPowerUnlocked = true;
  			else if(game.achievedSpeed())
  				speedPowerUnlocked = true;
  			else if(game.achievedCoin())
  				coinPowerUnlocked = true;
  			
  			menu = new MenuManager(dim);
  			game = new GameManager(dim);
  			
  			//Restart game with achievements if unlocked
  			if(invulPowerUnlocked)
  				game.unlockInvulPower();
  			if(speedPowerUnlocked)
  				game.unlockSpeedPower();
  			if(coinPowerUnlocked)
  				game.unlockCoinPower();
  			
  			atMenu = true; // Send to menu
  		}
	}
	
	/**
	 * This method is called at every "rate" of the timer
	 *
	 * @param evt The event passed in at any action.
	 */
	public void actionPerformed(ActionEvent evt){
		// Update the appropriate manager
		if (graphicsSet){
			// If at the menu
			if (atMenu){
				// Update the menu
				menu.update();
			}else{
				// Otherwise, update the game
				game.update(frameRate);
			}
		}
		
		// Draw again
		repaint();
		
		// Creates a new timer till the next refresh
		clock.stop();
		clock = new Timer(frameRate, this);
		clock.setRepeats(false);
		clock.start();
	}
	
	/**
	 * This method is called when the user pushes a key down.
	 *
	 * @param e The event passed in when the user pushes a key down.
	 */
	public void keyPressed(KeyEvent e){
		// Passes storage/usage responsibility to a lower class
		menu.keyPressed(e.getKeyCode());
		game.keyPressed(e.getKeyCode());
		
		//Changes the frame rate of the game according to key presses
		if (e.getKeyCode() == KeyEvent.VK_PAGE_UP){
			// Faster
			if(frameRate == Globals.FRAME_RATE_4)
				frameRate = Globals.FRAME_RATE_2;
			else if(frameRate == Globals.FRAME_RATE_2)
				frameRate = Globals.FRAME_RATE_0;
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN){
			// Slower
			if(frameRate == Globals.FRAME_RATE_0)
				frameRate = Globals.FRAME_RATE_2;
			else if(frameRate == Globals.FRAME_RATE_2)
				frameRate = Globals.FRAME_RATE_4;
		}
	} 
	
	/**
	 * This method is called when the user releases any key.
	 *
	 * @param e The event passed in when the user releases any key.
	 */
	public void keyReleased(KeyEvent e){
		// Passes storage responsibility to a lower class
		menu.keyReleased(e.getKeyCode());
		game.keyReleased(e.getKeyCode());
	} 
	
	/**
	 * This method is called when the user pushes a key down and releases it.
	 *
	 * @param e The event passed in when the user pushes a key down and releases it.
	 */
	public void keyTyped(KeyEvent e) {}


}
