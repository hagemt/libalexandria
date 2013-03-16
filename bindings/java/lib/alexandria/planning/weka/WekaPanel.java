package lib.alexandria.planning.weka;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JPanel;

import weka.core.Instances;
import weka.gui.explorer.Explorer;
import weka.gui.explorer.Explorer.ExplorerPanel;

/**
 * 
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class WekaPanel extends JPanel implements ExplorerPanel {
	private static final long serialVersionUID = -5110298140815360991L;	
	private static final String TOOLTIP;
	static {
		TOOLTIP = "";
	}
	
	protected final String title;
	protected Explorer m_Explorer; 
	protected PropertyChangeSupport m_Support;
	
	public WekaPanel(final String name) {
		title = name;
		m_Explorer = null;
		m_Support = new PropertyChangeSupport(this);
	}

	/* (non-Javadoc)
	 * @see weka.gui.explorer.Explorer.ExplorerPanel#setExplorer(weka.gui.explorer.Explorer)
	 */
	@Override
	public void setExplorer(Explorer parent) {
		m_Explorer = parent;
	}

	/* (non-Javadoc)
	 * @see weka.gui.explorer.Explorer.ExplorerPanel#getExplorer()
	 */
	@Override
	public Explorer getExplorer() {
		return m_Explorer;
	}

	/* (non-Javadoc)
	 * @see weka.gui.explorer.Explorer.ExplorerPanel#setInstances(weka.core.Instances)
	 */
	@Override
	public void setInstances(Instances inst) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see weka.gui.explorer.Explorer.ExplorerPanel#getTabTitle()
	 */
	@Override
	public String getTabTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see weka.gui.explorer.Explorer.ExplorerPanel#getTabTitleToolTip()
	 */
	@Override
	public String getTabTitleToolTip() {
		return TOOLTIP;
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		m_Support.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		m_Support.removePropertyChangeListener(l);
	}

	public static void main(String[] args) {
		final String name = "Test";
		final javax.swing.JFrame window = new javax.swing.JFrame(name);
		window.getContentPane().add(new WekaPanel(name));
		window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		window.pack();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				window.setVisible(true);
			}
		});
	}

}
