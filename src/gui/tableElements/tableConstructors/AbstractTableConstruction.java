package gui.tableElements.tableConstructors;

import java.util.ArrayList;

public abstract class AbstractTableConstruction {
	protected Integer[] segmentSize = new Integer[4];
	protected Integer[][] schemaColumn;
	protected int columnsNumber;
	protected int maxInsertions = 1;
	protected int maxDeletions = 1;
	protected int maxUpdates = 1;
	protected int maxTransitions = 1;

	public String[] constructColumns() {
		final ArrayList<String> columnsList = new ArrayList<String>();
		schemaColumn = new Integer[getSchemaColumnSize()][2];
		for (int index = 0; index < getSchemaColumnSize(); index++) {
			schemaColumn[index][0] = index;
			if (index == 0) {
				schemaColumn[index][1] = 1;
			} else {
				schemaColumn[index][1] = schemaColumn[index - 1][1] + getSchemaAdditionValue();
			}
		}
		columnsList.add("Table name");
		editColumnsList(columnsList);
		columnsNumber = columnsList.size();
		final String[] columns = new String[columnsNumber];
		for (int index = 0; index < columnsNumber; index++) {
			columns[index] = columnsList.get(index);
		}
		return columns;
	}

	public String[][] constructRows() {
		ArrayList<String[]> allRows = new ArrayList<String[]>();
		ArrayList<String> allTables = new ArrayList<String>();
		loopMapEntry(allRows, allTables);
		String[][] rows = new String[allRows.size()][columnsNumber];
		for (int outerIndex = 0; outerIndex < allRows.size(); outerIndex++) {
			String[] oneRow = allRows.get(outerIndex);
			for (int innerIndex = 0; innerIndex < oneRow.length; innerIndex++) {
				rows[outerIndex][innerIndex] = oneRow[innerIndex];
			}
		}
		float maximumInsertions = (float) maxInsertions / 4;
		segmentSize[0] = (int) Math.rint(maximumInsertions);
		float maximumUpdates = (float) maxUpdates / 4;
		segmentSize[1] = (int) Math.rint(maximumUpdates);
		float maximumDeletions = (float) maxDeletions / 4;
		segmentSize[2] = (int) Math.rint(maximumDeletions);
		float maximumTransitions = (float) maxTransitions / 4;
		segmentSize[3] = (int) Math.rint(maximumTransitions);
		return rows;
	}

	public Integer[] getSegmentSize() {
		return segmentSize;
	}
	
	protected String[] loopOneRow(final String[] oneRow) {
		for (int index = 0; index < oneRow.length; index++) {
			if (oneRow[index] == null) {
				oneRow[index] = "";
			}
		}
		return oneRow;
	}
	
	protected int isNotReborn(final String[] oneRow, final int pointerCell, final int reborn) {
		int pointer = pointerCell;
		if (reborn == 0) {
			oneRow[pointerCell] = "";
			pointer++;
		}
		return pointer;
	}
	
	protected int calculateTotalChanges(String[] oneRow, final int pointerCell, final int updates, final int deletions, final int insertions,
			final int reborn) {
		final int changes = insertions + updates + deletions;
		if (changes >= 0 && reborn == 1 || reborn == -1) {
			oneRow[pointerCell] = Integer.toString(changes);
		}
		return changes;
	}
	
	protected void checkMaxTransitions(final int changes) {
		if (changes > maxTransitions) {
			maxTransitions = changes;
		}
	}
	
	protected abstract int getSchemaColumnSize();
	
	protected abstract int getSchemaAdditionValue();
	
	protected abstract void editColumnsList(ArrayList<String> columnsList);
	
	protected abstract void loopMapEntry(ArrayList<String[]> allRows, ArrayList<String> allTables);

}
