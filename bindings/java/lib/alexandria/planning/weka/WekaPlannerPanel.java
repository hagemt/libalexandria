/**
 * 
 */
package lib.alexandria.planning.weka;

import lib.alexandria.planning.Objective;
import lib.alexandria.planning.Planner;
import lib.alexandria.planning.Stream;
import lib.alexandria.planning.Trainer;

/**
 * @author Tor E Hagemann <hagemt@rpi.edu>
 *
 */
public class WekaPlannerPanel extends WekaPanel implements Planner {
	private static final long serialVersionUID = 3409476524444377128L;
	private WekaHarness harness;

	public WekaPlannerPanel() {
		super("Planner");
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
