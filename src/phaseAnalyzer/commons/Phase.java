package phaseAnalyzer.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import data.dataKeeper.GlobalDataKeeper;
import data.dataPPL.pplTransition.PlutarchParallelLivesTransition;

@SuppressWarnings("serial")
public class Phase implements Serializable {
	private int endPosition;
	private String endOfSQLFile;
	private TreeMap<Integer, PlutarchParallelLivesTransition> phasePPLTransitions = new TreeMap<Integer, PlutarchParallelLivesTransition>();
	private int startPosition;
	private String startOfSQLFile;
	private ArrayList<Phase> subPhases = new ArrayList<Phase>();
	private double sum = 0;
	private int totalUpdates;
	private TransitionHistory transitionHistory;

	public Phase(TransitionHistory transitionHistory) {
		this.transitionHistory = transitionHistory;
		subPhases = new ArrayList<Phase>();
	}

	public double calculateTotalDistance(Phase anotherPhase, float timeWeight, float changeWeight) {
		int transitionHistoryTotalUpdates = transitionHistory.getTotalUpdates();
		double changeDistance = Math.abs(this.totalUpdates - anotherPhase.totalUpdates) / ((double) transitionHistoryTotalUpdates);
		double timeDistance = 0;
		Phase subsequent, preceding;
		if (this.startPosition > anotherPhase.endPosition) {
			subsequent = this;
			preceding = anotherPhase;
		} else {
			preceding = this;
			subsequent = anotherPhase;
		}
		timeDistance = (((transitionHistory.getValues().get(subsequent.startPosition).getTime()
				- transitionHistory.getValues().get(preceding.endPosition).getTime()) / 86400)) / (transitionHistory.getTotalTime());
		double totalDistance = changeWeight * changeDistance + timeWeight * timeDistance;
		return totalDistance;
	}

	public void connectWithPPLTransitions(GlobalDataKeeper tmpGlobalDataKeeper) {
		TreeMap<Integer, PlutarchParallelLivesTransition> allPPLTransitions = tmpGlobalDataKeeper.getAllPlutarchParallelLivesTransitions();
		boolean found = false;
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> tr : allPPLTransitions.entrySet()) {
			if (tr.getValue().getOldVersionName().equals(startOfSQLFile)) {
				found = true;
			}
			if (found) {
				this.phasePPLTransitions.put(tr.getKey(), tr.getValue());
			}
			if (tr.getValue().getNewVersionName().equals(endOfSQLFile)) {
				break;
			}
		}
		System.out.println(startPosition + " " + startOfSQLFile + " " + endPosition + " " + endOfSQLFile);

	}

	public int getEndPosition() {
		return endPosition;
	}

	public TreeMap<Integer, PlutarchParallelLivesTransition> getPhasePPLTransitions() {
		return phasePPLTransitions;
	}

	public int getSize() {
		return phasePPLTransitions.size();
	}

	public int getStartPos() {
		return startPosition;
	}

	public double getSum() {
		return sum;
	}

	public int getTotalAdditionsOfPhase() {
		int additions = 0;
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> pplTr : phasePPLTransitions.entrySet()) {
			additions = additions + pplTr.getValue().getAdditionNumberOfSpecificTransition();
		}
		return additions;
	}

	public int getTotalDeletionsOfPhase() {
		int deletions = 0;
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> pplTr : phasePPLTransitions.entrySet()) {
			deletions = deletions + pplTr.getValue().getDeletionNumberOfSpecificTransition();
		}
		return deletions;
	}

	public int getTotalUpdates() {
		return totalUpdates;
	}

	public int getTotalUpdatesOfPhase() {
		int updates = 0;
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> pplTr : phasePPLTransitions.entrySet()) {
			updates = updates + pplTr.getValue().getUpdateNumberOfSpecificTransition();
		}
		return updates;
	}

	public TransitionHistory getTransitionHistory() {
		return transitionHistory;
	}

	public Phase mergeWithNextPhase(Phase nextPhase) {
		Phase newPhase = new Phase(transitionHistory);
		newPhase.startPosition = this.startPosition;
		newPhase.endPosition = nextPhase.endPosition;
		newPhase.startOfSQLFile = this.startOfSQLFile;
		newPhase.endOfSQLFile = nextPhase.endOfSQLFile;
		newPhase.totalUpdates = this.totalUpdates + nextPhase.totalUpdates;
		newPhase.subPhases.add(this);
		newPhase.subPhases.add(nextPhase);
		for (int i = 0; i < this.subPhases.size(); i++) {
			newPhase.subPhases.add(this.subPhases.get(i));
		}
		for (int i = 0; i < nextPhase.subPhases.size(); i++) {
			newPhase.subPhases.add(nextPhase.subPhases.get(i));
		}
		return newPhase;
	}

	public void setEndPos(int endPos) {
		this.endPosition = endPos;
		endOfSQLFile = this.transitionHistory.getValues().get(endPos).getNewVersionFile();
	}

	public void setStartPos(int startPos) {
		this.startPosition = startPos;
		startOfSQLFile = this.transitionHistory.getValues().get(startPos).getOldVersionFile();
	}

	public void setTotalUpdates(int totalUpdates) {
		this.totalUpdates = totalUpdates;
	}

	public void setTransitionHistory(TransitionHistory transitionHistory) {
		this.transitionHistory = transitionHistory;
	}

}
