package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import database.GroupData;

public class UserGUI extends JPanel {

	private JPanel left;
	private JPanel right;
	
	public UserGUI() {
		setSize(500,400);
		setLayout(new GridLayout(1,2));
		add(new JLabel("Current and Suggested Groups Here"));
		add(new GroupProfile(new GroupData(1, "The Group", "CSE 110", "1~")));
	}
}
