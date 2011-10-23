package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;

/** A JDialog for creating a new study group. This is displayed when the user selects
 * New Study Group.
 */
public class NewGroupDialog extends JDialog implements ActionListener, PropertyChangeListener {

	/** The {@link GUIFrame} that is displaying this NewGroupDialog */
	GUIFrame parent;
	
	/** Constructs this NewGroupDialog object using the {@link GUIFrame} that will display it.
	 * 
	 * @param parent the {@link GUIFrame} that will display this NewGroupDialog.
	 */
	public NewGroupDialog(GUIFrame parent) {
		super(parent, true);
		
		//Give this Dialog a reference to its GUIFrame parent
		this.parent = parent;
		
		//Set the size, position, and title of this dialog
		setTitle("New Study Group");
		setSize(400,300);
		setLocationRelativeTo(null);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}