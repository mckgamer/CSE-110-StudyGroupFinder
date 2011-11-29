package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		setResizable(false);
		setLocationRelativeTo(null);
		String colo = parent.getTheme().headColor().getRed() + "," + parent.getTheme().headColor().getGreen() + "," + parent.getTheme().headColor().getBlue();
		String colo2 = parent.getTheme().textColor().getRed() + "," + parent.getTheme().textColor().getGreen() + "," + parent.getTheme().textColor().getBlue();
		String message = "<html><body style='color:rgb(" + colo2 + ");'><h2 style='color:rgb(" + colo + ");'>Study Group Finder</h2><b>Version 0.95</b><br/><br/>Developed by:<ul><li>Michael Kirby</li><li>Bob Filiczkowski</li><li>Mike Claffey</li><li>Robert Jang</li></ul><p width='220px'>Developed for UCSD's CSE 110 class of Fall 2011 taught by Professor William Howden.</p><br/>&copy; 2011 - All Rights Reserved</body></html>";

	    UIManager.put("OptionPane.background", new Color(0,0,0,0));
	    UIManager.put("Panel.background", new Color(0,0,0,0));
		
		//Create the JOptionPane.
		JButton ok = new JButton("Ok");
		ok.addActionListener(this);
		Object options[] = {ok};
		JOptionPane optionPane = new JOptionPane(message,
		      JOptionPane.INFORMATION_MESSAGE,
		      JOptionPane.DEFAULT_OPTION, parent.getTheme().getIcon(), options);
		
		//Make this dialog display it.
		//optionPane.setOpaque(false);
		BGPanel panel = new BGPanel(parent.getTheme());
		
		panel.add(optionPane);
		
		setContentPane(panel);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}
}
