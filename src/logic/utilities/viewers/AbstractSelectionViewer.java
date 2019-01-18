package logic.utilities.viewers;

import gui.mainEngine.Gui;
import gui.tableElements.tableConstructors.AbstractTableConstruction;
import logic.centralLogic.CentralLinker;
import logic.utilities.makers.TableMakerFactory;

public abstract class AbstractSelectionViewer {
	
	public AbstractSelectionViewer() {}
	
	public void showSelection(final Gui theGui, final int selectedColumn) {
		final AbstractTableConstruction table = initializeTable(selectedColumn);
		final String[] columns = table.constructColumns();
		final String[][] rows = table.constructRows();
		CentralLinker.setSegmentSizeZoomArea(table.getSegmentSize());
		System.out.println("Schemas: " + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesSchemas().size());
		System.out.println("C: " + columns.length + " R: " + rows.length);
		CentralLinker.setFinalColumnsZoomArea(columns);
		CentralLinker.setFinalRowsZoomArea(rows);
		theGui.getTabbedPane().setSelectedIndex(0);
		TableMakerFactory.makeTableMaker("Zoom").makeSpecialtyTable(null, null, false, theGui);
	}

	protected abstract AbstractTableConstruction initializeTable(int selectedColumn);

}
