package logic.mouseAdapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import logic.centralLogic.CentralLinker;

class ZoomOutMouse extends MouseAdapter {
	
	public ZoomOutMouse() {}

	@Override
	public void mouseClicked(final MouseEvent event) {
		CentralLinker.setRowHeight(CentralLinker.getRowHeight() - 2);
		CentralLinker.setColumnWidth(CentralLinker.getColumnWidth() - 1);
		if (CentralLinker.getRowHeight() < 1) {
			CentralLinker.setRowHeight(1);
		}
		if (CentralLinker.getColumnWidth() < 1) {
			CentralLinker.setColumnWidth(1);
		}
		CentralLinker.getZoomAreaTable().setZoom(CentralLinker.getRowHeight(), CentralLinker.getColumnWidth());
	}

}
