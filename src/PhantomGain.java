import java.util.ArrayList;

/**
 * The PhantomGain enemy that appears randomly and moves through walls towards the player
 * This Enemy represents the actual financial term of a phantom gain where investment is loss.
 * @author John Kim, Tanner Lai, Raymond Zhao
 */
public class PhantomGain extends Character{
	
	private float moveToX; //x coordinate to move to
	private float moveToY; //y coordinate to move to

	/**
	 * Constructs a PhantomGain
	 * 
	 * @param id The asset ID of the PhantomGain
	 * @param asset The Asset object of the PhantomGain
	 * @param x x-coordinate location
	 * @param y y-coordinate location
	 * @param width width of PhantomGain
	 * @param height height of PhantomGain
	 */
	public PhantomGain(int id, Asset asset, float x, float y, int width, int height) {
		super(id, asset, x, y, width, height);
		vecX = Globals.GHOST_SPEED; //CONSTANT
		vecY = Globals.GHOST_FALL; //CONSTANT
		moveToX = 0;
		moveToY = 0;
	}
	
	/**
	 * Sets up appropriate values, such as movement, for the PhantomGain.
	 */
	protected void setUp(){
		if (vecX > 0){
			// Moving right
			flip = true;
		} else if (vecX < 0){
			// Moving left
			flip = false;
		}
	}
	
	/**
	 * Overrides the hasCollided method in the superclass to indicate that
	 * the PhantomGain can not collide with anything
	 * @return false
	 */
	protected boolean hasCollided(Thing collided){
		return false;
	}
	
	/**
	 * Set the location to move towards
	 * @param x x-coordinate to move to
	 * @param y y-coordinate to move to
	 */
	public void setMoveLoc(float x, float y){
		moveToX = x;
		moveToY = y;
	}
	
	/**
	 * Call this every game cycle to update the PhantomGain to move towards the Hero
	 * @param time Time since the last game cycle
	 * @param things The list of all things in the game
	 * @param assetStorage Library of the assets in the game
	 */
	public void update(float time, ArrayList<Thing> things, AssetStorage assetStorage){
		//Updated images normally if moving
		if(isMoving){
			super.update(time, things, assetStorage);
		}
		
		//Set up Character values
		setUp();
			
		//Move towards the Hero's location
		if(x > moveToX)
			vecX = Globals.GHOST_SPEED;
		else if(x < moveToX)
			vecX = -Globals.GHOST_SPEED;
		else
			vecX = 0;
		
		if(y > moveToY)
			vecY = -Globals.GHOST_FALL;
		else if(y < moveToY)
			vecY = Globals.GHOST_FALL;
		else
			vecY = 0;
		x+=vecX;
		y+=vecY;
	}
}
