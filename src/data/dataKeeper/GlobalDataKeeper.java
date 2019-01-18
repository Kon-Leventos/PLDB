package data.dataKeeper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesSchema;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesTable;
import data.dataPPL.pplTransition.AtomicChange;
import data.dataPPL.pplTransition.PlutarchParallelLivesTransition;
import data.dataPPL.pplTransition.TableChange;
import data.dataProccessing.Worker;
import phaseAnalyzer.commons.PhaseCollector;
import tableClustering.clusterExtractor.commons.ClusterCollector;

@SuppressWarnings("serial")
public class GlobalDataKeeper implements Serializable {
	private TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas;
	private TreeMap<Integer, PlutarchParallelLivesTransition> allPlutarchParallelLivesTransitions;
	private TreeMap<String, PlutarchParallelLivesTable> allTables;
	private ArrayList<AtomicChange> atomicChanges;
	private ArrayList<ClusterCollector> clusterCollectors;
	private String filename;
	private ArrayList<PhaseCollector> phaseCollectors;
	private String projectDataFolder;
	private TreeMap<String, TableChange> tableChanges;
	private TreeMap<String, TableChange> dualTableChanges;
	private String transitionsFile = "";

	public GlobalDataKeeper() {
	}

	public GlobalDataKeeper(final String filename, final String transitionsFile) {
		allPlutarchParallelLivesSchemas = new TreeMap<String, PlutarchParallelLivesSchema>();
		allTables = new TreeMap<String, PlutarchParallelLivesTable>();
		atomicChanges = new ArrayList<AtomicChange>();
		tableChanges = new TreeMap<String, TableChange>();
		dualTableChanges = new TreeMap<String, TableChange>();
		allPlutarchParallelLivesTransitions = new TreeMap<Integer, PlutarchParallelLivesTransition>();
		phaseCollectors = new ArrayList<PhaseCollector>();
		clusterCollectors = new ArrayList<ClusterCollector>();
		this.filename = filename;
		this.transitionsFile = transitionsFile;
	}

	public TreeMap<String, PlutarchParallelLivesSchema> getAllPlutarchParallelLivesSchemas() {
		return allPlutarchParallelLivesSchemas;
	}

	public TreeMap<String, PlutarchParallelLivesTable> getAllPlutarchParallelLivesTables() {
		return allTables;
	}

	public TreeMap<Integer, PlutarchParallelLivesTransition> getAllPlutarchParallelLivesTransitions() {
		return allPlutarchParallelLivesTransitions;
	}

	public TreeMap<String, TableChange> getAllTableChanges() {
		return tableChanges;
	}

	public ArrayList<AtomicChange> getAtomicChanges() {
		return atomicChanges;
	}

	public ArrayList<ClusterCollector> getClusterCollectors() {
		return this.clusterCollectors;
	}

	public String getDataFolder() {
		return projectDataFolder;
	}

	public ArrayList<PhaseCollector> getPhaseCollectors() {
		return this.phaseCollectors;
	}

	public TreeMap<String, TableChange> getTemporalTableChanges() {
		return dualTableChanges;
	}

	private void setAllPlutarchParallelLivesSchemas(final TreeMap<String, PlutarchParallelLivesSchema> temporalAllPlutarchParallelLivesSchemas) {
		allPlutarchParallelLivesSchemas = temporalAllPlutarchParallelLivesSchemas;
	}

	private void setAllPlutarchParallelLivesTables(final TreeMap<String, PlutarchParallelLivesTable> temporalAllTables) {
		allTables = temporalAllTables;
	}

	private void setAllPlutarchParallelLivesTransitions(final TreeMap<Integer, PlutarchParallelLivesTransition> allPlutarchParallelLivesTransitions) {
		this.allPlutarchParallelLivesTransitions = allPlutarchParallelLivesTransitions;
	}

	private void setAllTableChanges(final TreeMap<String, TableChange> temporalTableChanges) {
		tableChanges = temporalTableChanges;
	}

	private void setAtomicChanges(final ArrayList<AtomicChange> temporalAtomicChanges) {
		atomicChanges = temporalAtomicChanges;
	}

	public void setClusterCollectors(final ArrayList<ClusterCollector> clusterCollectors) {
		this.clusterCollectors = clusterCollectors;
	}

	public void setData() {
		final Worker dataWorker = new Worker(filename, transitionsFile);
		try {
			dataWorker.work();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setAllPlutarchParallelLivesSchemas(dataWorker.getAllPlutarchParallelLivesSchemas());
		setAllPlutarchParallelLivesTables(dataWorker.getAllPlutarchParallelLivesTables());
		setAllPlutarchParallelLivesTransitions(dataWorker.getAllPlutarchParallelLivesTransitions());
		setAllTableChanges(dataWorker.getTableChanges());
		setAtomicChanges(dataWorker.getAtomicChanges());
		setDataFolder(dataWorker.getDataFolder());
	}

	private void setDataFolder(final String temporalProjectDataFolder) {
		projectDataFolder = temporalProjectDataFolder;
	}

	public void setPhaseCollectors(final ArrayList<PhaseCollector> phaseCollectors) {
		this.phaseCollectors = phaseCollectors;
	}

	private void writeObject(final java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(allPlutarchParallelLivesSchemas);
		out.writeObject(allPlutarchParallelLivesTransitions);
		out.writeObject(allTables);
		out.writeObject(atomicChanges);
		out.writeObject(clusterCollectors);
		out.writeObject(filename);
		out.writeObject(phaseCollectors);
		out.writeObject(projectDataFolder);
		out.writeObject(tableChanges);
		out.writeObject(dualTableChanges);
		out.writeObject(transitionsFile);
	}

}
