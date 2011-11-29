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

import database.UserData;
import domainlogic.CreateUserEvent;
import domainlogic.Status;
import domainlogic.StatusType;

/** This JDialog is shown when a user chooses to create a new account.
 * 
 * @author Michael Kirby
 *
 */
public class NewAccountDialog extends JDialog implements ActionListener, PropertyChangeListener {

	/** The {@link GUIFrame} of the program. */
	GUIFrame parent;
	
	/** The {@link CreateUserEvent} associated with this Dialog. */
	CreateUserEvent event;
	
	/** The JTextField for the username. */
	JTextField unameField;
	
	/** The JTextField for the password. */
	JTextField pwField;
	
	/** The JTextField for the courses*/
	JTextField courseField;
	
	/** Constructs this NewAccountDialog using its GUIFrame parent
	 * 
	 * @param parent the GUIFrame of the program.
	 */
	public NewAccountDialog(GUIFrame parent) {
		super(parent, true);
		this.parent = parent;
		setTitle("Create New Account");
		setSize(400,225);
		setResizable(false);
		setLocationRelativeTo(null);
		
		event = new CreateUserEvent(parent.getSGS());
		
		unameField = new JTextField(25);
		pwField = new JTextField(25);
		courseField = new JTextField(25);
		
		//Create an array of the text and components to be displayed.
		JLabel msgString1 = new JLabel("Username: ");
		JLabel msgString2 = new JLabel("Password: ");
		JLabel msgString3 = new JLabel("My Courses: (seperated by comma)");
		msgString1.setForeground(parent.getTheme().headColor());
		msgString2.setForeground(parent.getTheme().headColor());
		msgString3.setForeground(parent.getTheme().headColor());
		Object[] array = {msgString1, unameField, msgString2, pwField, msgString3, courseField};

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
			event.setData(new UserData(0,unameField.getText(),pwField.getText(), "~", "~",courseField.getText()));
			event.validate();
			event.execute();
			Status result = event.getStatus();
			if (result.getStatus() == StatusType.SUCCESS) {
				System.out.println("Worked");
			}
			StatusDialog sd = new StatusDialog(result, parent);
			sd.setVisible(true);
			setVisible(false);
		} else if ("cancel".equals(e.getActionCommand())) {
			setVisible(false);
		}
	}

}
