package tableClustering.clusterExtractor.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@SuppressWarnings("serial")
public class ClusterCollector implements Serializable {
	private ArrayList<Cluster> clusters;

	public ClusterCollector() {
		clusters = new ArrayList<Cluster>();
	}

	public void addCluster(Cluster c) {
		this.clusters.add(c);
	}

	public ArrayList<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(ArrayList<Cluster> clusters) {
		this.clusters = clusters;
	}

	public void sortClustersByBirthDeath() {
		Collections.sort(clusters, new Comparator<Cluster>() {
			@Override
			public int compare(final Cluster object1, final Cluster object2) {
				if (object1.getBirth() < object2.getBirth()) {
					return -1;
				} else if (object1.getBirth() > object2.getBirth()) {
					return 1;
				} else {
					return Integer.compare(object1.getDeath(), object2.getDeath());
				}
			}
		});
	}

	@Override
	public String toString() {
		String toReturn = "";
		for (int i = 0; i < this.clusters.size(); i++) {
			toReturn = toReturn + this.clusters.get(i).toString();
		}
		return toReturn;
	}

}
