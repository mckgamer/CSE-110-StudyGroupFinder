package gui;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/** Class used to maintain layout theme throughout the program.
 * 
 * @author Michael Kirby
 */
public class GUITheme {
	
	/** The Background Image for the Program */
	private Image image;
	
	/** The Color of Headings in the Program */
	private Color heading;
	
	/** The Color of all other text in the Program */
	private Color standard;
	
	/** Constructs this GUITheme.
	 * 
	 * @param image The string location of the background image.
	 * @param heading The color to use for the heading.
	 * @param standard The color to use for default text.
	 */
	public GUITheme(String image, Color heading, Color standard) {
		try {
			this.image = ImageIO.read(GUITheme.class.getResource(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.heading = heading;
		this.standard = standard;
	}
	
	/** Returns the default text color.
	 * @return the default text color.
	 */
	public Color textColor() {
		return standard;
	}
	
	/** Returns the heading text color.
	 * @return the heading text color.
	 */
	public Color headColor() {
		return heading;
	}
	
	/** Returns the background image.
	 * @return the background image.
	 */
	public Image background() {
		return image;
	}

}
