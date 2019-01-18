package tableClustering.clusterExtractor.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesTable;

@SuppressWarnings("serial")
public class Cluster implements Serializable {
	private int birth;
	private String birthVersion;
	private int death;
	private String deathVersion;
	private TreeMap<String, PlutarchParallelLivesTable> tables = null;
	private int totalChanges = 0;

	public Cluster() {
		tables = new TreeMap<String, PlutarchParallelLivesTable>();
	}

	public Cluster(int birth, String birthVersion, int death, String deathVersion, int totalChanges) {
		this.birth = birth;
		this.birthVersion = birthVersion;
		this.death = death;
		this.deathVersion = deathVersion;
		this.totalChanges = totalChanges;
		tables = new TreeMap<String, PlutarchParallelLivesTable>();
	}

	public void addTable(PlutarchParallelLivesTable table) {
		this.tables.put(table.getName(), table);
	}

	public double distance(Cluster anotherCluster, Double birthWeight, Double deathWeight, Double changeWeight, int dbDuration) {
		double normalizedChangeDistance = Math
				.abs((this.totalChanges - anotherCluster.totalChanges) / ((double) (this.totalChanges + anotherCluster.totalChanges)));
		double normalizedBirthDistance = Math.abs((this.birth - anotherCluster.birth) / (double) dbDuration);
		double normalizedDeathDistance = Math.abs((this.death - anotherCluster.death) / (double) dbDuration);
		double normalizedTotalDistance = changeWeight * normalizedChangeDistance + birthWeight * normalizedBirthDistance
				+ deathWeight * normalizedDeathDistance;
		return normalizedTotalDistance;
	}

	public int getBirth() {
		return this.birth;
	}

	public String getBirthSqlFile() {
		return this.birthVersion;
	}

	public int getDeath() {
		return this.death;
	}

	public String getDeathSqlFile() {
		return this.deathVersion;
	}

	public ArrayList<String> getNamesOfTables() {
		ArrayList<String> tablesNames = new ArrayList<String>();
		for (Map.Entry<String, PlutarchParallelLivesTable> pplTb : tables.entrySet()) {
			tablesNames.add(pplTb.getKey());
		}
		return tablesNames;
	}

	public TreeMap<String, PlutarchParallelLivesTable> getTables() {
		return tables;
	}

	public int getTotalChanges() {
		return totalChanges;
	}

	private void maximizeDeath(Cluster nextCluster, Cluster newCluster, int minimumBirthValue, String minimumBirthVersion) {
		int maximumDeathValue;
		String maxDeathVersion = "";
		if (this.death >= nextCluster.death) {
			maximumDeathValue = this.death;
			maxDeathVersion = this.deathVersion;
		} else {
			maximumDeathValue = nextCluster.death;
			maxDeathVersion = nextCluster.deathVersion;
		}
		newCluster.birth = minimumBirthValue;
		newCluster.birthVersion = minimumBirthVersion;
		newCluster.death = maximumDeathValue;
		newCluster.deathVersion = maxDeathVersion;
		newCluster.totalChanges = this.totalChanges + nextCluster.totalChanges;
		for (Map.Entry<String, PlutarchParallelLivesTable> tab : tables.entrySet()) {
			newCluster.addTable(tab.getValue());
		}
		for (Map.Entry<String, PlutarchParallelLivesTable> tabNext : nextCluster.getTables().entrySet()) {
			newCluster.addTable(tabNext.getValue());
		}
	}

	public Cluster mergeWithNextCluster(Cluster nextCluster) {
		Cluster newCluster = new Cluster();
		int minimumBirthValue;
		String minimumBirthVersion = "";
		if (this.birth <= nextCluster.birth) {
			minimumBirthValue = this.birth;
			minimumBirthVersion = this.birthVersion;
		} else {
			minimumBirthValue = nextCluster.birth;
			minimumBirthVersion = nextCluster.birthVersion;
		}
		maximizeDeath(nextCluster, newCluster, minimumBirthValue, minimumBirthVersion);
		return newCluster;
	}

	@Override
	public String toString() {
		String toReturn = "Cluster";
		toReturn = toReturn + "\t" + this.birth + "\t" + this.death + "\t" + this.totalChanges + "\n";
		for (Map.Entry<String, PlutarchParallelLivesTable> t : this.tables.entrySet()) {
			toReturn = toReturn + t.getKey() + "\t" + t.getValue().getBirthVersionIdentification() + "\t"
					+ t.getValue().getDeathVersionIdentification() + "\t" + t.getValue().getTotalChanges() + "\n";
		}
		return toReturn;
	}

}
