package gui;

import javax.swing.JPanel;

/**
 * This interface allows for {@link UserGUI} and {@link AdminGUI} to be required to
 * have certain refreshing methods.
 * 
 * @author Michael Kirby
 */
public interface GUIPanel {
	
	/** Set the right JPanel of the GUIPanel and refresh the right Panel. 
	 * 
	 * @param panel the panel to put on the right of this GUIPanel.
	 */
	public void setRight(JPanel panel);
	
	/** Refresh the left Panel of this GUIPanel.
	 */
	public void refreshLeft();

}
