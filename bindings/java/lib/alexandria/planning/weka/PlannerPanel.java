package lib.alexandria.planning.weka;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

import lib.alexandria.planning.Objective;
import lib.alexandria.planning.Planner;
import lib.alexandria.planning.Stream;
import lib.alexandria.planning.Trainer;

import weka.gui.Logger;
import weka.gui.SysErrLog;
import weka.gui.explorer.Explorer.CapabilitiesFilterChangeEvent;
import weka.gui.explorer.Explorer.CapabilitiesFilterChangeListener;

/**
 * Constructs a tab-view for using Planner in Weka.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class PlannerPanel extends WekaPanel implements Planner, CapabilitiesFilterChangeListener {
	private static final long serialVersionUID = 3409476524444377128L;
	
	private final WekaHarness harness;
	private final NeatPanel[] inner_panels;

	public PlannerPanel() {
		super("Planner");
		harness = new WekaHarness(this.getName());
		setLayout(new BorderLayout());
		inner_panels = new NeatPanel[2];
		inner_panels[1] = new NeatPanel("Results") {
			private int iterations, max = 10;
			private Timer timer;
			private JTextField k_optimal_field;
			private JProgressBar progress_bar;
			@Override
			public String getPosition() {
				return BorderLayout.EAST;
			}
			@Override
			public void setup() {
				timer = new Timer(1000, this);
				progress_bar = new JProgressBar();
				k_optimal_field = new JTextField();
				k_optimal_field.setEditable(false);
				k_optimal_field.addActionListener(this);
				this.setLayout(new FlowLayout());
				this.add(new JLabel("And the winner is..."));
				this.add(k_optimal_field);
				this.add(progress_bar);
				this.reset();
			}
			private void reset() {
				iterations = 0;
				progress_bar.setValue(0);
				k_optimal_field.setText("???");
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				++iterations;
				progress_bar.setValue(100 * iterations / max);
				k_optimal_field.setText(Long.toString(iterations));
				if (iterations < max) {
					timer.restart();
				} else {
					timer.stop();
					iterations = 0;
					PlannerPanel.this.status("Result: '" + k_optimal_field.getText() + "'");
				}
			}
		};
		inner_panels[0] = new NeatPanel("Hyper-parameters") {
			private JSpinner k_spin_start, k_spin_count;
			private SpinnerNumberModel k_spin_model1, k_spin_model2;
			private JButton button;
			@Override
			public String getPosition() {
				return BorderLayout.CENTER;
			}
			@Override
			public void setup() {
				k_spin_model1 = new SpinnerNumberModel(0, 0, 10, 1);
				k_spin_model2 = new SpinnerNumberModel(0, 0, 10, 1);
				k_spin_start = new JSpinner(k_spin_model1);
				k_spin_count = new JSpinner(k_spin_model2);
				button = new JButton("Plan!");
				button.addActionListener(this);
				this.setLayout(new FlowLayout());
				this.add(new JLabel("Plan to find c = 10^k..."));
				this.add(new JLabel("k starts at:"));
				this.add(k_spin_start);
				this.add(new JLabel("k goes for:"));
				this.add(k_spin_count);
				this.add(button);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				PlannerPanel.this.inner_panels[1].actionPerformed(e);
				PlannerPanel.this.log("Started planning...");
			}
		};
		super.setLog(new SysErrLog());
		for (NeatPanel panel : inner_panels) {
			add(panel, panel.getPosition());
		}
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
	public void setLog(Logger newLog) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void capabilitiesFilterChanged(CapabilitiesFilterChangeEvent e) {
		// TODO Auto-generated method stub
	}
}
