package data.dataPPL.pplTransition;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PlutarchParallelLivesTransition implements Serializable {
	private String newSchema;
	private String oldSchema;
	private int plutarchParallelLiveTransitionIdentification;
	private ArrayList<TableChange> tableChanges = new ArrayList<TableChange>();

	public PlutarchParallelLivesTransition(final String oldSchema, final String newSchema, final int transitionIdentification) {
		this.oldSchema = oldSchema;
		this.newSchema = newSchema;
		this.plutarchParallelLiveTransitionIdentification = transitionIdentification;
	}

	private int decideModeNumber(final String mode, final int innerIndex) {
		switch (mode) {
			case "Addition":
				return tableChanges.get(innerIndex).getAdditionNumberOfSpecificTransition();
			case "Change":
				return tableChanges.get(innerIndex).getAtomicChangesForOneTransition().size();
			case "Deletion":
				return tableChanges.get(innerIndex).getDeletionNumberOfSpecificTransition();
			case "Update":
				return tableChanges.get(innerIndex).getUpdateNumberOfSpecificTransition();
			default:
				return 0;
		}
	}

	public int getAdditionNumberOfSpecificTransition() {
		return getSpecificNumberOfSpecificTransition("Addition");
	}

	public int getChangeNumberOfSpecificTransition() {
		return getSpecificNumberOfSpecificTransition("Change");
	}

	public int getClusterAdditionNumberOfSpecificTransition(final String[][] rowsZoom) {
		return getClusterSpecificNumberOfSpecificTransition(rowsZoom, "Addition");
	}

	public int getClusterChangeNumberOfSpecificTransition(final String[][] rowsZoom) {
		return getClusterSpecificNumberOfSpecificTransition(rowsZoom, "Change");
	}

	public int getClusterDeletionNumberOfSpecificTransition(final String[][] rowsZoom) {
		return getClusterSpecificNumberOfSpecificTransition(rowsZoom, "Deletion");
	}

	private int getClusterSpecificNumberOfSpecificTransition(final String[][] rowsZoom, final String mode) {
		int number = 0;
		for (int outerIndex = 0; outerIndex < rowsZoom.length; outerIndex++) {
			for (int innerIndex = 0; innerIndex < tableChanges.size(); innerIndex++) {
				if (tableChanges.get(innerIndex).getAffectedTableName().equals(rowsZoom[outerIndex][0])) {
					number += decideModeNumber(mode, innerIndex);
				}
			}
		}
		return number;
	}

	public int getClusterUpdateNumberOfSpecificTransition(final String[][] rowsZoom) {
		return getClusterSpecificNumberOfSpecificTransition(rowsZoom, "Update");
	}

	public int getDeletionNumberOfSpecificTransition() {
		return getSpecificNumberOfSpecificTransition("Deletion");
	}

	public String getNewVersionName() {
		return newSchema;
	}

	public String getOldVersionName() {
		return oldSchema;
	}

	public int getPlutarchParallelLiveTransitionIdentification() {
		return plutarchParallelLiveTransitionIdentification;
	}

	private int getSpecificNumberOfSpecificTransition(final String mode) {
		int number = 0;
		for (int index = 0; index < tableChanges.size(); index++) {
			number += decideModeNumber(mode, index);
		}
		return number;
	}

	public ArrayList<TableChange> getTableChanges() {
		return tableChanges;
	}

	public int getUpdateNumberOfSpecificTransition() {
		return getSpecificNumberOfSpecificTransition("Update");
	}

	public void setTableChanges(final ArrayList<TableChange> tableChanges) {
		this.tableChanges = tableChanges;
	}

}
