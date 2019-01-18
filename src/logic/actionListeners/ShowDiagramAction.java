package logic.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.mainEngine.Gui;
import gui.tableElements.tableConstructors.AbstractTableConstruction;
import gui.tableElements.tableConstructors.TableConstructionFactory;
import logic.centralLogic.CentralLinker;
import logic.utilities.fillers.TreeFillerFactory;
import logic.utilities.makers.TableMakerFactory;

class ShowDiagramAction implements ActionListener {
	private Gui theGui;

	public ShowDiagramAction(final Gui theGui) {
		this.theGui = theGui;
	}

	@Override
	public void actionPerformed(final ActionEvent action) {
		if (!(CentralLinker.getCurrentProject() == null)) {
			theGui.getZoomInButton().setVisible(true);
			theGui.getZoomOutButton().setVisible(true);
			final AbstractTableConstruction table = TableConstructionFactory.makeTableConstruction("IDU", CentralLinker.getGlobalDataKeeper(), null, 0);
			final String[] columns = table.constructColumns();
			final String[][] rows = table.constructRows();
			CentralLinker.setSegmentSizeZoomArea(table.getSegmentSize());
			System.out.println("Schemas: " + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesSchemas().size());
			System.out.println("C: " + columns.length + " R: " + rows.length);
			CentralLinker.setFinalColumnsZoomArea(columns);
			CentralLinker.setFinalRowsZoomArea(rows);
			theGui.getTabbedPane().setSelectedIndex(0);
			TableMakerFactory.makeTableMaker("General").makeSpecialtyTable(null, null, false, theGui);
			TreeFillerFactory.makeTreeFiller("Tree").fillTree(theGui);
		} else {
			JOptionPane.showMessageDialog(null, "Select a Project first");
			return;
		}
	}

}
