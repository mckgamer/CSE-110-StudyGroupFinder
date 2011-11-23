package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/** This JDialog displays the credits of Study Group Finder.
 * 
 * @author Michael Kirby
 */
public class AboutSGFDialog extends JDialog implements ActionListener {
	
	/** The {@link GUIFrame} of the program. */
	private GUIFrame parent;
	
	/** Create this AboutSGFDialog using the {@link GUIFrame}.
	 * 
	 * @param parent the {@link GUIFrame} of the program.
	 */
	public AboutSGFDialog(GUIFrame parent) {
		super(parent,true);
		this.parent = parent;
		setTitle("About Study Group Finder");
		setSize(400,350);
		setLocationRelativeTo(null);
		String message = "<html><h2>Study Group Finder</h2><b>Version 0.7</b><br/><br/>Developed by:<ul><li>Michael Kirby</li><li>Bob Filiczkowski</li><li>Mike Claffey</li><li>Robert Jang</li></ul><p>Developed for UCSD's CSE 110 class of Fall 2011 taught by Professor William Howden.</p><br/>&copy; 2011 - All Rights Reserved</html>";
		
		
		//Create the JOptionPane.
		JButton ok = new JButton("Ok");
		ok.addActionListener(this);
		Object options[] = {ok};
		JOptionPane optionPane = new JOptionPane(message,
		      JOptionPane.INFORMATION_MESSAGE,
		      JOptionPane.DEFAULT_OPTION, null, options);
		
		//Make this dialog display it.
		setContentPane(optionPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}
}
