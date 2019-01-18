package data.dataPPL.pplSQLSchema;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import data.dataPPL.pplTransition.AtomicChange;
import data.dataPPL.pplTransition.TableChange;
import gr.uoi.cs.daintiness.hecate.sql.Table;

@SuppressWarnings("serial")
public class PlutarchParallelLivesTable implements Serializable {
	private boolean active;
	private int age;
	private TreeMap<String, PlutarchParallelLivesAttribute> attributes;
	private String birth;
	private int birthVersionIdenification;
	private ArrayList<Integer> changesForChart = new ArrayList<Integer>();
	private HashMap<String, Integer> additionalChanges;
	private int currentChanges;
	private String death;
	private int deathVersionIdentification;
	private Table hecateTable;
	private String name = "";
	private HashMap<String, Integer> sequenceOfAdditionalChanges;
	private TableChange tableChanges;
	private int totalChanges;
	private HashMap<String, Integer> windowSpecificAdditionalChanges;

	public PlutarchParallelLivesTable() {
	}

	public PlutarchParallelLivesTable(final String temporalName, final Table temporalHecateTable) {
		hecateTable = temporalHecateTable;
		name = temporalName;
		this.attributes = new TreeMap<String, PlutarchParallelLivesAttribute>();
	}

	public void addAttribute(final PlutarchParallelLivesAttribute attribute) {
		this.attributes.put(attribute.getName(), attribute);
	}

	public HashMap<String, Integer> getAdditionalChanges() {
		return additionalChanges;
	}

	public int getAdditionNumberOfSpecificTransition(final Integer transition) {
		return tableChanges.getAdditionNumberOfSpecificTransition(transition);
	}

	public int getAge() {
		return age;
	}

	public TreeMap<String, PlutarchParallelLivesAttribute> getAttributes() {
		return this.attributes;
	}

	public String getBirth() {
		return this.birth;
	}

	public int getBirthVersionIdentification() {
		return birthVersionIdenification;
	}

	public ArrayList<Integer> getChangesForChart() {
		return changesForChart;
	}

	public int getCurrentChanges() {
		return currentChanges;
	}

	public String getDeath() {
		return this.death;
	}

	public int getDeathVersionIdentification() {
		return deathVersionIdentification;
	}

	public int getDeletionNumberOfSpecificTransition(final Integer transition) {
		return tableChanges.getDeletionNumberOfSpecificTransition(transition);
	}

	public Table getHecateTable() {
		return hecateTable;
	}

	public String getName() {
		return name;
	}

	public HashMap<String, Integer> getSequenceOfAdditionalChanges() {
		return sequenceOfAdditionalChanges;
	}

	public int getSize() {
		return attributes.size();
	}

	public TableChange getTableChanges() {
		return tableChanges;
	}

	public int getTotalChanges() {
		return totalChanges;
	}

	public int getTotalChangesForOnePhase(final int startingPosition, final int endingPosition) {
		int counter = 0;
		for (int i = startingPosition; i <= endingPosition; i++) {
			if (tableChanges.getAtomicChangeForSpecificTransition(i) != null) {
				counter = counter + tableChanges.getAtomicChangeForSpecificTransition(i).size();
			}
		}
		return counter;
	}

	public int getUpdateNumberOfSpecificTransition(final Integer transition) {
		return tableChanges.getUpdateNumberOfSpecificTransition(transition);
	}

	public HashMap<String, Integer> getWindowSpecificAdditionalChanges() {
		return windowSpecificAdditionalChanges;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setAge(final int temporalAge) {
		age = temporalAge;
	}

	public void setBirth(final String birth) {
		this.birth = birth;
	}

	public void setBirthVersionIdentification(final int birthIdentification) {
		birthVersionIdenification = birthIdentification;
	}

	public void setChangesForChart(final ArrayList<Integer> temporalChangesForChart) {
		changesForChart = temporalChangesForChart;
	}

	public void setCoChanges(final HashMap<String, Integer> temporalChanges) {
		additionalChanges = temporalChanges;
	}

	public void setCurrentChanges(final int temporalCurrentChanges) {
		currentChanges = temporalCurrentChanges;
	}

	public void setDeath(final String death) {
		this.death = death;
	}

	public void setDeathVersionIentification(final int deathIdentification) {
		deathVersionIdentification = deathIdentification;
	}

	public void setSequenceOfAdditionalChanges(final HashMap<String, Integer> temporalSequenceOfAdditionalChanges) {
		sequenceOfAdditionalChanges = temporalSequenceOfAdditionalChanges;
	}

	public void setTableChanges(final TableChange temporalTableChanges) {
		tableChanges = temporalTableChanges;
	}

	public void setTotalChanges() {
		final TreeMap<Integer, ArrayList<AtomicChange>> totalTableChanges = tableChanges.getTableAtomicChanges();
		for (Map.Entry<Integer, ArrayList<AtomicChange>> tableChangesSet : totalTableChanges.entrySet()) {
			totalChanges = totalChanges + tableChangesSet.getValue().size();
		}
	}

	public void setWindowSpecificAdditionalChanges(final HashMap<String, Integer> temporalWindowSpecificAdditionalChanges) {
		windowSpecificAdditionalChanges = temporalWindowSpecificAdditionalChanges;
	}

	public void toggleActive() {
		this.active = !this.active;
	}

	private void writeObject(final java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(active);
		out.writeObject(age);
		out.writeObject(attributes);
		out.writeObject(birth);
		out.writeObject(birthVersionIdenification);
		out.writeObject(changesForChart);
		out.writeObject(additionalChanges);
		out.writeObject(currentChanges);
		out.writeObject(death);
		out.writeObject(deathVersionIdentification);
		out.writeObject(hecateTable.toString());
		out.writeObject(name);
		out.writeObject(sequenceOfAdditionalChanges);
		out.writeObject(tableChanges);
		out.writeObject(totalChanges);
		out.writeObject(windowSpecificAdditionalChanges);
	}

}
