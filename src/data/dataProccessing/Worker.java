package data.dataProccessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesSchema;
import data.dataPPL.pplSQLSchema.PlutarchParallelLivesTable;
import data.dataPPL.pplTransition.AtomicChange;
import data.dataPPL.pplTransition.PlutarchParallelLivesTransition;
import data.dataPPL.pplTransition.TableChange;
import gr.uoi.cs.daintiness.hecate.sql.Schema;
import gr.uoi.cs.daintiness.hecate.transitions.TransitionList;

public class Worker {
	private TreeMap<String, PlutarchParallelLivesSchema> allPlutarchParallelLivesSchemas;
	private TreeMap<Integer, PlutarchParallelLivesTransition> allPlutarchParallelLivesTransitions;
	private TreeMap<String, PlutarchParallelLivesTable> allPlutarchParallelLivesTables;
	private ArrayList<AtomicChange> atomicChanges;
	private String filename;
	private TreeMap<String, TableChange> tableChanges;
	private String transitionsFile;

	public Worker(final String filename, final String transitionsFile) {
		allPlutarchParallelLivesSchemas = new TreeMap<String, PlutarchParallelLivesSchema>();
		allPlutarchParallelLivesTables = new TreeMap<String, PlutarchParallelLivesTable>();
		atomicChanges = new ArrayList<AtomicChange>();
		tableChanges = new TreeMap<String, TableChange>();
		allPlutarchParallelLivesTransitions = new TreeMap<Integer, PlutarchParallelLivesTransition>();
		this.filename = filename;
		this.transitionsFile = transitionsFile;
	}

	private void calculateAll(final ImportSchemas filesToImportData) {
		ArrayList<Schema> hecateSchemas = new ArrayList<Schema>();
		hecateSchemas = filesToImportData.getAllHecateSchemas();
		final ArrayList<TransitionList> allTransitions = filesToImportData.getAllTransitions();
		final PlutarchParallelLivesSchemasConstruction schemas = new PlutarchParallelLivesSchemasConstruction(hecateSchemas);
		schemas.makePlutarchParallelLivesSchemas();
		allPlutarchParallelLivesSchemas = schemas.getAllPlutarchParallelLivesSchemas();
		final PlutarchParallelLivesTablesConstruction tables = new PlutarchParallelLivesTablesConstruction(allPlutarchParallelLivesSchemas);
		tables.makeAllPlutarchParallelLivesTables();
		allPlutarchParallelLivesTables = tables.getAllPlutarchParallelLivesTables();
		final AtomicChangeConstruction atomic = new AtomicChangeConstruction(allTransitions);
		atomic.makeAtomicChanges();
		atomicChanges = atomic.getAtomicChanges();
		final TableChangeConstruction tableChange = new TableChangeConstruction(atomicChanges, allPlutarchParallelLivesTables);
		tableChange.makeTableChanges();
		tableChanges = tableChange.getTableChanges();
		final PlutarchParallelLivesTransitionConstruction transitionChange = new PlutarchParallelLivesTransitionConstruction(
				allPlutarchParallelLivesSchemas, tableChanges);
		transitionChange.makePlutarchParallelLivesTransitions();
		allPlutarchParallelLivesTransitions = transitionChange.getAllPlutarchParallelLivesTransitions();
	}

	public TreeMap<String, PlutarchParallelLivesSchema> getAllPlutarchParallelLivesSchemas() {
		return allPlutarchParallelLivesSchemas;
	}

	public TreeMap<String, PlutarchParallelLivesTable> getAllPlutarchParallelLivesTables() {
		return allPlutarchParallelLivesTables;
	}

	public TreeMap<Integer, PlutarchParallelLivesTransition> getAllPlutarchParallelLivesTransitions() {
		return allPlutarchParallelLivesTransitions;
	}

	public ArrayList<AtomicChange> getAtomicChanges() {
		return atomicChanges;
	}

	public String getDataFolder() {
		return filename.replaceAll(".txt", "");
	}

	public TreeMap<String, TableChange> getTableChanges() {
		return tableChanges;
	}

	public void work() throws IOException {
		final ImportSchemas filesToImportData = new ImportSchemas(filename, transitionsFile);
		try {
			filesToImportData.loadDataset();
		} catch (IOException e) {
			e.printStackTrace();
		}
		calculateAll(filesToImportData);
	}

}
