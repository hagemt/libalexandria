package lib.alexandria.planning.weka;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class NeatPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 7056669861471822224L;
	private static final Border DEFAULT_BORDER;
	static {
		DEFAULT_BORDER = BorderFactory.createLoweredBevelBorder();
	}

	public NeatPanel(final String title) {
		setBorder(BorderFactory.createTitledBorder(DEFAULT_BORDER, title));
		setup();
	}

	public abstract String getPosition();
	public abstract void setup();
}