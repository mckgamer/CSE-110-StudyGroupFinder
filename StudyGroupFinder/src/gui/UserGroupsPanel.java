package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.GroupData;

public class UserGroupsPanel extends JPanel implements ListSelectionListener {
	
	GUIFrame parent;
	UserGUI ug;
	
	public UserGroupsPanel(GUIFrame parent, UserGUI ug) {
		this.parent = parent;
		this.ug = ug;
		
		setLayout(new GridLayout(4,1));
		
		add(new JLabel("Current Groups"));
		GroupList myGroups = new GroupList(parent, this, parent.getSGS().getLoggedUser().getUserOf().toArray());
		JScrollPane mg = new JScrollPane(myGroups);
		mg.setPreferredSize(new Dimension(40,40));
		add(mg);
		add(new JLabel("Suggested Groups"));
		Object[] empty = {};
		GroupList suggestGroups = new GroupList(parent, this, empty);
		JScrollPane sg = new JScrollPane(suggestGroups);
		sg.setPreferredSize(new Dimension(40,50));
		add(sg);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			GroupData temp = ((GroupList)e.getSource()).getSelectedData();
			if (temp != null) {
				ug.setRight(new GroupProfile(parent.getSGS().getGroup(temp.getId())));
			} else {
				ug.setRight(new JPanel());
			}
		}
	}

}
