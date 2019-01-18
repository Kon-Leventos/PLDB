package data.dataPPL.pplSQLSchema;

import java.io.IOException;
import java.io.Serializable;
import gr.uoi.cs.daintiness.hecate.sql.Attribute;

@SuppressWarnings("serial")
public class PlutarchParallelLivesAttribute implements Serializable {
	private Attribute hecateAttribute;
	private int totalAttributeChanges;

	public PlutarchParallelLivesAttribute() {
	}

	public PlutarchParallelLivesAttribute(final Attribute temporalHecateAttribute) {
		hecateAttribute = temporalHecateAttribute;
	}

	public Attribute getHecateAttribute() {
		return hecateAttribute;
	}

	public String getName() {
		return hecateAttribute.getName();
	}

	public int getTotalAttributeChanges() {
		return totalAttributeChanges;
	}

	public void setTotalAttributeChanges(final int temporalTotalAttributeChanges) {
		totalAttributeChanges = temporalTotalAttributeChanges;
	}

	private void writeObject(final java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(hecateAttribute.toString());
		out.writeObject(totalAttributeChanges);
	}

}
