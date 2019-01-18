package phaseAnalyzer.commons;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class TransitionHistory implements Serializable {
	private double totalTime;
	private int totalUpdates;
	private ArrayList<TransitionStats> values;

	public TransitionHistory() {
		this.values = new ArrayList<TransitionStats>();
	}

	public void addValue(TransitionStats v) {
		values.add(v);
	}

	public void consoleVerticalReport() {
		for (TransitionStats v : values) {
			System.out.println(v.toStringShort());
		}
		System.out.println();
	}

	public double getTotalTime() {
		return this.totalTime;
	}

	public int getTotalUpdates() {
		return this.totalUpdates;
	}

	public ArrayList<TransitionStats> getValues() {
		return values;
	}

	public void setTotalTime() {
		this.totalTime = (this.values.get(this.values.size() - 1).getTime() - this.values.get(0).getTime()) / 86400;
	}

	public void setTotalUpdates(int totalUpdates) {
		this.totalUpdates = totalUpdates;
	}

}
