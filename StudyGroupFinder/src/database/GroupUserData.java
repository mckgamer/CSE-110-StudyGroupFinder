package database;

import java.util.ArrayList;

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
	
	public GroupUserData(UserData uid, GroupData gid) {
		this.user = user;
		this.group = group;
	}
	
	@Override
	public boolean validate() {
		return true; //TODO check for valid
	}

	@Override
	public int getId() {
		return user.getId();
	}
	
	public int getGroupId() {
		return group.getId();
	}

}
