package gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import domainlogic.User.Logged;

public class LogInDialog extends JDialog
implements ActionListener,
PropertyChangeListener {
	private String enteredUname = null;
	private String enteredPW = null;
private JTextField textField;
private JTextField textField2;

private JOptionPane optionPane;

private String btnString1 = "Login";
private String btnString2 = "Exit";
private JButton createAcct = new JButton("Create Account");

private GUIFrame gframe;

private Logged log;

public Logged getStatus() {
	return log;
}
/**
* Returns null if the typed string was invalid;
* otherwise, returns the string as the user entered it.
*/
public String getValidatedText() {
return enteredUname;
}

/** Creates the reusable dialog. */
public LogInDialog(GUIFrame gframe) {
super(gframe, true);

this.gframe = gframe;

setTitle("Login");
 this.setSize(400,200);
 setLocationRelativeTo(null);
textField = new JTextField(10);
textField2 = new JTextField(10);

//Create an array of the text and components to be displayed.
String msgString1 = "Username: ";
String msgString2 = "Password: ";
Object[] array = {msgString1, textField, msgString2, textField2};

//Create an array specifying the number of dialog buttons
//and their text.
Object[] options = {btnString1, btnString2, createAcct};

createAcct.setActionCommand("createAcct");
createAcct.addActionListener(this);

//Create the JOptionPane.
optionPane = new JOptionPane(array,
      JOptionPane.QUESTION_MESSAGE,
      JOptionPane.YES_NO_OPTION,
      null,
      options,
      options[0]);

//Make this dialog display it.
setContentPane(optionPane);

//Handle window closing correctly.
setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
addWindowListener(new WindowAdapter() {
public void windowClosing(WindowEvent we) {
/*
* Instead of directly closing the window,
* we're going to change the JOptionPane's
* value property.
*/
optionPane.setValue(new Integer(
          JOptionPane.CLOSED_OPTION));
}
});

//Ensure the text field always gets the first focus.
addComponentListener(new ComponentAdapter() {
public void componentShown(ComponentEvent ce) {
textField.requestFocusInWindow();
}
});

//Register an event handler that puts the text into the option pane.
textField.addActionListener(this);
textField2.addActionListener(this);

//Register an event handler that reacts to option pane state changes.
optionPane.addPropertyChangeListener(this);
}

/** This method handles events for the text field. */
public void actionPerformed(ActionEvent e) {
	if ("createAcct".equals(e.getActionCommand())) {
		NewAccountDialog nad = new NewAccountDialog(gframe);
		nad.setVisible(true);
	} else {
		optionPane.setValue(btnString1);
	}
}

/** This method reacts to state changes in the option pane. */
public void propertyChange(PropertyChangeEvent e) {
	
String prop = e.getPropertyName();

if (isVisible()
&& (e.getSource() == optionPane)
&& (JOptionPane.VALUE_PROPERTY.equals(prop) ||
JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
Object value = optionPane.getValue();

if (value == JOptionPane.UNINITIALIZED_VALUE) {
//ignore reset
return;
}

//Reset the JOptionPane's value.
//If you don't do this, then if the user
//presses the same button next time, no
//property change event will be fired.
optionPane.setValue(
JOptionPane.UNINITIALIZED_VALUE);

if (btnString1.equals(value)) { 
enteredUname = textField.getText();
enteredPW = textField2.getText();
Logged result = (gframe.getSGS()).login(enteredUname, enteredPW); 
if (result != Logged.INVALID) {
	//we're done; clear and dismiss the dialog
	log = result;
	clearAndHide();
} else {
//text was invalid
textField.selectAll();
JOptionPane.showMessageDialog(
      LogInDialog.this,
      "Sorry Invalid Credentials",
      "Try again",
      JOptionPane.ERROR_MESSAGE);
enteredUname = null;
textField.requestFocusInWindow();
}
} else { //user closed dialog or clicked cancel
	enteredUname = null;
clearAndHide(); 
}

}
}

/** This method clears the dialog and hides it. */
public void clearAndHide() {
textField.setText(null);
setVisible(false);
}
}