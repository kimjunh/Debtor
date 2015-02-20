import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

/**
 * The manager for the main menu at the beginning of the game
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class MenuManager {

	// Contained objects
	private Dimension dim; // Screen dimensions
	private KeyStorage keyStorage; // Manages game key presses

	private Image beginSelect; // Play Selected Screen
	private Image instructionsSelect; // Instructions Selected Screen
	private Image creditsSelect; // Credits Selected Screen
	private Image instructions; // Instructions
	private Image credits; // Credits
	private Image fast; // Fast Mode Selected
	private Image medium; // Medium Mode Selected
	private Image slow; // Slow Mode Selected
	private Wave anamanaguchiMenuMusic; // Menu song
	
	// Fields
	private boolean selectPlay; // Currently selecting play?
	private boolean selectHelp; // Currently selecting help?
	private boolean selectCredits; // Currently selecting credits?
	public boolean playGame; // Play game?
	public boolean displayHelp; // Display Help?
	public boolean displayCredits; // Display Credits?
	public int mode; // What speed mode?
	private boolean canPressSpace; // Can press the space bar? (Prevents holding down = many presses) TODO this isnt used
	

	
	/**
	 * This constructor sets up the drawing tool (Graphics2D) and gets the screen dimensions.
	 * Other field values are also set.
	 * @param dim The dimensions of the screen.
	 */
	public MenuManager(Dimension dim){
		// Set up the menu
		this.dim = dim;
		//Declare all fields
		keyStorage = new KeyStorage();
		selectPlay = true;
		selectHelp = false;
		selectCredits = false;
		playGame = false;
		displayHelp = false;
		displayCredits = false;
		mode = 0;
		loadImages();
		canPressSpace = true;
		anamanaguchiMenuMusic = new Wave("Music/AnamanaguchiMenu.wav");
		anamanaguchiMenuMusic.start();
		
	}
	
	/**
	 * This method loads all the images for the menu
	 * AssetStorage is not used as the images are loaded based on the aspect ratio
	 */
	private void loadImages(){
		double aspectRatio = dim.getWidth() / dim.getHeight();
		// 16:9
		if ((double) Math.abs((aspectRatio - (double) 1.77)) <= (double) 0.01) {
			beginSelect = new ImageIcon("Images/Backgrounds/Menu/16-9/debtorb169.png").getImage();
			instructionsSelect = new ImageIcon("Images/Backgrounds/Menu/16-9/debtori169.png").getImage();
			creditsSelect = new ImageIcon("Images/Backgrounds/Menu/16-9/debtorc169.png").getImage();
		//16:10
		} else if ((double) Math.abs((aspectRatio - (double) 1.6)) <= (double) 0.01) {
			beginSelect = new ImageIcon("Images/Backgrounds/Menu/16-10/debtorb1610.png").getImage();
			instructionsSelect = new ImageIcon("Images/Backgrounds/Menu/16-10/debtori1610.png").getImage();
			creditsSelect = new ImageIcon("Images/Backgrounds/Menu/16-10/debtorc1610.png").getImage();
		
		// 4:3
		} else if ((double) Math.abs((aspectRatio - (double) 1.33)) <= (double) 0.01) {
			beginSelect = new ImageIcon("Images/Backgrounds/Menu/4-3/debtorb43.png").getImage();
			instructionsSelect = new ImageIcon("Images/Backgrounds/Menu/4-3/debtori43.png").getImage();
			creditsSelect = new ImageIcon("Images/Backgrounds/Menu/4-3/debtorc43.png").getImage();
		
		// 5:4
		} else if ((double) Math.abs((aspectRatio - (double) 1.25)) <= (double) 0.01) {
			beginSelect = new ImageIcon("Images/Backgrounds/Menu/5-4/debtorb54.png").getImage();
			instructionsSelect = new ImageIcon("Images/Backgrounds/Menu/5-4/debtori54.png").getImage();
			creditsSelect = new ImageIcon("Images/Backgrounds/Menu/5-4/debtorc54.png").getImage();
		//If for some reason one of the above ratios do not work
		} else {
			beginSelect = new ImageIcon("Images/Backgrounds/Menu/16-10/debtorb1610.png").getImage();
			instructionsSelect = new ImageIcon("Images/Backgrounds/Menu/16-10/debtori1610.png").getImage();
			creditsSelect = new ImageIcon("Images/Backgrounds/Menu/16-10/debtorc1610.png").getImage();
		}
		
		//Load other menu images
		instructions = new ImageIcon("Images/Backgrounds/Menu/instructions.png").getImage();
		credits = new ImageIcon("Images/Backgrounds/Menu/credits.png").getImage();
		fast = new ImageIcon("Images/Backgrounds/Menu/fast.png").getImage();
		medium = new ImageIcon("Images/Backgrounds/Menu/medium.png").getImage();
		slow = new ImageIcon("Images/Backgrounds/Menu/slow.png").getImage(); 
	}
	
	/**
	 * This method is called every "frame" and sets up what is drawn
	 * and controls user interaction with the game itself.
	 */
	public void update(){
		// Read the keys and perform corresponding output
		for (int i = 0; i < Globals.KEYS_STORED; i++){
			// Read through keys and respond correspondingly
			int key = keyStorage.getKeyAt(i);
			if (key == KeyEvent.VK_ESCAPE){
				// Exit
				System.exit(1);
			} else if (key == KeyEvent.VK_UP){
				// Select the "play" option
				if (selectHelp&&!(displayCredits||displayHelp)){
					selectPlay = true;
					selectHelp = false;
				}else if (selectCredits&&!(displayCredits||displayHelp)){
				// Select the "help" option
					selectHelp = true;
					selectCredits = false;
				}
			}else if (key == KeyEvent.VK_DOWN){
				// Select the "credit" option
				if (selectHelp&&!(displayCredits||displayHelp)){
					selectHelp = false;
					selectCredits = true;
				}else if (selectPlay&&!(displayCredits||displayHelp)){
				// Select the "help" option
					selectPlay = false;
					selectHelp = true;
				} else if ((displayCredits||displayHelp)){
				// Exit out of displaying the credit or the help
					displayCredits=false;
					displayHelp=false;
				}
			}else if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER && canPressSpace){	
				// Do what is selected
				if (selectPlay){
					anamanaguchiMenuMusic.stopped = true;
					playGame = true;
				}else if(selectHelp){
					displayHelp=true;
				}else if(selectCredits){
					displayCredits=true;
				}
				//Prevents multiple calls on one press
				canPressSpace = false;
			}else if(key == KeyEvent.VK_PAGE_UP && mode > 0 && mode <= 2){
				//Changes the mode of the game to be faster
				mode--;
			}else if(key == KeyEvent.VK_PAGE_DOWN && mode >= 0 && mode < 2){
				//Changes the mode of the game to be slower
				mode++;
			}
		}
		
		// Clear keys
		keyStorage.clearAllKeys();
	}
	
	/**
	 * This method controls the aspects of the menu's drawings.
	 */
	public void draw(Graphics2D g){
		//Draws menu images based on states
		if (selectPlay)
			g.drawImage(beginSelect, 0 , 0, (int)dim .getWidth(), (int)dim.getHeight(), null);
		else if (selectHelp)
			g.drawImage(instructionsSelect, 0, 0,(int)dim .getWidth(), (int)dim.getHeight(), null);
		else if (selectCredits)
			g.drawImage(creditsSelect, 0, 0, (int)dim .getWidth(), (int)dim.getHeight(), null);
		

		if (displayHelp)
			g.drawImage(instructions, (int) (dim.getWidth() / 2) - 400,	(int) (dim.getHeight() / 2) - 300, 800, 600, null);
		else if (displayCredits)
			g.drawImage(credits, (int) (dim.getWidth() / 2) - 400, (int) (dim.getHeight() / 2) - 300, 800, 600, null);
		
		
		if(mode == 0)
			g.drawImage(fast, dim.width - fast.getWidth(null) - 20, fast.getHeight(null) / 2, null);
		else if(mode == 1)
			g.drawImage(medium, dim.width - medium.getWidth(null) - 20, medium.getHeight(null) / 2, null);
		else if(mode == 2)
			g.drawImage(slow, dim.width - slow.getWidth(null) - 20, slow.getHeight(null) / 2, null);
	}
	
	/**
	 * This method is called when the user pushes a key down. It sends the key's 
	 * information to be stored in one of this object's sub classes.
	 *
	 * @param key The integer id of the key pressed down.
	 */
	public void keyPressed(int key){
		// Add a keypress
		keyStorage.add(key);
	}
	
	/**
	 * This method is called when the user releases a key. It sends the key's 
	 * information to be stored in one of this object's sub classes.
	 *
	 * @param key The integer id of the key released.
	 */
	public void keyReleased(int key){
		// Ensure multiple selections aren't made on one press
		if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER){
			// Can now press space bar
			canPressSpace = true;
		}
		
		// Remove the key press
		keyStorage.remove(key);
	}
}

