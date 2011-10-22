package gui;

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
		course = new JLabel(gd.getCourse());
		description = new JLabel("We like to study " + gd.getCourse());
		
        setLayout(new GridLayout(4,1));
        
        JPanel namePan = new JPanel();
        namePan.setLayout(new GridLayout(1,2));
        namePan.add(name);
        namePan.add(new JLabel("School: UCSD"));
        
        add(namePan);
  
        add(description);
        
        JPanel submembPan = new JPanel();
        submembPan.setLayout(new GridLayout(2,2));
        submembPan.add(new JLabel("Course"));
        submembPan.add(new JLabel("Members"));
        add(course);
        Object[] members = {"Melissa Grant", "Tom Johnson", "Steve Lopkins"};
        submembPan.add(new JScrollPane(new JList(members)));
        
        add(submembPan);
        
        JPanel meetControlPanel = new JPanel();
        meetControlPanel.setLayout(new GridLayout(2,2));
        meetControlPanel.add(new JLabel("Meetings"));
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
