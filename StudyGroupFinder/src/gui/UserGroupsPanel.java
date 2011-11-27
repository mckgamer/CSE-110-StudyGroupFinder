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

import database.GroupData;
import database.SearchData;
import domainlogic.GroupSearchEvent;

/** This Panel is displayed on the left of the UserGUI. It displays the users current groups
 * as well as groups suggested to them by the system.
 * 
 * @author Michael Kirby
 */
public class UserGroupsPanel extends JPanel implements ActionListener, ListSelectionListener {
	
	/** The GUIFrame of the program */
	GUIFrame parent;
	
	/** THis GroupList displays the users current groups */
	GroupList currGroup;
	
	/** This GroupList displays the users suggest groups */
	GroupList suggGroup;
	
	JTextField filtsg;
	
	GroupSearchEvent search;
	
	public UserGroupsPanel(GUIFrame parent, SearchData oldsearch) {
		
		this.parent = parent;
		
		search = new GroupSearchEvent(parent.getSGS());
		if (oldsearch == null) {
			oldsearch = new SearchData();
			oldsearch.setPrivateTerms(parent.getSGS().getSuggestedTerms());
			search.setData(oldsearch);
			search.execute();
			search.removeCurrent();
		} else {
			search.setData(oldsearch);
		}
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		// Display the Users Current Groups
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 20;
		c.ipadx = 160;
		add(new JLabel("Current Groups"),c);
		ArrayList<Integer> temp = new ArrayList<Integer>(parent.getSGS().getLoggedUser().getModOf());
		temp.addAll(parent.getSGS().getLoggedUser().getUserOf());
		currGroup = new GroupList(parent, this, temp.toArray());
		JScrollPane mg = new JScrollPane(currGroup);
		mg.setPreferredSize(new Dimension(100,40));
	    c.gridy = 1;
	    c.ipady = 80;
	    c.insets = new Insets(0, 0, 30, 0);
		add(mg,c);
		
		//Display the Groups Suggested to the User
	    c.gridy = 2;
	    c.ipady = 20;
	    c.insets = new Insets(0, 0, 0, 0);
		add(new JLabel("Suggested Groups"),c);
		JPanel filt = new JPanel(new GridLayout(1,2));
		filtsg = new JTextField(oldsearch.getTerms());
		filt.add(filtsg);
		JButton filter = new JButton("Filter");
        filter.setActionCommand("filter");
        filter.addActionListener(this);
        filt.add(filter);
        c.gridy = 3;
        c.ipady = 0;
	    c.insets = new Insets(0, 0, 10, 0);
		add(filt,c);
		
		//Update the search event if it has yet to be executed
		if (((SearchData)search.getData()).getResults() == null) {
			search.execute();
		} 
		suggGroup = new GroupList(parent, this, ((SearchData)search.getData()).getResults().toArray());

		//suggGroup = new GroupList(parent, this, empty.toArray());
		JScrollPane sg = new JScrollPane(suggGroup);
		sg.setPreferredSize(new Dimension(40,50));
	    c.gridy = 4;
	    c.ipady = 80;
		add(sg,c);
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
			((SearchData)search.getData()).setTerms(filtsg.getText());
			search.execute();
			search.removeCurrent();
			parent.getGUI().refreshLeft();
		}
	}
	
	public SearchData getFilter() {
		return (SearchData) search.getData();
	}

}
