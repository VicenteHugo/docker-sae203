package ihm.partie;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelInformation extends JPanel 
{
	private JLabel     temps;

	/**
	 * Constructeur du PanelInformation
	 */
	public PanelInformation()
	{
		this.setBackground(Color.DARK_GRAY);
		this.setOpaque(true);

		this.temps = new JLabel("00:00 | 00:00", JLabel.CENTER);
		this.temps.setBackground(Color.WHITE);
		this.temps.setOpaque(true);
		this.temps.setFont(new Font(this.temps.getFont().getName(), this.temps.getFont().getStyle(), 20));

		this.add(this.temps);
	}	
}
