package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.UserData;
import domainlogic.DeleteUserEvent;

/** UserProfile is a JPanel that is able to display user data using a {@link UserData} object. */
public class UserProfile extends JPanel implements ActionListener, ListSelectionListener {
	
	private GUIFrame parent;
	private UserData ud;
	
	private GroupList groupList;
	
	/** Constructs this UserProfile JPanel using a UserData object.
	 * 
	 * @param ud the UserData object to use for this Profile.
	 */
	public UserProfile(GUIFrame parent, UserData ud) {
		
		setOpaque(false);
		
		this.parent = parent;
		this.ud = ud;
		
		setLayout(new GridLayout(4,1,8,8));
		
		// Show the Users Username
		JLabel name = new JLabel(ud.getUName());
		name.setForeground(parent.getTheme().headColor());
		name.setFont(new Font("Dialog", Font.BOLD, 24));
		add(name);
  
        // Show the Users Courses
        JLabel courses = new JLabel("My Courses: " + ud.getCourses());
        courses.setForeground(parent.getTheme().textColor());
        add(courses);
        
        // Show the Users Current Groups
        JPanel submembPan = new JPanel();
        submembPan.setOpaque(false);
        submembPan.setLayout(new GridLayout(2,1,5,0));
        JLabel mem = new JLabel("Groups");
        mem.setForeground(parent.getTheme().headColor());
        mem.setFont(new Font("Dialog", Font.BOLD, 14));
        submembPan.add(mem);
        
        ArrayList<Integer> temp = new ArrayList<Integer>(ud.getModOf());
		temp.addAll(ud.getUserOf());
        groupList = new GroupList(parent, this, temp.toArray());
       
        JScrollPane membersList = new JScrollPane(groupList);
        membersList.setPreferredSize(new Dimension(40,50));
        submembPan.add(membersList);
        
        
        add(submembPan);
        
        // Show control panel
        JPanel meetControlPanel = new JPanel();
        meetControlPanel.setOpaque(false);
        meetControlPanel.setLayout(new GridLayout(1,1));
        
        // Create Panel for Buttons
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new GridLayout(0,2,5,4));
        
        // Edit user button	
        JButton edit = new JButton("Edit User");
        edit.setActionCommand("edit");
        edit.addActionListener(this);
        buttons.add(edit);
        
        // Delete User button
        JButton delete = new JButton("Delete User");
        delete.setActionCommand("delete");
        delete.addActionListener(this);
        buttons.add(delete);
        
        buttons.add(new JLabel());
        buttons.add(new JLabel());
        buttons.add(new JLabel());
        // Add buttons to Control Panel
        meetControlPanel.add(buttons);
       
        // Show Control Panel
        add(meetControlPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ("edit".equals(e.getActionCommand())) {
			UpdateUserDialog uud = new UpdateUserDialog(parent, ud.getId()); 
			uud.setVisible(true);
		} else if ("delete".equals(e.getActionCommand())) {
			//TODO DEFINITELY show confirmation
			DeleteUserEvent du = new DeleteUserEvent(parent.getSGS());
			du.setData(ud);
			du.validate();
			du.execute();
			parent.getGUI().setRight(new JPanel());
			parent.getGUI().refreshLeft();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
