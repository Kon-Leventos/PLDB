package data.dataPPL.pplTransition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class TableChange implements Serializable {
	private String affectedTable;
	private TreeMap<Integer, ArrayList<AtomicChange>> atomicChanges = new TreeMap<Integer, ArrayList<AtomicChange>>();
	private ArrayList<AtomicChange> atomicChangesForOneTransition = new ArrayList<AtomicChange>();

	public TableChange() {
	}

	public TableChange(final String affectedTable, final ArrayList<AtomicChange> atomicChangesForOneTransition) {
		this.affectedTable = affectedTable;
		this.atomicChangesForOneTransition = atomicChangesForOneTransition;
	}

	public TableChange(final String affectedTable, final TreeMap<Integer, ArrayList<AtomicChange>> atomicChanges) {
		this.affectedTable = affectedTable;
		this.atomicChanges = atomicChanges;
	}

	public int getAdditionNumberOfSpecificTransition() {
		return getSpecificNumberofSpecificTransition("Addition");
	}

	public int getAdditionNumberOfSpecificTransition(final Integer transition) {
		return getSpecificNumberOfSpecificTransition("Addition", transition);
	}

	public String getAffectedTableName() {
		return affectedTable;
	}

	public ArrayList<AtomicChange> getAtomicChangeForSpecificTransition(final Integer transition) {
		return atomicChanges.get(transition);
	}

	public ArrayList<AtomicChange> getAtomicChangesForOneTransition() {
		return atomicChangesForOneTransition;
	}

	public int getDeletionNumberOfSpecificTransition() {
		return getSpecificNumberofSpecificTransition("Deletion");
	}

	public int getDeletionNumberOfSpecificTransition(final Integer transition) {
		return getSpecificNumberOfSpecificTransition("Deletion", transition);
	}

	private int getSpecificNumberofSpecificTransition(final String mode) {
		int number = 0;
		for (int index = 0; index < atomicChangesForOneTransition.size(); index++) {
			final AtomicChange atomicChange = atomicChangesForOneTransition.get(index);
			if (atomicChange.getType().contains(mode)) {
				number++;
			}
		}
		return number;
	}

	private int getSpecificNumberOfSpecificTransition(final String mode, final Integer transition) {
		int number = 0;
		final ArrayList<AtomicChange> atomicChange = atomicChanges.get(transition);
		for (int index = 0; index < atomicChange.size(); index++) {
			final AtomicChange specificAtomicChange = atomicChange.get(index);
			if (specificAtomicChange.getType().contains(mode)) {
				number++;
			}
		}
		return number;
	}

	public TreeMap<Integer, ArrayList<AtomicChange>> getTableAtomicChanges() {
		return atomicChanges;
	}

	public int getUpdateNumberOfSpecificTransition() {
		return getSpecificNumberofSpecificTransition("Change");
	}

	public int getUpdateNumberOfSpecificTransition(final Integer transition) {
		return getSpecificNumberOfSpecificTransition("Change", transition);
	}

	@Override
	public String toString() {
		String message = "Table Change \n";
		for (int i = 0; i < atomicChanges.size(); i++) {
			message = message + atomicChanges.get(i).toString();
			message = message + "\n";
		}
		return message;
	}

}
