package gui;

import javax.swing.JDialog;
import javax.swing.JLabel;

import domainlogic.Status;
import domainlogic.StatusType;

public class StatusDialog extends JDialog {

	private Status status;
	private GUIFrame parent;
	
	public StatusDialog(Status status, GUIFrame parent) {
		super(parent,true);
		this.parent = parent;
		this.status = status;
		setTitle("Result");
		setSize(200,100);
		setLocationRelativeTo(null);
		
		if (status.getStatus() == StatusType.SUCCESS) {
			add(new JLabel("Success! " + status.getMessage()));
		} else if (status.getStatus() == StatusType.INVALID){
			add(new JLabel("Invalid data entered! " + status.getMessage()));
		} else {
			add(new JLabel("Failure! " + status.getMessage()));
		}
	}
}
