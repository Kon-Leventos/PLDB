package logic.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.antlr.v4.runtime.RecognitionException;
import gui.mainEngine.Gui;
import logic.centralLogic.CentralLinker;
import logic.centralLogic.DataImporter;

class LoadProjectAction implements ActionListener {
	private Gui theGui;

	public LoadProjectAction(final Gui theGui) {
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
		} else {
			return;
		}
		try {
			DataImporter.importData(fileName, theGui);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
			return;
		} catch (RecognitionException e) {
			JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
			return;
		}
	}

}
