package domainlogic;

import database.MapDatabase;
import database.UserData;
import domainlogic.User;
import domainlogic.User.Logged;


/**
 * StudyGroupSystem class is the domain logic class for the Study Group System.
 * The gui of the system calls methods within this class to carry out user
 * functions. The class iterfaces with the database. A database
 * of MapDatabase type needs to be passed into the class.
 * @author Robert Filiczkowski
 * Date Created 10/21/2011
 *
 */
public class StudyGroupSystem {
	
	//Class Variables
	private MapDatabase database = new MapDatabase();
	private User sgfUser;
	
	/**
	 * Class Constructor that takes a MapDatabase as a parameter
	 * @param mapData
	 */
	public StudyGroupSystem(MapDatabase mapData){
		this.database = mapData;
	}
	
	/**
	 * Login Method
	 * @param userName
	 * @param pw
	 * @return Logged Enumerator Type
	 */
	public Logged login(String userName, String pw){
		//call database
		if(userName == null || pw == null)
			return Logged.INVALID;
		this.sgfUser = database.login(userName, pw);
		return sgfUser.getStatus();
	}
	
	/**
	 * Checks to see if user is logged in
	 * @return Boolean True or False
	 */
	public boolean isLogged(){
		//Return user status
		if(sgfUser == null)
			return false;
		else if(sgfUser.getStatus() == Logged.USER || sgfUser.getStatus() == Logged.ADMIN)
			return true;
		else
			return false;
	}
	
	/**
	 * Creates a new user into database
	 * @param id
	 * @param userName
	 * @param pw
	 * @param mod
	 * @return A Status object 
	 */
	public Status createUser(int id, String userName, String pw, String mod){
		Status tempStatus = new Status();
		// Create user data object to send to the database
		UserData uData = new UserData(id, userName, pw, mod);
		
		//call to database to add user
		if(uData.validate())
			tempStatus = database.addUser(uData);
		
		return tempStatus;
	}
	
	/**
	 * This function updates user data if the user is logged in
	 * @param uName User Name
	 * @param pw User Password
	 * @return Status, if sucessful Status = StatusType.SUCCESSFUL, else it will return UNSUCCESSFUL
	 */
	public Status updateUserProfile(String uName, String pw){
		Status tempStatus = new Status(StatusType.UNSUCCESSFUL);
		
		//Catch empty strings
		if(uName.isEmpty() || pw.isEmpty())
			return tempStatus;
		//Update User Data
		if(isLogged()){
			// create updated userData Object
			UserData tempUD = new UserData(sgfUser.user.getId(), uName, pw, "modOf()");
			// create Status object to return to GUI
			tempStatus = database.updateUser(tempUD);
			return tempStatus;
		}
		else
			// Return program error if user is not logged in.
			return tempStatus;
	}
	
	/**
	 * Changes the status of logged in user to LOGGEDOFF
	 * @return Status object for the gui to evaluate
	 */
	public Status logoff(){
		Status tempStatus = new Status();
		if(isLogged()){
			sgfUser.setStatus(Logged.LOGGEDOFF);
			tempStatus.setStatus(StatusType.SUCCESS);
			return tempStatus;
		}
		tempStatus.setStatus(StatusType.UNSUCCESSFUL);
		return tempStatus;
	}
	
	
	// These are list of methods identified from The subsystem interaction diagrams
	// They will need to be implemented at some point. Not all methods will be included
	// in phase 1 of this project.
	
	
	
	
	void createNewGroup(){
		
	}
	
	void getGroup(){
		
	}
	
	void addUserToGroup(){
		
	}
	
	void updateGroupData(){
		
	}
	
	void removeUserFromGroup(){
		
	}
	
	void deleteGroup(){
		
	}
	
}
