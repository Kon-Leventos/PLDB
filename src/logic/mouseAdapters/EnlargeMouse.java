package logic.mouseAdapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import gui.dialogs.EnlargeTable;
import logic.centralLogic.CentralLinker;

class EnlargeMouse extends MouseAdapter {
	
	public EnlargeMouse() {}

	@Override
	public void mouseClicked(final MouseEvent event) {
		final EnlargeTable showEnlargmentPopup = new EnlargeTable(CentralLinker.getFinalRowsZoomArea(), CentralLinker.getFinalColumnsZoomArea(),
				CentralLinker.getSegmentSizeZoomArea());
		showEnlargmentPopup.setBounds(100, 100, 1300, 700);
		showEnlargmentPopup.setVisible(true);
	}
}
