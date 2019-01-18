package logic.utilities.fillers;

public final class TreeFillerFactory {
	
	private TreeFillerFactory() {}

	public static AbstractTreeFiller makeTreeFiller(final String name) {
		switch (name) {
			case "Tree":
				return new TreeFiller();
			case "Phases":
				return new PhasesTreeFiller();
			case "Clusters":
				return new ClustersTreeFiller();
			default:
				return null;
		}
	}
}
