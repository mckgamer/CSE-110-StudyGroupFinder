package gui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import database.SearchData;

/** UserGUI is a JPanel and implementation of {@link GUIPanel} that manages 
 *  everything that a user can do.
 * 
 * @author Michael Kirby
 *
 */
public class UserGUI extends JPanel implements GUIPanel  {

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
	
	/** Constructs this UserGUI object using the {@link GUIFrame} that will display it.
	 * 
	 * @param parent the {@link GUIFrame} that will display this UserGUI.
	 */
	public UserGUI(GUIFrame parent, GUITheme theme) {
		
		this.theme = theme;
		
		//Give this GUI a reference to its GUIFrame parent
		this.parent = parent;
		
		//Set the menubar of the GUIFrame to a UserMenu
		UserMenu menubar = new UserMenu(parent);
        parent.setJMenuBar(menubar);
        
        //Set the size and layout of the Panel
		setSize(500,400);
		setLayout(new GridLayout(1,2,15,0));
		
		//Initialize the left and right JPanels for this GUI
		left = new UserGroupsPanel(parent, null);
		
		right = new JPanel();
		right.setOpaque(false);
		
		//Apply this GUI's left and right JPanels
		add(left);
		add(right);
	}
	
	/** Set the right JPanel of the UserGUI and refresh the Panel. 
	 * 
	 * @param panel the panel to put on the right of this UserGUI.
	 */
	public void setRight(JPanel panel) {
		remove(right);
		right = panel;
		add(right);
		getRootPane().revalidate();
	}
	
	public void refreshLeft() {
		removeAll();
		left = new UserGroupsPanel(parent, ((UserGroupsPanel)left).getFilter());
		add(left);
		add(right);
		getRootPane().revalidate();
	}
	
	public void fullRefresh() {
		removeAll();
		SearchData temp = ((UserGroupsPanel)left).getFilter();
		temp.setResults(null);
		left = new UserGroupsPanel(parent, null);
		right = new JPanel();
		add(left);
		add(right);
		getRootPane().revalidate();
	}

}
