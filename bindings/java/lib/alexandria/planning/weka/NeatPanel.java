package lib.alexandria.planning.weka;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class NeatPanel extends JPanel {
	private static final long serialVersionUID = 7056669861471822224L;
	private static final Border DEFAULT_BORDER;
	static {
		DEFAULT_BORDER = BorderFactory.createLoweredBevelBorder();
	}

	public NeatPanel(final String title) {
		setBorder(BorderFactory.createTitledBorder(DEFAULT_BORDER, title));
		setName(title);
	}
}