import java.util.ArrayList;

/**
 * The MoneyBullet object being shot by the player when he has the
 * coin-shooting power-up
 * @author John Kim, Tanner Lai, Raymond Zhao
 */
public class MoneyBullet extends Thing {
	
	//Fields
	public static final int MOVEMENT_SPEED = 8; //Movement speed of the MoneyBullet
	private int movementX; //The x movement of the MoneyBullet
	private int time; //Dies after a certain time
	
	/**
	 * Constructs a MoneyBullet at any location
	 * 
	 * @param id ID of MoneyBullet
	 * @param asset MoneyBullet asset
	 * @param x x-coordinate of bullet
	 * @param y y-coordinate of bullet
	 * @param width Width of bullet
	 * @param height Height of bullet
	 * @param facingRight whether or not the player is facing right (to shoot the coin left or right)
	 */
	public MoneyBullet(int id, Asset asset, float x, float y, int width, int height, boolean facingRight) {
		super(id, asset, x, y, width, height);
		time = 0;
		if (facingRight){
			movementX = MOVEMENT_SPEED;
		} else {
			movementX = -MOVEMENT_SPEED;
		}
	}


	/**
	 * Call this every cycle of the game to update its position and test if it has collided
	 * with a block/enemy
	 * 
	 * @param time The time since the last game cycle
	 * @param things The list of game objects to check for collision with
	 * @param assetStorage the library of assets
	 */
	public void update(float time, ArrayList<Thing> things, AssetStorage assetStorage){
		//Updates the x position of the bullet and then checks for collisions
		x += movementX;
		this.time++;
		for (int i = 0; i < things.size(); i++){
			Thing thing = things.get(i);
			if (hasCollided(thing)){
				if (200 <= thing.getID() && thing.getID() < 300){ //between 200 and 300
					removeSelfFromGrid(things); //Removes itself if collided with blocks				
				} else if (110 <= thing.getID() && thing.getID() < 200){ 
					things.remove(i); //Removes itself and the character if collided with a character
					i--;
					removeSelfFromGrid(things);
				}
			}
		}
		
		//remove the bullet
		if (this.time > 20){
			removeSelfFromGrid(things);
		}
	}
	
	/**
	 * Removes itself from the game
	 * @param things The list of things in the game
	 */
	private void removeSelfFromGrid(ArrayList<Thing> things){
		//backwards loop should be faster since bullets are added to end of arraylist
		for (int i = things.size() - 1; i >= 0; i--){
			if (things.get(i) == this){
				things.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Test if it has collided with another thing.
	 * @param collided The Thing to test collision with
	 * @return whether or not it has collided with the other thing
	 */
	public boolean hasCollided(Thing collided){
		if(x + width > collided.getX() && x < collided.getX() + collided.getWidth() && y + height > collided.getY() && y < collided.getY() + collided.getHeight())
			return true;
		else
			return false;
	}
}
