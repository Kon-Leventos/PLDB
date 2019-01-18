package data.dataProccessing;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesSchema;
import data.dataPPL.pplTransition.AtomicChange;
import data.dataPPL.pplTransition.PlutarchParallelLivesTransition;
import data.dataPPL.pplTransition.TableChange;

public class PlutarchParallelLivesTransitionConstruction {
	private static TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas = new TreeMap<String, PlutarchParallelLivesSchema>();
	private static TreeMap<Integer, PlutarchParallelLivesTransition> allPlutarchParallelLivesTransitions;
	private static TreeMap<String, TableChange> allTableChanges = new TreeMap<String, TableChange>();

	public PlutarchParallelLivesTransitionConstruction(final TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas,
			final TreeMap<String, TableChange> allTableChanges) {
		allPlutarchParallelLivesTransitions = new TreeMap<Integer, PlutarchParallelLivesTransition>();
		PlutarchParallelLivesTransitionConstruction.allPlutarchParallelLivesSchemas = allPlutarchParallelLivesSchemas;
		PlutarchParallelLivesTransitionConstruction.allTableChanges = allTableChanges;
	}

	private ArrayList<TableChange> calculateTableChanges(ArrayList<TableChange> tableChanges) {
		for (Map.Entry<Integer, PlutarchParallelLivesTransition> transition : allPlutarchParallelLivesTransitions.entrySet()) {
			for (Map.Entry<String, TableChange> table : allTableChanges.entrySet()) {
				final TableChange tableChange = table.getValue();
				final TreeMap<Integer, ArrayList<AtomicChange>> atomicChanges = tableChange.getTableAtomicChanges();
				iterateOverAtomicChanges(tableChanges, transition, table, tableChange, atomicChanges);
			}
			transition.getValue().setTableChanges(tableChanges);
			tableChanges = new ArrayList<TableChange>();
		}
		return tableChanges;
	}

	public TreeMap<Integer, PlutarchParallelLivesTransition> getAllPlutarchParallelLivesTransitions() {
		return allPlutarchParallelLivesTransitions;
	}

	private void iterateOverAtomicChanges(final ArrayList<TableChange> tableChanges,
			final Map.Entry<Integer, PlutarchParallelLivesTransition> transition, final Map.Entry<String, TableChange> table,
			final TableChange tableChange, final TreeMap<Integer, ArrayList<AtomicChange>> atomicChanges) {
		for (Map.Entry<Integer, ArrayList<AtomicChange>> atomicChange : atomicChanges.entrySet()) {
			if (atomicChange.getKey().equals(transition.getKey())) {
				final TableChange newTableChange = new TableChange(table.getKey(),
						tableChange.getAtomicChangeForSpecificTransition(transition.getKey()));
				tableChanges.add(newTableChange);
			}
		}
	}

	public void makePlutarchParallelLivesTransitions() {
		allPlutarchParallelLivesTransitions = new TreeMap<Integer, PlutarchParallelLivesTransition>();
		ArrayList<TableChange> tableChanges = new ArrayList<TableChange>();
		final Set<String> schemaKeys = allPlutarchParallelLivesSchemas.keySet();
		final ArrayList<String> assistantKeys = new ArrayList<String>();
		for (String keys : schemaKeys) {
			assistantKeys.add(keys);
		}
		for (int index = 0; index < assistantKeys.size() - 1; index++) {
			final PlutarchParallelLivesTransition transition = new PlutarchParallelLivesTransition(assistantKeys.get(index),
					assistantKeys.get(index + 1), index);
			allPlutarchParallelLivesTransitions.put(index, transition);
		}
		tableChanges = calculateTableChanges(tableChanges);
	}

}
