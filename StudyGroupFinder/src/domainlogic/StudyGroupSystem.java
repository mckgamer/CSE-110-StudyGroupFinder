package domainlogic;

import database.GroupData;
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
	 * @param UserData Object
	 * @return A Status object 
	 */
	public Status createUser(UserData u){
		Status tempStatus = new Status();
		// Create user data object to send to the database
		UserData uData = u;
		
		//call to database to add user
		if(uData.validate())
			tempStatus = database.addUser(uData);
		
		return tempStatus;
	}
	
	/**
	 * This function updates user data if the user is logged in
	 * @param UserObject
	 * @return Status, if sucessful Status = StatusType.SUCCESSFUL, else it will return UNSUCCESSFUL
	 */
	public Status updateUserProfile(UserData u){
		
		Status tempStatus = new Status(StatusType.UNSUCCESSFUL);
		if(isLogged()){
			tempStatus = database.updateUser(u);
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
/*	public Status logoff(){
		Status tempStatus = new Status();
		if(isLogged()){
			sgfUser.setStatus(Logged.LOGGEDOFF);
			tempStatus.setStatus(StatusType.SUCCESS);
			return tempStatus;
		}
		tempStatus.setStatus(StatusType.UNSUCCESSFUL);
		return tempStatus;
	/*
	/** Logs the user out of the program by calling the User logoff method. */
	public void logoff(){
		if (sgfUser != null) {
			sgfUser.logoff();
		}
	  
	}
	
	
	// These are list of methods identified from The subsystem interaction diagrams
	// They will need to be implemented at some point. Not all methods will be included
	// in phase 1 of this project.
	
	/**
	 * Gets User Data For logged In User
	 * @return UserData Object
	 */
	public UserData getLoggedUser(){
		return sgfUser.getUserData();
	}
	
	
	/**
	 * Create a new group with data passed by Gui
	 * @param gd
	 * @return Status Objects
	 */
	public Status createNewGroup(GroupData gd){
		Status tempStatus = new Status();
		tempStatus = database.addGroup(gd);
		return tempStatus;
		
	}
	/**
	 * Gets Group Data from ID
	 * @param id
	 * @return GroupData Object
	 */
	public GroupData getGroup(int id){
		GroupData tempData = database.getGroup(id);
		return tempData;
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
