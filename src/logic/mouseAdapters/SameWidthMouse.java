package logic.mouseAdapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import logic.centralLogic.CentralLinker;

class SameWidthMouse extends MouseAdapter {
	
	public SameWidthMouse() {}

	@Override
	public void mouseClicked(final MouseEvent event) {
		CentralLinker.getLifeTimeTable().uniformlyDistributed(1);
	}

}
