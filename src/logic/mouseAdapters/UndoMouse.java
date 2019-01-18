package logic.mouseAdapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import gui.mainEngine.Gui;
import logic.centralLogic.CentralLinker;
import logic.utilities.makers.TableMakerFactory;

class UndoMouse extends MouseAdapter {
	private Gui theGui;

	public UndoMouse(final Gui theGui) {
		this.theGui = theGui;
	}

	@Override
	public void mouseClicked(final MouseEvent event) {
		if (CentralLinker.getFirstLevelUndoColumnsZoomArea() != null) {
			CentralLinker.setFinalColumnsZoomArea(CentralLinker.getFirstLevelUndoColumnsZoomArea());
			CentralLinker.setFinalRowsZoomArea(CentralLinker.getFirstLevelUndoRowsZoomArea());
			TableMakerFactory.makeTableMaker("Cluster").makeSpecialtyTable(null, null, false, theGui);
		}
	}

}
