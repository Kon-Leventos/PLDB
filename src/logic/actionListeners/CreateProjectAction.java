package logic.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.antlr.v4.runtime.RecognitionException;
import gui.dialogs.CreateProjectJDialog;
import gui.mainEngine.Gui;
import logic.centralLogic.CentralLinker;
import logic.centralLogic.DataImporter;

class CreateProjectAction implements ActionListener {
	private Gui theGui;

	public CreateProjectAction(final Gui theGui) {
		this.theGui = theGui;
	}

	@Override
	public void actionPerformed(final ActionEvent action) {
		final CreateProjectJDialog createProjectDialog = new CreateProjectJDialog("", "", "", "", "", "");
		createProjectDialog.setModal(true);
		createProjectDialog.setVisible(true);
		if (createProjectDialog.getConfirmation()) {
			createProjectDialog.setVisible(false);
			final File projectFile = createProjectDialog.getFile();
			System.out.println(projectFile.toString());
			CentralLinker.setProject(projectFile.getName());
			final String fileName = projectFile.toString();
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

}
