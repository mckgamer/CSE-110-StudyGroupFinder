package domainlogic;

import database.MapDatabase;
import domainlogic.User;
import domainlogic.User.Logged;

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
	 * @return
	 */
	public Logged login(String userName, String pw){
		//call database
		
		this.sgfUser = database.login(userName, pw);
		return sgfUser.getStatus();
	}
	
	/**
	 * Checks to see if user is logged in
	 * @return
	 */
	public boolean isLogged(){
		//Return user status
		if(sgfUser == null)
			return false;
		else if(sgfUser.getStatus() == Logged.USER)
			return true;
		else
			return false;
	}
}
