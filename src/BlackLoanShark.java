
/**
 * A type of enemy that occasionally jumps.
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class BlackLoanShark extends Character{

	private int jumpTime; //amount of time to jump in the air for

	
	/**
	 * Constructs a BlackLoanShark that jumps every 100 time units
	 * 
	 * @param id The asset ID of the BlackLoanShark
	 * @param asset The Asset object of the BlackLoanShark
	 * @param x x-coordinate location
	 * @param y y-coordinate location
	 * @param width width of BlackLoanShark
	 * @param height height of BlackLoanShark
	 */
	public BlackLoanShark(int id, Asset asset, float x, float y, int width, int height) {
		super(id, asset, x, y, width, height, Globals.DEFAULT_CHARACTER_SPEED);
		
		jumpTime = 100;
	}
	
	/**
	 * Sets up appropriate values, such as movement and the jump timer, for a BlackLoanShark.
	 */
	protected void setUp(){
		super.setUp();
		
		if(jumpTime <= 0){
			vecY += Globals.PLAYER_JUMP;
			inAir = true;
			jumpTime = 100;
		}
		
		vecY += Globals.PLAYER_ADDED_FALL_SPEED;
		
		jumpTime--;
	}

}
