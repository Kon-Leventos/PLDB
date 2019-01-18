package logic.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.dialogs.ProjectInfoDialog;
import logic.centralLogic.CentralLinker;

class InfoAction implements ActionListener {
	
	public InfoAction() {}

	@Override
	public void actionPerformed(final ActionEvent action) {
		if (!(CentralLinker.getCurrentProject() == null)) {
			System.out.println("Project Name:" + CentralLinker.getProjectName());
			System.out.println("Dataset txt:" + CentralLinker.getDatasetTxt());
			System.out.println("Input Csv:" + CentralLinker.getInputCsv());
			System.out.println("Output Assessment1:" + CentralLinker.getOutputAssessment1());
			System.out.println("Output Assessment2:" + CentralLinker.getOutputAssessment2());
			System.out.println("Transitions File:" + CentralLinker.getTransitionsFile());
			System.out.println("Schemas:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesSchemas().size());
			System.out.println("Transitions:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions().size());
			System.out.println("Tables:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables().size());
			final ProjectInfoDialog infoDialog = new ProjectInfoDialog(CentralLinker.getProjectName(), CentralLinker.getDatasetTxt(),
					CentralLinker.getInputCsv(), CentralLinker.getTransitionsFile(),
					CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesSchemas().size(),
					CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions().size(),
					CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables().size());
			infoDialog.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Select a Project first");
		}
	}

}
