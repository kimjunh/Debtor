import java.awt.Image;
import java.util.ArrayList;

/**
 * Thing represents all the objects within the game such as characters, items, and obstacles.
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class Thing {
	
	// Fields
	private Asset thisAsset; //Current asset
	private int ID; // Id of the entity
	private float currentTime = 0; // The current time of which the animation is set to
	protected float x = 0; // The x position of the entity
	protected float y = 0; // The y position of the entity
	protected int width = 0; // The width of the entity
	protected int height = 0; // The height of the entity
	protected boolean flip = false; // Flip image? False = facing left, True = facing right
	
	//Constructors
	/**
	 * Constructs a Thing without the width or height specified
	 * 
	 * @param id The asset ID of the Thing
	 * @param asset The Asset object of the Thing
	 * @param x x-coordinate location
	 * @param y y-coordinate location
	 */
	public Thing(int id, Asset asset, float x, float y){
		thisAsset = asset;
		ID = id;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructs a Thing
	 * 
	 * @param id The asset ID of the Thing
	 * @param asset The Asset object of the Thing
	 * @param x x-coordinate location
	 * @param y y-coordinate location
	 * @param width width of Thing
	 * @param height height of Thing
	 */
	public Thing(int id, Asset asset, float x, float y, int width, int height){
		thisAsset = asset;
		ID = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//Methods
	
	/**
	 * Returns the ID of the Thing
	 */
	public int getID(){
		return ID;
	}
	
	/**
	 * Returns x position of the Thing
	 */
	public float getX(){
		return x;
	}
	
	/**
	 * Returns y position of the Thing
	 */
	public float getY(){
		return y;
	}
	
	/**
	 * Returns width of the Thing
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Returns height of the Thing
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * Returns true if the Thing is facing right, false if otherwise
	 */
	public boolean isFlipped(){
		return flip;
	}
	
	/**
	 * Sets the x position of the Thing to a new value
	 * @param newX The new x position of the Thing
	 */
	public void setX(float newX){
		x = newX;
	}
	
	/**
	 * Sets the y position of the Thing to a new value
	 * @param newY The new y position of the Thing
	 */
	public void setY(float newY){
		y = newY;
	}
	
	/**
	 * Sets the width of the Thing to a new value
	 * @param newWidth The new width of the Thing
	 */
	public void setWidth(int newWidth){
		width = newWidth;
	}
	
	/**
	 * Sets the height of the Thing to a new value
	 * @param newHeight The new height of the Thing
	 */
	public void setHeight(int newHeight){
		height = newHeight;
	}
	
	/**
	 * Returns the current image of the Thing
	 */
	public Image getImage(){
		return thisAsset.get(currentTime);
	}
	
	/**
	 * Sets the Asset of the Thing to a new Asset
	 * @param newAsset The new Asset the Thing is set to
	 */
	public void setAsset(Asset newAsset){
		thisAsset = newAsset;
	}
	
	/**
	 * Call this every cycle of the game to update its time
	 * 
	 * @param time The time since the last game cycle
	 * @param things The list of game objects to check for collision with
	 * @param assetStorage the library of assets
	 */
	public void update(float time, ArrayList<Thing> things, AssetStorage assetStorage){
		// Add the given time
		currentTime += time;
		
		// Resets Asset animation
		while (currentTime > thisAsset.getTotalTime()){
			// Subtract from total length
			currentTime -= thisAsset.getTotalTime();
		}
	}
	
	/**
	 * Moves the thing towards a position
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void move(float x, float y){
		this.x = x;
		this.y = y;
	}
}
