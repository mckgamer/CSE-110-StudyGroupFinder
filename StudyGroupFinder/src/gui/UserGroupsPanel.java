package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.GroupData;

/** This Panel is displayed on the left of the UserGUI. It displays the users current groups
 * as well as groups suggested to them by the system.
 * @author Michael Kirby
 *
 */
public class UserGroupsPanel extends JPanel implements ActionListener, ListSelectionListener {
	
	/** The GUIFrame of the program */
	GUIFrame parent;
	
	/** THis GroupList displays the users current groups */
	GroupList currGroup;
	
	/** This GroupList displays the users suggest groups */
	GroupList suggGroup;
	
	JTextField filtsg;
	
	public UserGroupsPanel(GUIFrame parent, String filterTerms) {
		this.parent = parent;
		
		setLayout(new GridLayout(5,1));
		
		add(new JLabel("Current Groups"));
		ArrayList<Integer> temp = new ArrayList<Integer>(parent.getSGS().getLoggedUser().getModOf());
		temp.addAll(parent.getSGS().getLoggedUser().getUserOf());
		currGroup = new GroupList(parent, this, temp.toArray()); //TODO user also
		JScrollPane mg = new JScrollPane(currGroup);
		mg.setPreferredSize(new Dimension(40,40));
		add(mg);
		add(new JLabel("Suggested Groups"));
		JPanel filt = new JPanel(new GridLayout(1,2));
		filtsg = new JTextField(filterTerms);
		filt.add(filtsg);
		JButton filter = new JButton("Filter");
        filter.setActionCommand("filter");
        filter.addActionListener(this);
        filt.add(filter);
		add(filt);
		// TODO DUMMY SUGGEST CODE FOLLOWS
		ArrayList<Integer> empty = new ArrayList<Integer>();
		for (int x = 1; x < 10; x++) {
			if (parent.getSGS().getGroup(x) != null && !parent.getSGS().getLoggedUser().isUserOf(x) && !parent.getSGS().getLoggedUser().isModOf(x)) {
				empty.add(x);
			}
		}
		//TODO END DUMMY SUGGEST CODE
		//suggGroup = new GroupList(parent, this, parent.getSGS().getSuggestedGroups(filterTerms));
		suggGroup = new GroupList(parent, this, empty.toArray());
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("filter".equals(e.getActionCommand())) {
			System.out.println("Filter TBI.");	
			parent.getGUI().refreshLeft();
		}
	}
	
	public String getFilter() {
		return filtsg.getText();
	}

}
