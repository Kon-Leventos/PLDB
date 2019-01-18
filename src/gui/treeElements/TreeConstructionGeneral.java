package gui.treeElements;

import java.util.Map;
import java.util.TreeMap;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import data.dataKeeper.GlobalDataKeeper;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesSchema;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesTable;

class TreeConstructionGeneral implements ITreeConstruction {
	private GlobalDataKeeper dataKeeper;

	protected TreeConstructionGeneral(final GlobalDataKeeper dataKeeper) {
		this.dataKeeper = dataKeeper;
	}

	@Override
	public JTree constructTree() {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Versions");
		final TreeMap<String, PlutarchParallelLivesSchema> schemas = dataKeeper.getAllPlutarchParallelLivesSchemas();
		for (Map.Entry<String, PlutarchParallelLivesSchema> schema : schemas.entrySet()) {
			final DefaultMutableTreeNode node = new DefaultMutableTreeNode(schema.getKey());
			top.add(node);
			final TreeMap<String, PlutarchParallelLivesTable> tables = schema.getValue().getTables();
			for (Map.Entry<String, PlutarchParallelLivesTable> table : tables.entrySet()) {
				final DefaultMutableTreeNode tableNode = new DefaultMutableTreeNode(table.getKey());
				node.add(tableNode);
			}
		}
		final JTree treeToConstruct = new JTree(top);
		return treeToConstruct;
	}

}
