package data.dataSorters;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import data.dataKeeper.GlobalDataKeeper;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesTable;

public class DatasetRowSorter {
	private String[][] finalRows;
	private GlobalDataKeeper globalDataKeeper = new GlobalDataKeeper();

	public DatasetRowSorter(final String[][] finalRows, final GlobalDataKeeper globalDataKeeper) {
		this.finalRows = finalRows;
		this.globalDataKeeper = globalDataKeeper;
	}

	private SortedSet<Map.Entry<String, PlutarchParallelLivesTable>> entriesSortedByBirthDeath(final Map<String, PlutarchParallelLivesTable> map) {
		final SortedSet<Map.Entry<String, PlutarchParallelLivesTable>> entries = new TreeSet<Map.Entry<String, PlutarchParallelLivesTable>>(
				new Comparator<Map.Entry<String, PlutarchParallelLivesTable>>() {
					@Override
					public int compare(final Map.Entry<String, PlutarchParallelLivesTable> firstEntry,
							final Map.Entry<String, PlutarchParallelLivesTable> secondEntry) {
						if (firstEntry.getValue().getBirthVersionIdentification() < secondEntry.getValue().getBirthVersionIdentification()) {
							return -1;
						} else if (firstEntry.getValue().getBirthVersionIdentification() > secondEntry.getValue().getBirthVersionIdentification()) {
							return 1;
						} else {
							if (firstEntry.getValue().getDeathVersionIdentification() < secondEntry.getValue().getDeathVersionIdentification()) {
								return -1;
							} else {
								return 1;
							}
						}
					}
				});
		entries.addAll(map.entrySet());
		return entries;
	}

	private void iterateOverEntries(final String[][] sortedRows, final int counter, final Map.Entry<String, PlutarchParallelLivesTable> entries,
			final int outerIndex) {
		if (finalRows[outerIndex][0].equals(entries.getKey())) {
			for (int innerIndex = 0; innerIndex < finalRows[0].length; innerIndex++) {
				sortedRows[counter][innerIndex] = finalRows[outerIndex][innerIndex];
			}
		}
	}

	public String[][] sortRows() {
		final String[][] sortedRows = new String[finalRows.length][finalRows[0].length];
		final Map<String, PlutarchParallelLivesTable> tables = globalDataKeeper.getAllPlutarchParallelLivesTables();
		int counter = 0;
		for (Map.Entry<String, PlutarchParallelLivesTable> entries : entriesSortedByBirthDeath(tables)) {
			for (int outerIndex = 0; outerIndex < finalRows.length; outerIndex++) {
				iterateOverEntries(sortedRows, counter, entries, outerIndex);
			}
			counter++;
		}
		return sortedRows;
	}

}
