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

class TableConstructionInsertDeleteUpdate extends AbstractTableConstruction {
	private static TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas = new TreeMap<String, PlutarchParallelLivesSchema>();
	private TreeMap<Integer, PlutarchParallelLivesTransition> allPlutarchParallelLivesTransitions =
			new TreeMap<Integer, PlutarchParallelLivesTransition>();
	private ArrayList<PlutarchParallelLivesTable> tables = new ArrayList<PlutarchParallelLivesTable>();

	public TableConstructionInsertDeleteUpdate(final GlobalDataKeeper globalDataKeeper) {
		allPlutarchParallelLivesSchemas = globalDataKeeper.getAllPlutarchParallelLivesSchemas();
		allPlutarchParallelLivesTransitions = globalDataKeeper.getAllPlutarchParallelLivesTransitions();
	}

	private String[] constructOneRow(final PlutarchParallelLivesTable oneTable, final int schemaVersion) {
		final String[] oneRow = new String[columnsNumber];
		int pointerCell = 0;
		oneRow[pointerCell] = oneTable.getName();
		pointerCell = checkIfSchemaVersion(schemaVersion, pointerCell);
		final int initialization = findInitialization(schemaVersion);
		final Integer[] mapKeys = loopTransitionKeys();
		loopTransitions(oneTable, oneRow, pointerCell, initialization, mapKeys);
		return loopOneRow(oneRow);
	}

	private void loopTransitions(final PlutarchParallelLivesTable oneTable, final String[] oneRow, int pointerCell, final int initialization,
			final Integer[] mapKeys) {
		int deletedAllTable = 0;
		int updates = 0;
		int deletions = 0;
		int insertions = 0;
		int reborn = 1;
		int totalChangesForOneTransition = 0;
		for (int index = initialization; index < allPlutarchParallelLivesTransitions.size(); index++) {
			final Integer[] result = loopOneTransition(deletedAllTable, updates, deletions, insertions, reborn, totalChangesForOneTransition, 
					mapKeys, index, oneTable);
			deletedAllTable = result[0];
			updates = result[1];
			deletions = result[2];
			insertions = result[3];
			reborn = result[4];
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
	
	private Integer[] loopOneTransition(int deletedAllTable, int updates, int deletions, int insertions, int reborn, int changes, 
			final Integer[] mapKeys, final int index, final PlutarchParallelLivesTable oneTable) {
		final Integer position = mapKeys[index];
		final PlutarchParallelLivesTransition transition = allPlutarchParallelLivesTransitions.get(position);
		final String versionName = transition.getNewVersionName();
		updates = 0;
		deletions = 0;
		insertions = 0;
		final ArrayList<TableChange> tableChanges = transition.getTableChanges();
		if (tableChanges != null) {
			changes = -1;
			final Integer[] result = loopTableChanges(deletedAllTable, updates, deletions, insertions, reborn, tableChanges, versionName, oneTable);
			deletedAllTable = result[0];
			updates = result[1];
			deletions = result[2];
			insertions = result[3];
			reborn = result[4];
		}
		return new Integer[] {deletedAllTable, updates, deletions, insertions, reborn, changes};
	}
	
	private Integer[] loopTableChanges(int deletedAllTable, int updates, int deletions, int insertions, int reborn, 
			final ArrayList<TableChange> tableChanges, final String versionName, final PlutarchParallelLivesTable oneTable) {
		for (int outerIndex = 0; outerIndex < tableChanges.size(); outerIndex++) {
			final TableChange tableChange = tableChanges.get(outerIndex);
			if (tableChange.getAffectedTableName().equals(oneTable.getName())) {
				if (deletedAllTable == 1) {
					reborn = 1;
				}
				deletedAllTable = 0;
				final Integer[] results = loopTableChanges(deletedAllTable, updates, deletions, insertions, tableChange, versionName, oneTable);
				deletedAllTable = results[0];
				updates = results[1];
				deletions = results[2];
				insertions = results[3];
			}
		}
		return new Integer[] {deletedAllTable, updates, deletions, insertions, reborn};
	}
	
	private Integer[] loopTableChanges(int deletedAllTable, int updates, int deletions, int insertions,
			final TableChange tableChange, final String versionName, final PlutarchParallelLivesTable oneTable) {
		final ArrayList<AtomicChange> atomicChanges = tableChange.getAtomicChangesForOneTransition();
		for (int innerIndex = 0; innerIndex < atomicChanges.size(); innerIndex++) {
			Integer[] result = loopOneTableChange(deletedAllTable, updates, deletions, insertions, innerIndex, atomicChanges, versionName, oneTable);
			deletedAllTable = result[0];
			updates = result[1];
			deletions = result[2];
			insertions = result[3];
		}
		return new Integer[] {deletedAllTable, updates, deletions, insertions};
	}
	
	private Integer[] loopOneTableChange(int deletedAllTable, int updates, int deletions, int insertions, final int innerIndex,
			final ArrayList<AtomicChange> atomicChanges, final String versionName, final PlutarchParallelLivesTable oneTable) {
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
			final int attributeAmount = getNumOfAttributesOfNextSchema(versionName, oneTable.getName());
			if (attributeAmount == 0) {
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

	private int findInitialization(final int schemaVersion) {
		int initialization = 0;
		if (schemaVersion > 0) {
			initialization = schemaVersion;
		}
		return initialization;
	}

	private Integer[] loopTransitionKeys() {
		final Integer[] mapKeys = new Integer[allPlutarchParallelLivesTransitions.size()];
		int positionNumber = 0;
		for (Integer key : allPlutarchParallelLivesTransitions.keySet()) {
			mapKeys[positionNumber++] = key;
		}
		return mapKeys;
	}

	private int checkIfSchemaVersion(final int schemaVersion, int pointerCell) {
		if (schemaVersion == -1) {
			pointerCell++;
		} else {
			for (int index = 0; index < schemaColumn.length; index++) {
				if (schemaVersion == schemaColumn[index][0]) {
					pointerCell = schemaColumn[index][1];
					break;
				}
			}
		}
		return pointerCell;
	}

	private int getNumOfAttributesOfNextSchema(final String schemaName, final String table) {
		int attributeAmount = 0;
		final PlutarchParallelLivesSchema schema = allPlutarchParallelLivesSchemas.get(schemaName);
		for (int index = 0; index < schema.getTables().size(); index++) {
			if (schema.getTableAt(index).getName().equals(table)) {
				attributeAmount = schema.getTableAt(index).getAttributes().size();
				return attributeAmount;
			}
		}
		return attributeAmount;
	}

	@Override
	protected int getSchemaColumnSize() {
		return allPlutarchParallelLivesTransitions.size();
	}

	@Override
	protected void editColumnsList(final ArrayList<String> columnsList) {
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> transitions : allPlutarchParallelLivesTransitions.entrySet()) {
			final String label = Integer.toString(transitions.getKey());
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
			if (found == 0) {
				allTables.add(tableName);
				tables.add(oneTable);
				String[] tmpOneRow = constructOneRow(oneTable, index - 1);
				allRows.add(tmpOneRow);
				oneTable = new PlutarchParallelLivesTable();
				tmpOneRow = new String[columnsNumber];
			} else {
				found = 0;
			}
		}
		return found;
	}

	@Override
	protected int getSchemaAdditionValue() {
		return 1;
	}

}
