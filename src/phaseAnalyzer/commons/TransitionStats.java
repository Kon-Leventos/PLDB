package phaseAnalyzer.commons;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TransitionStats implements Serializable {
	private String newVersionFile;
	private int numberOfAttributesDel;
	private int numberOfAttrDelWithDelTables;
	private int numberAttrInKeyAlt;
	private int numberAttrIns;
	private int numberAttrInsInNewTables;
	private int numberAttrWithTypeAlt;
	private int numberOfNewAttributes;
	private int numberOfNewTables;
	private int numberOfOldAtributes;
	private int numberOfOldTables;
	private int numberTablesDel;
	private int numberTablesIns;
	private String oldVersionFile;
	private int time;
	private int timeDistanceFromPrevious;
	private int totalAttrChange;
	private int totalAttrInsDel;
	private int totalAttrUpd;
	private int totalTableInsDel;
	private int totalUpdatesInTr;
	private int transitionId;

	public TransitionStats(int transitionId, int time, String oldVersionFile, String newVersionFile, int numOldTables, int numNewTables,
			int numOldAtributes, int numNewAttributes, int numTablesIns, int numTablesDel, int numAttrIns, int numAttrDel, int numAttrWithTypeAlt,
			int numAttrInKeyAlt, int numAttrInsInNewTables, int numAttrDelWithDelTables, int totalUpdatesInTr) {
		this.transitionId = transitionId;
		this.time = time;
		this.oldVersionFile = oldVersionFile;
		this.newVersionFile = newVersionFile;
		this.numberOfOldTables = numOldTables;
		this.numberOfNewTables = numNewTables;
		this.numberOfOldAtributes = numOldAtributes;
		this.numberOfNewAttributes = numNewAttributes;
		this.numberTablesIns = numTablesIns;
		this.numberTablesDel = numTablesDel;
		this.numberAttrIns = numAttrIns;
		this.numberOfAttributesDel = numAttrDel;
		this.numberAttrWithTypeAlt = numAttrWithTypeAlt;
		this.numberAttrInKeyAlt = numAttrInKeyAlt;
		this.numberAttrInsInNewTables = numAttrInsInNewTables;
		this.numberOfAttrDelWithDelTables = numAttrDelWithDelTables;
		this.totalUpdatesInTr = totalUpdatesInTr;
		this.totalTableInsDel = this.numberTablesIns + this.numberTablesDel;
		this.totalAttrInsDel = this.numberAttrIns + this.numberOfAttributesDel;
		this.totalAttrUpd = this.numberAttrWithTypeAlt + this.numberAttrInKeyAlt;
		this.totalAttrChange = this.totalAttrInsDel + this.totalAttrUpd + this.numberAttrInsInNewTables + this.numberOfAttrDelWithDelTables;
	}

	public String getNewVersionFile() {
		return newVersionFile;
	}

	public int getNumAttrDel() {
		return numberOfAttributesDel;
	}

	public int getNumAttrDelWithDelTables() {
		return numberOfAttrDelWithDelTables;
	}

	public int getNumAttrInKeyAlt() {
		return numberAttrInKeyAlt;
	}

	public int getNumAttrIns() {
		return numberAttrIns;
	}

	public int getNumAttrInsInNewTables() {
		return numberAttrInsInNewTables;
	}

	public int getNumAttrWithTypeAlt() {
		return numberAttrWithTypeAlt;
	}

	public int getNumberOfNewAttributes() {
		return numberOfNewAttributes;
	}

	public int getNumberOfNewTables() {
		return numberOfNewTables;
	}

	public int getNumberOfOldAtributes() {
		return numberOfOldAtributes;
	}

	public int getNumberOfOldTables() {
		return numberOfOldTables;
	}

	public int getNumTablesDel() {
		return numberTablesDel;
	}

	public int getNumTablesIns() {
		return numberTablesIns;
	}

	public String getOldVersionFile() {
		return oldVersionFile;
	}

	public int getTime() {
		return time;
	}

	public int getTimeDistFromPrevious() {
		return timeDistanceFromPrevious;
	}

	public int getTotalAttrChange() {
		return totalAttrChange;
	}

	public int getTotalAttrInsDel() {
		return totalAttrInsDel;
	}

	public int getTotalAttrUpd() {
		return totalAttrUpd;
	}

	public int getTotalTableInsDel() {
		return totalTableInsDel;
	}

	public int getTotalUpdInTr() {
		return totalUpdatesInTr;
	}

	public int getTransitionId() {
		return transitionId;
	}

	public void setTimeDistFromPrevious(int timeDistFromPrevious) {
		this.timeDistanceFromPrevious = timeDistFromPrevious;
	}

	public String toStringShort() {
		String shortDescription = new String();
		shortDescription = transitionId + "\t" + time + "\t" + timeDistanceFromPrevious + "\t" + numberTablesIns + "\t" + numberTablesDel + "\t"
				+ numberAttrIns + "\t" + numberOfAttributesDel + "\t" + numberAttrWithTypeAlt + "\t" + numberAttrInKeyAlt + "\t"
				+ numberAttrInsInNewTables + "\t" + numberOfAttrDelWithDelTables;
		return shortDescription;
	}

}
