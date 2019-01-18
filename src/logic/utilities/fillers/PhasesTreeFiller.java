package logic.utilities.fillers;

import gui.treeElements.ITreeConstruction;
import gui.treeElements.TreeConstructionFactory;
import logic.centralLogic.CentralLinker;

final class PhasesTreeFiller extends AbstractTreeFiller {

	public PhasesTreeFiller() {}	

	@Override
	protected ITreeConstruction makeTreeConstruction() {
		return TreeConstructionFactory.makeTreeConstruction("Phases", CentralLinker.getGlobalDataKeeper());
	}

	@Override
	protected String setText() {
		return "Phases Tree";
	}

}
