package gui;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import domainlogic.StudyGroupSystem;
import domainlogic.User.Logged;

/** This is the main GUI class of the program, is a JFrame and controls most of the running of the program.
 * 
 * @author Michael Kirby
 */
public class GUIFrame extends JFrame {
	
	/** The {@link StudyGroupSystem} associated with this GUIFrame. */
	private StudyGroupSystem sgs;
	
	/** The Current panel that this frame is displaying i.e {@link UserGUI}, {@link AdminGUI}, etc*/
	private JPanel gui;
	
	/** Constructs this GUIFrame. Sets the default size and Title
	 * 
	 * @param sgs the {@link StudyGroupSystem} to use with this GUIFrame.
	 */
	public GUIFrame(StudyGroupSystem sgs) {
		this.sgs = sgs;
		setTitle("Study Group Finder 0.1");
        setMinimumSize(new Dimension(740,500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gui = new JPanel();
	}
	
	/** Controls Logging in and makes sure this GUIFrame is displaying its curent JPanel (gui) */
	public void runProgram() {
		while (sgs.isLogged() == false) {
			LogInDialog ld = new LogInDialog(this);
	        ld.setVisible(true);
	        if (ld.getStatus() != Logged.USER) {
				gui = new UserGUI(this);
			} else if (ld.getStatus() != Logged.ADMIN) {
				gui = new AdminGUI(this);
			}
		}
		setContentPane(gui);
		this.setVisible(true);
	}
	
	/** Returns the {@link StudyGroupSystem} associated with this GUIFrame.
	 * 
	 * @return the {@link StudyGroupSystem} associated with this GUIFrame.
	 */
	public StudyGroupSystem getSGS() {
		return sgs;
	}
	
	public UserGUI getGUI() {
		return (UserGUI)gui;
	}
}
