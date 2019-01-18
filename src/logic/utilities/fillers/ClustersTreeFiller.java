package logic.utilities.fillers;

import gui.treeElements.ITreeConstruction;
import gui.treeElements.TreeConstructionFactory;
import logic.centralLogic.CentralLinker;

final class ClustersTreeFiller extends AbstractTreeFiller {

	public ClustersTreeFiller() {}	

	@Override
	protected ITreeConstruction makeTreeConstruction() {
		return TreeConstructionFactory.makeTreeConstruction("Clusters", CentralLinker.getGlobalDataKeeper());
	}

	@Override
	protected String setText() {
		return "Clusters Tree";
	}
	
}
