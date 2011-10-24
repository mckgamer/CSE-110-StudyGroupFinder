import javax.swing.*;

import database.*;
import domainlogic.*;
import gui.*;

/** The main holding class for StudyGroupFinder. This initializes an instance of the program. */
public class Start extends JPanel {

	/** The {@link MapDatabase} to use for the program. */
	final static MapDatabase database = new MapDatabase();
	
	/** The {@link StudyGroupSystem} to use for the program. */
	final static StudyGroupSystem system = new StudyGroupSystem(database);
	
	/** The {@link GUIFrame} to use for the program. */
	final static GUIFrame gui = new GUIFrame(system);
	
	//TODO don't think this is necessary
	Start() {
	}
	
	/** Called by main. Calls all of the logic of the program */
	private static void createAndShowGUI() {
		
        //gui.setContentPane(new Start())
        
		// Call the GUIFrame and make it run the program
        gui.runProgram();
        
        //Display the window.
        gui.pack();
        gui.setVisible(true);
	}
	
	/** The main method for the Study Group Finder. Calls the needed methods to run the program.
	 * 
	 * @param args 
	 */ //TODO for args we could maybe allow -debug to be an argument that enables logging or something.
	public static void main(String[] args) {
    	
    	System.out.println("It compiles now!");
    	
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

	}
	
}
