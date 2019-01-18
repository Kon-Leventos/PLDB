package data.dataProccessing;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesTable;
import data.dataPPL.pplTransition.AtomicChange;
import data.dataPPL.pplTransition.TableChange;

public class TableChangeConstruction {
	private static TreeMap<String, TableChange> allTableChanges;
	private static TreeMap<String, PlutarchParallelLivesTable> allTables = new TreeMap<String, PlutarchParallelLivesTable>();
	private static ArrayList<AtomicChange> atomicChanges = new ArrayList<AtomicChange>();

	public TableChangeConstruction(final ArrayList<AtomicChange> atomicChanges, final TreeMap<String, PlutarchParallelLivesTable> allTables) {
		TableChangeConstruction.atomicChanges = atomicChanges;
		TableChangeConstruction.allTables = allTables;
		TableChangeConstruction.allTableChanges = new TreeMap<String, TableChange>();
	}

	private void calculateTableChanges(final int index) {
		if (allTableChanges.containsKey(atomicChanges.get(index).getAffectedTableName())) {
			final Integer transition = atomicChanges.get(index).getTransitionIdentification();
			if (allTableChanges.get(atomicChanges.get(index).getAffectedTableName()).getTableAtomicChanges().containsKey(transition)) {
				allTableChanges.get(atomicChanges.get(index).getAffectedTableName()).getTableAtomicChanges().get(transition)
						.add(atomicChanges.get(index));
			} else {
				final ArrayList<AtomicChange> atomicChange = new ArrayList<AtomicChange>();
				allTableChanges.get(atomicChanges.get(index).getAffectedTableName()).getTableAtomicChanges().put(transition, atomicChange);
				allTableChanges.get(atomicChanges.get(index).getAffectedTableName()).getTableAtomicChanges().get(transition)
						.add(atomicChanges.get(index));
			}
		} else {
			final TreeMap<Integer, ArrayList<AtomicChange>> atomicChange = new TreeMap<Integer, ArrayList<AtomicChange>>();
			final Integer transition = atomicChanges.get(index).getTransitionIdentification();
			atomicChange.put(transition, new ArrayList<AtomicChange>());
			atomicChange.get(transition).add(atomicChanges.get(index));
			final TableChange tableChange = new TableChange(atomicChanges.get(index).getAffectedTableName(), atomicChange);
			allTableChanges.put(atomicChanges.get(index).getAffectedTableName(), tableChange);
		}
	}

	public TreeMap<String, TableChange> getTableChanges() {
		return allTableChanges;
	}

	public void makeTableChanges() {
		for (int index = 0; index < atomicChanges.size(); index++) {
			calculateTableChanges(index);
		}
		for (Map.Entry<String, TableChange> table : allTableChanges.entrySet()) {
			allTables.get(table.getKey()).setTableChanges(table.getValue());
			allTables.get(table.getKey()).setTotalChanges();
		}
	}

}
