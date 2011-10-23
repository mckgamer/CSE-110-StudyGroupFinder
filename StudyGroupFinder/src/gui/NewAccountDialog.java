package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;

import domainlogic.CreateUserEvent;
import domainlogic.Status;

public class NewAccountDialog extends JDialog implements ActionListener, PropertyChangeListener {

	GUIFrame parent;
	CreateUserEvent event;
	
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
