/**
 * Holds all the global variables used in the program
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class Globals{
	// Fields
	public static final int FRAME_RATE_0 = 1; // Frame rates (used)
	public static final int FRAME_RATE_1 = 4; // Frame rates
	public static final int FRAME_RATE_2 = 8; // Frame rates (used)
	public static final int FRAME_RATE_3 = 12; // Frame rates
	public static final int FRAME_RATE_4 = 16; // Frame rates (used)
	public static final int KEYS_STORED = 7; // Max number of key presses stored in KeyQuery
	public static final int GRIDSIZE = 32; // Game gridsize
	public static final int COLLISION_RANGE = 400; // Range of which object checks collision
	public static final int LEVELEND_LENGTH = 150; // How long it takes for the next level to be transitioned to
	
	public static final float PLAYER_SPEED =  4; // Player movement speed
	public static final float PLAYER_AIR_SPEED = 3; // Player air speed
	public static final float PLAYER_JUMP = (float) -12.0; // Player falling speed
	public static final float PLAYER_ADDED_FALL_SPEED = (float) 0.2; // Lighter falling speed for player
	public static final float PLAYER_FRICTION = 1; // Friction for player
	public static final int PLAYER_INVUL_KILL_TIME = 7; // How long player should be invulnerable after jumping on enemy
	public static final int PLAYER_INVUL_POWER_TIME = 700; //Invulnerability time
	public static final int PLAYER_SPEED_TIME = 1000; //Speed time
	public static final int PLAYER_MONEY_BULLET_TIME = 1000; //Coin Time
	
	public static final float GHOST_SPEED = (float) -2.0; //The ghost's speed
	public static final float GHOST_FALL = (float) 1; // The ghost's fall speed
	public static final float DEFAULT_CHARACTER_SPEED = (float) -2.0; // Character's default speed
	public static final float DEFAULT_CHARACTER_FALL = (float) 0.3; // Character's default fall speed
}