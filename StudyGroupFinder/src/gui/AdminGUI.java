package gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/** AdminGUI is a JPanel and implementation of {@link GUIPanel} that manages 
 *  everything that an admin can do.
 * 
 * @author Michael Kirby
 */
public class AdminGUI extends JPanel implements GUIPanel {

	/** The current JPanel that this UserGUI is displaying on the left. */
	private JPanel left;
	
	/** The current JPanel that this UserGUI is displaying on the right. */
	private JPanel right;
	
	/** The {@link GUIFrame} that is displaying this UserGUI */
	private GUIFrame parent;
	
	private GUITheme theme;
	
	public void paintComponent(Graphics g) {
	    g.drawImage(theme.background(), 0, 0, null);
	  }
	
	/** Constructs this AdminGUI object using the {@link GUIFrame} that will display it.
	 * 
	 * @param parent the {@link GUIFrame} that will display this AdminGUI.
	 */
	public AdminGUI(GUIFrame parent, GUITheme theme) {
		
		this.theme = theme;
		
		//Give this GUI a reference to its GUIFrame parent
		this.parent = parent;
		
		//Set the menubar of the GUIFrame to a UserMenu
		AdminMenu menubar = new AdminMenu(parent);
        parent.setJMenuBar(menubar);
        
        //Set the size and layout of the Panel
		setSize(500,400);
		setLayout(new GridLayout(1,2,15,0));
		
		//Initialize the left and right JPanels for this GUI
		left = new AdminPanel(parent, null, null);
		
		right = new JPanel();
		right.setOpaque(false);
		
		//Apply this GUI's left and right JPanels
		add(left);
		add(right);
	}
	
	@Override
	public void setRight(JPanel panel) {
		remove(right);
		right = panel;
		add(right);
		getRootPane().revalidate();
	}

	@Override
	public void refreshLeft() {
		removeAll();
		left = new AdminPanel(parent, ((AdminPanel)left).getGroupSearch(), ((AdminPanel)left).getUserSearch());
		add(left);
		add(right);
		getRootPane().revalidate();
		
	}

}
