package gui.tableElements.tableConstructors;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import data.dataKeeper.GlobalDataKeeper;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesSchema;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesTable;
import data.dataPPL.pplTransition.AtomicChange;
import data.dataPPL.pplTransition.PlutarchParallelLivesTransition;
import data.dataPPL.pplTransition.TableChange;
import phaseAnalyzer.commons.Phase;

class TableConstructionClusterTablesPhasesZoomArea extends AbstractTableConstruction {
	private static TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas = new TreeMap<String, PlutarchParallelLivesSchema>();
	private ArrayList<Phase> phases = new ArrayList<Phase>();
	private ArrayList<PlutarchParallelLivesTable> tables = new ArrayList<PlutarchParallelLivesTable>();
	private ArrayList<String> tablesOfCluster = new ArrayList<String>();

	public TableConstructionClusterTablesPhasesZoomArea(final GlobalDataKeeper globalDataKeeper, final ArrayList<String> tablesOfCluster) {
		allPlutarchParallelLivesSchemas = globalDataKeeper.getAllPlutarchParallelLivesSchemas();
		phases = globalDataKeeper.getPhaseCollectors().get(0).getPhases();
		this.tablesOfCluster = tablesOfCluster;
	}

	private String[] constructOneRow(PlutarchParallelLivesTable oneTable, int schemaVersion, String schemaName) {
		final String[] oneRow = new String[columnsNumber];
		int pointerCell = 0;
		oneRow[pointerCell] = oneTable.getName();
		pointerCell = checkIfSchemaVersion(schemaVersion, schemaName, pointerCell);
		int initialization = 0;
		if (pointerCell > 0) {
			initialization = pointerCell - 1;
		}
		loopPhases(oneTable, oneRow, pointerCell, initialization);
		return loopOneRow(oneRow);
	}

	private void loopPhases(PlutarchParallelLivesTable oneTable, String[] oneRow, int pointerCell, int initialization) {
		int deletedAllTable = 0;
		int updates = 0;
		int deletions = 0;
		int insertions = 0;
		int reborn = 1;
		int totalChangesForOnePhase = 0;
		for (int index = initialization; index < phases.size(); index++) {
			final TreeMap<Integer, PlutarchParallelLivesTransition> transitions = makeTreeMap(totalChangesForOnePhase, index);
			totalChangesForOnePhase = 0;
			final Integer[] results = loopTransitions(deletedAllTable, updates, deletions, insertions, reborn, transitions, oneTable);
			deletedAllTable = results[0];
			updates = results[1];
			deletions = results[2];
			insertions = results[3];
			reborn = results[4];
			if (pointerCell >= columnsNumber) {
				break;
			}
			totalChangesForOnePhase = calculateTotalChanges(oneRow, pointerCell, updates, deletions, insertions, reborn);
			pointerCell++;
			if (deletedAllTable == 1) {
				if (pointerCell >= columnsNumber) {
					break;
				}
				pointerCell = isNotReborn(oneRow, pointerCell, reborn);
				reborn = 0;
			}
			insertions = 0;
			updates = 0;
			deletions = 0;
		}
	}

	private TreeMap<Integer, PlutarchParallelLivesTransition> makeTreeMap(final int totalChangesForOnePhase, final int index) {
		final TreeMap<Integer, PlutarchParallelLivesTransition> transitions = phases.get(index).getPhasePPLTransitions();
		if (totalChangesForOnePhase > maxTransitions) {
			maxTransitions = totalChangesForOnePhase;
		}
		return transitions;
	}
	
	private Integer[] loopTransitions(int deletedAllTable, int updates, int deletions, int insertions, int reborn,
			final TreeMap<Integer, PlutarchParallelLivesTransition> transitions, final PlutarchParallelLivesTable oneTable) {
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> transition : transitions.entrySet()) {
			final String versionName = transition.getValue().getNewVersionName();
			final ArrayList<TableChange> tableChanges = transition.getValue().getTableChanges();
			if (tableChanges != null) {
				for (int outerIndex = 0; outerIndex < tableChanges.size(); outerIndex++) {
					final TableChange tableChange = tableChanges.get(outerIndex);
					if (tableChange.getAffectedTableName().equals(oneTable.getName())) {
						if (deletedAllTable == 1) {
							reborn = 1;
						}
						deletedAllTable = 0;
						Integer[] results = loopTableChanges(deletedAllTable, updates, deletions, insertions, tableChange, versionName, oneTable);
						deletedAllTable = results[0];
						updates = results[1];
						deletions = results[2];
						insertions = results[3];
					}
				}
			}
		}
		return new Integer[] {deletedAllTable, updates, deletions, insertions, reborn};
	}
	
	private Integer[] loopTableChanges(int deletedAllTable, int updates, int deletions, int insertions, final TableChange tableChange,
			final String versionName, final PlutarchParallelLivesTable oneTable) {
		final ArrayList<AtomicChange> atomicChanges = tableChange.getAtomicChangesForOneTransition();
		for (int innerIndex = 0; innerIndex < atomicChanges.size(); innerIndex++) {
			if (atomicChanges.get(innerIndex).getType().contains("Addition")) {
				deletedAllTable = 0;
				insertions++;
				if (insertions > maxInsertions) {
					maxInsertions = insertions;
				}
			} else if (atomicChanges.get(innerIndex).getType().contains("Deletion")) {
				deletions++;
				if (deletions > maxDeletions) {
					maxDeletions = deletions;
				}
				final boolean existsLater = getNumOfAttributesOfNextSchema(versionName, oneTable.getName());
				if (!existsLater) {
					deletedAllTable = 1;
				}
			} else {
				updates++;
				if (updates > maxUpdates) {
					maxUpdates = updates;
				}
			}
		}
		return new Integer[] {deletedAllTable, updates, deletions, insertions};
	}

	private int checkIfSchemaVersion(final int schemaVersion, final String schemaName, int pointerCell) {
		if (schemaVersion == 0) {
			pointerCell++;
		} else {
			for (int index = 0; index < phases.size(); index++) {
				final TreeMap<Integer, PlutarchParallelLivesTransition> phasePPLTransitions = phases.get(index).getPhasePPLTransitions();
				for (Map.Entry<Integer, PlutarchParallelLivesTransition> transition : phasePPLTransitions.entrySet()) {
					if (transition.getValue().getNewVersionName().equals(schemaName)) {
						pointerCell = index + 1;
						break;
					}
				}
			}
		}
		return pointerCell;
	}

	private boolean getNumOfAttributesOfNextSchema(final String schemaName, final String table) {
		final PlutarchParallelLivesSchema schema = allPlutarchParallelLivesSchemas.get(schemaName);
		return schema.getTables().containsKey(table);
	}

	private int loopOneSchema(final ArrayList<String[]> allRows, final ArrayList<String> allTables, int found, final int index,
			final PlutarchParallelLivesSchema oneSchema) {
		for (int outerIndex = 0; outerIndex < oneSchema.getTables().size(); outerIndex++) {
			PlutarchParallelLivesTable oneTable = oneSchema.getTableAt(outerIndex);
			final String tableName = oneTable.getName();
			for (int innerIndex = 0; innerIndex < allTables.size(); innerIndex++) {
				if (!tableName.equals(allTables.get(innerIndex))) {
					found = 0;
				} else {
					found = 1;
					break;
				}
			}
			if (found == 0 && tablesOfCluster.contains(tableName)) {
				allTables.add(tableName);
				tables.add(oneTable);
				String[] oneRow = constructOneRow(oneTable, index, oneSchema.getName());
				allRows.add(oneRow);
				oneTable = new PlutarchParallelLivesTable();
				oneRow = new String[columnsNumber];
			} else {
				found = 0;
			}
		}
		return found;
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
		int found = 0;
		int index = 0;
		for (Map.Entry<String, PlutarchParallelLivesSchema> schema : allPlutarchParallelLivesSchemas.entrySet()) {
			final PlutarchParallelLivesSchema oneSchema = schema.getValue();
			found = loopOneSchema(allRows, allTables, found, index, oneSchema);
			index++;
		}
	}

	@Override
	protected int getSchemaAdditionValue() {
		return 1;
	}


}
