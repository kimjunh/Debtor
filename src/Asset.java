import java.util.ArrayList;
import java.awt.Image;

/**
 * This class stores assets in the game, and hold the image frames to allow for animation.
 * @author John Kim, Tanner Lai, Raymond Zhao
 *
 */
public final class Asset{
	// Contained objects
	private ArrayList<Sprite> frames; // List of Sprites in class
	
	// Fields
	private float totalTime = 0; // Total duration of full asset animation
	
	/**
	 * This constructor sets up a new asset with one image already included.
	 *
	 * @param image The image file that is contained in the asset.
	 */
	public Asset(Image image){
		// Adds a new "frame" class to an array list
		frames = new ArrayList<Sprite>();
		frames.add(new Sprite(image, 1));
		
		// Extends the total length of the full asset by one
		totalTime+= 1;
	}
	
	/**
	 * This constructor sets up a new asset with one image already included with the given duration.
	 *
	 * @param image The image file that is contained in the asset.
	 ** @param duration The amount of time the image would be shown before changing to the next one.
	 */
	public Asset(Image image, float duration){
		// Adds a new "frame" class to an array list
		frames = new ArrayList<Sprite>();
		frames.add(new Sprite(image, duration));
		
		// Extends the total length of the full asset by the given duration
		totalTime+= duration;
	}
	
	/**
	 * This method adds a new image to the asset with a duration of one "time".
	 *
	 * @param image The image file that is contained in the asset.
	 */
	public void add(Image image){
		// Adds a new "frame" class to an array list
		frames.add(new Sprite(image, 1));
		
		// Extends the total length of the full asset by one
		totalTime += 1;
	}
	
	
	/**
	 * This method adds a new image to the asset with a certain duration.
	 *
	 * @param image The image file that is contained in the asset.
	 * @param duration The amount of time the image would be shown before changing to the next one.
	 */
	public void add(Image image, float duration){
		// Adds a new "frame" class to an array list
		frames.add(new Sprite(image, duration));
		
		// Extends the total length of the full asset
		totalTime += duration;
	}
	
	/**
	 * This method is returns the total length of the asset.
	 */
	public float getTotalTime(){
		// Return total length of the asset
		return totalTime;
	}
	
	/**
	 * This method returns the image within the set of frames.
	 *
	 * @param time The amount of how much the image should update.
	 */
	public Image get(float time){
		// Set up variables
		int id = 0;
		
		// For all the frames
		for (int i = 0; i < frames.size(); i++){
			// Subtract the time length of the frame
			Sprite frame = frames.get(i);
			time -= frame.getDuration();
			
			// Until reached the asked time
			if (time <= 0){
				// Then set it to the id of that frame at that time
				id = i;
				break;
			}
		}
		
		// Receive the image and return
		return frames.get(id).getImage();
	}
	
	/**
	 * This method returns the given frame.
	 *
	 * @param frame The number frame that is requested (frames stored sequentially).
	 */
	public Image getFrame(int frame){
		// If an invalid frame, return null
		if (frame >= frames.size() || frame < 0)
			return null;
		else
			// Otherwise, get the frame and return it
			return frames.get(frame).getImage();
	}
	
	/**
	 * This method returns the requested frame's width.
	 *
	 * @param frame The number frame that is requested (frames stored sequentially).
	 */
	public int getWidth(int frame){
		// If an invalid frame, return 0
		if (frame >= frames.size() || frame < 0)
			// Return 0
			return 0;
		
		// Get the specific image of that Sprite
		Image sprite = frames.get(frame).getImage();
		
		// Return width
		return sprite.getWidth(null);
	}
	
	/**
	 * This method returns the requested frame's height.
	 *
	 * @param frame The number frame that is requested (frames stored sequentially).
	 */
	public int getHeight(int frame){
		// If an invalid frame, return 0
		if (frame >= frames.size() || frame < 0)
			// Return 0
			return 0;
			
		// Get the specific image of that Sprite
		Image sprite = frames.get(frame).getImage();
		// Return height
		return sprite.getHeight(null);
	}
	
	/**
	 * Inner class designed to hold the individual images in an asset.
	 * @author John Kim, Tanner Lai, Raymond Zhao
	 *
	 */
	private class Sprite{
		// Contained objects
		Image image;
		
		// Fields
		float duration;
		
		/**
		 * This constructor sets up the entity with the given data.
		 *
		 * @param image The image file to be contained within the asset.
		 * @param duration The length of time the image is to be shown before moving to the next.
		 */
		public Sprite(Image image, float duration){
			// Set variables as given
			this.image = image;
			this.duration = duration;
		}
		
		/**
		 * This method returns the image contained in the sprite.
		 */
		public Image getImage(){
			// Return the image
			return image;
		}
		
		/**
		 * This method returns the duration the sprite is displayed.
		 */
		public float getDuration(){
			// Return the duration of the image
			return duration;
		}
	}
}