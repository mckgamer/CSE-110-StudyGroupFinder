package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.GroupData;
import database.UserData;
import domainlogic.CreateStudyGroupEvent;
import domainlogic.Status;
import domainlogic.StatusType;
import domainlogic.UpdateUserProfileEvent;

/** A JDialog for creating a new study group. This is displayed when the user selects
 * New Study Group.
 */
public class NewGroupDialog extends JDialog implements ActionListener, PropertyChangeListener {

	/** The {@link GUIFrame} that is displaying this NewGroupDialog */
	GUIFrame parent;
	
	/** The {@link CreateStudyGroupEvent} used by this NewGroupDialog */
	CreateStudyGroupEvent event;
	
	JTextField nameField;
	JTextField courseField;
	
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
		
		event = new CreateStudyGroupEvent(parent.getSGS());
		
		nameField = new JTextField(10);
		courseField = new JTextField(10);
		
		//Create an array of the text and components to be displayed.
		String msgString1 = "Group Name: ";
		String msgString2 = "Course Studied: ";
		Object[] array = {msgString1, nameField, msgString2, courseField};

		//Create an array specifying the number of dialog buttons
		//and their text.
		JButton submit = new JButton("Submit");
		submit.setActionCommand("submit");
		JButton cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		submit.addActionListener(this);
		cancel.addActionListener(this);
		Object[] options = {submit, cancel};

		//Create the JOptionPane.
		JOptionPane optionPane = new JOptionPane(array,
		      JOptionPane.QUESTION_MESSAGE,
		      JOptionPane.YES_NO_OPTION,
		      null,
		      options,
		      options[0]);
		
		//Make this dialog display it.
		setContentPane(optionPane);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("submit".equals(e.getActionCommand())) { //TODO this needs to update the User object for the session
			int currUser = parent.getSGS().getLoggedUser().getId();
			event.setData(new GroupData(0,nameField.getText(),courseField.getText(), currUser+"~", "~"));
			event.validate();
			event.execute();
			Status result = event.getStatus();
			if (result.getStatus() == StatusType.SUCCESS) {
				System.out.println("Worked");
			}
			StatusDialog sd = new StatusDialog(result, parent);
			sd.setVisible(true);
			setVisible(false);
			//System.out.println("Added " + parent.getSGS().getGroup(3).getName()); //TODO only temp test for 1st added
		} else if ("cancel".equals(e.getActionCommand())) {
			setVisible(false);
		}
	}

}