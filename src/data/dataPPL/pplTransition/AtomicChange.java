package data.dataPPL.pplTransition;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AtomicChange implements Serializable {
	private String affectedAttribute;
	private String affectedTable;
	private String newSchema;
	private String oldSchema;
	private Integer transitionIdentification;
	private String type;

	public AtomicChange(final String affectedTable, final String affectedAttribute, final String type, final String oldSchema, final String newSchema,
			final Integer transitionIdentification) {
		this.affectedTable = affectedTable;
		this.affectedAttribute = affectedAttribute;
		this.type = type;
		this.oldSchema = oldSchema;
		this.newSchema = newSchema;
		this.transitionIdentification = transitionIdentification;
	}

	public String getAffectedAttributeName() {
		return affectedAttribute;
	}

	public String getAffectedTableName() {
		return affectedTable;
	}

	public String[] getBothVersions() {
		final String[] versions = new String[2];
		versions[0] = oldSchema;
		versions[1] = newSchema;
		return versions;
	}

	public Integer getTransitionIdentification() {
		return transitionIdentification;
	}

	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "AtomicChange: Table: " + affectedTable + "\t" + "Attribute: " + affectedAttribute + "\t" + "Type: " + type + "\t" + "oldSchema: "
				+ oldSchema + "\t" + "newSchema: " + newSchema;
	}

}
