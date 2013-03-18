package lib.alexandria.planning.weka;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

import lib.alexandria.planning.Objective;
import lib.alexandria.planning.Planner;
import lib.alexandria.planning.Stream;
import lib.alexandria.planning.Trainer;

import weka.gui.SysErrLog;
import weka.gui.explorer.Explorer.CapabilitiesFilterChangeEvent;
import weka.gui.explorer.Explorer.CapabilitiesFilterChangeListener;

/**
 * Constructs a tab-view for using Planner in Weka.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class PlannerPanel extends WekaPanel implements
	ActionListener,
	CapabilitiesFilterChangeListener,
	ClipboardOwner,
	Planner {
	private static final long serialVersionUID = 3409476524444377128L;
	private static final int DEFAULT_GAP = 3;

	private final WekaHarness harness;
	private final JProgressBar bar;
	private final Timer timer;

	public static class ButtonBox extends JPanel {
		private static final long serialVersionUID = 3905814695438407077L;
		private final HashMap<String, JButton> buttons;
		private final JTextField field;
		public ButtonBox(String... labels) {
			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			add(Box.createHorizontalGlue());
			field = new JTextField("Quick Feedback");
			field.setEnabled(false);
			add(field);
			buttons = new HashMap<String, JButton>();
			for (String label : labels) {
				JButton button = new JButton(label);
				buttons.put(label, button);
				add(Box.createHorizontalStrut(DEFAULT_GAP));
				add(button);
			}
		}
		public boolean bindFunction(String label, ActionListener action) {
			JButton button = buttons.get(label);
			if (label == null) {
				return false;
			}
			button.addActionListener(action);
			return true;
		}
	}

	public PlannerPanel() {
		super("Planning");
		super.setLog(new SysErrLog());
		super.setLayout(new GridLayout(1, 2, DEFAULT_GAP, DEFAULT_GAP));
		harness = new WekaHarness(this.getName());
		timer = new Timer(1000, this);

		JPanel hyper = new NeatPanel("Hyperparameters");
		hyper.setLayout(new BoxLayout(hyper, BoxLayout.PAGE_AXIS));
		JPanel k = new NeatPanel("k");
		k.setLayout(new FlowLayout(FlowLayout.LEFT, DEFAULT_GAP, DEFAULT_GAP));
		k.add(new JLabel("SVM with c = 10^k, where k = "));
		SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 4, 1);
		SpinnerNumberModel model2 = new SpinnerNumberModel(5, 5, 9, 1);
		k.add(new JSpinner(model1));
		k.add(new JSpinner(model2));
		hyper.add(new JLabel("('c' controls the SVM's behavior)"));
		hyper.add(k);
		hyper.add(bar = new JProgressBar(0));

		JPanel arbiter = new NeatPanel("Arbitration");
		arbiter.setLayout(new GridLayout(2, 3, DEFAULT_GAP, DEFAULT_GAP));
		arbiter.add(new JLabel("Data split(s):"));
		arbiter.add(new JComboBox<Integer>(new Integer[] { 1, 2, 3, 5, 10 }));
		arbiter.add(new JButton("Run..."));
		arbiter.add(new JLabel("Mechanism:"));
		arbiter.add(new JComboBox<String>(new String[] { "Naive", "SRM" }));
		arbiter.add(new JButton("About..."));

		JPanel svms = new JPanel(new BorderLayout());
		svms.add(hyper, BorderLayout.CENTER);
		svms.add(arbiter, BorderLayout.SOUTH);
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("SVMs", svms);
		add(tabs);

		JPanel results = new NeatPanel("Results");
		results.setLayout(new BorderLayout());
		final JTextArea output = new JTextArea("...");
		output.setEditable(false);
		JScrollPane pane = new JScrollPane(output);
		results.add(pane, BorderLayout.CENTER);
		ButtonBox box = new ButtonBox("Spin!", "Copy", "Clear");
		box.bindFunction("Copy", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection selection = new StringSelection(output.getText());
			    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			    clipboard.setContents(selection, PlannerPanel.this);
			}
		});
		box.bindFunction("Clear", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				output.setText("");
			}
		});
		box.bindFunction("Spin!", this);
		results.add(box, BorderLayout.SOUTH);
		add(results);
	}

	/* (non-Javadoc)
	 * @see lib.alexandria.planning.Planner#addLowerObjective(lib.alexandria.planning.Objective)
	 */
	@Override
	public void addLowerObjective(Objective j1) {
		harness.addLowerObjective(j1);
	}

	/* (non-Javadoc)
	 * @see lib.alexandria.planning.Planner#addUpperObjective(lib.alexandria.planning.Objective)
	 */
	@Override
	public void addUpperObjective(Objective j2) {
		harness.addUpperObjective(j2);
	}

	/* (non-Javadoc)
	 * @see lib.alexandria.planning.Planner#addTrainer(lib.alexandria.planning.Trainer)
	 */
	@Override
	public void addTrainer(Trainer t) {
		harness.addTrainer(t);
	}

	/* (non-Javadoc)
	 * @see lib.alexandria.planning.Planner#attach(lib.alexandria.planning.Stream)
	 */
	@Override
	public void attach(Stream s) {
		harness.attach(s);
	}

	/* (non-Javadoc)
	 * @see lib.alexandria.planning.Planner#getParallelism()
	 */
	@Override
	public synchronized int getParallelism() {
		return harness.getParallelism();
	}

	/* (non-Javadoc)
	 * @see lib.alexandria.planning.Planner#setParallelism(int)
	 */
	@Override
	public void setParallelism(int n) {
		harness.setParallelism(n);
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// TODO Auto-generated method stub, do nothing?
	}

	@Override
	public void capabilitiesFilterChanged(CapabilitiesFilterChangeEvent e) {
		// TODO handle data/load change... (and call libSVM.jar)
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		bar.setValue((bar.getValue() + 1) % 100);
		timer.restart();
	}
}
