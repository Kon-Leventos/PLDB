package logic.centralLogic;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import data.dataKeeper.GlobalDataKeeper;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;
import gui.tableElements.commons.MyTableModel;

public final class CentralLinker {
	private static String project;
	private static String projectName;
	private static String datasetTxt;
	private static String inputCsv;
	private static String outputAssessment1;
	private static String outputAssessment2;
	private static String transitionsFile;
	private static GlobalDataKeeper globalDataKeeper;
	private static String currentProject;
	private static Integer[] segmentSizeZoomArea = new Integer[4];
	private static String[] finalColumnsZoomArea;
	private static String[][] finalRowsZoomArea;
	private static Float timeWeight;
	private static Float changeWeight;
	private static Boolean preProcessingChange;
	private static Boolean preProcessingTime;
	private static Integer numberOfPhases;
	private static Integer numberOfClusters;
	private static Integer[] segmentSize = new Integer[4];
	private static String[] finalColumns;
	private static String[][] finalRows;
	private static boolean showingPld;
	private static ArrayList<Integer> selectedRows = new ArrayList<Integer>();
	private static MyTableModel zoomModel;
	private static int rowHeight = 1;
	private static int columnWidth = 1;
	private static int wholeCol = -1;
	private static int wholeColZoomArea = -1;
	private static int selectedColumnZoomArea = -1;
	private static ArrayList<String> selectedFromTree = new ArrayList<String>();
	private static int[] selectedRowsFromMouse;
	private static JvTable zoomAreaTable;
	private static MyTableModel generalModel;
	private static int selectedColumn = -1;
	private static JvTable lifeTimeTable;
	private static ArrayList<String> tablesSelected = new ArrayList<String>();
	private static String[] firstLevelUndoColumnsZoomArea;
	private static String[][] firstLevelUndoRowsZoomArea;
	private static Integer[] segmentSizeDetailedTable = new Integer[3];
	private static MyTableModel detailedModel;
	private static Double birthWeight;
	private static Double deathWeight;
	private static Double changeWeightCl;

	private CentralLinker() {}

	public static Double getBirthWeight() {
		return birthWeight;
	}

	public static Float getChangeWeight() {
		return changeWeight;
	}

	public static Double getChangeWeightCl() {
		return changeWeightCl;
	}

	public static int getColumnWidth() {
		return columnWidth;
	}

	public static String getCurrentProject() {
		return currentProject;
	}

	public static String getDatasetTxt() {
		return datasetTxt;
	}

	public static Double getDeathWeight() {
		return deathWeight;
	}

	public static MyTableModel getDetailedModel() {
		return detailedModel;
	}

	public static String[] getFinalColumns() {
		return finalColumns;
	}

	public static String[] getFinalColumnsZoomArea() {
		return finalColumnsZoomArea;
	}

	public static String[][] getFinalRows() {
		return finalRows;
	}

	public static String[][] getFinalRowsZoomArea() {
		return finalRowsZoomArea;
	}

	public static String[] getFirstLevelUndoColumnsZoomArea() {
		return firstLevelUndoColumnsZoomArea;
	}

	public static String[][] getFirstLevelUndoRowsZoomArea() {
		return firstLevelUndoRowsZoomArea;
	}

	public static MyTableModel getGeneralModel() {
		return generalModel;
	}

	public static GlobalDataKeeper getGlobalDataKeeper() {
		return globalDataKeeper;
	}

	public static String getInputCsv() {
		return inputCsv;
	}

	public static JvTable getLifeTimeTable() {
		return lifeTimeTable;
	}

	public static Integer getNumberOfClusters() {
		return numberOfClusters;
	}

	public static Integer getNumberOfPhases() {
		return numberOfPhases;
	}

	public static String getOutputAssessment1() {
		return outputAssessment1;
	}

	public static String getOutputAssessment2() {
		return outputAssessment2;
	}

	public static Boolean getPreProcessingChange() {
		return preProcessingChange;
	}

	public static Boolean getPreProcessingTime() {
		return preProcessingTime;
	}

	public static String getProject() {
		return project;
	}

	public static String getProjectName() {
		return projectName;
	}

	public static int getRowHeight() {
		return rowHeight;
	}

	public static Integer[] getSegmentSize() {
		return segmentSize;
	}

	public static Integer[] getSegmentSizeDetailedTable() {
		return segmentSizeDetailedTable;
	}

	public static Integer[] getSegmentSizeZoomArea() {
		return segmentSizeZoomArea;
	}

	public static int getSelectedColumn() {
		return selectedColumn;
	}

	public static int getSelectedColumnZoomArea() {
		return selectedColumnZoomArea;
	}

	public static ArrayList<String> getSelectedFromTree() {
		return selectedFromTree;
	}

	public static ArrayList<Integer> getSelectedRows() {
		return selectedRows;
	}

	public static int[] getSelectedRowsFromMouse() {
		return selectedRowsFromMouse;
	}

	public static ArrayList<String> getTablesSelected() {
		return tablesSelected;
	}

	public static Float getTimeWeight() {
		return timeWeight;
	}

	public static String getTransitionsFile() {
		return transitionsFile;
	}

	public static int getWholeCol() {
		return wholeCol;
	}

	public static int getWholeColZoomArea() {
		return wholeColZoomArea;
	}

	public static JvTable getZoomAreaTable() {
		return zoomAreaTable;
	}

	public static MyTableModel getZoomModel() {
		return zoomModel;
	}

	public static boolean isShowingPld() {
		return showingPld;
	}

	public static void keepDetailsForTesting(final String ending, final Gui theGui) {
		try {
			final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			final ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
			writeAllObjects(theGui, objectStream);
			objectStream.flush();
			objectStream.close();
			byteStream.close();
			final byte[] allData = byteStream.toByteArray();
			final FileOutputStream fileStream = new FileOutputStream(projectName + ending);
			final BufferedOutputStream bufferedStream = new BufferedOutputStream(fileStream);
			bufferedStream.write(allData);
			bufferedStream.flush();
			bufferedStream.close();
			fileStream.close();
			System.out.println(">> " + projectName + ending + " made!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private static void writeAllObjects(final Gui theGui, final ObjectOutputStream objectStream) throws IOException {
		writeObjectsFirstHalf(objectStream);
		writeObjectsLastHalf(theGui, objectStream);
	}

	private static void writeObjectsFirstHalf(final ObjectOutputStream objectStream) throws IOException {
		objectStream.writeObject(project);
		objectStream.writeObject(projectName);
		objectStream.writeObject(datasetTxt);
		objectStream.writeObject(inputCsv);
		objectStream.writeObject(outputAssessment1);
		objectStream.writeObject(outputAssessment2);
		objectStream.writeObject(transitionsFile);
		objectStream.writeObject(globalDataKeeper);
		objectStream.writeObject(segmentSizeZoomArea);
		objectStream.writeObject(finalColumnsZoomArea);
		objectStream.writeObject(finalRowsZoomArea);
		objectStream.writeObject(timeWeight);
		objectStream.writeObject(changeWeight);
		objectStream.writeObject(preProcessingChange);
		objectStream.writeObject(preProcessingTime);
		objectStream.writeObject(numberOfPhases);
		objectStream.writeObject(numberOfClusters);
		objectStream.writeObject(segmentSize);
		objectStream.writeObject(finalColumns);
		objectStream.writeObject(finalRows);
		objectStream.writeObject(showingPld);
		objectStream.writeObject(selectedRows);
	}
	
	private static void writeObjectsLastHalf(final Gui theGui, final ObjectOutputStream objectStream) throws IOException {
		objectStream.writeObject((zoomModel != null) ? zoomModel.toString() : "");
		objectStream.writeObject(rowHeight);
		objectStream.writeObject(columnWidth);
		objectStream.writeObject(wholeCol);
		objectStream.writeObject(wholeColZoomArea);
		objectStream.writeObject(selectedColumnZoomArea);
		objectStream.writeObject(selectedFromTree);
		objectStream.writeObject(selectedRowsFromMouse);
		objectStream.writeObject((zoomAreaTable != null) ? zoomAreaTable.toString() : "");
		objectStream.writeObject((generalModel != null) ? generalModel.toString() : "");
		objectStream.writeObject(selectedColumn);
		objectStream.writeObject((lifeTimeTable != null) ? lifeTimeTable.toString() : "");
		objectStream.writeObject(tablesSelected);
		objectStream.writeObject(firstLevelUndoColumnsZoomArea);
		objectStream.writeObject(firstLevelUndoRowsZoomArea);
		objectStream.writeObject(segmentSizeDetailedTable);
		objectStream.writeObject((detailedModel != null) ? detailedModel.toString() : "");
		objectStream.writeObject(birthWeight);
		objectStream.writeObject(deathWeight);
		objectStream.writeObject(changeWeightCl);
		objectStream.writeObject(theGui.toString());
	}

	public static void setBirthWeight(final Double birthWeight) {
		CentralLinker.birthWeight = birthWeight;
	}

	public static void setChangeWeight(final Float changeWeight) {
		CentralLinker.changeWeight = changeWeight;
	}

	public static void setChangeWeightCl(final Double changeWeightCl) {
		CentralLinker.changeWeightCl = changeWeightCl;
	}

	public static void setColumnWidth(final int columnWidth) {
		CentralLinker.columnWidth = columnWidth;
	}

	public static void setCurrentProject(final String currentProject) {
		CentralLinker.currentProject = currentProject;
	}

	public static void setDatasetTxt(final String datasetTxt) {
		CentralLinker.datasetTxt = datasetTxt;
	}

	public static void setDeathWeight(final Double deathWeight) {
		CentralLinker.deathWeight = deathWeight;
	}

	public static void setDetailedModel(final MyTableModel detailedModel) {
		CentralLinker.detailedModel = detailedModel;
	}

	public static void setFinalColumns(final String[] finalColumns) {
		CentralLinker.finalColumns = finalColumns;
	}

	public static void setFinalColumnsZoomArea(final String[] finalColumnsZoomArea) {
		CentralLinker.finalColumnsZoomArea = finalColumnsZoomArea;
	}

	public static void setFinalRows(final String[][] finalRows) {
		CentralLinker.finalRows = finalRows;
	}

	public static void setFinalRowsZoomArea(final String[][] finalRowsZoomArea) {
		CentralLinker.finalRowsZoomArea = finalRowsZoomArea;
	}

	public static void setFirstLevelUndoColumnsZoomArea(final String[] firstLevelUndoColumnsZoomArea) {
		CentralLinker.firstLevelUndoColumnsZoomArea = firstLevelUndoColumnsZoomArea;
	}

	public static void setFirstLevelUndoRowsZoomArea(final String[][] firstLevelUndoRowsZoomArea) {
		CentralLinker.firstLevelUndoRowsZoomArea = firstLevelUndoRowsZoomArea;
	}

	public static void setGeneralModel(final MyTableModel generalModel) {
		CentralLinker.generalModel = generalModel;
	}

	public static void setGlobalDataKeeper(final GlobalDataKeeper globalDataKeeper) {
		CentralLinker.globalDataKeeper = globalDataKeeper;
	}

	public static void setInputCsv(final String inputCsv) {
		CentralLinker.inputCsv = inputCsv;
	}

	public static void setLifeTimeTable(final JvTable lifeTimeTable) {
		CentralLinker.lifeTimeTable = lifeTimeTable;
	}

	public static void setNumberOfClusters(final Integer numberOfClusters) {
		CentralLinker.numberOfClusters = numberOfClusters;
	}

	public static void setNumberOfPhases(final Integer numberOfPhases) {
		CentralLinker.numberOfPhases = numberOfPhases;
	}

	public static void setOutputAssessment1(final String outputAssessment1) {
		CentralLinker.outputAssessment1 = outputAssessment1;
	}

	public static void setOutputAssessment2(final String outputAssessment2) {
		CentralLinker.outputAssessment2 = outputAssessment2;
	}

	public static void setPreProcessingChange(final Boolean preProcessingChange) {
		CentralLinker.preProcessingChange = preProcessingChange;
	}

	public static void setPreProcessingTime(final Boolean preProcessingTime) {
		CentralLinker.preProcessingTime = preProcessingTime;
	}

	public static void setProject(final String project) {
		CentralLinker.project = project;
	}

	public static void setProjectName(final String projectName) {
		CentralLinker.projectName = projectName;
	}

	public static void setRowHeight(final int rowHeight) {
		CentralLinker.rowHeight = rowHeight;
	}

	public static void setSegmentSize(final Integer[] segmentSize) {
		CentralLinker.segmentSize = segmentSize;
	}

	public static void setSegmentSizeDetailedTable(final Integer[] segmentSizeDetailedTable) {
		CentralLinker.segmentSizeDetailedTable = segmentSizeDetailedTable;
	}

	public static void setSegmentSizeZoomArea(final Integer[] segmentSizeZoomArea) {
		CentralLinker.segmentSizeZoomArea = segmentSizeZoomArea;
	}

	public static void setSelectedColumn(final int selectedColumn) {
		CentralLinker.selectedColumn = selectedColumn;
	}

	public static void setSelectedColumnZoomArea(final int selectedColumnZoomArea) {
		CentralLinker.selectedColumnZoomArea = selectedColumnZoomArea;
	}

	public static void setSelectedFromTree(final ArrayList<String> selectedFromTree) {
		CentralLinker.selectedFromTree = selectedFromTree;
	}

	public static void setSelectedRows(final ArrayList<Integer> selectedRows) {
		CentralLinker.selectedRows = selectedRows;
	}

	public static void setSelectedRowsFromMouse(final int[] selectedRowsFromMouse) {
		CentralLinker.selectedRowsFromMouse = selectedRowsFromMouse;
	}

	public static void setShowingPld(final boolean showingPld) {
		CentralLinker.showingPld = showingPld;
	}

	public static void setTablesSelected(final ArrayList<String> tablesSelected) {
		CentralLinker.tablesSelected = tablesSelected;
	}

	public static void setTimeWeight(final Float timeWeight) {
		CentralLinker.timeWeight = timeWeight;
	}

	public static void setTransitionsFile(final String transitionsFile) {
		CentralLinker.transitionsFile = transitionsFile;
	}

	public static void setWholeCol(final int wholeCol) {
		CentralLinker.wholeCol = wholeCol;
	}

	public static void setWholeColZoomArea(final int wholeColZoomArea) {
		CentralLinker.wholeColZoomArea = wholeColZoomArea;
	}

	public static void setZoomAreaTable(final JvTable zoomAreaTable) {
		CentralLinker.zoomAreaTable = zoomAreaTable;
	}

	public static void setZoomModel(final MyTableModel zoomModel) {
		CentralLinker.zoomModel = zoomModel;
	}

}
