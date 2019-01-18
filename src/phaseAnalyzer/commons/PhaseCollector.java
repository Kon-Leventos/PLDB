package phaseAnalyzer.commons;

import java.io.Serializable;
import java.util.ArrayList;
import data.dataKeeper.GlobalDataKeeper;

@SuppressWarnings("serial")
public class PhaseCollector implements Serializable {
	private ArrayList<Phase> phases;
	private double totalSum = 0;

	public PhaseCollector() {
		phases = new ArrayList<Phase>();
	}

	public void addPhase(Phase p) {
		this.phases.add(p);
	}

	public void connectPhasesWithTransitions(GlobalDataKeeper tmpGlobalDataKeeper) {
		for (Phase phase : phases) {
			phase.connectWithPPLTransitions(tmpGlobalDataKeeper);
		}
	}

	public ArrayList<Phase> getPhases() {
		return phases;
	}

	public int getSize() {
		return this.phases.size();
	}

	public double getTotalSum() {
		return totalSum;
	}

	public void setPhases(ArrayList<Phase> phases) {
		this.phases = phases;
	}

}
