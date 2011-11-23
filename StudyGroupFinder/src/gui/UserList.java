package gui;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import database.UserData;

/** UserList is a JList that can display numerous UserData by interfacing with the SGS.
 * 
 * @author Michael Kirby
 *
 */
public class UserList extends JList {

	/** The parent {@link GUIFrame} of this JList */
	private GUIFrame parent;
	
	/** An ArrayList of UserData for this List to display as users. */
	private ArrayList<UserData> users;
	
	/** An ArrayList of UserData for this List to display as Moderators. */
	private ArrayList<UserData> mods;
	
	/** The ListSelectionListener used by this GroupList */
	private ListSelectionListener ug;
	
	/** Construct a UserList using its GUIFrame and an ArrayList of UserData.
	 * 
	 * @param parent the GUIFrame of the program.
	 * @param users an ArrayList of UserData.
	 */
	public UserList(GUIFrame parent, ArrayList<UserData> users) {
		this.parent = parent;
		this.users = users;
		//TODO
	}
	
	/** Construct a UserList using its GUIFrame and an array of Objects that are ids.
	 * 
	 * @param parent the GUIFrame of the program.
	 * @param userids and array of objects of the ids of the users for this list.
	 */
	public UserList(GUIFrame parent, ListSelectionListener ug, Object[] userids) {
		this.parent = parent;
		this.ug = ug;
		users = new ArrayList<UserData>();
		if (userids.length != 0) {
			for (Object i : userids) {
				users.add(parent.getSGS().getUser((Integer)i));
			}
		}
		Vector<Object> options =  new Vector<Object>();
		for (UserData ud : users) {
			options.add(ud.getUName());
		}
		this.setListData(options);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addListSelectionListener(ug);
	}
	
	/** Returns the {@link UserData} object for the item currently selected in this UserList.
	 * 
	 * @return the {@link UserData} object for the item currently selected in this UserList.
	 */
	public UserData getSelectedData() {
		int temp = this.getSelectedIndex();
		if (temp >= 0) {
			return users.get(temp);
		} else {
			return null;
		}
	}
	
}
