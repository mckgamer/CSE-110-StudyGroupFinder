package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;

public class NewAccountDialog extends JDialog implements ActionListener, PropertyChangeListener {

	GUIFrame parent;
	
	public NewAccountDialog(GUIFrame parent) {
		super(parent, true);
		this.parent = parent;
		setTitle("Create New Account");
		setSize(400,300);
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
