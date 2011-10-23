package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import database.UserData;
import domainlogic.CreateUserEvent;
import domainlogic.Status;
import domainlogic.StatusType;
import domainlogic.UpdateUserProfileEvent;

/** A JDialog for updating the users profile. This is displayed when the user selects
 * My Profile.
 */
public class UpdateUserDialog extends JDialog implements ActionListener, PropertyChangeListener {

	/** The {@link GUIFrame} that is displaying this UpdateUserDialog */
	GUIFrame parent;
	
	/** The {@link UpdateUserProfileEvent} used by this UpdateUserDialog */
	UpdateUserProfileEvent event;
	
	/** Constructs this UpdateUserDialog object using the {@link GUIFrame} that will display it.
	 * 
	 * @param parent the {@link GUIFrame} that will display this UpdateUserDialog.
	 */
	public UpdateUserDialog(GUIFrame parent) {
		super(parent, true);
		
		//Give this Dialog a reference to its GUIFrame parent
		this.parent = parent;
		
		//Set the size, position, and title of this dialog
		setTitle("Update My Profile");
		setSize(400,300);
		setLocationRelativeTo(null);
		
		event = new UpdateUserProfileEvent(parent.getSGS());
		
		JButton temp = new JButton("Push");
		temp.addActionListener(this);
		add(temp);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		UserData temp = parent.getSGS().getLoggedUser();
		event.setData(new UserData(temp.getId(),"Doug",temp.getPW(), "1~2~"));
		event.validate();
		event.execute();
		Status result = event.getStatus();
		if (result.getStatus() == StatusType.SUCCESS) {
			System.out.println("Worked");
		}
		StatusDialog sd = new StatusDialog(result);
	}

}
