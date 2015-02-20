import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * This is the class that takes care of all the processes that go on in the game. Holds
 * all objects in the game, music being played, player attributes, etc.
 * Manages key presses, music played, power-ups held, and drawing.
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class GameManager {
	
	//Did not initialize fields within the constructor for eyesight's sake.
	
	// Contained Objects
	private Dimension dim; // Screen dimensions
	private Point offset = new Point(); // x and y variables determining how far the game entities should be offsetted when drawn
	private Point landing = new Point(); // y variable that determines screen movement vertically for more flowlike movement
	private KeyStorage keyStorage = new KeyStorage(); // Manages all key presses/pulls
	private LevelManager levelManager = new LevelManager(); // Manages the level loading 
	private AssetStorage assetStorage = new AssetStorage(); // Manages all the game's assets
	private QuestionManager questionManager = new QuestionManager(); //Manages all the question loading
	private Wave bitShifterGameMusic = new Wave("Music/BitShifterGame.wav"); // Game song
	private Wave museQuestionMusic = new Wave("Music/MuseQuestion.wav"); // Question song
	private Random random = new Random(); // Random generator
	private ArrayList<Thing> things = new ArrayList<Thing>(); // Holds all the game's things
	private Hero hero; // The hero
	private Font gameFont = new Font("Impact", Font.PLAIN, 25); // Set up game font
	private Font questionFont = new Font("Impact", Font.PLAIN, 20); // Set up question font
	private Font endFont = new Font("Impact", Font.PLAIN, 40); // Set up end font
	// Fields
	public boolean winner = false; // Did the player win the game?
	private boolean canJump = true; // Can the player jump at the moment?
	private boolean canSkip = true; // Can the player skip the level?
	private boolean canShoot = true; // Can the hero shoot money bullet coins? (the key release trigger)
	private boolean canRestart = true; // Can the user restart the level?
	private boolean canPowerUp = true; 
	private int endCount = Globals.LEVELEND_LENGTH; // How long till change level after completion
	private double tempMade = 0; // Temporary amount of money made for the level
	private double made = 0; // Current amount of money "made"
	private int ghosts = 0; // The number of phantom gains on the screen
	private boolean gameMusicPlaying = false; // Is the game music playing?
	private boolean questionMusicPlaying = false; // Is the question room music playing?
	private boolean loading = false; // Is the game on standby?
	private boolean coinPowerUnlocked = false; // Has the hero unlocked the coin power?
	private boolean speedPowerUnlocked = false; // Has the hero unlocked the speed power?
	private boolean invulPowerUnlocked = false; // Has the hero unlocked the invulnerability power?
	
	//QuestionRoom based
	public boolean inQuestionRoom = false; // Is the hero in the question room?
	private Point storedPoint = new Point(); //The location of the hero before entering a question room mid level
	private boolean enteredFromLevel = false; //Has the hero entered from mid level?
	private boolean enablePowerUp = false; //Has the hero gotten the question right?
	private int questionRoomAmt = 0; //How many question rooms there are in the level
	private int totalQuestionRooms = -1; // The total number of question rooms so far in the game.
	private boolean transitioning = false; // Transitioning between levels
	
	/**
	 * This constructor loads the first level and does minimal pre-game set up.
	 *
	 * @param dim The dimensions of the screen.
	 */
	public GameManager(Dimension dim){
		// Store the screen dimensions as given and load the game's first level
		this.dim = dim;
		loadLevel();
	}
	

	/**
	 * This method is called when the game actually starts, not when the object is created.
	 */
	public void startMusic(){
		// Star the game music
		bitShifterGameMusic.start();
		gameMusicPlaying = true;
	}
	
	/**
	 * This method is called at the end of the game to stop the music.
	 */
	public void stopMusic(){
		// Stop the music
		bitShifterGameMusic.stopped = true;
		museQuestionMusic.stopped = true;
	}

	/**
	 * This method is called whenever the hero leaves the question room and
	 * continues on with the game.
	 */
	public void startGameMusic(){
		// Stop the question music and start the game music
		if(!gameMusicPlaying){
			museQuestionMusic.stopped = true;
				
			if(museQuestionMusic.getState().equals(Thread.State.TERMINATED)){
				//Start playing new music when the previous music has stopped
				bitShifterGameMusic = new Wave("Music/BitShifterGame.wav");
				bitShifterGameMusic.start();
				gameMusicPlaying = true;
				questionMusicPlaying = false;
				loading = false;
			}else
				loading = true; //Put the game on standby
		}
	}
	
	/**
	 * This method is called whenever the hero enters the question room.
	 */
	public void startQuestionMusic(){
		// Stop the game music and start the question music
		if(!questionMusicPlaying){
			bitShifterGameMusic.stopped = true;
	   	
			if(bitShifterGameMusic.getState().equals(Thread.State.TERMINATED)){
				//Start playing new music when the previous music has stopped
				museQuestionMusic = new Wave("Music/MuseQuestion.wav");
				museQuestionMusic.start();
				questionMusicPlaying = true;
				gameMusicPlaying = false;
				loading = false;
			}else
				loading = true; //Put the game on standby
		}
	}
	
	/**
	 * This method unlocks the ability to use coin power up at any time.
	 */
	public void unlockCoinPower(){
		coinPowerUnlocked = true;
	}
	
	/**
	 * This method unlocks the ability to use speed power up at any time.
	 */
	public void unlockSpeedPower(){
		speedPowerUnlocked = true;
	}
	 
	/**
	 * This method unlocks the ability to use invul power up at any time.
	 */
	public void unlockInvulPower(){
		invulPowerUnlocked = true;
	}
	

	/**
	 * This method is called to check whether or not the hero has obtained enough
	 * money to unlock the coin power up.
	 */
	public boolean achievedCoin(){
		if(!coinPowerUnlocked)
			return 	(hero.getMoney() + made + tempMade) >= 300000;
		else
			return false;
	}
	
	/**
	 * This method is called to check whether or not the hero has obtained enough
	 * money to unlock the speed power up.
	 */
	public boolean achievedSpeed(){
		if(!speedPowerUnlocked)
			return 	(hero.getMoney() + made + tempMade) >= 400000;
		else
			return false;
	}
	
	/**
	 * This method is called to check whether or not the hero has obtained enough
	 * money to unlock the invulnerability power up.
	 */
	public boolean achievedInvul(){
		if(!invulPowerUnlocked)
			return 	(hero.getMoney() + made + tempMade) >= 500000;
		else
			return false;
	}
	
	/**
	 * This method is called every "frame" and changes all the objects in the game accordingly.
	 * It essentially controls all the games interactions and actions.
	 */
	public void update(int frameRate){
		
		if(!loading){ //If not on standby, update the game normally
			//Check key presses
			for(int i = 0; i < Globals.KEYS_STORED; i++){
				int key = keyStorage.getKeyAt(i);
				
				if(key == KeyEvent.VK_ESCAPE){
					System.exit(1);
				}else if(key == KeyEvent.VK_RIGHT && !hero.atLevelEnd()){
					if (hero.inAir()){
						// If in air, move left according to in-air multiplier
						hero.setVecX(Globals.PLAYER_AIR_SPEED  * hero.getVecXMultiplier());
					}else // Move normally
						hero.setVecX(Globals.PLAYER_SPEED * hero.getVecXMultiplier());
					hero.setIsMoving(true);
				}else if(key == KeyEvent.VK_LEFT && !hero.atLevelEnd()){
					if (hero.inAir()){
						// If in air, move left according to in-air multiplier
						hero.setVecX(-Globals.PLAYER_AIR_SPEED  * hero.getVecXMultiplier());
					}else // Move normally
						hero.setVecX(-Globals.PLAYER_SPEED * hero.getVecXMultiplier());
					hero.setIsMoving(true);
				}else if(key == KeyEvent.VK_UP){ //Jumping
					if(!hero.inAir() && canJump){
						new OneWave("Sounds/jump.wav").start();
						hero.setInAir(true);
						hero.setVecY(Globals.PLAYER_JUMP * hero.getVecYMultiplier());
					}
					canJump = false;
				}else if(key == KeyEvent.VK_R){
					if(canRestart){
						if(inQuestionRoom){ //restart in the question room
							loadQuestionLevel(false);
							questionManager.setAnswered(false);
							enablePowerUp = false;
						}else{ //restart in the level
							//set the question room level to the amount at the beginning of the level
							levelManager.setQuestionLevel(totalQuestionRooms);
							ghosts = 0;
							tempMade = 0;
							loadLevel();	
						}
						// Prevents restarting multiple times on one press
						canRestart = false;
					}
				}else if (key == KeyEvent.VK_N && !hero.atLevelEnd() && levelManager.getLevelNumber() <= 4){ // Level skipping 
					if (canSkip && !hero.isDead()){
						// Play sound and load next level
						new OneWave("Sounds/win.wav").start();
						
						//Add the total number of question rooms available if not transitioning between levels
						if(inQuestionRoom)
							transitioning = false;
						
						if(!transitioning)
							totalQuestionRooms+=questionRoomAmt + 1;
							
						//load the next level
						inQuestionRoom = false;
						questionManager.setAnswered(false);
						enablePowerUp = false;
						enteredFromLevel = false;
						storedPoint.setLocation(0,0);
						levelManager.setQuestionLevel(totalQuestionRooms);
						ghosts = 0;
						tempMade += hero.getMoney();
						
						loadLevel(true);
					}				
					// Prevents skiping multiple times on one press
					canSkip = false;
				}else if (key == KeyEvent.VK_Z){ // Firing Coins if the hero has the power up
					if (canShoot){
						if (hero.canShoot() && !hero.isDead()){
							new OneWave("Sounds/coin.wav").start();
							if (hero.isFlipped()){
								things.add(new MoneyBullet(313, assetStorage.getAsset(313), hero.getX() + hero.getWidth(), hero.getY(), assetStorage.getWidth(313, 0), assetStorage.getHeight(313, 0), true));
							}else{
								things.add(new MoneyBullet(313, assetStorage.getAsset(313), hero.getX() - hero.getWidth()-2, hero.getY(), assetStorage.getWidth(313, 0), assetStorage.getHeight(313, 0), false));
							}
							hero.setMoney(hero.getMoney() - 100);
						}
						//Prevents shooting multiple times on one press
						canShoot = false;
					}
				//IF UNLOCKED
				}else if(key == KeyEvent.VK_1 && coinPowerUnlocked){ //Instant Coin Power up
					if(canPowerUp)
						hero.giveCoin();
					//Prevents powering up multiple times on one press
					canPowerUp = false;
				}else if(key == KeyEvent.VK_2 && speedPowerUnlocked){ //Instant Speed Power up
					if(canPowerUp)
						hero.giveSpeed();
					//Prevents powering up multiple times on one press
					canPowerUp = false;
				}else if(key == KeyEvent.VK_3 && invulPowerUnlocked){ //Instant Invul Power up
					if(canPowerUp)
						hero.giveInvul();
					//Prevents powering up multiple times on one press
					canPowerUp = false;
				}
			}
			
			//OUTSIDE OBJECT UPDATES***********************************
			if(!hero.atLevelEnd()){
				int temp = 0;
				for(int i = 0; i < things.size(); i++){
					Thing updateThis = things.get(i);
					//Within Screen
					if ((Math.abs(updateThis.getX() - hero.getX()) < dim.getWidth()/2 + Globals.GRIDSIZE*2 && Math.abs(updateThis.getY() - hero.getY()) < dim.getHeight()/2 + Globals.GRIDSIZE*2)){
						//Ensures the object's width is set
						if (updateThis.getWidth() < 0)
							updateThis.setWidth(assetStorage.getWidth(updateThis.getID(), 0));
						
						// Ensures the object's height is set
						if (updateThis.getHeight() < 0)
							updateThis.setHeight(assetStorage.getHeight(updateThis.getID(), 0));
						
						//Update money and ghost amount if there are phantom gains on the screen
						if(updateThis.getID() == 113){
							((PhantomGain) updateThis).setMoveLoc(hero.getX(), (hero.getY()));
							hero.setMoney(hero.getMoney() - 5.0);
							temp++;
						}
						//Updates the objects within the level or room
						updateThis.update((float) 1, things, assetStorage);
					}
				}
				ghosts = temp;
			}else
				ghosts = 0;
			
			//Spawns the phantom gain randomly
			if(!inQuestionRoom && !hero.atLevelEnd() && levelManager.getLevelNumber() <= 4)
				spawn();
			
			
			//HERO UPDATES ****************************************
			
			//Ensures the Hero's width is set
			if (hero.getWidth() < 0)
				hero.setWidth(assetStorage.getWidth(hero.getID(), 0));		
			
			// Ensures the Hero's height is set
			if (hero.getHeight() < 0)
				hero.setHeight(assetStorage.getHeight(hero.getID(), 0));
			
			
			//Check to see if hero is invulnerable and changes images accordingly
			if(hero.getInvulTimer() > 0){
				if(hero.isMoving() && !hero.inAir() || hero.atLevelEnd())
					hero.setAsset(assetStorage.getAsset(104));
				else if(hero.inAir())
					hero.setAsset(assetStorage.getAsset(105));
				else
					hero.setAsset(assetStorage.getAsset(103));
			}else{
				//Changes hero's images based on movement or death
				if(hero.isDead())
					hero.setAsset(assetStorage.getAsset(106));
				else if(hero.isMoving() && !hero.inAir() || hero.atLevelEnd())
					hero.setAsset(assetStorage.getAsset(101));
				else if(hero.inAir())
					hero.setAsset(assetStorage.getAsset(102));	
				else
					hero.setAsset(assetStorage.getAsset(100));
			}
			
			//Update Accordingly for the hero
			if (levelManager.getLevelNumber() > 4){
				hero.win(true);
				hero.update(1, things, assetStorage);
			}else
				hero.update(1, things, assetStorage);
			
			
			//Reload level if hero is dead
			if(hero.getY() + hero.getHeight() + offset.getY() > dim.height){
				if (levelManager.getLevelNumber() > 4){
					// Win the game
					winner = true;
				}else if(!hero.isDead()){ 
					//Set the hero dead if the hero falls off the screen
					hero.setDead();
				}else{
					//Set the question room level to the amount at the beginning of the level
					levelManager.setQuestionLevel(totalQuestionRooms);
					ghosts = 0;
					tempMade = 0;
					loadLevel();
				}
			}	
			
			//Set up if entering question room from level
			if(hero.getEnterQRoom()){
				storedPoint.setLocation(hero.getX(), hero.getY());
				inQuestionRoom = true;
				hero.setEnterQRoom(false);
				enteredFromLevel = true;
				loadQuestionLevel(true);
				loading = true;
			}
			
			//Act Appropriately if the Hero is in the question room
			if(inQuestionRoom){
				startQuestionMusic();
				if (hero.getAnswerSelection() != 0 && !questionManager.isAnswered()){
					questionManager.setAnswered(true);
					if (hero.getAnswerSelection() == questionManager.getAnswer()){
						enablePowerUp = true;
						new OneWave("Sounds/correct.wav").start();
					} else {
						new OneWave("Sounds/wrong.wav").start();
					}
				}
			}else
				startGameMusic();
			
			//Load new level or question room after reaching the end of the level or room
			if(hero.atQuestionEnd()){
				if(enteredFromLevel){ //load the same level if entered question room mid level
					ghosts = 0;
					loadLevel();
				}else{ //load new level
					loadLevel(true);
					totalQuestionRooms++;
					transitioning = false;
				}
				inQuestionRoom = false;
				questionManager.setAnswered(false);
				enteredFromLevel = false;
				questionManager.randomizeQ();
				loading = true; //Put game on standby
			}else if(hero.atLevelEnd()){
				// Start the counter till next level
				endCount--;
				// Go to the next level
				if (endCount < 0){
					// Set countdown back to default and load the transition question room
					endCount = Globals.LEVELEND_LENGTH;
					
					totalQuestionRooms += questionRoomAmt;
					levelManager.setQuestionLevel(totalQuestionRooms);
					
					if(levelManager.getLevelNumber() < 4){	
						loadQuestionLevel(true);
						inQuestionRoom = true;
						storedPoint.setLocation(0, 0);
						transitioning = true;
						loading = true;
					}else
						loadLevel(true);
				}
			}
		}else{ //Put the game on standby until the music is loaded
			if(inQuestionRoom)
				startQuestionMusic();
			else
				startGameMusic();
		}
	}
	
	/**
	 * This method goes through all the in game objects and draws them onto the screen.
	 */
	public void draw(Graphics2D g){
		
		if(!loading){ //If the game is not on standby	
			
			//Set coordinates for the drawing
			float dx = (float) ((dim.getWidth()/2 - hero.getX()) - landing.getX())/10;
			float dy = (float) ((dim.getHeight()/2 - hero.getY()) - landing.getY())/10;
			landing.setLocation(landing.getX() + dx, landing.getY() + dy); // Smooth out vertical movement
			
			if(!hero.isDead())
				offset.setLocation(dim.getWidth()/2 - hero.getX(), offset.getY() + dy);
			
			
			if(inQuestionRoom){ //Draw question room based images
				int bgX = 0;
				g.drawImage(assetStorage.getAsset(1).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 2.5 + offset.getY()), null);
			}else{ //Draw the games background based on level
				int levelNum = levelManager.getLevelNumber(); // Level number
				
				// If not the last level
				if (levelNum <= 4){
					int id = levelNum + 30;
					int bgX = -assetStorage.getWidth(id, 0); // x position in process of drawing backgrounds sequentially
					
					for(int i = 0; i < Math.abs(levelManager.getWidth() * Globals.GRIDSIZE / assetStorage.getAsset(id).getWidth(0) + 1); i++){
						// Draw the backgrounds
						if ((bgX + (offset.getX())/2) < dim.getWidth() || (bgX + (offset.getX())/2 + assetStorage.getWidth(id, 0)) > 0){
							// Specifics to levels and phantom gain numbers
							if (levelNum == 0){
								if(ghosts > 2)
									g.drawImage(assetStorage.getAsset(id+15).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 2.5 + offset.getY()), null);
								else if(ghosts > 0)
									g.drawImage(assetStorage.getAsset(id+10).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 2.5 + offset.getY()), null);
								else
									g.drawImage(assetStorage.getAsset(id).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 2.5 + offset.getY()), null);
							}else if(levelNum == 1){
								if(ghosts > 2)
									g.drawImage(assetStorage.getAsset(id+15).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 1.5 + offset.getY()), null);
								else if(ghosts > 0)
									g.drawImage(assetStorage.getAsset(id+10).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 1.5 + offset.getY()), null);
								else
									g.drawImage(assetStorage.getAsset(id).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 1.5  + offset.getY()), null);
							}else if(levelNum == 2){
								if(ghosts > 2)
									g.drawImage(assetStorage.getAsset(id+15).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 2.5 + offset.getY()), null);
								else if(ghosts > 0)
									g.drawImage(assetStorage.getAsset(id+10).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 2.5 + offset.getY()), null);
								else
									g.drawImage(assetStorage.getAsset(id).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 2.5 + offset.getY()), null);
							}else if(levelNum == 3){
								if(ghosts > 2)
									g.drawImage(assetStorage.getAsset(id+15).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 15 + offset.getY()), null);
								else if(ghosts > 0)
									g.drawImage(assetStorage.getAsset(id+10).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 15 + offset.getY()), null);
								else
									g.drawImage(assetStorage.getAsset(id).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 15 + offset.getY()), null);
							}else if(levelNum == 4){
								if(ghosts > 2)
									g.drawImage(assetStorage.getAsset(id+15).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 22 + offset.getY()), null);
								else if(ghosts > 0)
									g.drawImage(assetStorage.getAsset(id+10).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 22 + offset.getY()), null);
								else
									g.drawImage(assetStorage.getAsset(id).getFrame(0), (int)(bgX + offset.getX() / 5), (int)(-dim.getHeight() / 22 + offset.getY()), null);
	
							}
							bgX += assetStorage.getWidth(id, 0); // Continue onward with drawing backgrounds
						} 
					}
				}else{ //Draw the win screen
					g.drawImage(assetStorage.getAsset(3).getFrame(0), 0, 0, null);
					g.drawImage(assetStorage.getAsset(2).getFrame(0), (int) (dim.width / 2 - assetStorage.getWidth(2, 0) / 2), (int) (dim.getHeight() / 8), null);
				}
			}
	
			//Draw All Things on Level
			for(int i = 0; i < things.size(); i++){
				// Draw the thing
				Thing obj = things.get(i);
				
				// If on screen
				if (obj.getX() + offset.getX() < dim.getWidth() + Globals.GRIDSIZE && obj.getX() + offset.getX() > -Globals.GRIDSIZE && obj.getY() + offset.getY() < dim.getHeight() + Globals.GRIDSIZE &&  obj.getY() + offset.getY() > -Globals.GRIDSIZE){
					if(obj.getID() == 98 && !questionManager.isAnswered()){
					}else{					
						Image image = obj.getImage();
						// Get variables of the image
						int x = (int) (obj.getX() + offset.getX());
						int y = (int) (obj.getY() + offset.getY());
						int width = image.getWidth(null);
						int height = image.getHeight(null);
						
						//If image facing right
						if (obj.isFlipped()){	
							// Draw image facing right
							g.drawImage(image, x + width, y, x, y + height, 0, 0, width, height, null);			
						} else{
							// Draw image facing left
							g.drawImage(image, x, y, null);
						}
					}
				}
			}
					
			//Draw the hero
			if (hero != null){
				// Flip image
				Image image = hero.getImage();
				
				// Get variables of the image
				int x = (int) (hero.getX() + offset.getX());
				int y = (int) (hero.getY() + offset.getY());
				int width = image.getWidth(null);
				int height = image.getHeight(null);
					
				// If hero facing right
				if (hero.isFlipped()){	
					// Draw player facing right
					g.drawImage(hero.getImage(), x + width, y, x, y + height, 0, 0, width, height, null);
				} else{
					// Draw hero facing left
					g.drawImage(image, x, y, null);
				}
			}
			
			
			g.setFont(questionFont); //Set question Font
			
			//Draw the questions if the hero is in the question room
			if(inQuestionRoom){
				questionManager.draw(g, dim, assetStorage);
				if(enablePowerUp){ // Draw the power up if earned
					int pID = levelManager.getPowerID();
					Image image = assetStorage.getAsset(pID).getFrame(0);
					g.drawImage(image, (int)(hero.getX() + offset.getX()), (int)(hero.getY() + offset.getY()) - 32, null);
				}
					
			}
			
			g.setFont(gameFont);//Set game font
			
			// Get the total amount of money the player has made based on location
			double money;
			
			if(inQuestionRoom)
				money = made + tempMade;
			else
				money = hero.getMoney() + made + tempMade;
			
			// Draw HUD
			if(levelManager.getLevelNumber() <= 4){
				g.setColor(Color.BLACK);
				g.drawString("Money Earned $" + money, 3, (int) (dim.getHeight() / 20) - 2);
				g.drawString("Money Earned $" + money, 3, (int) (dim.getHeight() / 20) + 2);
				g.setColor(new Color(0, 235, 0));
				g.drawString("Money Earned $" + money, 5, (int) (dim.getHeight() / 20));
			}else{
				g.setFont(endFont);
				g.setColor(Color.BLACK);
				g.drawString("TOTAL MONEY EARNED $" + money, 3, (int) (dim.getHeight() / 20) - 2);
				g.drawString("TOTAL MONEY EARNED $" + money, 3, (int) (dim.getHeight() / 20) + 2);
				g.setColor(new Color(0, 235, 0));
				g.drawString("TOTAL MONEY EARNED $" + money, 5, (int) (dim.getHeight() / 20));
				
				if(achievedInvul()){
					g.drawImage(assetStorage.getAsset(7).getFrame(0), (int) (dim.width / 2 - assetStorage.getWidth(7, 0) / 2), (int) (dim.getHeight() * 13 / 16), null);
				}else if(achievedSpeed()){
					g.drawImage(assetStorage.getAsset(6).getFrame(0), (int) (dim.width / 2 - assetStorage.getWidth(6, 0) / 2), (int) (dim.getHeight() * 13 / 16), null);
				}else if(achievedCoin()){
					g.drawImage(assetStorage.getAsset(5).getFrame(0), (int) (dim.width / 2 - assetStorage.getWidth(5, 0) / 2), (int) (dim.getHeight() * 13 / 16), null);
				}
	
			}
		}else{ //Draw the standby screen
			g.setColor(new Color(72, 209, 204));
			g.fillRect(0, 0, dim.width, dim.height);
			g.drawImage(assetStorage.getAsset(4).getFrame(0), (int) (dim.width / 2 - assetStorage.getWidth(4, 0) / 2), (int) (dim.getHeight() / 8), null);
		}
	}
	
	
	/**
	 * This method randomly (like bad luck) spawns the Phantom Gains
	 */
	private void spawn(){
		int r = random.nextInt(9000); //randomly generate the phantom gains
		
		switch(r){
			case 0: things.add(new PhantomGain(113, assetStorage.getAsset(113), (float)(hero.getX() + dim.getWidth() / 2), (float)(hero.getY() + dim.getHeight() / 2), assetStorage.getWidth(113, 0), assetStorage.getHeight(113, 0)));
					new OneWave("Sounds/ghost.wav").start();
					break;
			case 1: things.add(new PhantomGain(113, assetStorage.getAsset(113), (float)(hero.getX() + dim.getWidth() / 2), hero.getY(), assetStorage.getWidth(113, 0), assetStorage.getHeight(113, 0)));
					new OneWave("Sounds/ghost.wav").start();
					break;
			case 2: things.add(new PhantomGain(113, assetStorage.getAsset(113), (float)(hero.getX() + dim.getWidth() / 2), (float)(hero.getY() - dim.getHeight() / 2), assetStorage.getWidth(113, 0), assetStorage.getHeight(113, 0)));
					new OneWave("Sounds/ghost.wav").start();
					break;
			case 3: things.add(new PhantomGain(113, assetStorage.getAsset(113), hero.getX(), (float)(hero.getY() + dim.getHeight() / 2), assetStorage.getWidth(113, 0), assetStorage.getHeight(113, 0)));
					new OneWave("Sounds/ghost.wav").start();
					break;
			case 4: things.add(new PhantomGain(113, assetStorage.getAsset(113), hero.getX(), (float)(hero.getY() - dim.getHeight() / 2), assetStorage.getWidth(113, 0), assetStorage.getHeight(113, 0)));
					new OneWave("Sounds/ghost.wav").start();
					break;
			case 5: things.add(new PhantomGain(113, assetStorage.getAsset(113), (float)(hero.getX() - dim.getWidth() / 2), (float)(hero.getY() + dim.getHeight() / 2), assetStorage.getWidth(113, 0), assetStorage.getHeight(113, 0)));
					new OneWave("Sounds/ghost.wav").start();
					break;
			case 6: things.add(new PhantomGain(113, assetStorage.getAsset(113), (float)(hero.getX() - dim.getWidth() / 2), hero.getY(), assetStorage.getWidth(113, 0), assetStorage.getHeight(113, 0)));
					new OneWave("Sounds/ghost.wav").start();
					break;
			case 7: things.add(new PhantomGain(113, assetStorage.getAsset(113), (float)(hero.getX() - dim.getWidth() / 2), (float)(hero.getY() - dim.getHeight() / 2), assetStorage.getWidth(113, 0), assetStorage.getHeight(113, 0)));
					new OneWave("Sounds/ghost.wav").start();
					break;
		}
	}
	
	
	/**
	 * This method generates the level entities and stores them.
	 */
	public void loadLevel(){	
		
		// Loads level, prepares reading
		things.clear(); // Clears out past entities
		questionRoomAmt = 0;
		
		int[][] level = levelManager.loadLevel(); // Gets the integer array of the level's entities
		int gridsize = Globals.GRIDSIZE; // Grid size
				
		// Find the offset of the level
		offset.setLocation(0, ((dim.getHeight() - levelManager.getHeight() * gridsize) / 2));
		
		// For all the level columns
		for (int y = 0; y < level.length; y++){
			// For all the level rows
			for (int x = 0; x < level[y].length; x++){
				// Depending on id of entity, add specific entity to list
				if (level[y][x] >= 300){
					// Add Items
					int id = level[y][x];
					things.add(new Thing(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, gridsize, gridsize));
					if(id == 999)
						questionRoomAmt++;
				} else if (level[y][x] > 199){
					// Add Blocks
					int id = level[y][x];
					things.add(new Thing(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
				} else if (level[y][x] > 109){
					// Add Characters(enemy)
					int id = level[y][x];
					if(id == 110)
						things.add(new GreenLoanShark(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
					else if(id == 111)
						things.add(new BlueLoanShark(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
					else if(id == 112)
						things.add(new BlackLoanShark(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
					else if(id == 113){
						things.add(new PhantomGain(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
					}
				}else if (level[y][x] == 100){
					// Add hero
					int id = level[y][x];
					hero = new Hero(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0));
					offset.setLocation(dim.getWidth() / 2 - x * gridsize, dim.getHeight() / 2 - y * gridsize);
					landing.setLocation(dim.getWidth() / 2 - x * gridsize, dim.getHeight() / 2 - y * gridsize);					
				}else if(level[y][x] == 99){
					//Add the level end
					int id = level[y][x];
					things.add(new Thing(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
				}else{
					// Add nothing
				}
			}
		}
		
		//Adds and removes certain objects if the question was loaded mid level
		if(enteredFromLevel){
			hero.setX((float)storedPoint.getX());
			hero.setY((float)storedPoint.getY());
			
			for(int i = 0; i < things.size(); i++){
				Thing obj = things.get(i);
				if(obj.getID() == 999){ //Removes the question room item to prevent entering
					if(Math.abs(obj.getX() - hero.getX()) < 50){
						things.remove(i);
						if(enablePowerUp){ //Adds the power up if earned
							int pID = levelManager.getPowerID();
							things.add(new Thing(pID, assetStorage.getAsset(pID), hero.getX(), hero.getY() - hero.getHeight() / 2,  assetStorage.getWidth(pID, 0), assetStorage.getHeight(pID, 0)));
							enablePowerUp = false;
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * This method generates the next levels entities and stores them.
	 */
	public void loadLevel(boolean pass){
		// Add to the money
		if(levelManager.getLevelNumber() < 4)
			made += tempMade;
		else
			made += tempMade + hero.getMoney();
		tempMade = 0;
		// Loads level, prepares reading
		things.clear();
		questionRoomAmt = 0;
		
		int[][] level = levelManager.loadLevel(true); // Integer array of all the level's entities
		int gridsize = Globals.GRIDSIZE; // Gridsize
		
		// Gets the title image for the specific level
		
		// Find the offset of the level
		offset.setLocation(0, ((dim.getHeight() - levelManager.getHeight()*gridsize) / 2));
		
		// For all the level columns
		for (int y = 0; y < level.length; y++){
			// For all the level rows
			for (int x = 0; x < level[y].length; x++){
				// Depending on id of entity, add specific entity to list
				if (level[y][x] >= 300){
					// Add Items
					int id = level[y][x];
					things.add(new Thing(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, gridsize, gridsize));
					if(id == 999)
						questionRoomAmt++;
				} else if (level[y][x] > 199){
					// Add Blocks
					int id = level[y][x];
					things.add(new Thing(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
				} else if (level[y][x] > 109){
					// Add Characters(enemy)
					int id = level[y][x];
					if(id == 110)
						things.add(new GreenLoanShark(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
					else if(id == 111)
						things.add(new BlueLoanShark(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
					else if(id == 112)
						things.add(new BlackLoanShark(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
					else if(id == 113){
						things.add(new PhantomGain(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
					}
				}else if (level[y][x] == 100){
					// Add hero
					int id = level[y][x];
					hero = new Hero(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0));
					offset.setLocation(dim.getWidth() / 2 - x * gridsize, dim.getHeight() / 2 - y * gridsize);
					landing.setLocation(dim.getWidth() / 2 - x * gridsize, dim.getHeight() / 2 - y * gridsize);					
				}else if(level[y][x] == 99){
					//Add the level end
					int id = level[y][x];
					things.add(new Thing(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
				}else{
					//Add nothing
				}
			}
		}
		
		if(enablePowerUp){ //Add power up if earned
			int pID = levelManager.getPowerID();
			things.add(new Thing(pID, assetStorage.getAsset(pID), hero.getX(), hero.getY() - hero.getHeight(),  assetStorage.getWidth(pID, 0), assetStorage.getHeight(pID, 0)));
			enablePowerUp = false;
		}
		
		// Play winning money sound
		if (levelManager.getLevelNumber() == 5){
			// Play the sound
			new OneWave("Sounds/money.wav").start();
		}
	}
	
	/**
	 * This method generates the level entities and stores them.
	 */
	public void loadQuestionLevel(boolean pass){
		// Loads level, prepares reading
		inQuestionRoom = true;
		
		//Add to money
		if(hero != null)
			tempMade += hero.getMoney();
		
		things.clear(); // Clears out past entities
		int[][] level = levelManager.loadQuestionLevel(pass); // Gets the integer array of the level's entities
		int gridsize = Globals.GRIDSIZE; // Grid size
		
		// Find the offset of the level
		offset.setLocation(0, ((dim.getHeight() - levelManager.getHeight() * gridsize) / 2));
		
		// For all the level columns
		for (int y = 0; y < level.length; y++){
			// For all the level rows
			for (int x = 0; x < level[y].length; x++){
				// Depending on id of entity, add specific entity to list
				if (level[y][x] >= 300){
					// Add Items
					int id = level[y][x];
					things.add(new Thing(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, gridsize, gridsize));
				} else if (level[y][x] > 199){
					// Add Blocks
					int id = level[y][x];
					things.add(new Thing(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
				} else if (level[y][x] > 109){
					// Add Characters(enemy)
					int id = level[y][x];
					things.add(new GreenLoanShark(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
				}else if (level[y][x] == 100){
					// Add hero
					int id = level[y][x];
					hero = new Hero(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0));		
					offset.setLocation(dim.getWidth() / 2 - x * gridsize, dim.getHeight() / 2 - y * gridsize);
					landing.setLocation(dim.getWidth() / 2 - x * gridsize, dim.getHeight() / 2 - y * gridsize);				
				}else if(level[y][x] == 98){
					//Add the question level end
					int id = level[y][x];
					things.add(new Thing(id, assetStorage.getAsset(id), x * gridsize, y * gridsize, assetStorage.getWidth(id, 0), assetStorage.getHeight(id, 0)));
				}else{
					// Add nothing
				}
			}
		}
	}
	
	
	/**
	 * This method is called when the user pushes a key down. It sends the key's 
	 * information to be stored in one of this object's sub classes.
	 *
	 * @param key The integer id of the key pressed down.
	 */
	public void keyPressed(int key){
		// Add the entered key press
		keyStorage.add(key);
	}
	
	/**
	 * This method is called when the user releases a key. It sends the key's 
	 * information to be stored in one of this object's sub classes.
	 *
	 * @param key The integer id of the key released.
	 */
	public void keyReleased(int key){
		// Prevents jumping by holding up
		if (key == KeyEvent.VK_UP){
			// Can jump again
			canJump = true;
		}else if (key == KeyEvent.VK_N){
			// Can skip the level again
			canSkip = true;
		}else if(key == KeyEvent.VK_R){
			// Can restart the level again
			canRestart = true;
		}else if (key == KeyEvent.VK_Z){
			// Can shoot again
			canShoot = true;
		}else if (key == KeyEvent.VK_1 || key == KeyEvent.VK_2 || key == KeyEvent.VK_3){
			// Can Instant Power Up again
			canPowerUp = true;
		}
		
		// Remove the entered key
		keyStorage.remove(key);
	}


}
