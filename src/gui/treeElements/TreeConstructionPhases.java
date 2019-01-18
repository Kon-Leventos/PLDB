package gui.treeElements;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import data.dataKeeper.GlobalDataKeeper;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesSchema;
import data.dataPPL.pplTransition.PlutarchParallelLivesTransition;
import data.dataPPL.pplTransition.TableChange;
import phaseAnalyzer.commons.Phase;

class TreeConstructionPhases implements ITreeConstruction {
	private GlobalDataKeeper dataKeeper;

	protected TreeConstructionPhases(final GlobalDataKeeper dataKeeper) {
		this.dataKeeper = dataKeeper;
	}

	@Override
	public JTree constructTree() {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Phases");
		final TreeMap<String, PlutarchParallelLivesSchema> schemas = new TreeMap<String, PlutarchParallelLivesSchema>();
		final ArrayList<Phase> phases = dataKeeper.getPhaseCollectors().get(0).getPhases();
		for (int i = 0; i < phases.size(); i++) {
			final DefaultMutableTreeNode node = new DefaultMutableTreeNode("Phase " + i);
			top.add(node);
			final TreeMap<Integer, PlutarchParallelLivesTransition> transitions = phases.get(i).getPhasePPLTransitions();
			for (Map.Entry<Integer, PlutarchParallelLivesTransition> transition : transitions.entrySet()) {
				final DefaultMutableTreeNode transitionNode = new DefaultMutableTreeNode(transition.getKey());
				final ArrayList<TableChange> tableChanges = transition.getValue().getTableChanges();
				for (int j = 0; j < tableChanges.size(); j++) {
					final DefaultMutableTreeNode specificNode = new DefaultMutableTreeNode(tableChanges.get(j).getAffectedTableName());
					transitionNode.add(specificNode);
				}
				node.add(transitionNode);
				schemas.put(transition.getValue().getOldVersionName(),
						dataKeeper.getAllPlutarchParallelLivesSchemas().get(transition.getValue().getOldVersionName()));
				schemas.put(transition.getValue().getNewVersionName(),
						dataKeeper.getAllPlutarchParallelLivesSchemas().get(transition.getValue().getNewVersionName()));
			}
		}
		final JTree treeToConstruct = new JTree(top);
		return treeToConstruct;
	}

}
