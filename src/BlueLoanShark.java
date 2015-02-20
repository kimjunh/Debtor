/**
 * A type of enemy that moves faster
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class BlueLoanShark extends Character{
	
	/**
	 * Constructs an enemy that moves twice as fast as the normal enemy.
	 * 
	 * @param id The asset ID of the BlueLoanShark
	 * @param asset The Asset object of the BlueLoanShark
	 * @param x x-coordinate location
	 * @param y y-coordinate location
	 * @param width width of BlueLoanShark
	 * @param height height of BlueLoanShark
	 */
	public BlueLoanShark(int id, Asset asset, float x, float y, int width, int height) {
		super(id, asset, x, y, width, height);
		vecX = Globals.DEFAULT_CHARACTER_SPEED * 2;
	}

}
