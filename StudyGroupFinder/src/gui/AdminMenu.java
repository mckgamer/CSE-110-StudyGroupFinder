package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/** This JMenuBar is used by the AdminGUI to update the frames menubar appropriately. 
 * 
 * @author Michael Kirby
 *
 */
public class AdminMenu extends JMenuBar implements ActionListener {

	/** The {@link GUIFrame} that is displaying this AdminMenu */
	GUIFrame parent;
	
	/** Constructs this AdminMenu object using the {@link GUIFrame} that will display it.
	 * 
	 * @param parent the {@link GUIFrame} that will display this AdminMenu.
	 */
	public AdminMenu(GUIFrame parent) {
		
		//Give this Dialog a reference to its GUIFrame parent
		this.parent = parent;
		
		//Create the Menu
		JMenu menu = new JMenu("Admin");
	    JMenuItem menuItem = new JMenuItem("New Study Group");
	    menuItem.setActionCommand("New Group");
	    menuItem.addActionListener(this);
	    menu.add(menuItem);
	    menuItem = new JMenuItem("Check For Invites");
	    menuItem.setEnabled(false);
	    menu.add(menuItem);
	    add(menu);
	    
	    menu = new JMenu("My Account");
	    menuItem = new JMenuItem("Log Off");
	    menuItem.setActionCommand("Log Off");
	    menuItem.addActionListener(this);
	    menu.add(menuItem);
	    add(menu);
	    
	    menu = new JMenu("Help");
	    menuItem = new JMenuItem("About StudyGroupFinder");
	    menuItem.setEnabled(false);
	    menu.add(menuItem);
	    add(menu);
	}
    
    @Override
	public void actionPerformed(ActionEvent e) {
    	if ("Log Off".equals(e.getActionCommand())) {
			parent.getSGS().logoff();
			if (parent.getSGS().isLogged() == false) {
				parent.setVisible(false);
				parent.runProgram();
			}
		} else if("New Group".equals(e.getActionCommand())) {
			NewGroupDialog ngd = new NewGroupDialog(parent);
	        ngd.setVisible(true);
		}
	}
    
}
