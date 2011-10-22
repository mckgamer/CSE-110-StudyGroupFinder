package gui;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import database.GroupData;
import domainlogic.StudyGroupSystem;
import domainlogic.User.Logged;

public class GUIFrame extends JFrame {
	
	private StudyGroupSystem sgs;
	private JPanel gui;
	
	public GUIFrame(StudyGroupSystem sgs) {
		this.sgs = sgs;
		setTitle("Study Group Finder 0.1");
        setMinimumSize(new Dimension(740,500));
        
       // LogInDialog ld = new LogInDialog(this);
        //ld.setVisible(true);
        gui = new JPanel();
        //new UserGUI();
	}
	
	public void runProgram() {
		while (sgs.isLogged() == false) {
			LogInDialog ld = new LogInDialog(this);
	        ld.setVisible(true);
	        if (ld.getStatus() == Logged.USER) {
				gui = new UserGUI();
			} else if (ld.getStatus() == Logged.ADMIN) {
				//gui = new AdminGUI();
			}
		}
		setContentPane(gui);
	}
	
	public StudyGroupSystem getSGS() {
		return sgs;
	}

}
