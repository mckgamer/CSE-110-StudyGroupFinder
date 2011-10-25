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

/** This Panel is displayed on the left of the UserGUI. It displays the users current groups
 * as well as groups suggested to them by the system.
 * @author Michael Kirby
 *
 */
public class UserGroupsPanel extends JPanel implements ListSelectionListener {
	
	/** The GUIFrame of the program */
	GUIFrame parent;
	
	/** THis GroupList displays the users current groups */
	GroupList currGroup;
	
	/** This GroupList displays the users suggest groups */
	GroupList suggGroup;
	
	public UserGroupsPanel(GUIFrame parent) {
		this.parent = parent;
		
		setLayout(new GridLayout(4,1));
		
		add(new JLabel("Current Groups"));
		ArrayList<Integer> temp = new ArrayList<Integer>(parent.getSGS().getLoggedUser().getModOf());
		temp.addAll(parent.getSGS().getLoggedUser().getUserOf());
		currGroup = new GroupList(parent, this, temp.toArray()); //TODO user also
		JScrollPane mg = new JScrollPane(currGroup);
		mg.setPreferredSize(new Dimension(40,40));
		add(mg);
		add(new JLabel("Suggested Groups"));
		Object[] empty = {2};
		//suggGroup = new GroupList(parent, this, parent.getSGS().getSuggestedGroups().toArray()); //TODO getSugGroups() sgs
		suggGroup = new GroupList(parent, this, empty);
		JScrollPane sg = new JScrollPane(suggGroup);
		sg.setPreferredSize(new Dimension(40,50));
		add(sg);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			GroupData temp = ((GroupList)e.getSource()).getSelectedData();
			if ((GroupList)e.getSource() == currGroup) {
				suggGroup.removeSelectionInterval(0, 1);
			} else {
				currGroup.removeSelectionInterval(0, 1);
			}
			if (temp != null) {
				parent.getGUI().setRight(new GroupProfile(parent, parent.getSGS().getGroup(temp.getId())));
			} else {
				parent.getGUI().setRight(new JPanel());
			}
		}
	}

}
