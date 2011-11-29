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

import util.StringParser;

import database.GroupData;
import domainlogic.Status;
import domainlogic.StatusType;
import domainlogic.UpdateGroupProfileEvent;

/** This Dialog is displayed when a user selects the edit button on a {@link GroupProfile}.
 * 
 * @author Michael Kirby
 *
 */
public class UpdateGroupDialog extends JDialog implements ActionListener, PropertyChangeListener {
	
	/** The {@link GUIFrame} that is displaying this UpdateGroupDialog */
	GUIFrame parent;
	
	/** The {@link UpdateGroupProfileEvent} used by this UpdateGroupDialog */
	UpdateGroupProfileEvent event;
	
	/** The JTextField for the group name. */
	JTextField nameField;
	
	/** The JTextField for the course */
	JTextField courseField;
	
	/** Since this is an update, this is the GroupData as it was originally for prepopulating. */
	GroupData prepop;
	
	/** Constructs this UpdateGroupDialog object using the {@link GUIFrame} that will display it.
	 * 
	 * @param parent the {@link GUIFrame} that will display this UpdateGroupDialog.
	 */
	public UpdateGroupDialog(GUIFrame parent, int groupid) {
		super(parent, true);
		
		//Give this Dialog a reference to its GUIFrame parent
		this.parent = parent;
		
		//Set the size, position, and title of this dialog
		setTitle("Update Group Profile");
		setSize(400,200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		event = new UpdateGroupProfileEvent(parent.getSGS());
		
		nameField = new JTextField(25);
		courseField = new JTextField(25);

		prepop = parent.getSGS().getGroup(groupid);
		nameField.setText(prepop.getName());
		courseField.setText(prepop.getCourse());
		
		//Create an array of the text and components to be displayed.
		//String msgString1 = "Group Name: ";
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
		if ("submit".equals(e.getActionCommand())) { //TODO this needs to update the Group object for viewing
			event.setData(new GroupData(prepop.getId(),nameField.getText(),courseField.getText(), StringParser.unParseArray(prepop.getMods()), StringParser.unParseArray(prepop.getUsers())));
			event.validate();
			event.execute();
			Status result = event.getStatus();
			if (result.getStatus() == StatusType.SUCCESS) {
				System.out.println("Worked");
			}
			StatusDialog sd = new StatusDialog(result, parent);
			sd.setVisible(true);
			setVisible(false);
			parent.getGUI().refreshLeft();//TODO if this the best way for refresh right? see below
			parent.getGUI().setRight(new GroupProfile(parent,parent.getSGS().getGroup(prepop.getId())));
		} else if ("cancel".equals(e.getActionCommand())) {
			setVisible(false);
		}
	}

}
