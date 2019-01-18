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

class ShowPhasesDiagramAction implements ActionListener {
	private Gui theGui;

	public ShowPhasesDiagramAction(final Gui theGui) {
		this.theGui = theGui;
	}

	@Override
	public void actionPerformed(final ActionEvent action) {
		if (!(CentralLinker.getProject() == null)) {
			CentralLinker.setWholeCol(-1);
			final ParametersJDialog dialog = new ParametersJDialog(false);
			dialog.setModal(true);
			dialog.setVisible(true);
			if (dialog.getConfirmation()) {
				printParameters(dialog);
				final PhaseAnalyzerMainEngine mainEngine = new PhaseAnalyzerMainEngine(CentralLinker.getInputCsv(),
						CentralLinker.getOutputAssessment1(), CentralLinker.getOutputAssessment2(), CentralLinker.getTimeWeight(),
						CentralLinker.getChangeWeight(), CentralLinker.getPreProcessingTime(), CentralLinker.getPreProcessingChange());
				mainEngine.parseInput();
				System.out.println("\n\n\n");
				mainEngine.extractPhases(CentralLinker.getNumberOfPhases());
				mainEngine.connectTransitionsWithPhases(CentralLinker.getGlobalDataKeeper());
				CentralLinker.getGlobalDataKeeper().setPhaseCollectors(mainEngine.getPhaseCollectors());
				isPhaseFull();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please select a project first!");
		}
	}

	private void isPhaseFull() {
		if (CentralLinker.getGlobalDataKeeper().getPhaseCollectors().size() != 0) {
			wasNotZero();
		} else {
			JOptionPane.showMessageDialog(null, "Extract Phases first");
		}
	}

	private void printParameters(final ParametersJDialog dialog) {
		CentralLinker.setTimeWeight(dialog.getTimeWeight());
		CentralLinker.setChangeWeight(dialog.getChangeWeight());
		CentralLinker.setPreProcessingTime(dialog.getPreProcessingTime());
		CentralLinker.setPreProcessingChange(dialog.getPreProcessingChange());
		CentralLinker.setNumberOfPhases(dialog.getNumberOfPhases());
		System.out.println(CentralLinker.getTimeWeight() + " " + CentralLinker.getChangeWeight());
	}

	private void wasNotZero() {
		final AbstractTableConstruction table = TableConstructionFactory.makeTableConstruction("Phases", CentralLinker.getGlobalDataKeeper(), null, 0);
		final String[] columns = table.constructColumns();
		final String[][] rows = table.constructRows();
		CentralLinker.setSegmentSize(table.getSegmentSize());
		System.out.println("Schemas: " + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesSchemas().size());
		System.out.println("C: " + columns.length + " R: " + rows.length);
		CentralLinker.setFinalColumns(columns);
		CentralLinker.setFinalRows(rows);
		theGui.getTabbedPane().setSelectedIndex(0);
		TableMakerFactory.makeTableMaker("Phases").makeSpecialtyTable(null, null, false, theGui);
		TreeFillerFactory.makeTreeFiller("Phases").fillTree(theGui);
	}

}
