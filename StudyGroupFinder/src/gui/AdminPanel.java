package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.GroupData;

/** This Panel is displayed on the left of the {@link AdminGUI}. It displays all groups and all users.
 * 
 * @author Michael Kirby
 *
 */
public class AdminPanel extends JPanel implements ListSelectionListener {
	
	/** The GUIFrame of the program */
	GUIFrame parent;
	
	/** This GroupList displays the all groups. */
	GroupList groups;
	
	/** This UserList displays all users. */
	UserList users;
	
	public AdminPanel(GUIFrame parent) {
		this.parent = parent;
		
		setLayout(new GridLayout(4,1));
		
		add(new JLabel("Groups"));
		ArrayList<Integer> temp = new ArrayList<Integer>(parent.getSGS().searchGroups());
		groups = new GroupList(parent, this, temp.toArray());
		JScrollPane mg = new JScrollPane(groups);
		mg.setPreferredSize(new Dimension(40,40));
		add(mg);
		add(new JLabel("Users"));
		ArrayList<Integer> temp2 = new ArrayList<Integer>(parent.getSGS().searchUsers());
		users = new UserList(parent, this, temp2.toArray());
		JScrollPane sg = new JScrollPane(users);
		sg.setPreferredSize(new Dimension(40,50));
		add(sg);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			GroupData temp = ((GroupList)e.getSource()).getSelectedData();
			if ((GroupList)e.getSource() == groups) {
				groups.removeSelectionInterval(0, 1);
			} else {
				users.removeSelectionInterval(0, 1);
			}
			if (temp != null) {
				parent.getGUI().setRight(new GroupProfile(parent, parent.getSGS().getGroup(temp.getId())));
			} else {
				parent.getGUI().setRight(new JPanel());
			}
		}
	}

}
