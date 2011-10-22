import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.Database;
import database.GroupData;
import database.MapDatabase;
import database.VectorDatabase;
import domainlogic.StudyGroupSystem;
import gui.GUIFrame;
import gui.GroupProfile;


public class Start extends JPanel {

	final static MapDatabase database = new MapDatabase();
	final static StudyGroupSystem system = new StudyGroupSystem(database);
	final static GUIFrame gui = new GUIFrame(system);
	
	Start() {
	
	}
	
	private static void createAndShowGUI() {
		
        gui.setContentPane(new Start());
        
        gui.runProgram();
        
        //Display the window.
        gui.pack();
        gui.setVisible(true);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
    	//StudyGroupSystem sGSystem = new StudyGroupSystem();
    	//Database database = new VectorDatabase();
    	
    	System.out.println("It compiles now!");
    	
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

	}
	
}
