package lib.alexandria.planning;

public interface Planner {
	void addLowerObjective(Objective j1);
	void addUpperObjective(Objective j2);
	void addTrainer(Trainer t);
	
	void attach(Stream s);
	
	int getParallelism();
	void setParallelism(int n);
}
