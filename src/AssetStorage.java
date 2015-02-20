import java.util.ArrayList;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

/**
 * Storage containing all the assets in the game. Allows for easy addition and access of
 * assets.
 * @author John Kim, Tanner Lai, Raymond Zhao
 */
public final class AssetStorage{
	// Contained objects
	private ArrayList<Asset> assets; // ArrayList holding all game assets
	
	/**
	 * This constructor sets up the AssetCreator and, by default, loads all the assets.
	 */
	public AssetStorage(){
		assets = new ArrayList<Asset>();
		// Standard call, loads all the assets given in the createAssets() method
		createAssets();
	}
	
	/**
	 * This method creates a list of all the in game assets. To add more assets to this list,
	 * add it to the end of the <code>assets</code> ArrayList and add a comment indicating
	 * the position in the ArrayList for future reference.
	 */
	public ArrayList createAssets(){
		// 0
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("NULL")));
		
		//1
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl3.png").getImage()));

		//2
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl0.png").getImage()));

		//3
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Hero/herostand.gif")));
		
		//4
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Hero/herowalk.gif")));
		
		//5
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/Menu/winscreen.png").getImage()));

		//6
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Hero/heromidair.gif")));
		
		//7
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl3dark.png").getImage()));
		
		//8
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl3dark2.png").getImage()));
		
		//9
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/block.png")));
		
		//10
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/metalblock.png")));
		
		//11
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Items/money.gif")));
		
		//12
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Misc/debtfreezone.png")));
		
		//13
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Hero/herodeath.gif")));
		
		//14
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Misc/arrow.png")));
		
		//15
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Hero/herostandI.gif")));
		
		//16
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Hero/herowalkI.gif")));
		
		//17
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Hero/heromidairI.gif")));
		
		//18
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Items/itemInvincible.gif")));
		
		//19
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Items/itemShootMoney.gif")));
		
		//20
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Items/itemSpeed.gif")));
		
		//21
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/end.jpg").getImage()));
		
		//22
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/Menu/standby.gif").getImage()));
		
		//23
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/Ablock.png")));
		
		//24
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/Bblock.png")));
		
		//25
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/Cblock.png")));
		
		//26
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/Dblock.png")));

		//27
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/QuestionBG/questionbg.png").getImage()));
		
		//28
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/verticalblock.png")));

		//29
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/scaffoldblock.png")));

		//30
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/glassblock.png")));

		//31	
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/whiteblock.png")));

		//32
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Backgrounds/QuestionBG/questionroomBG.jpg")));

		//33	
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Backgrounds/QuestionBG/questionroomBG2.jpg")));

		//34
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/QuestionBG/questionRoomBG3.jpg").getImage()));

		//35	
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Backgrounds/QuestionBG/questionroomBG4.jpg")));
		
		//36
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Misc/questionMark.png")));
		
		//37
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Enemy/greenLoanShark.gif")));
		
		//38
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Enemy/blueLoanShark.gif")));
		
		//39
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Enemy/blackLoanShark.gif")));
		
		//40
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Characters/Enemy/ghost.gif")));
		
		//41
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl0dark.png").getImage()));
		
		//42
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl0dark2.png").getImage()));
		
		//43
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Items/coinsStar.png")));

		//44
		assets.add(new Asset(Toolkit.getDefaultToolkit().createImage("Images/Blocks/concreteblock.png")));

		//45
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl1.jpg").getImage()));
		
		//46
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl1dark.jpg").getImage()));
		
		//47
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl1dark2.jpg").getImage()));
		
		//48
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl2.png").getImage()));
		
		//49
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl2dark.png").getImage()));
		
		//50
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl2dark2.png").getImage()));
		
		//51
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl4.png").getImage()));
		
		//52
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl4dark.png").getImage()));
		
		//53
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/LevelBG/BGLvl4dark2.png").getImage()));
			
		//54
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/Menu/coinpowerup.png").getImage()));
		
		//55
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/Menu/speedpowerup.png").getImage()));
		
		//56
		assets.add(new Asset(new ImageIcon("Images/Backgrounds/Menu/invincibilitypowerup.png").getImage()));
		
		return assets;
	}
	
	/**
	 * This method returns the asset related to the specific entity id.
	 * 
	 * ID Key:
	 * 1-99 : Backgrounds and Misc
	 * 100-199: Characters and enemies
	 * 200-299: Blocks
	 * 300-399: Items
	 * 999+: Questions
	 * @param id The id number of the entity.
	 */
	public Asset getAsset(int id){
		// Go through the game id's, return the asked id
		switch(id){
			case 1: return assets.get(34); //QUESTION ROOM BG
			case 2: return assets.get(5); //WIN SCREEN
			case 3: return assets.get(21); //WIN BACKGROUND
			case 4: return assets.get(22); //STAND BY
			case 5: return assets.get(54); //COIN UNLOCK TEXT
			case 6: return assets.get(55); //SPEED UNLOCK TEXT
			case 7: return assets.get(56); //INVINCIBILITY UNLOCK TEXT
			case 30: return assets.get(2); //GAME BG0
			case 40: return assets.get(41); //GAME BG0 DARK
			case 45: return assets.get(42); //GAME BG0 DARKER
			case 31: return assets.get(45); //GAME BG1
			case 41: return assets.get(46); //GAME BG1 DARK
			case 46: return assets.get(47); //GAME BG1 DARKER
			case 32: return assets.get(48); //GAME BG2
			case 42: return assets.get(49); //GAME BG2 DARK
			case 47: return assets.get(50); //GAME BG2 DARKER
			case 33: return assets.get(1); //GAME BG3
			case 43: return assets.get(7); //GAME BG3 DARK
			case 48: return assets.get(8); //GAME BG3 DARKER
			case 34: return assets.get(51); //GAME BG4
			case 44: return assets.get(52); //GAME BG4 DARK
			case 49: return assets.get(53); //GAME BG4 DARKER
			case 91: return assets.get(27);//QUESTION DISPLAY
			case 98: return assets.get(14);//QUESTION END ARROW
			case 99: return assets.get(12); //LEVEL END		
			case 100: return assets.get(3); //HERO STAND
			case 101: return assets.get(4); //HERO WALK
			case 102: return assets.get(6); //HERO JUMP
			case 103: return assets.get(15); //HERO INVUL STAND
			case 104: return assets.get(16); //HERO INVUL WALK
			case 105: return assets.get(17); //HERO INVUL JUMP
			case 106: return assets.get(13); //HERO DEAD
			case 110: return assets.get(37); //GREEN LOAN SHARK
			case 111: return assets.get(38); //BLUE LOAN SHARK
			case 112: return assets.get(39); //BLACK LOAN SHARK
			case 113: return assets.get(40); //PHANTOM GAIN
			case 200: return assets.get(9); //BLOCK
			case 201: return assets.get(10); //PAVEMENT
			case 202: return assets.get(28); //VERTICAL BLOCK
			case 203: return assets.get(29); //SCAFFOLD BLOCK
			case 204: return assets.get(30); //GLASS BLOCK
			case 205: return assets.get(31); //WHITE BLOCK (TEST)
			case 206: return assets.get(44); //CONCRETE BLOCK
			case 210: return assets.get(23); //A BLOCK
			case 211: return assets.get(24); //B BLOCK
			case 212: return assets.get(25); //C BLOCK
			case 213: return assets.get(26); //D BLOCK
			case 300: return assets.get(11); //MONEY
			case 310: return assets.get(18); //INVINVIBILITY ITEM
			case 311: return assets.get(19); //SHOOT MONEY ITEM
			case 312: return assets.get(20); //SPEED ITEM
			case 313: return assets.get(43); //MONEY BULLET 
			case 999: return assets.get(36); //QUESTION ROOM
			case 1001: return assets.get(35); //QUESTION ROOM BG TEST 1
			case 1002: return assets.get(32); //QUESTION ROOM BG TEST 2
			case 1003: return assets.get(33); //QUESTION ROOM BG TEST 3

		}
		
		// Otherwise, return an empty asset
		return assets.get(0);
	}
	
	/**
	 * This method returns the asset with the given position in the created list.
	 *
	 * @param loc The position of the asset within the list of assets.
	 */
	public Asset getAssetByLoc(int loc){
		// Returns asset based on location in arraylist
		return assets.get(loc);
	}
	
	/**
	 * This method returns the width of the requested entity. Returns -1 if not known yet.
	 *
	 * @param id The id number of the entity.
	 * @param frame The frame of the given entity.
	 */
	public int getWidth(int id, int frame){
		// Get the asset and return the width
		return getAsset(id).getWidth(frame);
	}
	
	/**
	 * This method returns the width of the requested entity. Returns -1 if not known yet.
	 *
	 * @param id The id number of the entity.
	 * @param frame The frame of the given entity.
	 */
	public int getHeight(int id, int frame){
		// Get the asset return the height
		return getAsset(id).getHeight(frame);
	}
}