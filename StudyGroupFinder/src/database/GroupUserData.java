package database;

/**
 * This {@link Data} implementation is used in any case where a relationship between a User
 * and a group is required.
 * 
 * @author Michael Kirby
 */
public class GroupUserData implements Data {

	/** The UserData of the user. */
	private int user;

	/** The GroupData of the Group. */
	private int group;
	
	/** Construct this GroupUserData using a UserData and GroupData object.
	 * 
	 * @param uid the id of the user.
	 * @param gid the id of the group.
	 */
	public GroupUserData(int uid, int gid) {
		this.user = uid;
		this.group = gid;
	}
	
	@Override
	public boolean validate() {
		return true; //TODO check for valid
	}

	@Override
	public int getId() {
		return user;
	}
	
	/** Returns the id of the group of this Data.
	 * 
	 * @return the id of the group of this Data.
	 */
	public int getGroupId() {
		return group;
	}

}
