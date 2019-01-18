package data.dataPPL.pplSQLSchema;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import gr.uoi.cs.daintiness.hecate.sql.Schema;

@SuppressWarnings("serial")
public class PlutarchParallelLivesSchema implements Serializable {
	private String name;
	private TreeMap<String, PlutarchParallelLivesTable> tables;

	public PlutarchParallelLivesSchema() {
		this.tables = new TreeMap<String, PlutarchParallelLivesTable>();
	}

	public PlutarchParallelLivesSchema(final String temporalName, final Schema temporalHecateSchema) {
		name = temporalName;
		this.tables = new TreeMap<String, PlutarchParallelLivesTable>();
	}

	public void addTable(final PlutarchParallelLivesTable table) {
		this.tables.put(table.getName(), table);
	}

	public String getName() {
		return name;
	}

	public int[] getSize() {
		int attr = 0;
		for (PlutarchParallelLivesTable t : this.tables.values()) {
			attr += t.getSize();
		}
		final int[] res = {this.tables.size(), attr};
		return res;
	}

	public PlutarchParallelLivesTable getTableAt(final int index) {
		int c = 0;
		if (index >= 0 && index < tables.size()) {
			for (Map.Entry<String, PlutarchParallelLivesTable> t : tables.entrySet()) {
				if (c == index) {
					return t.getValue();
				}
				c++;
			}
		}
		return null;
	}

	public TreeMap<String, PlutarchParallelLivesTable> getTables() {
		return this.tables;
	}

	public void setTitle(final String title) {
		this.name = title;
	}

	@Override
	public String toString() {
		return name;
	}

}
