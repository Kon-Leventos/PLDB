package logic.utilities.viewers;

import gui.tableElements.tableConstructors.AbstractTableConstruction;
import gui.tableElements.tableConstructors.TableConstructionFactory;
import logic.centralLogic.CentralLinker;

final class SelectionToZoomAreaViewer extends AbstractSelectionViewer {
	
	public SelectionToZoomAreaViewer() {}

	@Override
	protected AbstractTableConstruction initializeTable(final int selectedColumn) {
		return TableConstructionFactory.makeTableConstruction("Area", CentralLinker.getGlobalDataKeeper(),
				CentralLinker.getTablesSelected(), selectedColumn);
	}

}
