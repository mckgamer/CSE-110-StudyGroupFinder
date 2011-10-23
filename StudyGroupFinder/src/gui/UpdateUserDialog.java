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

/** A JDialog for updating the users profile. This is displayed when the user selects
 * My Profile.
 */
public class UpdateUserDialog extends JDialog implements ActionListener, PropertyChangeListener {

	/** The {@link GUIFrame} that is displaying this UpdateUserDialog */
	GUIFrame parent;
	
	/** The {@link CreateUserEvent} used by this UpdateUserDialog */
	CreateUserEvent event;
	
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
		
		event = new CreateUserEvent(parent.getSGS());
		
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
		//parent.getSGS().createUser(new UserData(1,"Tom","tom","1~"));
		parent.getSGS().updateUserProfile(new UserData(1,"Roberto", "heybob", "modOf()"));
		
		/*
		event.setData(parent.getSGS().getLoggedUser());
		System.out.println(parent.getSGS().getLoggedUser().getUName());
		System.out.println(event.getStatus().getStatus());
		event.validate();
		System.out.println(event.getStatus().getStatus());
		event.execute();
		System.out.println(event.getStatus().getStatus());*/
		Status result = event.getStatus();
		if (result.getStatus() == StatusType.SUCCESS) {
			System.out.println("Worked");
		}
		StatusDialog sd = new StatusDialog(result);
	}

}
