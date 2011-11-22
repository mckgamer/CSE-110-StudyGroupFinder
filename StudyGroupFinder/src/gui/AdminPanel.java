package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.Data;
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
		//ArrayList<Integer> temp = new ArrayList<Integer>(parent.getSGS().searchGroups());
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(1);
		groups = new GroupList(parent, this, temp.toArray());
		JScrollPane mg = new JScrollPane(groups);
		mg.setPreferredSize(new Dimension(40,40));
		add(mg);
		add(new JLabel("Users"));
		//ArrayList<Integer> temp2 = new ArrayList<Integer>(parent.getSGS().searchUsers());
		ArrayList<Integer> temp2 = new ArrayList<Integer>();
		temp2.add(1);
		users = new UserList(parent, this, temp2.toArray());
		JScrollPane sg = new JScrollPane(users);
		sg.setPreferredSize(new Dimension(40,50));
		add(sg);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			Data temp;
			if (e.getSource().getClass() == GroupList.class) {
				temp = ((GroupList)e.getSource()).getSelectedData();
				users.removeSelectionInterval(0, 1);
				if (temp != null) {
					parent.getGUI().setRight(new GroupProfile(parent, parent.getSGS().getGroup(temp.getId())));
				} 
			} else {
				temp = ((UserList)e.getSource()).getSelectedData();
				groups.removeSelectionInterval(0, 1);
				if (temp != null) {
					parent.getGUI().setRight(new UserProfile(parent, parent.getSGS().getUser(temp.getId())));
				}
			}
			if (temp == null) {
				parent.getGUI().setRight(new JPanel());
			}
		}
	}

}
