package data.dataProccessing;

import java.util.Map;
import java.util.TreeMap;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesSchema;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesTable;

public class PlutarchParallelLivesTablesConstruction {
	private static TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas = new TreeMap<String, PlutarchParallelLivesSchema>();
	private TreeMap<String, PlutarchParallelLivesTable> allPlutarchParallelLivesTables;

	public PlutarchParallelLivesTablesConstruction(final TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas) {
		allPlutarchParallelLivesTables = new TreeMap<String, PlutarchParallelLivesTable>();
		PlutarchParallelLivesTablesConstruction.allPlutarchParallelLivesSchemas = allPlutarchParallelLivesSchemas;
	}

	private PlutarchParallelLivesSchema calculateSchema(final int version, final Map.Entry<String, PlutarchParallelLivesSchema> schema) {
		final PlutarchParallelLivesSchema oneSchema = schema.getValue();
		for (int j = 0; j < oneSchema.getTables().size(); j++) {
			PlutarchParallelLivesTable oneTable = oneSchema.getTableAt(j);
			if (!allPlutarchParallelLivesTables.containsKey(oneTable.getName())) {
				oneTable.setBirth(oneSchema.getName());
				oneTable.setBirthVersionIdentification(version);
				oneTable.setDeath(allPlutarchParallelLivesSchemas.get(allPlutarchParallelLivesSchemas.lastKey()).getName());
				oneTable.setDeathVersionIentification(allPlutarchParallelLivesSchemas.size() - 1);
				oneTable.toggleActive();
				allPlutarchParallelLivesTables.put(oneTable.getName(), oneTable);
				oneTable = new PlutarchParallelLivesTable();
			}
		}
		return oneSchema;
	}

	private int calculateVersion(final int version, final PlutarchParallelLivesSchema oneSchema) {
		boolean found = false;
		for (Map.Entry<String, PlutarchParallelLivesTable> table : allPlutarchParallelLivesTables.entrySet()) {
			found = false;
			for (int index = 0; index < oneSchema.getTables().size(); index++) {
				final PlutarchParallelLivesTable oneTable = oneSchema.getTableAt(index);
				if (table.getKey().equals(oneTable.getName())) {
					found = true;
					break;
				}
			}
			if (!found && table.getValue().isActive()) {
				table.getValue().setDeath(oneSchema.getName());
				table.getValue().setDeathVersionIentification(version);
				table.getValue().toggleActive();
			}
		}
		return version + 1;
	}

	public TreeMap<String, PlutarchParallelLivesTable> getAllPlutarchParallelLivesTables() {
		return allPlutarchParallelLivesTables;
	}

	public void makeAllPlutarchParallelLivesTables() {
		int version = 0;
		for (Map.Entry<String, PlutarchParallelLivesSchema> schema : allPlutarchParallelLivesSchemas.entrySet()) {
			final PlutarchParallelLivesSchema oneSchema = calculateSchema(version, schema);
			version = calculateVersion(version, oneSchema);
		}
	}

}
