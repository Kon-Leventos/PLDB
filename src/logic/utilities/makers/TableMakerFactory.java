package logic.utilities.makers;

public final class TableMakerFactory {
	
	private TableMakerFactory() {}
	
	public static IMaker makeTableMaker(final String name) {
		switch (name) {
			case "Detail":
				return new DetailedTableMaker();
			case "General":
				return new GeneralTableMaker();
			case "Phases":
				return new GeneralTablePhasesMaker();
			case "Cluster":
				return new ZoomAreaTableForClusterMaker();
			case "Zoom":
				return new ZoomAreaTableMaker();
			default:
				return null;
		}
	}
	
}
