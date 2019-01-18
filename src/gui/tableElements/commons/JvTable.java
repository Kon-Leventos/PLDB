package gui.tableElements.commons;

import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import data.dataKeeper.GlobalDataKeeper;

public class JvTable extends JTable implements Serializable {
	private static final long serialVersionUID = 1L;

	public JvTable(final TableModel dataModel) {
		super(dataModel);
	}

	public void notUniformlyDistributed(final GlobalDataKeeper globalDataKeeper) {
		for (int i = 0; i < super.getColumnCount(); i++) {
			if (i == 0) {
				super.getColumnModel().getColumn(0).setPreferredWidth(60);
			} else {
				final int tot = 750 / globalDataKeeper.getAllPlutarchParallelLivesTransitions().size();
				final int sizeOfColumn = globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(i - 1).getSize() * tot;
				super.getColumnModel().getColumn(i).setPreferredWidth(sizeOfColumn);
			}
		}
		firePropertyChange("uniformly", 1500, 5000);
	}

	public void setZoom(final int rowHeight, final int columnWidth) {
		for (int i = 0; i < super.getRowCount(); i++) {
			super.setRowHeight(i, rowHeight);
		}
		for (int i = 0; i < super.getColumnCount(); i++) {
			if (i == 0) {
				super.getColumnModel().getColumn(0).setPreferredWidth(columnWidth);
			} else {
				super.getColumnModel().getColumn(i).setPreferredWidth(columnWidth);
			}
		}
		firePropertyChange("zoom", 1500, 5000);
	}

	@Override
	public String toString() {
		String toReturn = "";
		toReturn += getColumnCount();
		toReturn += getRowCount();
		return toReturn;
	}

	public void uniformlyDistributed(final int columnWidth) {
		for (int i = 0; i < super.getColumnCount(); i++) {
			if (i == 0) {
				super.getColumnModel().getColumn(0).setPreferredWidth(86);
			} else {
				super.getColumnModel().getColumn(i).setPreferredWidth(1);
			}
		}
		firePropertyChange("uniformly", 1500, 5000);
	}

}
