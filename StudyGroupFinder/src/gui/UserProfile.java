package gui;

import java.awt.Dimension;
import java.awt.Font;
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
	private JLabel name;
	
	private GroupList groupList;
	
	/** Constructs this UserProfile JPanel using a UserData object.
	 * 
	 * @param ud the UserData object to use for this Profile.
	 */
	public UserProfile(GUIFrame parent, UserData ud) {
		this.parent = parent;
		this.ud = ud;
		name = new JLabel(ud.getUName());
		name.setFont(new Font("Dialog", Font.BOLD, 24));
		
        setLayout(new GridLayout(4,1));
        //add(new JLabel("1"));
        
        JPanel namePan = new JPanel();
        namePan.setLayout(new GridLayout(1,2));
        namePan.add(name);
        namePan.add(new JLabel());
        
        add(namePan);
  
        
        add(new JLabel());
        
        
        JPanel submembPan = new JPanel();
        submembPan.setLayout(new GridLayout(2,2,5,0));
        JLabel mem = new JLabel("Groups");
        mem.setFont(new Font("Dialog", Font.BOLD, 14));
        submembPan.add(mem);
        JLabel meet = new JLabel("Meetings");
        meet.setFont(new Font("Dialog", Font.BOLD, 14));
        submembPan.add(meet);
        
        ArrayList<Integer> temp = new ArrayList<Integer>(ud.getModOf());
		temp.addAll(ud.getUserOf());
        groupList = new GroupList(parent, this, temp.toArray());
       
        JScrollPane membersList = new JScrollPane(groupList);
        membersList.setPreferredSize(new Dimension(40,50));
        submembPan.add(membersList);
        JLabel meets = new JLabel("We meet every 2 days.");
        meets.setFont(new Font("Dialog", Font.PLAIN, 12));
        submembPan.add(meets);
        
        
        add(submembPan);
        
        JPanel meetControlPanel = new JPanel();
        meetControlPanel.setLayout(new GridLayout(1,1));
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0,2,5,4));
        
         //TODO Admin buttons here
        	
            JButton edit = new JButton("Edit User");
            edit.setActionCommand("edit");
            edit.addActionListener(this);
            buttons.add(edit);
            
            JButton delete = new JButton("Delete User");
            delete.setActionCommand("delete");
            delete.addActionListener(this);
            buttons.add(delete);
        
        
        
        
        meetControlPanel.add(buttons);
       
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
