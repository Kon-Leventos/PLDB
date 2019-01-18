package logic.mouseAdapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import logic.centralLogic.CentralLinker;

class ZoomInMouse extends MouseAdapter {
	
	public ZoomInMouse() {}

	@Override
	public void mouseClicked(final MouseEvent event) {
		CentralLinker.setRowHeight(CentralLinker.getRowHeight() + 2);
		CentralLinker.setColumnWidth(CentralLinker.getColumnWidth() + 1);
		CentralLinker.getZoomAreaTable().setZoom(CentralLinker.getRowHeight(), CentralLinker.getColumnWidth());
	}

}
