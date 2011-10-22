package gui;

import javax.swing.JPanel;

import database.GroupData;

public class UserGUI extends JPanel {

	public UserGUI() {
		add(new GroupProfile(new GroupData(1, "The Group", "CSE 110", "1~")));
	}
}
