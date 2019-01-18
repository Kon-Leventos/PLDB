package gui.treeElements;

import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import data.dataKeeper.GlobalDataKeeper;
import tableClustering.clusterExtractor.commons.Cluster;

class TreeConstructionPhasesWithClusters implements ITreeConstruction {
	private GlobalDataKeeper dataKeeper;

	protected TreeConstructionPhasesWithClusters(final GlobalDataKeeper dataKeeper) {
		this.dataKeeper = dataKeeper;
	}

	@Override
	public JTree constructTree() {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Clusters");
		final ArrayList<Cluster> clusters = dataKeeper.getClusterCollectors().get(0).getClusters();
		for (int i = 0; i < clusters.size(); i++) {
			final DefaultMutableTreeNode node = new DefaultMutableTreeNode("Cluster " + i);
			top.add(node);
			final ArrayList<String> tables = clusters.get(i).getNamesOfTables();
			for (String tableName : tables) {
				final DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(tableName);
				node.add(treeNode);
			}
		}
		final JTree treeToConstruct = new JTree(top);
		return treeToConstruct;
	}

}
