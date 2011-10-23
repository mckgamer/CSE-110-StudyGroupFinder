package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import database.GroupData;

/** UserGUI is a JPanel that manages everything that a user can do. */
public class UserGUI extends JPanel {

	/** The current JPanel that this UserGUI is displaying on the left. */
	private JPanel left;
	
	/** The current JPanel that this UserGUI is displaying on the right. */
	private JPanel right;
	
	/** The {@link GUIFrame} that is displaying this UserGUI */
	private GUIFrame parent;
	
	/** Constructs this UserGUI object using the {@link GUIFrame} that will display it.
	 * 
	 * @param parent the {@link GUIFrame} that will display this UserGUI.
	 */
	public UserGUI(GUIFrame parent) {
		
		//Give this GUI a reference to its GUIFrame parent
		this.parent = parent;
		
		//Set the menubar of the GUIFrame to a UserMenu
		UserMenu menubar = new UserMenu(parent);
        parent.setJMenuBar(menubar);
        
        //Set the size and layout of the Panel
		setSize(500,400);
		setLayout(new GridLayout(1,2));
		
		//Initialize the left and right JPanels for this GUI
		left = new JPanel();
		left.add(new JLabel("Current and Suggested Groups Here"));
		right = new GroupProfile(new GroupData(1, "The Group", "CSE 110", "1~"));
		
		//Apply this GUI's left and right JPanels
		add(left);
		add(right);
	}

}
