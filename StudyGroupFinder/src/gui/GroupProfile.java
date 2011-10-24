package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import database.GroupData;

/** GroupProfile is a JPanel that is able to display a study groups data using a {@link GroupData} object. */
public class GroupProfile extends JPanel {
	
	private GUIFrame parent;
	private JLabel name;
	private JLabel course;
	private JLabel description;
	
	/** Constructs this GroupProfile JPanel using a GroupData object.
	 * 
	 * @param gd the GroupData object to use for this Profile.
	 */
	public GroupProfile(GUIFrame parent, GroupData gd) {
		this.parent = parent;
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
        ArrayList<Integer> temp = gd.getUsers();
        temp.addAll(gd.getMods());
        Object[] members = temp.toArray();
        JScrollPane membersList = new JScrollPane(new JList(members));
        membersList.setPreferredSize(new Dimension(40,50));
        submembPan.add(membersList);
        JLabel meets = new JLabel("We meet every 2 days.");
        meets.setFont(new Font("Dialog", Font.PLAIN, 12));
        submembPan.add(meets);
        
        
        add(submembPan);
        
        JPanel meetControlPanel = new JPanel();
        meetControlPanel.setLayout(new GridLayout(2,2));
        meetControlPanel.add(new JLabel("ModButtons here?"));
        meetControlPanel.add(new JLabel());
        meetControlPanel.add(new JLabel());
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0,1,5,4));
        
        if (parent.getSGS().getLoggedUser().isModOf(gd.getId())) {
         //TODO Moderator buttons here
        	JButton remove = new JButton("Remove User");
            buttons.add(remove);
            JButton edit = new JButton("Edit Group");
            buttons.add(edit);
        } else {
	        if (parent.getSGS().getLoggedUser().isUserOf(gd.getId())) {
	        	//Add the Leave Button
	            JButton leave = new JButton("Leave");
	            //join.setActionCommand("Leave");
	            //join.addActionListener(this);
	            buttons.add(leave);
	        } else {
		        // Add The Join Button
		        JButton join = new JButton("Join");
		        //join.setActionCommand("Join");
		        //join.addActionListener(this);
		        buttons.add(join);
	        }
        }
        
        
        
        
        meetControlPanel.add(buttons);
       
        add(meetControlPanel);
	}

}
