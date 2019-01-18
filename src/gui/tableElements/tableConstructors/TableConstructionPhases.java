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

class TableConstructionPhases extends AbstractTableConstruction {
	private static TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas = new TreeMap<String, PlutarchParallelLivesSchema>();
	private ArrayList<Phase> phases = new ArrayList<Phase>();
	private ArrayList<PlutarchParallelLivesTable> tables = new ArrayList<PlutarchParallelLivesTable>();

	public TableConstructionPhases(final GlobalDataKeeper globalDataKeeper) {
		allPlutarchParallelLivesSchemas = globalDataKeeper.getAllPlutarchParallelLivesSchemas();
		phases = globalDataKeeper.getPhaseCollectors().get(0).getPhases();
	}

	private String[] constructOneRow(final PlutarchParallelLivesTable oneTable, final int schemaVersion, final String schemaName) {
		final String[] oneRow = new String[columnsNumber];
		int deletedAllTable = 0;
		int pointerCell = 0;
		int updates = 0;
		int deletions = 0;
		int insertions = 0;
		int totalChangesForOnePhase = 0;
		int reborn = 1;
		oneRow[pointerCell] = oneTable.getName();
		pointerCell = checkIfSchemaVersion(schemaVersion, schemaName, pointerCell);
		int initialization = makeInitialization(pointerCell);
		loopPhases(deletedAllTable, updates, deletions, insertions, reborn, totalChangesForOnePhase, pointerCell,
				oneTable, initialization, oneRow);
		return loopOneRow(oneRow);
	}
	
	private void loopPhases(int deletedAllTable, int updates, int deletions, int insertions, int reborn, int changes, int pointer,
			final PlutarchParallelLivesTable oneTable, final int initialization, final String[] oneRow) {
		for (int index = initialization; index < phases.size(); index++) {
			final Integer[] result = loopOnePhase(deletedAllTable, updates, deletions, insertions, reborn, changes, oneTable, index);
			deletedAllTable = result[0];
			updates = result [1];
			deletions = result[2];
			insertions = result[3];
			reborn = result[4];
			changes = result[5];
			if (pointer >= columnsNumber) {
				break;
			}
			changes = calculateTotalChanges(oneRow, pointer, updates, deletions, insertions, reborn);
			pointer++;
			if (deletedAllTable == 1) {
				if (pointer >= columnsNumber) {
					break;
				}
				pointer = isNotReborn(oneRow, pointer, reborn);
				reborn = 0;
			}
			insertions = 0;
			updates = 0;
			deletions = 0;
		}
	}

	private int checkIfSchemaVersion(final int schemaVersion, final String schemaName, int pointerCell) {
		if (schemaVersion == 0) {
			pointerCell++;
		} else {
			for (int index = 0; index < phases.size(); index++) {
				final TreeMap<Integer, PlutarchParallelLivesTransition> transitions = phases.get(index).getPhasePPLTransitions();
				for (Map.Entry<Integer, PlutarchParallelLivesTransition> transition : transitions.entrySet()) {
					if (transition.getValue().getNewVersionName().equals(schemaName)) {
						pointerCell = index + 1;
						break;
					}
				}
			}
		}
		return pointerCell;
	}

	private int makeInitialization(int pointerCell) {
		int initialization = 0;
		if (pointerCell > 0) {
			initialization = pointerCell - 1;
		}
		return initialization;
	}
	
	private Integer[] loopOnePhase(int deletedAllTable, int updates, int deletions, int insertions, int reborn, int changes,
			final PlutarchParallelLivesTable oneTable, final int index) {
		final TreeMap<Integer, PlutarchParallelLivesTransition> transitions = phases.get(index).getPhasePPLTransitions();
		if (changes > maxTransitions) {
			maxTransitions = changes;
		}
		changes = 0;
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> transition : transitions.entrySet()) {
			final String versionName = transition.getValue().getNewVersionName();
			final ArrayList<TableChange> tableChanges = transition.getValue().getTableChanges();
			if (tableChanges != null) {
				for (int outerIndex = 0; outerIndex < tableChanges.size(); outerIndex++) {
					Integer[] result = loopOneTableChange(deletedAllTable, updates, deletions, insertions, reborn, tableChanges, outerIndex,
							versionName, oneTable);
					deletedAllTable = result[0];
					updates = result [1];
					deletions = result[2];
					insertions = result[3];
					reborn = result[4];
				}
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
				updates = result [1];
				deletions = result[2];
				insertions = result[3];
			}
		}
		return new Integer[] {deletedAllTable, updates, deletions, insertions, reborn};
	}
	
	private Integer[] loopOneAtomicChange(int deletedAllTable, int updates, int deletions, int insertions, 
			final ArrayList<AtomicChange> atomicChanges, final int innerIndex, final String versionName, final PlutarchParallelLivesTable oneTable) {
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
		return new Integer[] {deletedAllTable, updates, deletions, insertions};
	}

	private boolean getNumOfAttributesOfNextSchema(String schemaName, String table) {
		PlutarchParallelLivesSchema schema = allPlutarchParallelLivesSchemas.get(schemaName);
		return schema.getTables().containsKey(table);
	}

	private int loopOneSchema(ArrayList<String[]> allRows, ArrayList<String> allTables, int found, int i, PlutarchParallelLivesSchema oneSchema) {
		for (int j = 0; j < oneSchema.getTables().size(); j++) {
			PlutarchParallelLivesTable oneTable = oneSchema.getTableAt(j);
			String tmpTableName = oneTable.getName();
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
				String[] tmpOneRow = constructOneRow(oneTable, i, oneSchema.getName());
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
			PlutarchParallelLivesSchema oneSchema = schema.getValue();
			found = loopOneSchema(allRows, allTables, found, index, oneSchema);
			index++;
		}
	}

	@Override
	protected int getSchemaAdditionValue() {
		return 1;
	}

}
