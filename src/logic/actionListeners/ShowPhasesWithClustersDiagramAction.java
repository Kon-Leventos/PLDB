package logic.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.dialogs.ParametersJDialog;
import gui.mainEngine.Gui;
import gui.tableElements.tableConstructors.AbstractTableConstruction;
import gui.tableElements.tableConstructors.TableConstructionFactory;
import logic.centralLogic.CentralLinker;
import logic.utilities.fillers.TreeFillerFactory;
import logic.utilities.makers.TableMakerFactory;
import phaseAnalyzer.engine.PhaseAnalyzerMainEngine;
import tableClustering.clusterExtractor.engine.TableClusteringMainEngine;

class ShowPhasesWithClustersDiagramAction implements ActionListener {
	private Gui theGui;

	public ShowPhasesWithClustersDiagramAction(final Gui theGui) {
		this.theGui = theGui;
	}

	@Override
	public void actionPerformed(final ActionEvent action) {
		CentralLinker.setWholeCol(-1);
		if (!(CentralLinker.getProject() == null)) {
			final ParametersJDialog dialog = new ParametersJDialog(true);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (dialog.getConfirmation()) {
				printParameters(dialog);
				final PhaseAnalyzerMainEngine mainEngine = new PhaseAnalyzerMainEngine(CentralLinker.getInputCsv(),
						CentralLinker.getOutputAssessment1(), CentralLinker.getOutputAssessment2(), CentralLinker.getTimeWeight(),
						CentralLinker.getChangeWeight(), CentralLinker.getPreProcessingTime(), CentralLinker.getPreProcessingChange());
				formMainEngine(mainEngine);
				if (CentralLinker.getGlobalDataKeeper().getPhaseCollectors().size() != 0) {
					wasNotZero();
				} else {
					JOptionPane.showMessageDialog(null, "Extract Phases first");
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please select a project first!");
		}
	}

	private void formMainEngine(final PhaseAnalyzerMainEngine mainEngine) {
		mainEngine.parseInput();
		System.out.println("\n\n\n");
		mainEngine.extractPhases(CentralLinker.getNumberOfPhases());
		mainEngine.connectTransitionsWithPhases(CentralLinker.getGlobalDataKeeper());
		CentralLinker.getGlobalDataKeeper().setPhaseCollectors(mainEngine.getPhaseCollectors());
		final TableClusteringMainEngine extraEngine = new TableClusteringMainEngine(CentralLinker.getGlobalDataKeeper(),
				CentralLinker.getBirthWeight(), CentralLinker.getDeathWeight(), CentralLinker.getChangeWeightCl());
		extraEngine.extractClusters(CentralLinker.getNumberOfClusters());
		CentralLinker.getGlobalDataKeeper().setClusterCollectors(extraEngine.getClusterCollectors());
		extraEngine.print();
	}

	private void printParameters(final ParametersJDialog dialog) {
		CentralLinker.setTimeWeight(dialog.getTimeWeight());
		CentralLinker.setChangeWeight(dialog.getChangeWeight());
		CentralLinker.setPreProcessingTime(dialog.getPreProcessingTime());
		CentralLinker.setPreProcessingChange(dialog.getPreProcessingChange());
		CentralLinker.setNumberOfPhases(dialog.getNumberOfPhases());
		CentralLinker.setNumberOfClusters(dialog.getNumberOfClusters());
		CentralLinker.setBirthWeight(dialog.geBirthWeight());
		CentralLinker.setDeathWeight(dialog.getDeathWeight());
		CentralLinker.setChangeWeightCl(dialog.getChangeWeightCluster());
		System.out.println(CentralLinker.getTimeWeight() + " " + CentralLinker.getChangeWeight());
	}

	private void wasNotZero() {
		final AbstractTableConstruction table = TableConstructionFactory.makeTableConstruction("Clusters", CentralLinker.getGlobalDataKeeper(), null, 0);
		final String[] columns = table.constructColumns();
		final String[][] rows = table.constructRows();
		CentralLinker.setSegmentSize(table.getSegmentSize());
		System.out.println("Schemas: " + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesSchemas().size());
		System.out.println("C: " + columns.length + " R: " + rows.length);
		CentralLinker.setFinalColumns(columns);
		CentralLinker.setFinalRows(rows);
		theGui.getTabbedPane().setSelectedIndex(0);
		TableMakerFactory.makeTableMaker("Phases").makeSpecialtyTable(null, null, false, theGui);
		TreeFillerFactory.makeTreeFiller("Clusters").fillTree(theGui);
	}

}
