package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;

import domainlogic.CreateUserEvent;
import domainlogic.Status;

/** This JDialog is shown when a user chooses to create a new account.
 * 
 * @author Michael Kirby
 *
 */
public class NewAccountDialog extends JDialog implements ActionListener, PropertyChangeListener {

	/** The {@link GUIFrame} of the program. */
	GUIFrame parent;
	
	/** The {@link CreateUserEvent} associated with this Dialog */
	CreateUserEvent event;
	
	/** Constructs this NewAccountDialog using its GUIFrame parent
	 * 
	 * @param parent the GUIFrame of the program.
	 */
	public NewAccountDialog(GUIFrame parent) {
		super(parent, true);
		this.parent = parent;
		setTitle("Create New Account");
		setSize(400,300);
		event = new CreateUserEvent(parent.getSGS());
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		event.setData(null);
		event.validate();
		event.execute();
		Status result = event.getStatus();
		StatusDialog sd = new StatusDialog(result, parent);
		setVisible(false);
	}

}
