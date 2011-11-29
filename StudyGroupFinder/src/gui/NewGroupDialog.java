package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import database.GroupData;
import domainlogic.CreateStudyGroupEvent;
import domainlogic.Status;
import domainlogic.StatusType;

/** A JDialog for creating a new study group. This is displayed when the user selects
 * New Study Group.
 */
public class NewGroupDialog extends JDialog implements ActionListener, PropertyChangeListener {

	/** The {@link GUIFrame} that is displaying this NewGroupDialog */
	GUIFrame parent;
	
	/** The {@link CreateStudyGroupEvent} used by this NewGroupDialog */
	CreateStudyGroupEvent event;
	
	/** The JTextField for the groups name. */
	JTextField nameField;
	
	/** The JTextField for the course the group studies. */
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
		setSize(400,200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		event = new CreateStudyGroupEvent(parent.getSGS());
		
		nameField = new JTextField(25);
		courseField = new JTextField(25);
		
		//Create an array of the text and components to be displayed.
		JLabel msgString1 = new JLabel("Group Name: ");
		JLabel msgString2 = new JLabel("Course: ");
		msgString1.setForeground(parent.getTheme().headColor());
		msgString2.setForeground(parent.getTheme().headColor());
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

		UIManager.put("OptionPane.background", new Color(0,0,0,0));
	    UIManager.put("Panel.background", new Color(0,0,0,0));
	    
		//Create the JOptionPane.
		JOptionPane optionPane = new JOptionPane(array,
		      JOptionPane.QUESTION_MESSAGE,
		      JOptionPane.YES_NO_OPTION,
		      parent.getTheme().getIcon(),
		      options,
		      options[0]);
		
		//Make this dialog display it.
		BGPanel panel = new BGPanel(parent.getTheme());
		panel.add(optionPane);
		setContentPane(panel);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("submit".equals(e.getActionCommand())) { //TODO this needs to update the User object for the session
			//TODO: is this better than doing it with SGS? int currUser = parent.getSGS().getLoggedUser().getId();
			event.setData(new GroupData(0,nameField.getText(),courseField.getText(), "~", "~"));
			event.validate();
			event.execute();
			Status result = event.getStatus();
			//TODO: parent.getSGS().refreshLoggedUser();
			if (result.getStatus() == StatusType.SUCCESS) {
				System.out.println("Worked");
			}
			StatusDialog sd = new StatusDialog(result, parent);
			sd.setVisible(true);
			setVisible(false);
			parent.getSGS().refreshLoggedUser();
			parent.getGUI().refreshLeft();
		} else if ("cancel".equals(e.getActionCommand())) {
			setVisible(false);
		}
	}

}