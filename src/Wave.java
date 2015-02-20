import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
 
/**
 * This class is a thread that plays a sound and 
 * loops it. It will continue playing until the 
 * stopped variable has been changed.
 *
 * @author John Kim, Tanner Lai, Raymond Zhao
 */
public class Wave extends Thread{
 	// Contained objects
    private String filename;
	private Position curPosition;
 	
 	// Fields
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
    public boolean stopped = false;
 	
 	/**
	 * This enum sets up three positions on the time track
	 *
	 * @author John Kim, Tanner Lai, Raymond Zhao
	 * 
	 */
    enum Position{
        LEFT, RIGHT, NORMAL
    };
 	
 	/**
	 * This constructor sets up the Wave with the given path to the
	 * sound.
	 *
	 * @param wavfile The path to the wave file.
	 */
    public Wave(String wavfile){
        filename = wavfile;
        curPosition = Position.NORMAL;
    }
 	
 	/**
	 * This constructor sets up the Wave with the given path to the
	 * sound at the given position.
	 *
	 * @param wavfile The path to the wave file.
	 * @param p The position within the Wave to be played at.
	 */
    public Wave(String wavfile, Position p){
        filename = wavfile;
        curPosition = p;
    }
    
 	/**
	 * This method is the standard for a Java "Thread" and will
	 * read the file, loop through it, and play the sound.
	 */
    public void run(){
    	// Forever looping...
    	while (true){
    		//Exit if stopped
    		if(stopped){
    			interrupt();
    			return;
    		}
    		// Load the file
	        File soundFile = new File(filename);
	        if (!soundFile.exists()){
	            System.err.println("Wave file not found: " + filename);
	            return;
	        }
	 		
	 		// Load a stream for the file
	        AudioInputStream audioInputStream = null;
	        try{
	            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
	        } catch (UnsupportedAudioFileException e1){
	            e1.printStackTrace();
	            return;
	        } catch (IOException e1){
	            e1.printStackTrace();
	            return;
	        }
	 		
	 		// Prepare reading
	        AudioFormat format = audioInputStream.getFormat();
	        SourceDataLine auline = null;
	        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
	 
	        try{
	            auline = (SourceDataLine) AudioSystem.getLine(info);
	            auline.open(format);
	        } catch (LineUnavailableException e){
	            e.printStackTrace();
	            return;
	        } catch (Exception e){
	            e.printStackTrace();
	            return;
	        }
	 
	        if (auline.isControlSupported(FloatControl.Type.PAN)){
	            FloatControl pan = (FloatControl) auline
	                    .getControl(FloatControl.Type.PAN);
	            if (curPosition == Position.RIGHT)
	                pan.setValue(1.0f);
	            else if (curPosition == Position.LEFT)
	                pan.setValue(-1.0f);
	        } 
	 
	        auline.start();
	        int nBytesRead = 0;
	        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
	 		
	 		// Play through the file
	        try{
	            while (nBytesRead != -1){    
	                if (stopped)
	                	return;
	                nBytesRead = audioInputStream.read(abData, 0, abData.length);
	                if (stopped)
	                	return;
	                if (nBytesRead >= 0)
	                    auline.write(abData, 0, nBytesRead);
					if (stopped)
	                	return;
	            }
	        } catch (IOException e){
	            e.printStackTrace();
	            return;
	        } finally{
	            auline.drain();
	            auline.close();
	        }
    	}
    }
}