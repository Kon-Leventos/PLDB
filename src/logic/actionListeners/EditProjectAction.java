package logic.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.antlr.v4.runtime.RecognitionException;
import gui.dialogs.CreateProjectJDialog;
import gui.mainEngine.Gui;
import logic.centralLogic.CentralLinker;
import logic.centralLogic.DataImporter;

class EditProjectAction implements ActionListener {
	private Gui theGui;

	public EditProjectAction(final Gui theGui) {
		this.theGui = theGui;
	}

	@Override
	public void actionPerformed(final ActionEvent action) {
		String fileName = null;
		final File directory = new File("filesHandler/inis");
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(directory);
		final int dialogValue = fileChooser.showDialog(theGui, "Open");
		if (dialogValue == JFileChooser.APPROVE_OPTION) {
			final File file = fileChooser.getSelectedFile();
			System.out.println(file.toString());
			CentralLinker.setProject(file.getName());
			fileName = file.toString();
			System.out.println("!!" + CentralLinker.getProject());
			readFromFileName(fileName);
			System.out.println(CentralLinker.getProjectName());
			final CreateProjectJDialog createProjectDialog = new CreateProjectJDialog(CentralLinker.getProjectName(), CentralLinker.getDatasetTxt(),
					CentralLinker.getInputCsv(), CentralLinker.getOutputAssessment1(), CentralLinker.getOutputAssessment2(),
					CentralLinker.getTransitionsFile());
			createProjectDialog.setModal(true);
			createProjectDialog.setVisible(true);
			createProjectDialog(createProjectDialog);
		}
	}

	private void createProjectDialog(final CreateProjectJDialog createProjectDialog) {
		final String fileName;
		final File file;
		if (createProjectDialog.getConfirmation()) {
			createProjectDialog.setVisible(false);
			file = createProjectDialog.getFile();
			System.out.println(file.toString());
			CentralLinker.setProject(file.getName());
			fileName = file.toString();
			System.out.println("!!" + CentralLinker.getProject());
			try {
				DataImporter.importData(fileName, theGui);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
			} catch (RecognitionException e) {
				JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
			}
		}
	}

	private void readFromFileName(final String fileName) {
		final BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(fileName));
			String line;
			while (true) {
				line = bufferedReader.readLine();
				if (line == null) {
					break;
				}
				checkIfLinesContains(line);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkIfLinesContains(final String line) {
		if (line.contains("Project-name")) {
			final String[] projectNameTable = line.split(":");
			CentralLinker.setProjectName(projectNameTable[1]);
		} else if (line.contains("Dataset-txt")) {
			final String[] datasetTxtTable = line.split(":");
			CentralLinker.setDatasetTxt(datasetTxtTable[1]);
		} else if (line.contains("Input-csv")) {
			final String[] inputCsvTable = line.split(":");
			CentralLinker.setInputCsv(inputCsvTable[1]);
		} else if (line.contains("Assessement1-output")) {
			final String[] outputAss1 = line.split(":");
			CentralLinker.setOutputAssessment1(outputAss1[1]);
		} else if (line.contains("Assessement2-output")) {
			final String[] outputAss2 = line.split(":");
			CentralLinker.setOutputAssessment2(outputAss2[1]);
		} else if (line.contains("Transition-xml")) {
			final String[] transitionXmlTable = line.split(":");
			CentralLinker.setTransitionsFile(transitionXmlTable[1]);
		}
	}

}
