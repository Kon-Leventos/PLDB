package gui.tableElements.tableConstructors;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import data.dataKeeper.GlobalDataKeeper;
import data.dataPPL.pplTransition.AtomicChange;
import data.dataPPL.pplTransition.PlutarchParallelLivesTransition;
import data.dataPPL.pplTransition.TableChange;
import phaseAnalyzer.commons.Phase;
import tableClustering.clusterExtractor.commons.Cluster;

class TableConstructionWithClusters extends AbstractTableConstruction {
	private ArrayList<Cluster> clusters = new ArrayList<Cluster>();
	private ArrayList<Phase> phases = new ArrayList<Phase>();

	public TableConstructionWithClusters(final GlobalDataKeeper globalDataKeeper) {
		globalDataKeeper.getAllPlutarchParallelLivesSchemas();
		phases = globalDataKeeper.getPhaseCollectors().get(0).getPhases();
		clusters = globalDataKeeper.getClusterCollectors().get(0).getClusters();
	}

	private String[] constructOneRow(final Cluster cluster, final int clusterAmount) {
		String[] oneRow = new String[columnsNumber];
		int deletedAllTable = 0;
		int pointerCell = 0;
		int updates = 0;
		int deletions = 0;
		int insertions = 0;
		int totalChangesForOnePhase = 0;
		int deadCell = 0;
		oneRow[pointerCell] = "Cluster " + clusterAmount;
		pointerCell = loopKeyTransitions(cluster, pointerCell);
		deadCell = loopKeyClusters(cluster, deadCell);
		final int initialization = makeInitialization(pointerCell);
		loopPhase(deletedAllTable, updates, deletions, insertions, pointerCell, totalChangesForOnePhase, oneRow, initialization, deadCell, cluster);
		return loopOneRow(oneRow);
	}

	private int makeInitialization(int pointerCell) {
		int initialization = 0;
		if (pointerCell > 0) {
			initialization = pointerCell - 1;
		}
		return initialization;
	}

	private int loopKeyClusters(final Cluster cluster, int deadCell) {
		for (int index = 0; index < phases.size(); index++) {
			if (phases.get(index).getPhasePPLTransitions().containsKey(cluster.getDeath() - 1)) {
				deadCell = index + 1;
				break;
			}
		}
		return deadCell;
	}

	private int loopKeyTransitions(final Cluster cluster, int pointerCell) {
		for (int index = 0; index < phases.size(); index++) {
			if (phases.get(index).getPhasePPLTransitions().containsKey(cluster.getBirth())) {
				pointerCell = index + 1;
				break;
			}
		}
		return pointerCell;
	}
	
	private void loopPhase(int deletedAllTable, int updates, int deletions, int insertions, int pointer, int changes,
			final String[] oneRow, final int initialization, final int deadCell, final Cluster cluster) {
		for (int index = initialization; index < phases.size(); index++) {
			if (index < deadCell) {
				final TreeMap<Integer, PlutarchParallelLivesTransition> transitions = phases.get(index).getPhasePPLTransitions();
				checkMaxTransitions(changes);
				changes = 0;
				final Integer[] result = loopTransitions(updates, deletions, insertions, transitions, deletedAllTable, cluster);
				updates = result[0];
				deletions = result[1];
				insertions = result[2];
				if (pointer >= columnsNumber) {
					break;
				}
				changes = calculateTotalChanges(oneRow, pointer, updates, deletions, insertions, -1);
				pointer++;
				if (deletedAllTable == 1) {
					break;
				}
				insertions = 0;
				updates = 0;
				deletions = 0;
			} else {
				break;
			}
		}
	}
	
	private Integer[] loopTransitions(int updates, int deletions, int insertions,
			final TreeMap<Integer, PlutarchParallelLivesTransition> transitions, final int deletedAllTable, final Cluster cluster) {
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> transitionsSet : transitions.entrySet()) {
			ArrayList<TableChange> tableChanges = transitionsSet.getValue().getTableChanges();
			if (tableChanges != null) {
				for (int outerIndex = 0; outerIndex < tableChanges.size(); outerIndex++) {
					final Integer[] result = loopOneTableChange(updates, deletions, insertions, tableChanges, outerIndex, cluster);
					updates = result[0];
					deletions = result[1];
					insertions = result[2];
				}
			}
			if (deletedAllTable == 1) {
				break;
			}
		}
		return new Integer[] {updates, deletions, insertions};
	}
	
	private Integer[] loopOneTableChange(int updates, int deletions, int insertions,
			final ArrayList<TableChange> tableChanges, final int outerIndex, final Cluster cluster) {
		TableChange tableChange = tableChanges.get(outerIndex);
		if (cluster.getTables().containsKey(tableChange.getAffectedTableName())) {
			ArrayList<AtomicChange> atomicChanges = tableChange.getAtomicChangesForOneTransition();
			for (int innerIndex = 0; innerIndex < atomicChanges.size(); innerIndex++) {
				final Integer[] result = loopOneAtomicChange(updates, deletions, insertions, atomicChanges, innerIndex);
				updates = result[0];
				deletions = result[1];
				insertions = result[2];
			}
		}
		return new Integer[] {updates, deletions, insertions};		
	}
			
	
	private Integer[] loopOneAtomicChange(int updates, int deletions, int insertions,
			final ArrayList<AtomicChange> atomicChanges, final int innerIndex) {
		if (atomicChanges.get(innerIndex).getType().contains("Addition")) {
			insertions++;
			if (insertions > maxInsertions) {
				maxInsertions = insertions;
			}
		} else if (atomicChanges.get(innerIndex).getType().contains("Deletion")) {
			deletions++;
			if (deletions > maxDeletions) {
				maxDeletions = deletions;
			}
		} else {
			updates++;
			if (updates > maxUpdates) {
				maxUpdates = updates;
			}
		}
		return new Integer[] {updates, deletions, insertions};
	}

	@Override
	protected int getSchemaColumnSize() {
		return phases.size();
	}

	@Override
	protected void editColumnsList(final ArrayList<String> columnsList) {
		for (int index = 0; index < phases.size(); index++) {
			final String label = "Phase " + index;
			columnsList.add(label);
		}
	}
	
	@Override
	protected void loopMapEntry(final ArrayList<String[]> allRows, final ArrayList<String> allTables) {
		for (int index = 0; index < clusters.size(); index++) {
			String[] tmpOneRow = constructOneRow(clusters.get(index), index);
			allRows.add(tmpOneRow);
			tmpOneRow = new String[columnsNumber];
		}
	}

	@Override
	protected int getSchemaAdditionValue() {
		return 1;
	}

}
