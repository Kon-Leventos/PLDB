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

class TableConstructionZoomArea extends AbstractTableConstruction {
	private static TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas = 
			new TreeMap<String, PlutarchParallelLivesSchema>();
	private static TreeMap<String, PlutarchParallelLivesSchema> selectedPlutarchParallelLivesSchemas = 
			new TreeMap<String, PlutarchParallelLivesSchema>();
	private GlobalDataKeeper globalDataKeeper = new GlobalDataKeeper();
	private TreeMap<Integer, PlutarchParallelLivesTransition> transitions = new TreeMap<Integer, PlutarchParallelLivesTransition>();
	private int selectedColumn;
	private TreeMap<String, PlutarchParallelLivesTable> selectedTables = new TreeMap<String, PlutarchParallelLivesTable>();
	private ArrayList<String> selectedTablesNames = new ArrayList<String>();
	private ArrayList<PlutarchParallelLivesTable> tables = new ArrayList<PlutarchParallelLivesTable>();

	public TableConstructionZoomArea(final GlobalDataKeeper globalDataKeeper, final ArrayList<String> selectedTablesNames, final int selectedColumn) {
		this.globalDataKeeper = globalDataKeeper;
		allPlutarchParallelLivesSchemas = globalDataKeeper.getAllPlutarchParallelLivesSchemas();
		this.selectedTablesNames = selectedTablesNames;
		this.selectedColumn = selectedColumn;
		fillSelectedTransitions();
		fillSelectedSchemas();
		fillSelectedTables();
	}

	private String[] constructOneRow(final PlutarchParallelLivesTable oneTable) {
		final String[] oneRow = new String[columnsNumber];
		int pointerCell = 0;
		oneRow[pointerCell] = oneTable.getName();
		boolean exists = false;
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> transitions : transitions.entrySet()) {
			pointerCell++;
			exists = findExists(oneTable, exists, transitions);
			if (exists) {
				break;
			}
		}
		final int initialization = pointerCell - 1;
		final Integer[] mapKeys = new Integer[transitions.size()];
		formMapKeys(mapKeys);
		int deletedAllTable = 0;
		int updates = 0;
		int deletions = 0;
		int insertions = 0;
		int reborn = 1;
		int totalChangesForOneTransition = -1;
		loopTransitionsWhenTheyExist(oneTable, oneRow, deletedAllTable, pointerCell, updates, deletions, insertions, totalChangesForOneTransition,
				reborn, exists, initialization, mapKeys);
		return loopOneRow(oneRow);
	}

	private boolean findExists(final PlutarchParallelLivesTable oneTable, boolean exists,
			final Map.Entry<Integer, PlutarchParallelLivesTransition> transitions) {
		final PlutarchParallelLivesSchema oldSchema = allPlutarchParallelLivesSchemas.get(transitions.getValue().getOldVersionName());
		final PlutarchParallelLivesSchema newSchema = allPlutarchParallelLivesSchemas.get(transitions.getValue().getNewVersionName());
		if (oldSchema.getTables().containsKey(oneTable.getName()) || newSchema.getTables().containsKey(oneTable.getName())) {
			exists = true;
		}
		return exists;
	}

	private void formMapKeys(final Integer[] mapKeys) {
		int position = 0;
		for (Integer key : transitions.keySet()) {
			mapKeys[position++] = key;
		}
	}

	private void loopTransitionsWhenTheyExist(final PlutarchParallelLivesTable oneTable, final String[] oneRow, int deletedAllTable, int pointerCell,
			int updates, int deletions, int insertions, int totalChangesForOneTransition, int reborn, final boolean exists, final int initialization,
			final Integer[] mapKeys) {
		if (exists) {
			for (int index = initialization; index < transitions.size(); index++) {
				final Integer[] result = loopOneTransition(deletedAllTable, updates, deletions, insertions, reborn, totalChangesForOneTransition,
						mapKeys, index , oneTable);
				deletedAllTable = result[0];
				updates = result[1];
				deletions = result[2];
				insertions = result[3];
				reborn = result [4];
				totalChangesForOneTransition = result[5];
				if (pointerCell >= columnsNumber) {
					break;
				}
				totalChangesForOneTransition = calculateTotalChanges(oneRow, pointerCell, updates, deletions, insertions, reborn);
				pointerCell++;
				if (deletedAllTable == 1) {
					if (pointerCell >= columnsNumber) {
						break;
					}
					pointerCell = isNotReborn(oneRow, pointerCell, reborn);
					reborn = 0;
				}
				checkMaxTransitions(totalChangesForOneTransition);
				insertions = 0;
				updates = 0;
				deletions = 0;
			}
		}
	}
	
	private Integer[] loopOneTransition(int deletedAllTable, int updates, int deletions, int insertions, int reborn, int changes,
			final Integer[] mapKeys, final int index, final PlutarchParallelLivesTable oneTable) {
		Integer pointer = mapKeys[index];
		final PlutarchParallelLivesTransition transition = transitions.get(pointer);
		final String versionName = transition.getNewVersionName();
		updates = 0;
		deletions = 0;
		insertions = 0;
		final ArrayList<TableChange> tableChanges = transition.getTableChanges();
		if (tableChanges != null) {
			changes = -1;
			for (int outerIndex = 0; outerIndex < tableChanges.size(); outerIndex++) {
				final Integer[] result = loopOneTableChange(deletedAllTable, updates, deletions, insertions, reborn, tableChanges,
						outerIndex, versionName, oneTable);
				deletedAllTable = result[0];
				updates = result[1];
				deletions = result[2];
				insertions = result[3];
				reborn = result [4];
			}
		}
		return new Integer[] {deletedAllTable, updates, deletions, insertions, reborn, changes};		
	}

	private Integer[] loopOneTableChange(int deletedAllTable, int updates, int deletions, int insertions, int reborn,
			final ArrayList<TableChange> tableChanges, final int outerIndex, final String versionName, final PlutarchParallelLivesTable oneTable) {
		final TableChange tableChange = tableChanges.get(outerIndex);
		if (tableChange.getAffectedTableName().equals(oneTable.getName())) {
			if (deletedAllTable == 1) {
				reborn = 1;
			}
			deletedAllTable = 0;
			final ArrayList<AtomicChange> atomicChanges = tableChange.getAtomicChangesForOneTransition();
			for (int innerIndex = 0; innerIndex < atomicChanges.size(); innerIndex++) {
				final Integer[] result = loopOneAtomicChange(deletedAllTable, updates, deletions, insertions, atomicChanges,
						innerIndex, versionName, oneTable);
				deletedAllTable = result[0];
				updates = result[1];
				deletions = result[2];
				insertions = result[3];
			}
		}
		return new Integer[] {deletedAllTable, updates, deletions, insertions, reborn};
	}
	
	private Integer[] loopOneAtomicChange(int deletedAllTable, int updates, int deletions, int insertions,
			final ArrayList<AtomicChange> atomicChanges, final int innerIndex, final String versionName, final PlutarchParallelLivesTable oneTable) {
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
			int num = getNumOfAttributesOfNextSchema(versionName, oneTable.getName());
			if (num == 0) {
				deletedAllTable = 1;
			}
		} else {
			updates++;
			if (updates > maxUpdates) {
				maxUpdates = updates;
			}
		}
		return new Integer[] {deletedAllTable, updates, deletions, insertions};
	}

	private void fillSelectedSchemas() {
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> transition : transitions.entrySet()) {
			selectedPlutarchParallelLivesSchemas.put(transition.getValue().getOldVersionName(),
					allPlutarchParallelLivesSchemas.get(transition.getValue().getOldVersionName()));
		}
	}

	private void fillSelectedTransitions() {
		if (selectedColumn == 0) {
			transitions = globalDataKeeper.getAllPlutarchParallelLivesTransitions();
		} else {
			transitions = globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(selectedColumn - 1).getPhasePPLTransitions();
		}
	}

	private void fillSelectedTables() {
		for (int index = 0; index < selectedTablesNames.size(); index++) {
			selectedTables.put(selectedTablesNames.get(index),
					this.globalDataKeeper.getAllPlutarchParallelLivesTables().get(selectedTablesNames.get(index)));
		}
	}

	private int getNumOfAttributesOfNextSchema(final String schemaName, final String table) {
		int num = 0;
		final PlutarchParallelLivesSchema schema = allPlutarchParallelLivesSchemas.get(schemaName);
		for (int index = 0; index < schema.getTables().size(); index++) {
			if (schema.getTableAt(index).getName().equals(table)) {
				num = schema.getTableAt(index).getAttributes().size();
				return num;
			}
		}
		return num;
	}

	@Override
	protected int getSchemaColumnSize() {
		return transitions.size();
	}

	@Override
	protected void editColumnsList(final ArrayList<String> columnsList) {
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> transition : transitions.entrySet()) {
			final String label = Integer.toString(transition.getKey());
			columnsList.add(label);
		}
	}
	
	@Override
	protected void loopMapEntry(final ArrayList<String[]> allRows, final ArrayList<String> allTables) {
		int found = 0;
		for (Map.Entry<String, PlutarchParallelLivesTable> oneTable : selectedTables.entrySet()) {
			final String tableName = oneTable.getKey();
			for (int index = 0; index < allTables.size(); index++) {
				if (!tableName.equals(allTables.get(index))) {
					found = 0;
				} else {
					found = 1;
					break;
				}
			}
			if (found == 0) {
				allTables.add(tableName);
				tables.add(oneTable.getValue());
				String[] oneRow = constructOneRow(oneTable.getValue());
				allRows.add(oneRow);
				oneRow = new String[columnsNumber];
			} else {
				found = 0;
			}
		}
	}

	@Override
	protected int getSchemaAdditionValue() {
		return 1;
	}


}
