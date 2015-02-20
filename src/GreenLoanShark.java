/**
 * A Character enemy who gets in the hero's way.
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class GreenLoanShark extends Character{

	/**
	 * Constructs a GreenLoanShark
	 * 
	 * @param id The asset ID of the GreenLoanShark
	 * @param asset The Asset object of the GreenLoanShark
	 * @param x x-coordinate location
	 * @param y y-coordinate location
	 * @param width width of GreenLoanShark
	 * @param height height of GreenLoanShark
	 */
	public GreenLoanShark(int id, Asset asset, float x, float y, int width, int height) {
		super(id, asset, x, y, width, height);
		vecX = Globals.DEFAULT_CHARACTER_SPEED;
	}
}
