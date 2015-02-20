import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * QuestionManager reads and displays finance based questions from a text file.
 * The questions can be displayed from items or at certain points in the level.
 * 
 * @author John Kim, Tanner Lai, Raymond Zhao
 * 
 */
public class QuestionManager {
	private ArrayList<String> questions; // questions to be asked
	private ArrayList<String> answers;
	private ArrayList<ArrayList<String>> answerChoices; // 2d arraylist containing the
													// multiple choice answers
													// to the questions
	private boolean answered; //whether or not the question has been answered
	
	private int randomQ; //random question number to be asked

	/**
	 * Constructions the question manager to read the questions, answers, and select
	 * a random question to ask
	 */
	public QuestionManager() {
		questions = new ArrayList<String>(30);
		answers = new ArrayList<String>(30);
		answerChoices = new ArrayList<ArrayList<String>>();
		readQuestions();
		readAnswers();
		randomQ = (int)(Math.random() * 30);
	}

	/**
	 * This method initializes the questions ArrayList to remove clutter in the
	 * constructor
	 */
	private void readQuestions() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(
					"Questions/questions.txt"));
			String line;
			// read all the lines of the questions file and add it to the
			// ArrayList
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith("//")){
					questions.add(line);
					answers.add(reader.readLine());//unhandled bad questions file
				}
			}
		} catch (IOException ex) {
			// TODO handle the error
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					// TODO handle error
				}
			}
		}

	}
	
	/**
	 * This method initializes the answers ArrayList to remove clutter in the
	 * constructor
	 */
	private void readAnswers() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(
					"Questions/answers.txt"));
			String line;
			int lineNumber = 0;//0 indexed line counter
			// read all the lines of the questions file and add it to the
			// ArrayList
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith("//")){
					if (lineNumber % 4 == 0){
						answerChoices.add(new ArrayList<String>(4));
					}
					answerChoices.get(lineNumber/4).add(line);
					lineNumber++;
				}
			}
		} catch (IOException ex) {
			// TODO handle the error
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					// TODO handle error
				}
			}
		}
	}

	/**
	 * This draws the question display at the bottom left corner of the screen
	 * 
	 * @param g The graphics object to draw with
	 */
	public void draw(Graphics2D g, Dimension dim, AssetStorage assetStorage) {
		Asset questionBG = assetStorage.getAsset(91);
		g.drawImage(questionBG.getFrame(0), 0, (int) dim.getHeight() - 225, null);
		
		g.setColor(Color.BLACK);
		drawString(g, questions.get(randomQ), 10, (int) (dim.getHeight() - 160), 625);
		for (int i = 0; i < answerChoices.get(randomQ).size(); i++){
			g.drawString(answerChoices.get(randomQ).get(i), 10, (int)dim.getHeight() - 95 + 25*i);
		}

	}

	/**
	 * Draws a string to word wrap it with the given width
	 * @param g Graphics object to draw with
	 * @param s String to draw
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param width Width to word wrap to
	 */
	public void drawString(Graphics g, String s, int x, int y, int width){
		// FontMetrics gives us information about the width,
	    // height, etc. of the current Graphics object's Font.
	    FontMetrics fm = g.getFontMetrics();
	    int lineHeight = fm.getHeight();
	    
	    int curX = x;
	    int curY = y;

	    String[] words = s.split(" ");

	    for (String word : words){
	    	// Find out thw width of the word.
	    	int wordWidth = fm.stringWidth(word + " ");

	    	// If text exceeds the width, then move to next line.
	    	if (curX + wordWidth >= x + width){
	    		curY += lineHeight;
                curX = x;
	        }

	    	g.drawString(word, curX, curY);

	    	// Move over to the right for next word.
	    	curX += wordWidth;
	    }
	}

	/**
	 * Returns the answer to the current question
	 * @return 1:A, 2:B, 3:C, 4:D, -1 if there's an error
	 */
	public int getAnswer(){
		if (answers.get(randomQ).equals("A")){
			return 1;
		} else if (answers.get(randomQ).equals("B")){
			return 2;
		} else if (answers.get(randomQ).equals("C")){
			return 3;
		} else if (answers.get(randomQ).equals("D")){
			return 4;
		}
		return -1;
	}
	
	/**
	 * Returns the true if the question is answered, false if otherwise
	 */
	public boolean isAnswered(){
		return answered;
	}
	
	/**
	 * Sets the answered to the boolean argument
	 */
	public void setAnswered(boolean b){
		answered = b;
	}
	
	/**
	 * Randomizes a new question to be asked
	 */
	public void randomizeQ(){
		randomQ = (int)(Math.random() * 30);
	}
}
