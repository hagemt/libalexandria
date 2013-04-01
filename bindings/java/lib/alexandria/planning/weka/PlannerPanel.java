package lib.alexandria.planning.weka;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import java.util.Date;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import lib.alexandria.planning.MATLABHarness;
import lib.alexandria.planning.Objective;
import lib.alexandria.planning.Planner;
import lib.alexandria.planning.Stream;
import lib.alexandria.planning.Trainer;

import weka.classifiers.Classifier;
import weka.experiment.ClassifierSplitEvaluator;
import weka.experiment.CrossValidationResultProducer;
import weka.experiment.Experiment;
import weka.experiment.PropertyNode;
import weka.experiment.SplitEvaluator;
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
	ChangeListener,
	ClipboardOwner,
	Planner {
	private static final long serialVersionUID = 3409476524444377128L;
	private static final int DEFAULT_GAP = 3;

	private MATLABHarness m_harness;
	private final WekaHarness harness;
	private final JProgressBar bar;
	private final Timer timer;
	private final JTextArea output;
	final DefaultListModel<String> m_model;
	final JTextField m_input;

	public static class ButtonBox extends JPanel {
		private static final long serialVersionUID = 3905814695438407077L;
		private final HashMap<String, JButton> buttons;
		public ButtonBox(String... labels) {
			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			add(Box.createHorizontalGlue());
			JLabel status = new JLabel("Status: Ready");
			status.setEnabled(false);
			add(status);
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

		// Results pane sits on the right
		JPanel results = new NeatPanel("Results");
		results.setLayout(new BorderLayout());
		output = new JTextArea("...");
		output.setEditable(false);
		JScrollPane pane = new JScrollPane(output);
		results.add(pane, BorderLayout.CENTER);
		ButtonBox box = new ButtonBox("Copy", "Clear", "Save");
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
		box.bindFunction("Save", this);
		results.add(box, BorderLayout.SOUTH);
		
		JPanel simple = new NeatPanel("c");
		simple.setLayout(new FlowLayout(FlowLayout.LEFT, DEFAULT_GAP, DEFAULT_GAP));
		simple.add(new JLabel("SVM with c = 10^k, where k = "));
		final SpinnerNumberModel model1 = new SpinnerNumberModel(0, 0, 4, 1);
		final SpinnerNumberModel model2 = new SpinnerNumberModel(5, 5, 9, 1);
		simple.add(new JSpinner(model1));
		simple.add(new JSpinner(model2));

		JPanel next = new NeatPanel("Kernel");
		next.setLayout(new FlowLayout(FlowLayout.CENTER, DEFAULT_GAP, DEFAULT_GAP));
		JRadioButton kernel_none = new JRadioButton("None", true);
		JRadioButton kernel_some = new JRadioButton("", false);
		JPanel kernel_opts = new JPanel();
		kernel_opts.setLayout(new BoxLayout(kernel_opts, BoxLayout.PAGE_AXIS));
		JCheckBox kernel_lin = new JCheckBox("Linear", false);
		JCheckBox kernel_ply = new JCheckBox("Polynomial", false);
		JCheckBox kernel_rbf = new JCheckBox("RBF", false);
		JCheckBox kernel_sig = new JCheckBox("Sigmoid", false);
		ButtonGroup kernel_buttons = new ButtonGroup();
		kernel_buttons.add(kernel_none);
		kernel_buttons.add(kernel_some);
		kernel_opts.add(kernel_lin);
		kernel_opts.add(kernel_ply);
		kernel_opts.add(kernel_rbf);
		kernel_opts.add(kernel_sig);
		next.add(kernel_none);
		next.add(kernel_some);
		next.add(kernel_opts);
		next.setEnabled(false);
		for (Component c : next.getComponents()) {
			c.setEnabled(false);
		}
		for (Component c : kernel_opts.getComponents()) {
			c.setEnabled(false);
		}
		
		// SVM Hyperparameters
		JPanel hyper = new NeatPanel("Hyperparameters");
		hyper.setLayout(new BoxLayout(hyper, BoxLayout.PAGE_AXIS));		
		hyper.add(new JLabel("('c' controls the SVM's behavior)"));
		hyper.add(simple);
		hyper.add(next);
		hyper.add(bar = new JProgressBar(0));

		JPanel arbiter = new NeatPanel("Arbitration");
		arbiter.setLayout(new GridLayout(3, 3, DEFAULT_GAP, DEFAULT_GAP));
		arbiter.add(new JLabel("Data split(s):"));
		arbiter.add(new JComboBox<Integer>(new Integer[] { 1, 2, 3, 5, 10 }));
		arbiter.add(new JLabel("-fold, using CV"));
		arbiter.add(new JLabel("Iterations:"));
		arbiter.add(new JComboBox<Integer>(new Integer[] { 1, 2, 3, 5, 10 }));
		JButton run_button = new JButton("Run...");
		run_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlannerPanel.this.LibSVM(1, 1, model1.getNumber(), model2.getNumber());
			}
		});
		arbiter.add(run_button);
		arbiter.add(new JLabel("Mechanism:"));
		arbiter.add(new JComboBox<String>(new String[] { "Naive", "SRM" }));
		JButton about_button = new JButton("About...");
		about_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final String message = "This will give information to the user about their options.";
				JOptionPane.showMessageDialog(PlannerPanel.this, message,
						"About SVMs", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		arbiter.add(about_button);
		for (Component c : arbiter.getComponents()) {
			if (c instanceof JComboBox) {
				c.setEnabled(false);
			}
		}

		JPanel svms = new JPanel(new BorderLayout());
		svms.add(hyper, BorderLayout.CENTER);
		svms.add(arbiter, BorderLayout.SOUTH);

		JPanel m_history = new NeatPanel("Commands");
		m_history.setLayout(new BorderLayout(DEFAULT_GAP, DEFAULT_GAP));
		m_model = new DefaultListModel<String>();
		final JList<String> m_list = new JList<String>(m_model);
		JScrollPane m_commands = new JScrollPane(m_list);
		m_history.add(m_commands, BorderLayout.CENTER);

		JPanel m_prompt = new JPanel();
		m_prompt.setLayout(new BoxLayout(m_prompt, BoxLayout.LINE_AXIS));
		m_prompt.add(new JLabel(">> "));
		m_input = new JTextField("# Enter MATLAB commands here");
		m_input.setEnabled(false);
		m_input.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String text = m_input.getText();
					m_model.addElement(text);
					m_input.setText("");
					e.consume();
					PlannerPanel.this.MATLAB(text);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) { }
		});
		m_input.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				m_input.selectAll();
			}
			@Override
			public void focusLost(FocusEvent e) { }
		});
		m_prompt.add(m_input);

		JPanel m_panel = new JPanel(new BorderLayout());
		m_panel.add(m_history, BorderLayout.CENTER);
		m_panel.add(m_prompt, BorderLayout.SOUTH);

		JTabbedPane tabs = new JTabbedPane();
		tabs.addChangeListener(this);
		tabs.addTab("SVMs", svms);
		tabs.addTab("MATLAB", m_panel);
		add(tabs);
		add(results);
	}

	protected void LibSVM(int splits, int iterations, Number low, Number high) {
		/*
		Experiment exp = new Experiment();
		exp.setPropertyArray(new Classifier[0]);
		exp.setUsePropertyIterator(true);
		SplitEvaluator se = new ClassifierSplitEvaluator();
		CrossValidationResultProducer cvrp = new CrossValidationResultProducer();
		cvrp.setNumFolds(splits);
		cvrp.setSplitEvaluator(se);
		Classifier sec = ((ClassifierSplitEvaluator) se).getClassifier();
		// Is this really necessary?
		PropertyNode[] propertyPath = new PropertyNode[2];
		try {
			PropertyDescriptor sepd = new PropertyDescriptor("splitEvaluator", cvrp.getClass());
			propertyPath[0] = new PropertyNode(se, sepd, cvrp.getClass());
			PropertyDescriptor secpd = new PropertyDescriptor("classifier", se.getClass());
			propertyPath[1] = new PropertyNode(sec, secpd, se.getClass());
		}
		catch (IntrospectionException e) {
			e.printStackTrace();
		}
		exp.setResultProducer(cvrp);
		exp.setPropertyPath(propertyPath);
	    exp.setRunLower(1);
	    exp.setRunUpper(iterations);
	    // Run experiments...
	    try {
			exp.initialize();
		    exp.runExperiment();
		    exp.postProcess();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    */
	    output.setText("Running experiment(s)...");
	}

	protected void MATLAB(String text) {
		if (m_harness == null || !m_harness.isConnected()) {
			output.setText("ERROR: problem communicating with MATLAB");
			return;
		}
		output.setText(m_harness.result(text));
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
		System.out.println(clipboard + ":\n" + contents);
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

	@Override
	public void stateChanged(ChangeEvent event) {
		output.setText("");
		Object src = event.getSource();
		if (src instanceof JTabbedPane) {
			JTabbedPane pane = (JTabbedPane) src;
			if (pane.getTitleAt(pane.getSelectedIndex()).equals("MATLAB")) {
				try {
					m_harness = new MATLABHarness(this.getTabTitle());
					m_model.addElement("### MATLAB: " + new Date().toString());
					m_input.setEnabled(true);
				} catch (Exception e) {
					output.setText(e.toString());
				}
			} else if (m_harness != null && m_harness.isConnected()) {
				try {
					m_input.setEnabled(false);
					m_harness.close();
					m_model.addElement("### CLOSED: " + new Date().toString());
				} catch (Exception e) {
					output.setText(e.toString());
				}
			}
		}
	}
}
