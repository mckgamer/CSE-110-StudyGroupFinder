package domainlogic;

import database.MapDatabase;
import database.UserData;
import domainlogic.User;
import domainlogic.User.Logged;

public class StudyGroupSystem {
	
	//Class Variables
	private MapDatabase database = new MapDatabase();
	private User sgfUser;
	
	/**Class Constructor that takes a MapDatabase as a parameter
	 * 
	 * @param mapData
	 */
	public StudyGroupSystem(MapDatabase mapData){
		this.database = mapData;
	}
	
	/**Login Method
	 * 
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
	
	/**Checks to see if user is logged in
	 * 
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
	
	/**Creates a new user into database
	 * 
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
}
