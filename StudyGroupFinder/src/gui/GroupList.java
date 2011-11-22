package gui;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import database.GroupData;

/** GroupList is a JList that can display numerous GroupData by interfacing with the SGS.
 * 
 * @author Michael Kirby
 *
 */
public class GroupList extends JList {
	
	/** The parent {@link GUIFrame} of this JList */
	private GUIFrame parent;
	
	/** An ArrayList of GroupData for this List to display as groups. */
	private ArrayList<GroupData> groups;
	
	/** The ListSelectionListener used by this GroupList */
	private ListSelectionListener ug;
	
	/** Construct a GroupList using its GUIFrame and an ArrayList of GroupData.
	 * 
	 * @param parent the GUIFrame of the program.
	 * @param groups an ArrayList of GroupData.
	 */
	public GroupList(GUIFrame parent, ArrayList<GroupData> groups) {
		this.parent = parent;
		this.groups = groups;
		//TODO
	}
	
	/** Construct a GroupList using its GUIFrame and an array of Objects that are IDs.
	 * 
	 * @param parent the GUIFrame of the program.
	 * @param groupids and array of objects of the IDs of the groups for this list.
	 */
	public GroupList(GUIFrame parent, ListSelectionListener ug, Object[] groupids) { //TODO pass this a SearchData object
		this.parent = parent;
		this.ug = ug;
		groups = new ArrayList<GroupData>();
		if (groupids.length != 0) {
			for (Object i : groupids) {
				GroupData temp = parent.getSGS().getGroup((Integer)i); //TODO This is a fix but inefficient for multi-db calls
				if (temp != null) {
					if (!temp.getMods().contains(parent.getSGS().getLoggedUser().getId()) || !temp.getUsers().contains(parent.getSGS().getLoggedUser().getId())) {
						groups.add(temp); //TODO this might be helpful once efficiency is considered
					}
				}
			}
		}
		Vector<Object> options =  new Vector<Object>();
		for (GroupData gd : groups) {
			options.add(gd.getName()+" - "+gd.getCourse());
		}
		this.setListData(options);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addListSelectionListener(ug);
	}
	
	/** Returns the {@link GroupData} object for the item currently selected in this GroupList.
	 * 
	 * @return the {@link GroupData} object for the item currently selected in this GroupList.
	 */
	public GroupData getSelectedData() {
		int temp = this.getSelectedIndex();
		if (temp >= 0) {
			return groups.get(temp);
		} else {
			return null;
		}
	}

}
