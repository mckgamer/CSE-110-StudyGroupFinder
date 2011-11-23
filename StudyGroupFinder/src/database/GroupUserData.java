package database;

/**
 * This {@link Data} implementation is used in any case where a relationship between a User
 * and a group is required.
 * 
 * @author Michael Kirby
 */
public class GroupUserData implements Data {

	/** The UserData of the user. */
	private UserData user;

	/** The GroupData of the Group. */
	private GroupData group;
	
	/** Construct this GroupUserData using a UserData and GroupData object.
	 * 
	 * @param ud the UserData object of the user.
	 * @param gd the GroupData object of the group.
	 */
	public GroupUserData(UserData ud, GroupData gd) {
		this.user = ud;
		this.group = gd;
	}
	
	@Override
	public boolean validate() {
		return true; //TODO check for valid
	}

	@Override
	public int getId() {
		return user.getId();
	}
	
	/** Returns the id of the group of this Data.
	 * 
	 * @return the id of the group of this Data.
	 */
	public int getGroupId() {
		return group.getId();
	}

}
