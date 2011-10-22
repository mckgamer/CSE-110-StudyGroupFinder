package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import database.GroupData;

public class GroupProfile extends JPanel {
	
	private JLabel name;
	private JLabel course;
	private JLabel description;
	
	public GroupProfile(GroupData gd) {
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
        Object[] members = {"Melissa Grant", "Tom Johnson", "Steve Lopkins", "Steve Lopkins", "Steve Lopkins", "Steve Lopkins", "Steve Lopkins"};
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
        
        // Add The Join Button
        JButton join = new JButton("Join");
        //join.setActionCommand("Join");
        //join.addActionListener(this);
        buttons.add(join);
        
        //Add the Leave Button
        join = new JButton("Leave");
        //join.setActionCommand("Leave");
        //join.addActionListener(this);
        buttons.add(join);
        
        
        meetControlPanel.add(buttons);
       
        add(meetControlPanel);
	}

}
