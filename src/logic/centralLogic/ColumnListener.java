package logic.centralLogic;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ColumnListener implements ListSelectionListener {
	
	public ColumnListener() {}
	
	@Override
	public void valueChanged(final ListSelectionEvent event) {
		if (event.getValueIsAdjusting()) {
			return;
		}
	}
}
