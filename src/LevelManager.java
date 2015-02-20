import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Manages the level the player is currently on and loads the map file for them
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public class LevelManager {
	// Fields
	private int level = 0; // What level the game is on
	private int questionLevel = -1; // What question level the game is on
	private int width = 0; // Width of the loaded level
	private int height = 0; // Height of the loaded level
	private int currentPowerID = -1; //The current power up ID
	
	/**
	 * LoadLevel is called to initialize a level or to change and load a new level.
	 * The level is loaded from a text file and the method returns a 2D array storing
	 * the in game level data.
	 */
	public int[][] loadLevel(){
		// Loads the level
		return loadLevel(level);
	}
	
	/**
	 * LoadLevel is called to initialize a level or to change and load a new level.
	 * The level is loaded from a text file and the method returns a 2D array storing
	 * the in game level data.
	 * 
	 * @param pass Skip to the next level, then load
	 */
	public int[][] loadLevel(boolean pass){
		// Loads the next level
		level++;
		return loadLevel(level);
	}
	
	/**
	 * LoadQuestionLevel is called to initialize a question room or to change and load a new question room.
	 * The room is loaded from a text file and the method returns a 2D array storing
	 * the in game room data.
	 * 
	 * @param pass load next room if true
	 */
	public int[][] loadQuestionLevel(boolean pass){
		// Loads the next question level
		if(pass)
			questionLevel++;
		return loadQuestionLevel(questionLevel);
	}
	
	/**
	/**
	 * LoadLevel is called to initialize a level or to change and load a new level.
	 * The level is loaded from a text file and the method returns a 2D array storing
	 * the in game level data.
	 * 
	 * @param level The level number.
	 */
	public int[][] loadLevel(int level){
		// Storage units
		ArrayList dictionary; // "What number matches to what number" list
		ArrayList levelData; // The level
		
		// Change the level
		this.level = level;
		
		// Load level from text file
		try{
			// Use readFile method to get data
			ArrayList[] levelList = readFile("Levels/level" + level + ".txt"); // Load stuff from text file
			dictionary = levelList[0]; // Load dictionary
			levelData = levelList[1]; // Load level content
		} catch (IOException ex){
			// Load nothing if error
			int[][] error = new int[1][1];
			error[0][0] = 0;
			return error;
		}
		
		// Setup arrays for the dictionary
		char[] dictChar = new char[dictionary.size()];
		int[] dictCode = new int[dictionary.size()];
		
		// For the whole dictionary list
		for(int i = 0; i < dictionary.size(); i++){
			String crypt = (String)dictionary.get(i);		
			dictChar[i] = crypt.charAt(1); //set symbol
			dictCode[i] = Integer.valueOf((crypt.substring(crypt.lastIndexOf("=") + 1)).trim()); // set int value
		}
		
		// Setup integer definition list of map
		int[][] levelMap = new int[height][width];
		
		// For the whole file (each line)
		for (int y = 0; y < height; y++){
			// Get a line from the file
			String row = (String) levelData.get(y);
			
			// For every character
			for (int x = 0; x < row.length(); x++){
				// Get character
				char c = row.charAt(x);
				
				// For the whole dictionary
				for (int j = 0; j < dictChar.length; j++){
					// If this character is in the dictionary
					if (c == dictChar[j]){
						// Add it to the level
						levelMap[y][x] = dictCode[j];
						break;
					}
				}
			}
		}
		// Return the level
		return levelMap;
	}
	
	/**
	 * LoadQuestionLevel is called to initialize the question level room.
	 * The level is loaded from a text file and the method returns a 2D array storing
	 * the in game level data. 
	 */
	public int[][] loadQuestionLevel(int questionLevel){
		// Storage units
		ArrayList dictionary; // "What number matches to what number" list
		ArrayList levelData; // The question level
		
		// Change the level
		this.questionLevel = questionLevel;
		
		// Load question level from text file
		try{
			// Use readFile method to get data
			ArrayList[] levelList = readFile("Questions/QuestionRoom" + questionLevel + ".txt"); // Load stuff from text file
			dictionary = levelList[0]; // Load dictionary
			levelData = levelList[1]; // Load question level content
		} catch (IOException ex){
			// Load nothing if error
			int[][] error = new int[1][1];
			error[0][0] = 0;
			return error;
		}
		
		// Setup arrays for the dictionary
		char[] dictChar = new char[dictionary.size()];
		int[] dictCode = new int[dictionary.size()];
		
		// For the whole dictionary list
		for(int i = 0; i < dictionary.size(); i++){
			String crypt = (String)dictionary.get(i);		
			dictChar[i] = crypt.charAt(1); //set symbol
			dictCode[i] = Integer.valueOf((crypt.substring(crypt.lastIndexOf("=") + 1)).trim()); // set int value
		}
		
		// Setup integer definition list of map
		int[][] levelMap = new int[height][width];
		
		// For the whole file (each line)
		for (int y = 0; y < height; y++){
			// Get a line from the file
			String row = (String) levelData.get(y);
			
			// For every character
			for (int x = 0; x < row.length(); x++){
				// Get character
				char c = row.charAt(x);
				
				// For the whole dictionary
				for (int j = 0; j < dictChar.length; j++){
					// If this character is in the dictionary
					if (c == dictChar[j]){
						// Add it to the level
						levelMap[y][x] = dictCode[j];
						break;
					}
				}
			}
		}
		// Return the level
		return levelMap;
	}

	
	/**
	 * This method is capable of reading a file and parses
	 * for the level lookup table that defines what each 
	 * character represents in the game and the level itself.
	 *
	 * @param filename The name of the file.
	 */
	private ArrayList[] readFile(String filename) throws IOException{
    	// Sets up the variables needed to read and store the level data
    	ArrayList<String> dictionary = new ArrayList<String>();
    	ArrayList<String> map = new ArrayList<String>();
    	BufferedReader reader = new BufferedReader(new FileReader(filename));
    	
    	// For the whole file
    	while (true){
    		// Read the line
    		String line = reader.readLine();
    		
    		// If there isn't a line
    		if (line == null){
    			// Clean up and exit
    			reader.close();
    			break;
    		}
    		
    		// If the line isn't a comment
			if (!line.startsWith("//")){
    			// If line defines a power up
				if(line.startsWith("P")){
					currentPowerID = Integer.parseInt(line.substring(1));  //Set the current power up id
					if(currentPowerID == 0){ //If 0, set it randomly
						int random = (int)(Math.random() * 3); 
						switch(random){
							case 0: currentPowerID = 310;
									break;
							case 1: currentPowerID = 311;
									break;
							case 2: currentPowerID = 312;
									break;
						}
					}
				}else if (line.startsWith("#")){
    				// Add it to the dictionary listing
    				dictionary.add(line);
    			}else{
	    			// Add it to the the array list / store it
	    			map.add(line);
	    			
	    			// Keep track of the largest line
	    			if (width < line.length()){
	    				// Sets the width of the level to the line length
	    				width = line.length();
	    			}
    			}
    		}
    	}
    	
    	// Load up the dictionary list + level setup
    	ArrayList[] level = new ArrayList[2];
    	level[0] = dictionary; // Set the dictionary in
    	level[1] = map; // Set the level in
        reader.close(); // Close the file reader
        
        // Set the height of the file
		height = map.size();
        
		return level; // Return the game level
    }
	
	/**
	 * This method returns the width of the level in tiles.
	 */
	public int getWidth(){
		// Return the width of the level
		return width;
	}
	    
    /**
	 * This method returns the height of the level in tiles.
	 */
	public int getHeight(){
		// Return the height of the level
		return height;
	}
	
	/**
	 * This method returns the level number.
	 */
	public int getLevelNumber(){
		// Return which level the player is on
		return level;
	}
	
	/**
	 * This method returns the question level number.
	 */
	public int getQuestionLevelNumber(){
		// Return which level the player is on
		return questionLevel;
	}
	
	/**
	 * This method returns the current power up id of the current question room.
	 */
	public int getPowerID(){
		return currentPowerID;
	}
	
	/**
	 * This method sets the question level to the new question level.
	 * @param level The new question level the question level is set to
	 */
	public void setQuestionLevel(int level){
		questionLevel = level;
	}
	
}
