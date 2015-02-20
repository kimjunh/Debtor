import java.util.ArrayList;

/**
 * Hero is a Character and the main protagonist of the game that the user controls.
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class Hero extends Character{
	
	private float vecXMult; //VecX Multiplier
	private float vecYMult; //VecY Multiplier
	private double money; // Money obtained
	private boolean dead; // Is the player dead?
	private boolean levelEnd; // Is the player at the end of the level?
	private boolean questionEnd;
	private boolean win; // Has the player won the game?
	private int invulKillTime; //Invulnerable time after killing enemy
	private int answerSelection; // 0 = none, 1 = a, 2 = b, 3 = c, 4 = d
	private int speedUpTime; //Amount of time for speed power up
	private int invulPowerTime; //Amount of time invulnerable for power up
	private int moneyBulletTime; //Amount of time for money shooting power up
	private boolean enterQRoom; //Is the Hero entering the question room mid game?
	
	/**
	 * Constructs a Hero
	 * 
	 * @param id The asset ID of the Hero
	 * @param asset The Asset object of the Hero
	 * @param x x-coordinate location
	 * @param y y-coordinate location
	 * @param width width of Hero
	 * @param height height of Hero
	 */
	public Hero(int id, Asset asset, float x, float y, int width, int height) {
		super(id, asset, x, y, width, height, 0);
		vecXMult = 1;
		vecYMult = 1;
		money = 0;
		flip = true;
		dead = false;
		levelEnd = false;
		win = false;
		invulKillTime = 0;
		speedUpTime = 0;
		invulPowerTime = 0;
		moneyBulletTime = 0;
		enterQRoom = false;
	}
	
	/**
	 * Sets up appropriate values, such as movement and power up timers, for the hero.
	 */
	protected void setUp(){
		super.setUp();
		// If not moving
		if (!isMoving){
			// Slow down
			if (vecX > 0){
				// Subtract based on friction and movement direction
				vecX -= Globals.PLAYER_FRICTION;
				if(vecX < 0)
					vecX = 0;
			} else if (vecX < 0){
				// Subtract based on friction and movement direction
				vecX += + Globals.PLAYER_FRICTION;
				if(vecX > 0)
					vecX = 0;
			} 
		}
		
		if(!win)
			vecY += Globals.PLAYER_ADDED_FALL_SPEED;
		else{
			vecY += -.2;
		}
		// If dead
		if (dead)
			// Don't move left or right
			vecX = 0;
		
		// If at the end of the level
		if (levelEnd){
			// Move standard speed
			if(flip)
				vecX = Globals.PLAYER_SPEED / 3;
			else
				vecX = -Globals.PLAYER_SPEED / 3;
		}
		
		//Subtract the times for the power ups if activated
		if(invulKillTime >= 0)
			invulKillTime--;
		if(invulPowerTime >= 0)
			invulPowerTime--;
		if(moneyBulletTime >= 0)
			moneyBulletTime--;
		if(speedUpTime <= 0){
			vecXMult = 1;
			vecYMult = 1;
		}else
			speedUpTime--;
	}
	
	/**
	 * Test if the hero has collided with another thing.
	 * @param collided The object to test collision with
	 * @return Returns whether or not it has collided with the object
	 */
	public boolean hasCollided(Thing collided){
		if(x + width > collided.getX() && x < collided.getX() + collided.getWidth() && y + height > collided.getY() && y < collided.getY() + collided.getHeight() && !dead)
			return true;
		else
			return false;
		
	}
	

	/**
	 * Call this after the hero collides with an enemy to kill the hero or enemy depending
	 * on the actions.
	 * @param things list of things in the game
	 * @param loc the location of the enemy in the arraylist
	 * @param assetStorage the library of assets
	 */

	protected void collidedEnemy(ArrayList<Thing> things, int loc, AssetStorage assetStorage){
		//Check collisions if the hero has not reached the end of the level
		if(!levelEnd){
			Thing enemy = things.get(loc);
			
			if(inAir && vecY > 0 || invulKillTime > 0 || invulPowerTime > 0){
				//If the hero is not invulnerable, kill the enemy normally
				if(invulPowerTime <= 0){
					setVecY(Globals.PLAYER_JUMP / (float) 1.5);
					inAir = true;
				}
				
				//Start the kill music
				new OneWave("sounds/kill.wav").start();
				
				//Remove the enemy and add money
				things.remove(loc);
				things.add(new Thing(300, assetStorage.getAsset(300), enemy.getX(), enemy.getY() + enemy.getHeight() / 2, assetStorage.getWidth(300, 0), assetStorage.getHeight(300, 0)));
				
				// Restart short invulnerability counter
				invulKillTime = Globals.PLAYER_INVUL_KILL_TIME;
			}else
				setDead(); //Set hero dead
		}
	}
	
	/**
	 * Call this once it has collided with an item to interact with money/power-ups
	 * @param things the list of things in the game
	 * @param loc location in the arraylist of the item it collided with
	 */
	protected void collidedItem(ArrayList<Thing> things, int loc){
		Thing item = things.get(loc);
		
		if(item.getID() == 300){ //Money
			new OneWave("Sounds/money.wav").start();
			money+=1000;	
		}else if(item.getID() == 312){ //Speed
			new OneWave("Sounds/powerup.wav").start();
			invulPowerTime = 0;
			moneyBulletTime = 0;
			vecXMult = (float) 1.5;
			vecYMult = (float) 1.2;
			speedUpTime = Globals.PLAYER_SPEED_TIME;
		}else if(item.getID() == 310){ //Invulnerability
			new OneWave("Sounds/powerup.wav").start();
			speedUpTime = 0;
			moneyBulletTime = 0;
			invulPowerTime = Globals.PLAYER_INVUL_POWER_TIME;
		}else if (item.getID() == 311){//Coin or MoneyBullet
			new OneWave("Sounds/powerup.wav").start();
			invulPowerTime = 0;
			speedUpTime = 0;
			moneyBulletTime = Globals.PLAYER_MONEY_BULLET_TIME;
		}
		//Remove the item
		things.remove(loc);
	}
	
	/**
	 * Call this once it has collided with the question room
	 * to move the player to the question room
	 */
	protected void collidedQuestionRoom(){
		new OneWave("Sounds/door.wav").start(); //Play entering sound
		enterQRoom = true;
	}
	
	/**
	 * Call this once the player has reached the end of the level
	 */
	protected void collidedLevelEnd(){
		if(!levelEnd){
			new OneWave("Sounds/win.wav").start(); //Play the win sound
			
			levelEnd = true;
			
			//Set the movement values of the hero upon reaching the end of the level
			if(flip)
				vecX = Globals.PLAYER_SPEED / 3;
			else
				vecX = -Globals.PLAYER_SPEED / 3;
			vecY = Globals.PLAYER_JUMP;
		}
	}
	
	/**
	 * Call this once the player has reached the end of the question level room
	 */
	protected void collidedQuestionEnd(){
		//Resets answer selection
		if(answerSelection != 0){
			if(!questionEnd){
				questionEnd = true;
				answerSelection = 0;
			}
		}
	}
	
	/**
	 * Set the winning of the player
	 * @param hasWon whether or not the player has won
	 */
	public void win(boolean hasWon){
		win = hasWon;
	}
	
	/**
	 * Kills the player
	 */
	public void setDead(){
		new OneWave("Sounds/die.wav").start(); // Play death sound
		
		//Set appropriate values of the hero when dead
		dead = true;
		inAir = true;
		isMoving = false;
		vecX = 0;
		vecY = (float) (Globals.PLAYER_JUMP /1.5);
	}
	
	/**
	 * Gives the player the movespeed buff, disabling other powerups
	 */
	public void giveSpeed(){
		new OneWave("Sounds/powerup.wav").start(); //Play powerup sound
		//Set appropriate values for giving hero speed
		invulPowerTime = 0;
		moneyBulletTime = 0;
		vecXMult = (float) 1.5;
		vecYMult = (float) 1.2;
		speedUpTime = Globals.PLAYER_SPEED_TIME;
	}
	
	/**
	 * Gives the player the invulnerability buff, disabling other power-ups
	 */
	public void giveInvul(){
		new OneWave("Sounds/powerup.wav").start(); //Play powerup sound
		//Set appropriate values for giving hero invulnerability
		speedUpTime = 0;
		moneyBulletTime = 0;
		invulPowerTime = Globals.PLAYER_INVUL_POWER_TIME;
	}
	
	/**
	 * Gives the player the coin-shooting buff, disabling other power-ups
	 */
	public void giveCoin(){
		new OneWave("Sounds/powerup.wav").start(); //Play powerup sound
		//Set appropriate values for giving hero coin power
		invulPowerTime = 0;
		speedUpTime = 0;
		moneyBulletTime = Globals.PLAYER_MONEY_BULLET_TIME;
	}
	
	/**
	 * Enters the player into the question room
	 * @param inRoom set whether or not the player is in the room
	 */
	public void setEnterQRoom(boolean inRoom){
		enterQRoom = inRoom;
	}
	
	/**
	 * Return whether or not the player is in the question room
	 */
	public boolean getEnterQRoom(){
		return enterQRoom;
	}
	
	/**
	 * Sets the amount of money the hero has
	 * @param newMoney the new amount of money the hero has
	 */
	public void setMoney(double newMoney){
		money = newMoney;
	}

	/**
	 * Answer selection during the multiple choice question room
	 * @param choice the answer chosen by the player
	 */
	public void setAnswerSelection(int choice){
		answerSelection = choice;
	}
	
	/**
	 * Return the answer chosen by player in question room
	 */
	public int getAnswerSelection(){
		return answerSelection;
	}
	
	/**
	 * Return the x vector movement multiplier value
	 */
	public float getVecXMultiplier() {
		return vecXMult;
	}
	
	/**
	 * Return the y vector movement multiplier value
	 */
	public float getVecYMultiplier() {
		return vecYMult;
	}
	
	/**
	 * Return the amount of time level for the invulnerability power
	 */
	public int getInvulTimer(){
		return invulPowerTime;
	}
	
	/**
	 * Return the amount of money the hero has
	 */
	public double getMoney(){
		return money;
	}
	
	/**
	 * Return true if the hero is dead, false if otherwise
	 */
	public boolean isDead(){
		return dead;
	}
	
	/**
	 * Return true if the hero is at the level end, false if otherwise
	 */
	public boolean atLevelEnd(){
		return levelEnd;
	}
	
	/**
	 * Return true if the hero is at the question level end, false if otherwise
	 */
	public boolean atQuestionEnd(){
		return questionEnd;
	}
	
	/**
	 * Return true if the hero can shoot coins, false if otherwise
	 */
	public boolean canShoot(){
		return moneyBulletTime > 0;
	}
}
