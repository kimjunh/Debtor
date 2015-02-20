import java.util.ArrayList;

/**
 * Character represents a character, hero or enemy, within the game.
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class Character extends Thing{

	protected float vecX = 0; // x vector of movement
	protected float vecY = 0; // y vector of movement
	protected boolean isMoving = false; // is the character moving?
	protected boolean inAir = true; // is the character in the air?
	
	/**
	 * This constructor sets up the character with the given data.
	 *
	 * @param id The id number of the character.
	 * @param newasset The image asset set to the character.
	 * @param xpos The starting x position of the character.
	 * @param ypos The starting y position of the character.
	 * @param width The width of the character.
	 * @param height The height of the character
	 */
	public Character(int id, Asset asset, float x, float y, int width, int height){
		super(id, asset, x , y, width, height);

	}
	
	/**
	 * This constructor sets up the character with the given data (+ speed).
	 *
	 * @param id The id number of the character.
	 * @param newasset The image asset set to the character.
	 * @param xpos The starting x position of the character.
	 * @param ypos The starting y position of the character.
	 * @param width The width of the character.
	 * @param height The height of the character
	 * @param speed The speed to which the character moves.
	 */
	public Character(int id, Asset asset, float x, float y, int width, int height, float speed){
		super(id, asset, x, y, width, height);
		
		// Set the velocity to the given speed
		vecX = speed;
	}
	
	/**
	 * Returns the x vector of movement
	 */
	public float getVecX(){
		// Return x vector of movement
		return vecX;
	}
	
	/**
	 * Returns the y vector of movement
	 */
	public float getVecY(){
		// Return y vector of movement
		return vecY;
	}
	
	/**
	 * Sets the x vector movement to a new value
	 * @param x The new x vector movement value
	 */
	public void setVecX(float x){
		// Change x vector of movement to given amount
		vecX = x;
	}
	
	/**
	 * Sets the y vector movement to a new value
	 * @param y The new y vector movement value
	 */
	public void setVecY(float y){
		// Change y vector of movement to given amount
		vecY = y;
	}
	
	/**
	 * Returns true if the character is moving, false if otherwise
	 */
	public boolean isMoving(){
		return isMoving;
	}
	
	/**
	 * Returns true if the character is in the air, false if otherwise
	 */
	public boolean inAir(){
		return inAir;
	}
	
	/**
	 * Sets the value of whether or not the character is moving
	 * @param isMoving The new boolean value of whether or not the character is moving
	 */
	public void setIsMoving(boolean isMoving){
		this.isMoving = isMoving;
	}
	
	/**
	 * Sets the value of whether or not the character is in the air
	 * @param inAir The new boolean value of whether or not the character is in the air
	 */
	public void setInAir(boolean inAir){
		this.inAir = inAir;
	}
	
	/**
	 * This method determines if this character is touching another thing.
	 *
	 * @param test The character to test collisions with.
	 */
	protected boolean hasCollided(Thing collided){
		if(x + width > collided.getX() && x < collided.getX() + collided.getWidth() && y + height > collided.getY() && y < collided.getY() + collided.getHeight())
			return true;
		else
			return false;
	}
	
	/**
	 * This method sets the character when the character collides with an enemy.
	 */
	protected void collidedEnemy(ArrayList<Thing> things, int loc, AssetStorage assetStorage){}
	//Pass as regular
	
	/**
	 * This method sets the character when the character collides with an item.
	 */
	protected void collidedItem(ArrayList<Thing> things, int loc){}
	//Pass as regular
	
	/**
	 * This method sets the character when the character collides with the end of the level.
	 */
	protected void collidedLevelEnd(){}
	//Pass as regular
	
	/**
	 * This method sets the character when the character collides with the end of the question level.
	 */
	protected void collidedQuestionEnd(){}
	//Pass as regular
	
	/**
	 * This method sets the character when the character collides with a question room entrance.
	 */
	protected void collidedQuestionRoom(){}
	//Pass as regular
	
	/**
	 * This method sets the character's answer choice when applicable.
	 * @param choice The answer choice of the character
	 */
	protected void setAnswerSelection(int choice){}
	//Pass as regular
	
	/**
	 * Sets up appropriate values, such as movement, for the character.
	 */
	protected void setUp(){
		if (vecX > 0){
			// Moving right
			flip = true;
		} else if (vecX < 0){
			// Moving left
			flip = false;
		}
		
		if(vecY != 0)
			inAir = true;
		
		// Accelerate fall
		vecY += Globals.DEFAULT_CHARACTER_FALL;
	}
	
	/**
	 * This method updates the character.
	 *
	 * @param time The amount of time passed for the character to update.
	 * @param things The ArrayList containing the things currently in the game
	 * @param assetStorage The storage of assets and images for the game
	 */
	public void update(float time, ArrayList<Thing> things, AssetStorage assetStorage){
		//Updated images normally if moving
		if(isMoving){
			super.update(time, things, assetStorage);
		}
		//Set up Character values
		setUp();
		
		//Check X collisions and move appropriately
		
		x += vecX;
		for(int i = 0; i < things.size(); i++){
			Thing testX = things.get(i);
			
			if(Math.abs(testX.getX() - x) < Globals.COLLISION_RANGE){
				if(hasCollided(testX) && testX.getID() == 999){
					collidedQuestionRoom();
				}else if(hasCollided(testX) && testX.getID() >= 300){
					collidedItem(things, i);
				}else if(hasCollided(testX) && testX.getID() >= 200){
					if(vecX > 0)
						x = testX.getX() - width;
					else if(vecX < 0)
						x = testX.getX() + testX.getWidth();
					
					if(getID() >= 100 && getID() <= 106)
						vecX = 0;
					else
						vecX *= -1;
				}else if(hasCollided(testX) && testX.getID() >= 110){
					collidedEnemy(things, i, assetStorage);
				}else if(hasCollided(testX) && testX.getID() == 99){
					collidedLevelEnd();
				}else if(hasCollided(testX) && testX.getID() == 98){
					collidedQuestionEnd();
				}
			}
		}
		
		//Check Y collisions and move appropriately		
		y += vecY;
		for(int j = 0; j < things.size(); j++){
			Thing testY = things.get(j);
			
			if(Math.abs(testY.getY() - y) < Globals.COLLISION_RANGE){
				if(hasCollided(testY) && testY.getID() == 999){
					collidedQuestionRoom();
				}else if(hasCollided(testY) && testY.getID() >= 300){
					collidedItem(things, j);
				}else if(hasCollided(testY) && testY.getID() >= 200){
					if(vecY > 0){
						y = testY.getY() - height;
						inAir = false;
						vecY = 0;
					}else if(vecY < 0){
						//if it's the hero
						if (getID() >= 100 && getID() <= 106){
							if (testY.getID() == 210){
								setAnswerSelection(1);
							} else if (testY.getID() == 211){
								setAnswerSelection(2);
							} else if (testY.getID() == 212){
								setAnswerSelection(3);
							} else if (testY.getID() == 213){
								setAnswerSelection(4);
							}
						}

						y = testY.getY() + testY.getHeight();
						vecY = 0;
					}
				}else if(hasCollided(testY) && testY.getID() >= 110){
					collidedEnemy(things, j, assetStorage);
				}else if(hasCollided(testY) && testY.getID() == 99){
					collidedLevelEnd();
				}else if(hasCollided(testY) && testY.getID() == 98){
					collidedQuestionEnd();
				}
			}
		}
		isMoving = false;
	}
}
