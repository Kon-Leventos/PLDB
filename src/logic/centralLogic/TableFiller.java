package logic.centralLogic;

import gui.mainEngine.Gui;
import gui.tableElements.tableConstructors.AbstractTableConstruction;
import gui.tableElements.tableConstructors.TableConstructionFactory;
import logic.utilities.fillers.TreeFillerFactory;
import logic.utilities.makers.TableMakerFactory;
import phaseAnalyzer.engine.PhaseAnalyzerMainEngine;
import tableClustering.clusterExtractor.engine.TableClusteringMainEngine;

public final class TableFiller {
	private static final float HALF_WEIGHT = (float) 0.5;
	private static final int MAXIMUM_PHASES = 56;
	private static final int NOMINAL_PHASES = 40;
	private static final int NOMINAL_CLUSTERS = 14;
	private static final double DEFAULT_WEIGHT = 0.3;
	
	private TableFiller() {}

	public static void fillTable(final Gui theGui) {
		final AbstractTableConstruction table = TableConstructionFactory.makeTableConstruction("IDU", CentralLinker.getGlobalDataKeeper(), null, 0);
		final String[] columns = table.constructColumns();
		final String[][] rows = table.constructRows();
		CentralLinker.setSegmentSizeZoomArea(table.getSegmentSize());
		CentralLinker.setFinalColumnsZoomArea(columns);
		CentralLinker.setFinalRowsZoomArea(rows);
		theGui.getTabbedPane().setSelectedIndex(0);
		TableMakerFactory.makeTableMaker("General").makeSpecialtyTable(null, null, false, theGui);
		CentralLinker.setTimeWeight(HALF_WEIGHT);
		CentralLinker.setChangeWeight(HALF_WEIGHT);
		CentralLinker.setPreProcessingTime(false);
		CentralLinker.setPreProcessingChange(false);
		decideOnPhases();
		CentralLinker.setNumberOfClusters(NOMINAL_CLUSTERS);
		System.out.println(CentralLinker.getTimeWeight() + " " + CentralLinker.getChangeWeight());
		final PhaseAnalyzerMainEngine mainEngine = new PhaseAnalyzerMainEngine(CentralLinker.getInputCsv(), CentralLinker.getOutputAssessment1(),
				CentralLinker.getOutputAssessment2(), CentralLinker.getTimeWeight(), CentralLinker.getChangeWeight(),
				CentralLinker.getPreProcessingTime(), CentralLinker.getPreProcessingChange());
		mainEngine.parseInput();
		System.out.println("\n\n\n");
		editPhaseEngine(theGui, mainEngine);
	}

	private static void decideOnPhases() {
		if (CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions().size() < MAXIMUM_PHASES) {
			CentralLinker.setNumberOfPhases(NOMINAL_PHASES);
		} else {
			CentralLinker.setNumberOfPhases(MAXIMUM_PHASES);
		}
	}

	private static void editPhaseEngine(final Gui theGui, final PhaseAnalyzerMainEngine mainEngine) {
		mainEngine.extractPhases(CentralLinker.getNumberOfPhases());
		mainEngine.connectTransitionsWithPhases(CentralLinker.getGlobalDataKeeper());
		CentralLinker.getGlobalDataKeeper().setPhaseCollectors(mainEngine.getPhaseCollectors());
		final Double birthWeight = DEFAULT_WEIGHT;
		final Double deathWeight = DEFAULT_WEIGHT;
		final Double changeWeight = DEFAULT_WEIGHT;
		final TableClusteringMainEngine mainEngine2 = new TableClusteringMainEngine(CentralLinker.getGlobalDataKeeper(), birthWeight, deathWeight,
				changeWeight);
		mainEngine2.extractClusters(CentralLinker.getNumberOfClusters());
		CentralLinker.getGlobalDataKeeper().setClusterCollectors(mainEngine2.getClusterCollectors());
		mainEngine2.print();
		isPhasesFull(theGui);
		System.out.println("Schemas:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesSchemas().size());
		System.out.println("Transitions:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions().size());
		System.out.println("Tables:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables().size());
	}

	private static void isPhasesFull(final Gui theGui) {
		if (CentralLinker.getGlobalDataKeeper().getPhaseCollectors().size() != 0) {
			final AbstractTableConstruction tableP = TableConstructionFactory.makeTableConstruction("Clusters", CentralLinker.getGlobalDataKeeper(), null, 0);
			final String[] columnsP = tableP.constructColumns();
			final String[][] rowsP = tableP.constructRows();
			CentralLinker.setSegmentSize(tableP.getSegmentSize());
			CentralLinker.setFinalColumns(columnsP);
			CentralLinker.setFinalRows(rowsP);
			theGui.getTabbedPane().setSelectedIndex(0);
			TableMakerFactory.makeTableMaker("Phases").makeSpecialtyTable(null, null, false, theGui);
			TreeFillerFactory.makeTreeFiller("Clusters").fillTree(theGui);
		}
	}

}
