package logic.centralLogic;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RowListener implements ListSelectionListener {
	
	public RowListener() {}
	
	@Override
	public void valueChanged(final ListSelectionEvent event) {
		if (event.getValueIsAdjusting()) {
			return;
		}
		final int selectedRow = CentralLinker.getLifeTimeTable().getSelectedRow();
		CentralLinker.getSelectedRows().add(selectedRow);
	}
}
