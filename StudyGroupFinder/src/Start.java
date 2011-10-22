import database.Database;
import database.VectorDatabase;
import domainlogic.StudyGroupSystem;
import gui.GUIFrame;
import database.MapDatabase;


public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		GUIFrame gui = new GUIFrame();
		MapDatabase mapData = new MapDatabase();
    	StudyGroupSystem sGSystem = new StudyGroupSystem(mapData);
    	//Database database = new VectorDatabase();
    	
    	System.out.println("It compiles now!");

	}

}
