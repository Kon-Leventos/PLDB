package logic.mouseAdapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import logic.centralLogic.CentralLinker;

class OverTimeMouse extends MouseAdapter {
	
	public OverTimeMouse() {}

	@Override
	public void mouseClicked(final MouseEvent event) {
		CentralLinker.getLifeTimeTable().notUniformlyDistributed(CentralLinker.getGlobalDataKeeper());
	}

}
