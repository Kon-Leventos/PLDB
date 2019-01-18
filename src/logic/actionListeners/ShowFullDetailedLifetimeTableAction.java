package logic.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import gui.mainEngine.Gui;
import gui.tableElements.tableConstructors.AbstractTableConstruction;
import gui.tableElements.tableConstructors.TableConstructionFactory;
import logic.centralLogic.CentralLinker;
import logic.utilities.makers.TableMakerFactory;

class ShowFullDetailedLifetimeTableAction implements ActionListener {
	private Gui theGui;

	public ShowFullDetailedLifetimeTableAction(final Gui theGui) {
		this.theGui = theGui;
	}

	@Override
	public void actionPerformed(final ActionEvent action) {
		if (!(CentralLinker.getCurrentProject() == null)) {
			final AbstractTableConstruction table = TableConstructionFactory.makeTableConstruction("Squares", CentralLinker.getGlobalDataKeeper(), null, 0);
			final String[] columns = table.constructColumns();
			final String[][] rows = table.constructRows();
			CentralLinker.setSegmentSizeDetailedTable(table.getSegmentSize());
			theGui.getTabbedPane().setSelectedIndex(0);
			TableMakerFactory.makeTableMaker("Detail").makeSpecialtyTable(columns, rows, true, theGui);
		} else {
			JOptionPane.showMessageDialog(null, "Select a Project first");
			return;
		}
	}

}
