package logic.centralLogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.antlr.v4.runtime.RecognitionException;
import data.dataKeeper.GlobalDataKeeper;
import gui.mainEngine.Gui;
import logic.utilities.fillers.TreeFillerFactory;

public final class DataImporter {
	
	private DataImporter() {}

	private static void checkBufferedLine(final String bufferedLine) {
		if (bufferedLine.contains("Project-name")) {
			final String[] projectNameTable = bufferedLine.split(":");
			CentralLinker.setProjectName(projectNameTable[1]);
		} else if (bufferedLine.contains("Dataset-txt")) {
			final String[] datasetTxtTable = bufferedLine.split(":");
			CentralLinker.setDatasetTxt(datasetTxtTable[1]);
		} else if (bufferedLine.contains("Input-csv")) {
			final String[] inputCsvTable = bufferedLine.split(":");
			CentralLinker.setInputCsv(inputCsvTable[1]);
		} else if (bufferedLine.contains("Assessement1-output")) {
			final String[] outputAss1 = bufferedLine.split(":");
			CentralLinker.setOutputAssessment1(outputAss1[1]);
		} else if (bufferedLine.contains("Assessement2-output")) {
			final String[] outputAss2 = bufferedLine.split(":");
			CentralLinker.setOutputAssessment2(outputAss2[1]);
		} else if (bufferedLine.contains("Transition-xml")) {
			final String[] transitionXmlTable = bufferedLine.split(":");
			CentralLinker.setTransitionsFile(transitionXmlTable[1]);
		}
	}

	public static void importData(final String fileName, final Gui theGui) throws IOException, RecognitionException {
		final BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		String bufferedLine;
		while (true) {
			bufferedLine = bufferedReader.readLine();
			if (bufferedLine == null) {
				break;
			}
			checkBufferedLine(bufferedLine);
		}
		bufferedReader.close();
		printElements(fileName);
		TableFiller.fillTable(theGui);
		TreeFillerFactory.makeTreeFiller("Tree").fillTree(theGui);
		CentralLinker.setCurrentProject(fileName);
		CentralLinker.keepDetailsForTesting("_new.txt", theGui);
	}

	private static void printElements(final String fileName) {
		System.out.println("Project Name:" + CentralLinker.getProjectName());
		System.out.println("Dataset txt:" + CentralLinker.getDatasetTxt());
		System.out.println("Input Csv:" + CentralLinker.getInputCsv());
		System.out.println("Output Assessment1:" + CentralLinker.getOutputAssessment1());
		System.out.println("Output Assessment2:" + CentralLinker.getOutputAssessment2());
		System.out.println("Transitions File:" + CentralLinker.getTransitionsFile());
		CentralLinker.setGlobalDataKeeper(new GlobalDataKeeper(CentralLinker.getDatasetTxt(), CentralLinker.getTransitionsFile()));
		CentralLinker.getGlobalDataKeeper().setData();
		System.out.println(CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables().size());
		System.out.println(fileName);
	}

}
