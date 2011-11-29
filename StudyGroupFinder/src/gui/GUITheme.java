package gui;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

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
	
	/** The Background for all pop-ups in the program. */
	private Image dialog_bg;
	
	/** The Icon for all pop-ups in the program. */
	private Icon dialog_icon;
	
	/** Constructs this GUITheme.
	 * 
	 * @param image The string location of the background image.
	 * @param heading The color to use for the heading.
	 * @param standard The color to use for default text.
	 */
	public GUITheme(String image, Color heading, Color standard, String dimg, String icon) {
		try {
			this.image = ImageIO.read(GUITheme.class.getResource(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			this.dialog_bg = ImageIO.read(GUITheme.class.getResource(dimg));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (icon != null) {
			java.net.URL url = GUITheme.class.getResource(icon);
			if (url != null) {
				this.dialog_icon = new ImageIcon(url);
			} else {
				this.dialog_icon = null;
			}
		} else {
			this.dialog_icon = null;
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
	
	/** Returns the dialog background image.
	 * @return the dialog background image.
	 */
	public Image dialogBG() {
		return dialog_bg;
	}
	
	/** Returns the dialog icon image.
	 * @return the dialog icon image.
	 */
	public Icon getIcon() {
		return dialog_icon;
	}

}
