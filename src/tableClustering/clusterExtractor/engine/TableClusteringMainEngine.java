package tableClustering.clusterExtractor.engine;

import java.util.ArrayList;
import data.dataKeeper.GlobalDataKeeper;
import tableClustering.clusterExtractor.analysis.IClusterExtractor;
import tableClustering.clusterExtractor.analysis.ClusterExtractorFactory;
import tableClustering.clusterExtractor.commons.ClusterCollector;

public class TableClusteringMainEngine {
	private ArrayList<ClusterCollector> allClusterCollectors;
	private Double birthWeight;
	private Double changeWeight;
	private ArrayList<ClusterCollector> clusterCollectors;
	private IClusterExtractor clusterExtractor;
	private ClusterExtractorFactory clusterExtractorFactory;
	private GlobalDataKeeper dataKeeper;
	private Double deathWeight;

	public TableClusteringMainEngine(GlobalDataKeeper dataKeeper, Double birthWeight, Double deathWeight, Double changeWeight) {
		this.dataKeeper = dataKeeper;
		this.birthWeight = birthWeight;
		this.deathWeight = deathWeight;
		this.changeWeight = changeWeight;
		clusterExtractorFactory = new ClusterExtractorFactory();
		clusterExtractor = clusterExtractorFactory.createClusterExtractor("AgglomerativeClusterExtractor");
		allClusterCollectors = new ArrayList<ClusterCollector>();
	}

	public void extractClusters(int numClusters) {
		clusterCollectors = new ArrayList<ClusterCollector>();
		ClusterCollector clusterCollector = new ClusterCollector();
		clusterCollector = clusterExtractor.extractAtMostKClusters(dataKeeper, numClusters, birthWeight, deathWeight, changeWeight);
		clusterCollector.sortClustersByBirthDeath();
		clusterCollectors.add(clusterCollector);
		allClusterCollectors.add(clusterCollector);
	}

	public ArrayList<ClusterCollector> getClusterCollectors() {
		return allClusterCollectors;
	}

	public void print() {
		String toPrint = "";
		for (int i = 0; i < allClusterCollectors.size(); i++) {
			ClusterCollector clusterCollector = allClusterCollectors.get(i);
			toPrint = toPrint + clusterCollector.toString();
		}
		System.out.println(toPrint);
	}

}
