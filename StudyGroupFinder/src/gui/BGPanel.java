package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class BGPanel extends JPanel {
	
	private GUITheme theme;
	
	public BGPanel(GUITheme theme) {
		this.theme = theme;
		setPreferredSize(new Dimension(400, 400));
		setMinimumSize(new Dimension(400, 400));
		setMaximumSize(new Dimension(400, 400));
	}
	
	public void paintComponent(Graphics g) {
	    g.drawImage(theme.dialogBG(), 0, 0, null);
	  }

}
