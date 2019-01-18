package gui.treeElements;

import data.dataKeeper.GlobalDataKeeper;

public final class TreeConstructionFactory {
	
	private TreeConstructionFactory() {}

	public static ITreeConstruction makeTreeConstruction(final String name, final GlobalDataKeeper dataKeeper) {
		switch (name) {
			case "General":
				return new TreeConstructionGeneral(dataKeeper);
			case "Phases":
				return new TreeConstructionPhases(dataKeeper);
			case "Clusters":
				return new TreeConstructionPhasesWithClusters(dataKeeper);
			default:
				return null;
		}
	}

}
