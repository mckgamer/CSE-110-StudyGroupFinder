package gui;


import java.awt.Color;
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
	
	/** The theme of the Program */
	private GUITheme theme;
	
	/** Constructs this GUIFrame. Sets the default size and Title
	 * 
	 * @param sgs the {@link StudyGroupSystem} to use with this GUIFrame.
	 */
	public GUIFrame(StudyGroupSystem sgs) {
		this.sgs = sgs;
		setTitle("Study Group Finder 0.9");
		setMinimumSize(new Dimension(740,500));
		this.setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gui = new JPanel();
        
        // Set the theme for the program
        theme = new GUITheme("background.jpeg", new Color(246, 180, 86), new Color(255,255,255));
	}
	
	/** Controls Logging in and makes sure this GUIFrame is displaying its current JPanel (gui) */
	public void runProgram() {
		while (sgs.isLogged() == false) {
			LogInDialog ld = new LogInDialog(this);
	        ld.setVisible(true);
	        if (ld.getStatus() == Logged.USER) {
				gui = new UserGUI(this, theme);
			} else if (ld.getStatus() == Logged.ADMIN) {
				gui = new AdminGUI(this, theme);
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
	
	public GUIPanel getGUI() {
		return (GUIPanel) gui;
	}
	
	public GUITheme getTheme() {
		return theme;
	}
}
