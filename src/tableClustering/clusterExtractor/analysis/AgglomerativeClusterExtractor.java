package tableClustering.clusterExtractor.analysis;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import data.dataKeeper.GlobalDataKeeper;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesTable;
import tableClustering.clusterExtractor.commons.Cluster;
import tableClustering.clusterExtractor.commons.ClusterCollector;

class AgglomerativeClusterExtractor implements IClusterExtractor {

	@Override
	public ClusterCollector extractAtMostKClusters(GlobalDataKeeper dataKeeper, int numClusters, Double birthWeight, Double deathWeight,
			Double changeWeight) {
		ClusterCollector initSolution = new ClusterCollector();
		this.init(dataKeeper, initSolution);
		ClusterCollector currentSolution = new ClusterCollector();
		currentSolution = this.newClusterCollector(initSolution, birthWeight, deathWeight, changeWeight,
				dataKeeper.getAllPlutarchParallelLivesSchemas().size() - 1);
		while (currentSolution.getClusters().size() > numClusters) {
			currentSolution = this.newClusterCollector(currentSolution, birthWeight, deathWeight, changeWeight,
					dataKeeper.getAllPlutarchParallelLivesSchemas().size() - 1);
		}
		return currentSolution;
	}

	private void formClusters(ClusterCollector newCollector, ArrayList<Cluster> newClusters, ArrayList<Cluster> oldClusters, int oldSize,
			double[][] distances) {
		int positionI = -1;
		double minimumDistance = Double.MAX_VALUE;
		int positionJ = -1;
		for (int i = 0; i < oldSize; i++) {
			for (int j = 0; j < oldSize; j++) {
				if (i != j) {
					if (distances[i][j] < minimumDistance) {
						positionI = i;
						positionJ = j;
						minimumDistance = distances[i][j];
					}
				}
			}
		}
		Cluster newCluster = makeClusterToMerge(oldClusters, positionI, positionJ);
		for (int i = 0; i < oldSize; i++) {
			if (i != positionI && i != positionJ) {
				Cluster c = oldClusters.get(i);
				newClusters.add(c);
			}
		}
		newClusters.add(newCluster);
		newCollector.setClusters(newClusters);
	}

	public ClusterCollector init(GlobalDataKeeper dataKeeper, ClusterCollector clusterCollector) {
		TreeMap<String, PlutarchParallelLivesTable> tables = dataKeeper.getAllPlutarchParallelLivesTables();
		for (Map.Entry<String, PlutarchParallelLivesTable> pplTable : tables.entrySet()) {
			Cluster cluster = new Cluster(pplTable.getValue().getBirthVersionIdentification(), pplTable.getValue().getDeath(),
					pplTable.getValue().getDeathVersionIdentification(), pplTable.getValue().getDeath(), pplTable.getValue().getTotalChanges());
			cluster.addTable(pplTable.getValue());
			clusterCollector.addCluster(cluster);
		}
		return clusterCollector;
	}

	private Cluster makeClusterToMerge(ArrayList<Cluster> oldClusters, int posI, int posJ) {
		Cluster toMerge = oldClusters.get(posI);
		Cluster newCluster = toMerge.mergeWithNextCluster(oldClusters.get(posJ));
		return newCluster;
	}

	private ClusterCollector newClusterCollector(ClusterCollector previousCollector, Double birthWeight, Double deathWeight, Double changeWeight,
			int dbDuration) {
		ClusterCollector newCollector = new ClusterCollector();
		ArrayList<Cluster> newClusters = new ArrayList<Cluster>();
		ArrayList<Cluster> oldClusters = previousCollector.getClusters();
		int oldSize = oldClusters.size();
		if (oldSize == 0) {
			System.exit(-10);
		}
		double distances[][] = new double[oldSize][oldSize];
		for (int oldI = 0; oldI < oldClusters.size(); oldI++) {
			Cluster currentCluster = oldClusters.get(oldI);
			for (int oldI1 = 0; oldI1 < oldClusters.size(); oldI1++) {
				Cluster clusterToCompare = oldClusters.get(oldI1);
				distances[oldI][oldI1] = currentCluster.distance(clusterToCompare, birthWeight, deathWeight, changeWeight, dbDuration);
			}
		}
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances[0].length; j++) {
			}
		}
		formClusters(newCollector, newClusters, oldClusters, oldSize, distances);
		return newCollector;
	}

}
