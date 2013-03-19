package lib.alexandria.planning.weka;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JPanel;

import weka.core.Instances;
import weka.gui.Logger;
import weka.gui.explorer.Explorer;
import weka.gui.explorer.Explorer.ExplorerPanel;
import weka.gui.explorer.Explorer.LogHandler;

/**
 * A Weka explorer panel with logging capabilities.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class WekaPanel extends JPanel implements ExplorerPanel, LogHandler {
	private static final long serialVersionUID = -5110298140815360991L;	
	private static final String TOOLTIP;
	static {
		TOOLTIP = "libalexandria";
	}
	
	private final String title;
	private final PropertyChangeSupport m_Support;
	private Explorer m_Explorer;
	private Logger m_Logger;
	
	public WekaPanel(final String name) {
		setName(title = name);
		m_Support = new PropertyChangeSupport(this);
		m_Explorer = null;
		m_Logger = null;
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

	@Override
	public void setLog(Logger newLog) {
		m_Logger = newLog;
	}
	
	protected void log(String s) {
		m_Logger.logMessage(s);
	}
	
	protected void status(String s) {
		m_Logger.statusMessage(s);
	}
}
