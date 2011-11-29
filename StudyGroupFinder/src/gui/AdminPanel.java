package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.Data;
import database.SearchData;
import domainlogic.GroupSearchEvent;
import domainlogic.UserSearchEvent;

/** This Panel is displayed on the left of the {@link AdminGUI}. It displays all groups and all users.
 * 
 * @author Michael Kirby
 *
 */
public class AdminPanel extends JPanel implements ActionListener, ListSelectionListener {
	
	/** The GUIFrame of the program */
	GUIFrame parent;
	
	/** This GroupList displays all groups. */
	GroupList groups;
	
	/** This UserList displays all users. */
	UserList users;
	
	/** The JTextField for Filtering groups */
	JTextField filtsg;
	
	/** The JTextField for Filtering Users */
	JTextField filtuser;
	
	/** The GroupSearchEvent used for the GroupList */
	GroupSearchEvent groupsearch;
	
	/** The UserSearchEvent used for the UserList */
	UserSearchEvent usersearch;
	
	/** Constructs this AdminPanel.
	 * @param parent the {@link GUIFrame} parent of this AdmninPanel.
	 * @param gsearch the SearchData of a group search to construct this with.
	 * @param usearch the SearchData of a user search to construct this with.
	 */
	public AdminPanel(GUIFrame parent, SearchData gsearch, SearchData usearch) {
		
		setOpaque(false);
		
		this.parent = parent;
		
		JLabel heading;
		
		groupsearch = new GroupSearchEvent(parent.getSGS());
		if (gsearch == null) {
			gsearch = new SearchData();
			groupsearch.setData(gsearch);
			groupsearch.execute();
		} else {
			groupsearch.setData(gsearch);
		}
		
		usersearch = new UserSearchEvent(parent.getSGS());
		if (usearch == null) {
			usearch = new SearchData();
			usersearch.setData(usearch);
			usersearch.execute();
		} else {
			usersearch.setData(usearch);
		}
		
		//Update the search event if it has yet to be executed
		if (((SearchData)groupsearch.getData()).getResults() == null) {
			groupsearch.execute();
		} 
		groups = new GroupList(parent, this, ((SearchData)groupsearch.getData()).getResults().toArray());
		
		//Update the search event if it has yet to be executed
		if (((SearchData)usersearch.getData()).getResults() == null) {
			usersearch.execute();
		} 
		users = new UserList(parent, this, ((SearchData)usersearch.getData()).getResults().toArray());
		
		// Set Layout Manager for Panel
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		// Place Groups Label
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 20;
		c.ipadx = 160;
		heading = new JLabel("Groups");
		heading.setForeground(parent.getTheme().headColor());
		add(heading,c);
		
		// Place Groups Filter
		JPanel filt = new JPanel(new GridLayout(1,2));
		filtsg = new JTextField(gsearch.getTerms());
		filt.add(filtsg);
		JButton filter = new JButton("Filter");
        filter.setActionCommand("gfilter");
        filter.addActionListener(this);
        filt.add(filter);
        c.gridy = 1;
        c.ipady = 0;
	    c.insets = new Insets(0, 0, 10, 0);
		add(filt,c);
		
		// Place GroupList
		JScrollPane mg = new JScrollPane(groups);
		mg.setPreferredSize(new Dimension(40,40));
		c.gridy = 2;
	    c.ipady = 80;
	    c.insets = new Insets(0, 0, 30, 0);
		add(mg,c);
		
		// Place Users Label
		c.gridy = 3;
	    c.ipady = 20;
	    c.insets = new Insets(0, 0, 0, 0);
	    heading = new JLabel("Users");
	    heading.setForeground(parent.getTheme().headColor());
		add(heading,c);
		
		// Place Users Filter
		JPanel ufilt = new JPanel(new GridLayout(1,2));
		filtuser = new JTextField(usearch.getTerms());
		ufilt.add(filtuser);
		JButton ufilter = new JButton("Filter");
        ufilter.setActionCommand("ufilter");
        ufilter.addActionListener(this);
        ufilt.add(ufilter);
        c.gridy = 4;
        c.ipady = 0;
	    c.insets = new Insets(0, 0, 10, 0);
		add(ufilt,c);
		
		// Place UserList
		JScrollPane sg = new JScrollPane(users);
		sg.setPreferredSize(new Dimension(40,50));
		c.gridy = 5;
	    c.ipady = 80;
		add(sg,c);
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
	
	public SearchData getGroupSearch() {
		return (SearchData) groupsearch.getData();
	}
	
	public SearchData getUserSearch() {
		return (SearchData) usersearch.getData();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("gfilter".equals(e.getActionCommand())) {
			((SearchData)groupsearch.getData()).setTerms(filtsg.getText());
			groupsearch.execute();
			parent.getGUI().refreshLeft();
		} else if ("ufilter".equals(e.getActionCommand())) {
			((SearchData)usersearch.getData()).setTerms(filtuser.getText());
			usersearch.execute();
			parent.getGUI().refreshLeft();
		}
		
	}

}
