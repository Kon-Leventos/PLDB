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

class TableConstructionAllSquaresIncluded extends AbstractTableConstruction {
	private static TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas = new TreeMap<String, PlutarchParallelLivesSchema>();
	private TreeMap<Integer, PlutarchParallelLivesTransition> allPlutarchParallelLivesTransitions = 
			new TreeMap<Integer, PlutarchParallelLivesTransition>();
	private ArrayList<PlutarchParallelLivesTable> tables = new ArrayList<PlutarchParallelLivesTable>();

	public TableConstructionAllSquaresIncluded(final GlobalDataKeeper globalDataKeeper) {
		allPlutarchParallelLivesSchemas = globalDataKeeper.getAllPlutarchParallelLivesSchemas();
		allPlutarchParallelLivesTransitions = globalDataKeeper.getAllPlutarchParallelLivesTransitions();
	}

	private int augmentOneRow(final String[] oneRow, int pointerCell) {
		oneRow[pointerCell] = "------";
		pointerCell++;
		return pointerCell;
	}

	private Integer[] checkIfContainsAddition(final TableChange tableChange, int insertions, int deletions, int deletedAllTable, int updates,
			final PlutarchParallelLivesTable oneTable, final String schema, final ArrayList<AtomicChange> atomicChanges) {
		for (int k = 0; k < atomicChanges.size(); k++) {
			if (atomicChanges.get(k).getType().contains("Addition")) {
				insertions++;
				if (insertions > maxInsertions) {
					maxInsertions = insertions;
				}
			} else if (atomicChanges.get(k).getType().contains("Deletion")) {
				deletions++;
				if (deletions > maxDeletions) {
					maxDeletions = deletions;
				}
				final int num = getNumOfAttributesOfNextSchema(schema, oneTable.getName());
				if (num == 0) {
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

	private int checkIfSchemaVersion(final int schemaVersion, final String[] oneRow, int pointerCell) {
		if (schemaVersion == 0) {
			pointerCell++;
			oneRow[pointerCell] = "---------";
			pointerCell++;
		} else {
			for (int index = 0; index < schemaColumn.length; index++) {
				if (schemaVersion == schemaColumn[index][0]) {
					pointerCell = schemaColumn[index][1] - 3;
					break;
				}
			}
		}
		return pointerCell;
	}

	private Integer[] checkTransition(final ArrayList<TableChange> transition, int insertions, int deletions, int deletedAllTable,
			int updates, final PlutarchParallelLivesTable oneTable, final String schema) {
		if (transition != null) {
			for (int index = 0; index < transition.size(); index++) {
				final TableChange tableChange = transition.get(index);
				if (tableChange.getAffectedTableName().equals(oneTable.getName())) {
					final ArrayList<AtomicChange> atomicChanges = tableChange.getAtomicChangesForOneTransition();
					final Integer[] results = checkIfContainsAddition(tableChange, insertions, deletions, deletedAllTable, updates, oneTable, schema,
							atomicChanges);
					deletedAllTable = results[0];
					updates = results[1];
					deletions = results[2];
					insertions = results[3];
				}
			}
		}
		return new Integer[] {deletedAllTable, updates, deletions, insertions};
	}

	private void checkTableName(final ArrayList<String> columnsList, final int index, final Map.Entry<String, PlutarchParallelLivesSchema> schemas) {
		if (index < allPlutarchParallelLivesSchemas.size() - 1) {
			final String label = "v" + schemas.getValue().getName().replaceAll(".sql", "");
			columnsList.add(label);
			for (int innerIndex = 0; innerIndex < 3; innerIndex++) {
				switch (innerIndex) {
					case 0:
						columnsList.add("I");
						break;
					case 1:
						columnsList.add("U");
						break;
					case 2:
						columnsList.add("D");
						break;
					default:
						break;
				}
			}
		} else {
			final String label = "v" + schemas.getValue().getName().replaceAll(".sql", "");
			columnsList.add(label);
		}
	}

	private String[] constructOneRow(final PlutarchParallelLivesTable oneTable, final int schemaVersion) {
		final String[] oneRow = new String[columnsNumber];
		int pointerCell = 0;
		oneRow[pointerCell] = oneTable.getName();
		pointerCell = checkIfSchemaVersion(schemaVersion, oneRow, pointerCell);
		int initialization = 0;
		if (schemaVersion > 0) {
			initialization = schemaVersion - 1;
		}
		final Integer[] mapKeys = new Integer[allPlutarchParallelLivesTransitions.size()];
		int pos2 = 0;
		for (Integer key : allPlutarchParallelLivesTransitions.keySet()) {
			mapKeys[pos2++] = key;
		}
		return loopPlutarchParallelLivesTransitions(oneTable, oneRow, pointerCell, initialization, mapKeys);
	}

	private int getNumOfAttributesOfNextSchema(final String schema, final String table) {
		int num = 0;
		final PlutarchParallelLivesSchema thisSchema = allPlutarchParallelLivesSchemas.get(schema);
		for (int index = 0; index < thisSchema.getTables().size(); index++) {
			if (thisSchema.getTableAt(index).getName().equals(table)) {
				num = thisSchema.getTableAt(index).getAttributes().size();
				return num;
			}
		}
		return num;
	}

	private String[] loopPlutarchParallelLivesTransitions(final PlutarchParallelLivesTable oneTable, final String[] oneRow,	int pointerCell, 
			final int initialization, final Integer[] mapKeys) {
		int deletedAllTable = 0;
		for (int index = initialization; index < allPlutarchParallelLivesTransitions.size(); index++) {
			final Integer position = mapKeys[index];
			final PlutarchParallelLivesTransition transitions = allPlutarchParallelLivesTransitions.get(position);
			final String versionName = transitions.getNewVersionName();
			final ArrayList<TableChange> specificTransition = transitions.getTableChanges();
			int updates = 0;
			int deletions = 0;
			int insertions = 0;
			final Integer[] results = checkTransition(specificTransition, insertions, deletions, deletedAllTable, updates, oneTable, versionName);
			deletedAllTable = results[0];
			updates = results[1];
			deletions = results[2];
			insertions = results[3];
			if (pointerCell >= columnsNumber) {
				break;
			}
			pointerCell = setOneRow(oneRow, pointerCell, updates, deletions, insertions);
			if (deletedAllTable == 1) {
				break;
			}
			pointerCell = augmentOneRow(oneRow, pointerCell);
		}
		return loopOneRow(oneRow);
	}

	private int setOneRow(final String[] oneRow, int pointerCell, int updates, int deletion, int insertion) {
		oneRow[pointerCell] = Integer.toString(insertion);
		pointerCell++;
		oneRow[pointerCell] = Integer.toString(updates);
		pointerCell++;
		oneRow[pointerCell] = Integer.toString(deletion);
		pointerCell++;
		return pointerCell;
	}

	private int setSchemaRows(final ArrayList<String[]> allRows, final ArrayList<String> allTables, int found, final int index,
			final PlutarchParallelLivesSchema oneSchema) {
		for (int j = 0; j < oneSchema.getTables().size(); j++) {
			PlutarchParallelLivesTable oneTable = oneSchema.getTableAt(j);
			final String tmpTableName = oneTable.getName();
			for (int k = 0; k < allTables.size(); k++) {
				if (!tmpTableName.equals(allTables.get(k))) {
					found = 0;
				} else {
					found = 1;
					break;
				}
			}
			if (found == 0) {
				allTables.add(tmpTableName);
				tables.add(oneTable);
				String[] tmpOneRow = constructOneRow(oneTable, index);
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
	protected int getSchemaColumnSize() {
		return allPlutarchParallelLivesSchemas.size();
	}

	@Override
	protected void editColumnsList(final ArrayList<String> columnsList) {
		int index = 0;
		for (Map.Entry<String, PlutarchParallelLivesSchema> schemas : allPlutarchParallelLivesSchemas.entrySet()) {
			checkTableName(columnsList, index, schemas);
			index++;
		}
	}
	
	@Override
	protected void loopMapEntry(final ArrayList<String[]> allRows, final ArrayList<String> allTables) {
		int found = 0;
		int index = 0;
		for (Map.Entry<String, PlutarchParallelLivesSchema> schemas : allPlutarchParallelLivesSchemas.entrySet()) {
			final PlutarchParallelLivesSchema oneSchema = schemas.getValue();
			found = setSchemaRows(allRows, allTables, found, index, oneSchema);
			index++;
		}
	}

	@Override
	protected int getSchemaAdditionValue() {
		return 4;
	}

}
