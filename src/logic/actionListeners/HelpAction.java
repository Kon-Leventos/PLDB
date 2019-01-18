package logic.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

class HelpAction implements ActionListener {
	
	public HelpAction() {}

	@Override
	public void actionPerformed(final ActionEvent action) {
		final String message = "To open a project, you must select a .txt file that contains the names ONLY of "
				+ "the SQL files of the dataset that you want to use." + "\n" + "The .txt file must have EXACTLY the same name with the folder "
				+ "that contains the DDL Scripts of the dataset." + "\n" + "Both .txt file and dataset folder must be in the same folder.";
		JOptionPane.showMessageDialog(null, message);
	}

}
