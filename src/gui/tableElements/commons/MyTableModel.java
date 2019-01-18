package gui.tableElements.commons;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] columns;
	private String[][] rows;

	public MyTableModel(final String[] columns, final String[][] rows) {
		this.columns = columns;
		this.rows = rows;
	}

	@Override
	public Class<? extends Object> getColumnClass(final int c) {
		final Object object = getValueAt(0, c);
		if (object == null) {
			return Object.class;
		}
		return getValueAt(0, c).getClass();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(final int col) {
		return columns[col];
	}

	@Override
	public int getRowCount() {
		return rows.length;
	}

	@Override
	public Object getValueAt(final int row, final int col) {
		return rows[row][col];
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		return false;
	}

	@Override
	public String toString() {
		String toReturn = "";
		for (String value : columns) {
			toReturn += value;
		}
		for (String[] array : rows) {
			for (String value : array) {
				toReturn += value;
			}
		}
		return toReturn;
	}

}
