/**
 * Manages the keys being pressed and released on the keyboard
 * @author John Kim, Tanner Lai, Raymond Zhao
 */
public class KeyStorage {
	
	// FIELD
	private int[] keys; // Keys that have been pressed
	
	//CONSTRUCTOR
	/**
	 * Constructs a KeyStorage
	 */
	public KeyStorage(){
		keys = new int[Globals.KEYS_STORED]; //maximum amount of keys held
	}
	
	//METHODS
	/**
	 * This method adds a user key input into the array at the first valid
	 * location and changes the array accordingly.
	 *
	 * @param key The character code of the key added by the user.
	 */
	public void add(int key){
		
		//Cleans out the key list of already existing keys
		for (int i = 0; i < keys.length; i++){
			if (keys[i] == key){
				clearEmpty();
				return;
			}
		}
		
		//Adds the key in to an empty space and cleans
		for (int i = 0; i < keys.length; i++){
			if (keys[i] == 0){
				keys[i] = key;
				clearEmpty();
				return;
			}
		}
		
		//Adds the key to the first position of previous attempts have failed
		keys[0] = key;
		clearEmpty();
	}
	
	/**
	 * This method removes a key if it is within the array.
	 *
	 * @param key The character code of the key removed by the user.
	 */
	public void remove(int key){
		//Loop through the key list and remove the key if within the array
		for (int i = 0; i < keys.length; i++){
			if (keys[i] == key)
				keys[i] = 0;
		}		
		clearEmpty();
	}
	
	/**
	 * This method moves all zeroes to the end of the array.
	 */
	public void clearEmpty(){
		//Set up temporary array
		int[] tempKeys = new int[keys.length];
		int count = 0;
		
		//Loop through the keys and count all the keys that are not zeroes
		for (int i = 0; i< keys.length; i++){
			if (keys[i] != 0){
				tempKeys[count] = keys[i];
				count++; 
			}
		}
		//Set the keys to the new key list with the zeroes to the end
		keys = tempKeys;
	}
	
	/**
	 * This method clears the key at the given index
	 *
	 * @param index The location within the key's array of the requested clearing.
	 */
	public void clear(int index){
		//Checks to see if the index is valid
		if (index < 0 || index >= keys.length)
			return;
		//Clears the key at the index
		keys[index] = 0;
	}
	
	/**
	 * Clears all the keys within the array.
	 */
	public void clearAllKeys(){
		//Loop through and clear all keys
		for (int i = 0; i < keys.length; i++){
			keys[i] = 0;
		}
	}
	
	/**
	 * This method returns the character code at the given index or
	 * otherwise return a 0.
	 *
	 * @param index The location within the key's array of the requested character.
	 */
	public int getKeyAt(int index){
		//Checks to see if the index is valid, returns zero if so
		if (index < 0 || index >= keys.length)
			return 0;
		else //returns the key at the specified index
			return keys[index];
	}
	
	/**
	 * Returns all the characters, stored in an array
	 */
	public int[] getAllKeys(){
		return keys;
	}
	
}