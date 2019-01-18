package tableClustering.clusterExtractor.analysis;

public class ClusterExtractorFactory {

	public IClusterExtractor createClusterExtractor(String concreteClassName) {
		if (concreteClassName.equals("AgglomerativeClusterExtractor")) {
			return new AgglomerativeClusterExtractor();
		}
		return null;
	}

}
