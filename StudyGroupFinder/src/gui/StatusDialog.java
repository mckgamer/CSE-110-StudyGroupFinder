package gui;

import javax.swing.JDialog;
import javax.swing.JLabel;

import domainlogic.Status;
import domainlogic.StatusType;

/** This JDialog takes on a Different purpose/look based on the {@link Status} object associated
 * with it.
 * @author Michael Kirby
 *
 */
public class StatusDialog extends JDialog {

	/** The {@link Status} object associated with this StatusDialog. */
	private Status status;
	
	/** The {@link GUIFrame} of the program. */
	private GUIFrame parent;
	
	/** Create this StatusDialog using a {@link Status} and the {@link GUIFrame}.
	 * 
	 * @param status the {@link Status} to use with this dialog.
	 * @param parent the {@link GUIFrame} of the program.
	 */
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
