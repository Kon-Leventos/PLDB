package data.dataProccessing;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesAttribute;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesSchema;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesTable;
import gr.uoi.cs.daintiness.hecate.sql.Attribute;
import gr.uoi.cs.daintiness.hecate.sql.Schema;
import gr.uoi.cs.daintiness.hecate.sql.Table;

public class PlutarchParallelLivesSchemasConstruction {
	private static TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas;
	private static ArrayList<Schema> allSchemas = new ArrayList<Schema>();

	public PlutarchParallelLivesSchemasConstruction(final ArrayList<Schema> allSchemas) {
		allPlutarchParallelLivesSchemas = new TreeMap<String, PlutarchParallelLivesSchema>();
		PlutarchParallelLivesSchemasConstruction.allSchemas = allSchemas;
	}

	public TreeMap<String, PlutarchParallelLivesSchema> getAllPlutarchParallelLivesSchemas() {
		return allPlutarchParallelLivesSchemas;
	}

	private void iterateAttributes(final PlutarchParallelLivesTable plutarchParallelLivesTables, final TreeMap<String, Attribute> hecateAttributes) {
		for (Map.Entry<String, Attribute> attribute : hecateAttributes.entrySet()) {
			final PlutarchParallelLivesAttribute plutarchParallelLivesAttributes = new PlutarchParallelLivesAttribute(attribute.getValue());
			plutarchParallelLivesTables.addAttribute(plutarchParallelLivesAttributes);
		}
	}

	public void makePlutarchParallelLivesSchemas() {
		for (int index = 0; index < allSchemas.size(); index++) {
			Schema hecateSchema = new Schema();
			hecateSchema = allSchemas.get(index);
			final PlutarchParallelLivesSchema plutarchParallelLivesSchema = new PlutarchParallelLivesSchema(hecateSchema.getName(), hecateSchema);
			TreeMap<String, Table> hecateTables = new TreeMap<String, Table>();
			hecateTables = hecateSchema.getTables();
			for (Map.Entry<String, Table> table : hecateTables.entrySet()) {
				final PlutarchParallelLivesTable plutarchParallelLivesTables = new PlutarchParallelLivesTable(table.getValue().getName(),
						table.getValue());
				TreeMap<String, Attribute> hecateAttributes = new TreeMap<String, Attribute>();
				hecateAttributes = table.getValue().getAttrs();
				iterateAttributes(plutarchParallelLivesTables, hecateAttributes);
				plutarchParallelLivesSchema.addTable(plutarchParallelLivesTables);
			}
			allPlutarchParallelLivesSchemas.put(plutarchParallelLivesSchema.getName(), plutarchParallelLivesSchema);
		}
	}

}
