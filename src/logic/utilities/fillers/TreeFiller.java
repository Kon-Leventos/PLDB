package logic.utilities.fillers;

import gui.treeElements.ITreeConstruction;
import gui.treeElements.TreeConstructionFactory;
import logic.centralLogic.CentralLinker;

final class TreeFiller extends AbstractTreeFiller {
	
	public TreeFiller() {}	

	@Override
	protected ITreeConstruction makeTreeConstruction() {
		return TreeConstructionFactory.makeTreeConstruction("General", CentralLinker.getGlobalDataKeeper());
	}

	@Override
	protected String setText() {
		return "General Tree";
	}

}
