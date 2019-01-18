package logic.utilities.viewers;

import java.util.ArrayList;

import gui.tableElements.tableConstructors.AbstractTableConstruction;
import gui.tableElements.tableConstructors.TableConstructionFactory;
import logic.centralLogic.CentralLinker;

final class ClusterSelectionToZoomAreaViewer extends AbstractSelectionViewer {
	
	public ClusterSelectionToZoomAreaViewer() {}

	@Override
	protected AbstractTableConstruction initializeTable(final int selectedColumn) {
		final ArrayList<String> tablesOfCluster = new ArrayList<String>();
		for (int outerIndex = 0; outerIndex < CentralLinker.getTablesSelected().size(); outerIndex++) {
			final String[] selectedClusterSplit = CentralLinker.getTablesSelected().get(outerIndex).split(" ");
			final int cluster = Integer.parseInt(selectedClusterSplit[1]);
			final ArrayList<String> namesOfTables = CentralLinker.getGlobalDataKeeper().getClusterCollectors().get(0).getClusters().get(cluster)
					.getNamesOfTables();
			for (int innerIndex = 0; innerIndex < namesOfTables.size(); innerIndex++) {
				tablesOfCluster.add(namesOfTables.get(innerIndex));
			}
			System.out.println(CentralLinker.getTablesSelected().get(outerIndex));
		}
		if (selectedColumn == 0) {
			return TableConstructionFactory.makeTableConstruction("Zoom", CentralLinker.getGlobalDataKeeper(), tablesOfCluster, 0);
		} else {
			return TableConstructionFactory.makeTableConstruction("Area", CentralLinker.getGlobalDataKeeper(), tablesOfCluster, selectedColumn);
		}
	}

}
