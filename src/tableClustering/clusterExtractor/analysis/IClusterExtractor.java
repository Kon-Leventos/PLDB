package tableClustering.clusterExtractor.analysis;

import data.dataKeeper.GlobalDataKeeper;
import tableClustering.clusterExtractor.commons.ClusterCollector;

public interface IClusterExtractor {

	public ClusterCollector extractAtMostKClusters(GlobalDataKeeper dataKeeper, int numberOfClustersClusters, Double birthWeight, Double deathWeight,
			Double changeWeight);

}
