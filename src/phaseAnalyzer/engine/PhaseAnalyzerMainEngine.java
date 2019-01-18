package phaseAnalyzer.engine;

import java.util.ArrayList;
import java.util.HashMap;
import data.dataKeeper.GlobalDataKeeper;
import phaseAnalyzer.analysis.IPhaseExtractor;
import phaseAnalyzer.analysis.PhaseExtractorFactory;
import phaseAnalyzer.commons.PhaseCollector;
import phaseAnalyzer.commons.TransitionHistory;
import phaseAnalyzer.parser.IParser;
import phaseAnalyzer.parser.ParserFactory;

public class PhaseAnalyzerMainEngine {
	private HashMap<String, ArrayList<PhaseCollector>> allPhaseCollectors;
	private float changeWeight;
	private String inputCsv;
	private IParser parser;
	private ParserFactory parserFactory;
	private ArrayList<PhaseCollector> phaseCollectors;
	private IPhaseExtractor phaseExtractor;
	private PhaseExtractorFactory phaseExtractorFactory;
	private boolean preProcessingChange;
	private boolean preProcessingTime;
	private float timeWeight;
	private TransitionHistory transitionHistory;

	public PhaseAnalyzerMainEngine(String inputCsv, String outputAssessment1, String outputAssessment2, Float temporaryTimeWeight,
			Float temporaryChangeWeight, Boolean temporaryPreProcessingTime, Boolean temporaryPreProcessingChange) {
		timeWeight = temporaryTimeWeight;
		changeWeight = temporaryChangeWeight;
		preProcessingTime = temporaryPreProcessingTime;
		preProcessingChange = temporaryPreProcessingChange;
		this.inputCsv = inputCsv;
		parserFactory = new ParserFactory();
		parser = parserFactory.createParser("SimpleTextParser");
		phaseExtractorFactory = new PhaseExtractorFactory();
		phaseExtractor = phaseExtractorFactory.createPhaseExtractor("BottomUpPhaseExtractor");
		transitionHistory = new TransitionHistory();
		allPhaseCollectors = new HashMap<String, ArrayList<PhaseCollector>>();

	}

	public void connectTransitionsWithPhases(GlobalDataKeeper temporaryGlobalDataKeeper) {
		phaseCollectors.get(0).connectPhasesWithTransitions(temporaryGlobalDataKeeper);
	}

	public void extractPhases(int numberOfPhases) {
		phaseCollectors = new ArrayList<PhaseCollector>();
		PhaseCollector phaseCollector = new PhaseCollector();
		phaseCollector = phaseExtractor.extractAtMostKPhases(transitionHistory, numberOfPhases, timeWeight, changeWeight, preProcessingTime,
				preProcessingChange);
		phaseCollectors.add(phaseCollector);
		allPhaseCollectors.put(inputCsv, phaseCollectors);
	}

	public ArrayList<PhaseCollector> getPhaseCollectors() {
		return phaseCollectors;
	}

	public void parseInput() {
		this.transitionHistory = parser.parse(inputCsv, ";");
		this.transitionHistory.consoleVerticalReport();
	}

}
