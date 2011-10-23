package gui;

import java.util.ArrayList;

import javax.swing.JList;

import database.UserData;

public class UserList extends JList {

	/** The parent {@link GUIFrame} of this JList */
	private GUIFrame parent;
	
	/** An ArrayList of UserData for this List to display as users. */
	private ArrayList<UserData> users;
	
	/** An ArrayList of UserData for this List to display as Moderators. */
	private ArrayList<UserData> mods;
	
	/** Construct a UserList using its GUIFrame and an ArrayList of UserData.
	 * 
	 * @param parent the GUIFrame of the program.
	 * @param users an ArrayList of UserData.
	 */
	public UserList(GUIFrame parent, ArrayList<UserData> users) {
		this.parent = parent;
		this.users = users;
	}
	
	/** Construct a UserList using its GUIFrame and an array of Objects that are ids.
	 * 
	 * @param parent the GUIFrame of the program.
	 * @param userids and array of objects of the ids of the users for this list.
	 */
	public UserList(GUIFrame parent, Object[] userids) {
		this.parent = parent;
		users = new ArrayList<UserData>();
		for (Object i : userids) {
			users.add(parent.getUser((Integer)i));
		}
	}
	
}
