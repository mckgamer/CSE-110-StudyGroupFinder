package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.GroupData;
import database.GroupUserData;
import domainlogic.AddUserToGroupEvent;
import domainlogic.DeModEvent;
import domainlogic.DeleteGroupEvent;
import domainlogic.MakeModeratorEvent;
import domainlogic.RemoveUserFromGroupEvent;
import domainlogic.UpdateGroupProfileEvent;
import domainlogic.User.Logged;

/** GroupProfile is a JPanel that is able to display a study groups data using a {@link GroupData} object. */
public class GroupProfile extends JPanel implements ActionListener, ListSelectionListener {
	
	private GUIFrame parent;
	private GroupData gd;
	private JLabel name;
	private JLabel course;
	private JLabel description;
	
	private UserList userList;
	
	/** Constructs this GroupProfile JPanel using a GroupData object.
	 * 
	 * @param gd the GroupData object to use for this Profile.
	 */
	public GroupProfile(GUIFrame parent, GroupData gd) {
		this.parent = parent;
		this.gd = gd;
		name = new JLabel(gd.getName());
		name.setFont(new Font("Dialog", Font.BOLD, 24));
		course = new JLabel("Course: " + gd.getCourse());
		description = new JLabel("<html>We like to study " + gd.getCourse() +". We are a really cool group and we<br> hope you join us.</html>");
		description.setFont(new Font("Dialog", Font.PLAIN, 12));
		
        setLayout(new GridLayout(4,1));
        //add(new JLabel("1"));
        
        JPanel namePan = new JPanel();
        namePan.setLayout(new GridLayout(1,2));
        namePan.add(name);
        namePan.add(course);
        
        add(namePan);
  
        
        add(description);
        
        
        JPanel submembPan = new JPanel();
        submembPan.setLayout(new GridLayout(2,2,5,0));
        JLabel mem = new JLabel("Members");
        mem.setFont(new Font("Dialog", Font.BOLD, 14));
        submembPan.add(mem);
        JLabel meet = new JLabel("Meetings");
        meet.setFont(new Font("Dialog", Font.BOLD, 14));
        submembPan.add(meet);
        
        ArrayList<Integer> temp = new ArrayList<Integer>(gd.getMods());
		temp.addAll(gd.getUsers());
        userList = new UserList(parent, this, temp.toArray());
        /*
        ArrayList<Integer> temp = gd.getUsers();
        temp.addAll(gd.getMods());
        Object[] members = temp.toArray();
        JScrollPane membersList = new JScrollPane(new JList(members));*/
        JScrollPane membersList = new JScrollPane(userList);
        membersList.setPreferredSize(new Dimension(40,50));
        submembPan.add(membersList);
        JLabel meets = new JLabel("We meet every 2 days.");
        meets.setFont(new Font("Dialog", Font.PLAIN, 12));
        submembPan.add(meets);
        
        
        add(submembPan);
        
        JPanel meetControlPanel = new JPanel();
        meetControlPanel.setLayout(new GridLayout(1,2));
        meetControlPanel.add(new JLabel("ModButtons here?"));
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0,1,5,4));
        
        if (parent.getSGS().getLoggedUser().isModOf(gd.getId()) || parent.getSGS().getUserStatus() == Logged.ADMIN) {
         //TODO Moderator buttons here
        	JButton remove = new JButton("Remove User");
        	//remove.setEnabled(false);
        	remove.setActionCommand("remove");
            remove.addActionListener(this);
            buttons.add(remove);
            JButton makemod = new JButton("Make User Mod");
            makemod.setActionCommand("makemod");
            makemod.addActionListener(this);
            buttons.add(makemod);
            JButton demod = new JButton("DeMod User");
            demod.setActionCommand("demod");
            demod.addActionListener(this);
            buttons.add(demod);
            JButton edit = new JButton("Edit Group");
            edit.setActionCommand("edit");
            edit.addActionListener(this);
            buttons.add(edit);
            if (parent.getSGS().getUserStatus() != Logged.ADMIN) {
	            JButton resign = new JButton("Resign");
	            resign.setEnabled(false);
	            resign.setActionCommand("resign");
	            resign.addActionListener(this);
	            buttons.add(resign);
            }
            JButton delete = new JButton("Delete Group");
            delete.setActionCommand("delete");
            delete.addActionListener(this);
            buttons.add(delete);
        } else {
	        if (parent.getSGS().getLoggedUser().isUserOf(gd.getId())) {
	        	//Add the Leave Button
	            JButton leave = new JButton("Leave");
	            leave.setActionCommand("leave");
	            leave.addActionListener(this);
	            buttons.add(leave);
	        } else {
		        // Add The Join Button
		        JButton join = new JButton("Join");
		        join.setActionCommand("join");
		        join.addActionListener(this);
		        buttons.add(join);
	        }
        }
        
        
        
        
        meetControlPanel.add(buttons);
       
        add(meetControlPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if ("edit".equals(e.getActionCommand())) {
			UpdateGroupDialog ugpd = new UpdateGroupDialog(parent, gd.getId()); 
			ugpd.setVisible(true);
		} else if ("leave".equals(e.getActionCommand())) {
			//TODO show confirmation
			RemoveUserFromGroupEvent rufg = new RemoveUserFromGroupEvent(parent.getSGS(), parent.getSGS().getLoggedUser().getId(),gd.getId());
			rufg.validate();
			rufg.execute();
			parent.getGUI().setRight(new GroupProfile(parent, parent.getSGS().getGroup(gd.getId()))); //TODO is this efficient enough? Maybe hack it till next time loaded?
			parent.getGUI().refreshLeft();
		} else if ("join".equals(e.getActionCommand())) {
			//TODO show confirmation
			AddUserToGroupEvent autg = new AddUserToGroupEvent(parent.getSGS(), parent.getSGS().getLoggedUser().getId(),gd.getId());
			autg.validate();
			autg.execute();
			parent.getGUI().setRight(new GroupProfile(parent, parent.getSGS().getGroup(gd.getId()))); //TODO is this efficient enough? Maybe hack it till next time loaded?
			parent.getGUI().refreshLeft();
		} else if ("remove".equals(e.getActionCommand())) {
			//TODO show confirmation
			if (userList.getSelectedData() != null) {
				RemoveUserFromGroupEvent rufg = new RemoveUserFromGroupEvent(parent.getSGS(), userList.getSelectedData().getId(),gd.getId());//TODO what userid? is this
				rufg.validate();
				rufg.execute();
				parent.getGUI().setRight(new GroupProfile(parent, parent.getSGS().getGroup(gd.getId()))); //TODO is this efficient enough? Maybe hack it till next time loaded?
			}
		} else if ("makemod".equals(e.getActionCommand())) {
			//TODO show confirmation
			if (userList.getSelectedData() != null) {
				MakeModeratorEvent mme = new MakeModeratorEvent(parent.getSGS());
				mme.setData(new GroupUserData(userList.getSelectedData(),gd));
				mme.validate();
				mme.execute();
				parent.getGUI().setRight(new GroupProfile(parent, parent.getSGS().getGroup(gd.getId()))); //TODO is this efficient enough? Maybe hack it till next time loaded?
			}
		} else if ("demod".equals(e.getActionCommand())) {
			//TODO show confirmation
			if (userList.getSelectedData() != null) {
				DeModEvent dme = new DeModEvent(parent.getSGS());
				dme.setData(new GroupUserData(userList.getSelectedData(),gd));
				dme.validate();
				dme.execute();
				parent.getGUI().setRight(new GroupProfile(parent, parent.getSGS().getGroup(gd.getId()))); //TODO is this efficient enough? Maybe hack it till next time loaded?
			}
		} else if ("delete".equals(e.getActionCommand())) {
			//TODO DEFINITELY show confirmation
			DeleteGroupEvent dg = new DeleteGroupEvent(parent.getSGS());
			dg.setData(gd);
			dg.validate();
			dg.execute();
			parent.getSGS().refreshLoggedUser();
			parent.getGUI().setRight(new JPanel());
			parent.getGUI().refreshLeft();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
