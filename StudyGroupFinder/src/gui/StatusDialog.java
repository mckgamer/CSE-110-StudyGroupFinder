package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import domainlogic.Status;
import domainlogic.StatusType;

/** This JDialog takes on a Different purpose/look based on the {@link Status} object associated
 * with it.
 * @author Michael Kirby
 *
 */
public class StatusDialog extends JDialog implements ActionListener {

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
		setSize(350,150);
		setResizable(false);
		setLocationRelativeTo(null);
		String message;
		
		if (status.getStatus() == StatusType.SUCCESS) {
			message = "Success! " + status.getMessage();
		} else if (status.getStatus() == StatusType.INVALID){
			message = "Invalid data entered! " + status.getMessage();
		} else {
			message = "Failure! " + status.getMessage();
		}
		
		UIManager.put("OptionPane.background", new Color(0,0,0,0));
	    UIManager.put("Panel.background", new Color(0,0,0,0));
	    
		//Create the JOptionPane.
		JButton ok = new JButton("Ok");
		ok.addActionListener(this);
		Object options[] = {ok};
		JLabel stat = new JLabel(message);
		stat.setForeground(parent.getTheme().textColor());
		JOptionPane optionPane = new JOptionPane(stat,
		      JOptionPane.INFORMATION_MESSAGE,
		      JOptionPane.DEFAULT_OPTION, null, options);
		
		//Make this dialog display it.
		BGPanel panel = new BGPanel(parent.getTheme());
		panel.add(optionPane);
		setContentPane(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}
}
