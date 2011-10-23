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
public class UserGUI extends JPanel implements ActionListener {

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
		this.parent = parent;
		JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Study Group");
        JMenuItem menuItem = new JMenuItem("New Study Group");
        menuItem.setActionCommand("New Group");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Check For Invites");
        menuItem.setEnabled(false);
        menu.add(menuItem);
        menubar.add(menu);
        
        menu = new JMenu("My Account");
        menuItem = new JMenuItem("My Profile");
        menuItem.setEnabled(false);
        menu.add(menuItem);
        menu.addSeparator();
        menu.add(menuItem);
        menuItem = new JMenuItem("Log Off");
        menuItem.setActionCommand("Log Off");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menubar.add(menu);
        
        menu = new JMenu("Help");
        menuItem = new JMenuItem("About StudyGroupFinder");
        menu.add(menuItem);
        menubar.add(menu);
        
        
        parent.setJMenuBar(menubar);
        
        
		setSize(500,400);
		setLayout(new GridLayout(1,2));
		add(new JLabel("Current and Suggested Groups Here"));
		add(new GroupProfile(new GroupData(1, "The Group", "CSE 110", "1~")));
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
