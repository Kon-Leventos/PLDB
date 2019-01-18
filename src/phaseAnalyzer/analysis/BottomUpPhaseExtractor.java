package phaseAnalyzer.analysis;

import java.util.ArrayList;
import java.util.Iterator;
import phaseAnalyzer.commons.Phase;
import phaseAnalyzer.commons.PhaseCollector;
import phaseAnalyzer.commons.TransitionHistory;
import phaseAnalyzer.commons.TransitionStats;

class BottomUpPhaseExtractor implements IPhaseExtractor {

	@Override
	public PhaseCollector extractAtMostKPhases(TransitionHistory transitionHistory, int numberOfPhases, float timeWeight, float changeWeight,
			boolean preProcessingTime, boolean preProcessingChange) {
		PhaseCollector initialSolution = new PhaseCollector();
		this.init(transitionHistory, initialSolution);
		PhaseCollector preProcessedSolutionTime = new PhaseCollector();
		preProcessedSolutionTime = initialSolution;
		if (preProcessingTime) {
			preProcessedSolutionTime = performTimePreprocessing(transitionHistory, initialSolution);
		}
		PhaseCollector preProcessedSolutionChanges = new PhaseCollector();
		preProcessedSolutionChanges = preProcessedSolutionTime;
		if (preProcessingChange) {
			preProcessedSolutionChanges = performChangePreprocessing(transitionHistory, preProcessedSolutionTime);
		}
		PhaseCollector currentSolution = new PhaseCollector();
		currentSolution = this.newPhaseCollector(transitionHistory, preProcessedSolutionChanges, timeWeight, changeWeight);
		while (currentSolution.getPhases().size() > numberOfPhases) {
			currentSolution = this.newPhaseCollector(transitionHistory, currentSolution, timeWeight, changeWeight);
		}
		return currentSolution;
	}

	public PhaseCollector init(TransitionHistory transitionHistory, PhaseCollector phaseCollector) {
		for (TransitionStats v : transitionHistory.getValues()) {
			Phase phase = new Phase(transitionHistory);
			int position = transitionHistory.getValues().indexOf(v);
			phase.setStartPos(position);
			phase.setEndPos(position);
			phase.setTotalUpdates(v.getTotalAttrChange());
			phaseCollector.addPhase(phase);
		}
		return phaseCollector;
	}

	private void loopNewPhases(ArrayList<Phase> oldPhases, ArrayList<Phase> newPhases, int pos, int oldSize, Phase previousPhase, Phase p) {
		Phase toMerge = previousPhase;
		Phase newPhase = toMerge.mergeWithNextPhase(p);
		for (int i = 0; i < pos - 1; i++) {
			Phase p1 = oldPhases.get(i);
			newPhases.add(p1);
		}
		newPhases.add(newPhase);
		if (pos < oldSize - 1) {
			for (int i = pos + 1; i < oldSize; i++) {
				Phase p1 = oldPhases.get(i);
				newPhases.add(p1);
			}
		}
	}

	private void makeMinDistMerge(PhaseCollector newCollector, ArrayList<Phase> newPhases, ArrayList<Phase> oldPhases, int oldSize,
			double[] distances) {
		int posI = -1;
		double minDist = Double.MAX_VALUE;
		for (int i = 1; i < oldSize; i++) {
			if (distances[i] < minDist) {
				posI = i;
				minDist = distances[i];
			}
		}
		Phase toMerge = oldPhases.get(posI - 1);
		Phase newPhase = toMerge.mergeWithNextPhase(oldPhases.get(posI));
		for (int i = 0; i < posI - 1; i++) {
			Phase p = oldPhases.get(i);
			newPhases.add(p);
		}
		newPhases.add(newPhase);
		if (posI < oldSize - 1) {
			for (int i = posI + 1; i < oldSize; i++) {
				Phase p = oldPhases.get(i);
				newPhases.add(p);
			}
		}
		newCollector.setPhases(newPhases);
	}

	public PhaseCollector newPhaseCollector(TransitionHistory transitionHistory, PhaseCollector prevCollector, float timeWeight, float changeWeight) {
		PhaseCollector newCollector = new PhaseCollector();
		ArrayList<Phase> newPhases = new ArrayList<Phase>();
		ArrayList<Phase> oldPhases = prevCollector.getPhases();
		int oldSize = oldPhases.size();
		if (oldSize == 0) {
			System.out.println("Sth went terribly worng at method XXX");
			System.exit(-10);
		}
		double distances[] = new double[oldSize];
		distances[0] = Double.MAX_VALUE;
		int pI = 0;
		Iterator<Phase> phaseIter = oldPhases.iterator();
		Phase previousPhase = phaseIter.next();
		while (phaseIter.hasNext()) {
			Phase p = phaseIter.next();
			pI++;
			distances[pI] = p.calculateTotalDistance(previousPhase, timeWeight, changeWeight);
			previousPhase = p;
		}
		makeMinDistMerge(newCollector, newPhases, oldPhases, oldSize, distances);
		return newCollector;
	}

	private PhaseCollector performChangePreprocessing(TransitionHistory transitionHistory, PhaseCollector preProcessedSolutionTime) {
		int oldSize;
		PhaseCollector preProcessedSolutionChanges = new PhaseCollector();
		preProcessedSolutionChanges = this.preProcessOverChanges(transitionHistory, preProcessedSolutionTime);
		oldSize = preProcessedSolutionTime.getPhases().size();
		while (oldSize != preProcessedSolutionChanges.getPhases().size()) {
			oldSize = preProcessedSolutionChanges.getPhases().size();
			preProcessedSolutionChanges = this.preProcessOverChanges(transitionHistory, preProcessedSolutionChanges);
		}
		return preProcessedSolutionChanges;
	}

	private PhaseCollector performTimePreprocessing(TransitionHistory transitionHistory, PhaseCollector initSolution) {
		PhaseCollector preProcessedSolutionTime = new PhaseCollector();
		preProcessedSolutionTime = this.preProcessOverTime(transitionHistory, initSolution);
		int oldSize = initSolution.getPhases().size();
		while (oldSize != preProcessedSolutionTime.getPhases().size()) {
			oldSize = preProcessedSolutionTime.getPhases().size();
			preProcessedSolutionTime = this.preProcessOverTime(transitionHistory, preProcessedSolutionTime);
		}
		return preProcessedSolutionTime;
	}

	private PhaseCollector preProcessOverChanges(TransitionHistory transitionHistory, PhaseCollector prevCollector) {
		PhaseCollector preProcessedCollector = new PhaseCollector();
		ArrayList<Phase> oldPhases = prevCollector.getPhases();
		ArrayList<Phase> newPhases = new ArrayList<Phase>();
		int pos = 0;
		int oldSize = oldPhases.size();
		Iterator<Phase> phaseIter = oldPhases.iterator();
		Phase previousPhase = phaseIter.next();
		while (phaseIter.hasNext()) {
			pos++;
			Phase p = phaseIter.next();
			if (((transitionHistory.getValues().get(p.getStartPos()).getTotalAttrChange()
					- transitionHistory.getValues().get(previousPhase.getEndPosition()).getTotalAttrChange() == 0))) {
				loopNewPhases(oldPhases, newPhases, pos, oldSize, previousPhase, p);
				break;
			}
			previousPhase = p;
		}
		if (newPhases.size() != 0) {
			preProcessedCollector.setPhases(newPhases);
		} else {
			preProcessedCollector.setPhases(oldPhases);
		}
		return preProcessedCollector;
	}

	private PhaseCollector preProcessOverTime(TransitionHistory transitionHistory, PhaseCollector prevCollector) {
		PhaseCollector preProcessedCollector = new PhaseCollector();
		ArrayList<Phase> oldPhases = prevCollector.getPhases();
		ArrayList<Phase> newPhases = new ArrayList<Phase>();
		int pos = 0;
		int oldSize = oldPhases.size();
		Iterator<Phase> phaseIter = oldPhases.iterator();
		Phase previousPhase = phaseIter.next();
		while (phaseIter.hasNext()) {
			pos++;
			Phase p = phaseIter.next();
			if (((transitionHistory.getValues().get(p.getStartPos()).getTime()
					- transitionHistory.getValues().get(previousPhase.getEndPosition()).getTime()) / 84600) < 3) {
				loopNewPhases(oldPhases, newPhases, pos, oldSize, previousPhase, p);
				break;
			}
			previousPhase = p;
		}
		if (newPhases.size() != 0) {
			preProcessedCollector.setPhases(newPhases);
		} else {
			preProcessedCollector.setPhases(oldPhases);
		}
		return preProcessedCollector;
	}

}
