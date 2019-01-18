package gui.tableElements.tableConstructors;

import java.util.ArrayList;
import data.dataKeeper.GlobalDataKeeper;

public final class TableConstructionFactory {
	
	private TableConstructionFactory() {}

	public static AbstractTableConstruction makeTableConstruction(final String name, final GlobalDataKeeper dataKeeper, final ArrayList<String> tables,
			final int selectedColumn) {
		switch (name) {
			case "Squares":
				return new TableConstructionAllSquaresIncluded(dataKeeper);
			case "Zoom":
				return new TableConstructionClusterTablesPhasesZoomArea(dataKeeper, tables);
			case "IDU":
				return new TableConstructionInsertDeleteUpdate(dataKeeper);
			case "Phases":
				return new TableConstructionPhases(dataKeeper);
			case "Clusters":
				return new TableConstructionWithClusters(dataKeeper);
			case "Area":
				return new TableConstructionZoomArea(dataKeeper, tables, selectedColumn);
			default:
				return null;
		}
	}

}
