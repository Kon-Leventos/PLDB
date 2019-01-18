package gui.tableElements.tableRenderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import data.dataKeeper.GlobalDataKeeper;
import gui.mainEngine.Gui;

public class TableRendererInsertDeleteUpdate extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private String description = "";
	private String[][] finalRows;
	private GlobalDataKeeper globalDataKeeper = new GlobalDataKeeper();
	private Gui gui;
	private Integer[] segmentSize = new Integer[3];
	private int selectedColumn;
	private int wholeCol = -1;

	public TableRendererInsertDeleteUpdate(final Gui gui, final String[][] finalRows, final GlobalDataKeeper globalDataKeeper,
			final Integer[] segmentSize) {
		this.finalRows = finalRows;
		this.globalDataKeeper = globalDataKeeper;
		this.segmentSize = segmentSize;
		this.gui = gui;
	}

	private void chechIfSelected(final int row, final Component component) {
		final Color color = new Color(255, 69, 0, 100);
		component.setBackground(color);
		String description = "Table:" + finalRows[row][0] + "\n";
		description = description + "Birth Version Name:"
				+ globalDataKeeper.getAllPlutarchParallelLivesTables().get(finalRows[row][0]).getBirth() + "\n";
		description = description + "Birth Version ID:"
				+ globalDataKeeper.getAllPlutarchParallelLivesTables().get(finalRows[row][0]).getBirthVersionIdentification() + "\n";
		description = description + "Death Version Name:"
				+ globalDataKeeper.getAllPlutarchParallelLivesTables().get(finalRows[row][0]).getDeath() + "\n";
		description = description + "Death Version ID:"
				+ globalDataKeeper.getAllPlutarchParallelLivesTables().get(finalRows[row][0]).getDeathVersionIdentification() + "\n";
		description = description + "Total Changes:"
				+ globalDataKeeper.getAllPlutarchParallelLivesTables().get(finalRows[row][0]).getTotalChanges() + "\n";
		gui.setDescription(description);
	}

	private void checkIfHasFocus(final JTable table, final int row, final int column, final Component component) {
		String description = "";
		if (!table.getColumnName(column).contains("Table name")) {
			description = "Table:" + finalRows[row][0] + "\n";
			description = description + "Old Version Name:"
					+ globalDataKeeper.getAllPlutarchParallelLivesTransitions().get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()
					+ "\n";
			description = description + "New Version Name:"
					+ globalDataKeeper.getAllPlutarchParallelLivesTransitions().get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()
					+ "\n";
			if (globalDataKeeper.getAllPlutarchParallelLivesTables().get(finalRows[row][0]).getTableChanges()
					.getAtomicChangeForSpecificTransition(Integer.parseInt(table.getColumnName(column))) != null) {
				description = makeDescription(table, row, column, description);
			} else {
				description = description + "Transition Changes:0" + "\n";
				description = description + "Additions:0" + "\n";
				description = description + "Deletions:0" + "\n";
				description = description + "Updates:0" + "\n";
			}
			gui.setDescription(description);
		}
		Color cl = new Color(255, 69, 0, 100);
		component.setBackground(cl);
	}

	private Color checkIfNumericValue(final int numericValue) {
		final Color insersionColor;
		if (numericValue == 0) {
			insersionColor = new Color(154, 205, 50, 200);
		} else if (numericValue > 0 && numericValue <= segmentSize[1]) {
			insersionColor = new Color(176, 226, 255);
		} else if (numericValue > segmentSize[1] && numericValue <= 2 * segmentSize[1]) {
			insersionColor = new Color(92, 172, 238);
		} else if (numericValue > 2 * segmentSize[1] && numericValue <= 3 * segmentSize[1]) {
			insersionColor = new Color(28, 134, 238);
		} else {
			insersionColor = new Color(16, 78, 139);
		}
		return insersionColor;
	}

	private void checkIfWholeCol(final JTable table, final int column, final Component component) {
		String description = "Transition ID:" + table.getColumnName(column) + "\n";
		description = description + "Old Version Name:"
				+ globalDataKeeper.getAllPlutarchParallelLivesTransitions().get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()
				+ "\n";
		description = description + "New Version Name:"
				+ globalDataKeeper.getAllPlutarchParallelLivesTransitions().get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()
				+ "\n";
		description = description + "Transition Changes:" + globalDataKeeper.getAllPlutarchParallelLivesTransitions()
				.get(Integer.parseInt(table.getColumnName(column))).getChangeNumberOfSpecificTransition() + "\n";
		description = description + "Additions:" + globalDataKeeper.getAllPlutarchParallelLivesTransitions()
				.get(Integer.parseInt(table.getColumnName(column))).getAdditionNumberOfSpecificTransition() + "\n";
		description = description + "Deletions:" + globalDataKeeper.getAllPlutarchParallelLivesTransitions()
				.get(Integer.parseInt(table.getColumnName(column))).getDeletionNumberOfSpecificTransition() + "\n";
		description = description + "Updates:" + globalDataKeeper.getAllPlutarchParallelLivesTransitions()
				.get(Integer.parseInt(table.getColumnName(column))).getUpdateNumberOfSpecificTransition() + "\n";
		gui.setDescription(description);
		Color color = new Color(255, 69, 0, 100);
		component.setBackground(color);
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus,
			final int row, final int column) {
		final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		final String temporalValue = finalRows[row][column];
		final String columnName = table.getColumnName(column);
		final Color frameColor = new Color(0, 0, 0);
		this.setForeground(frameColor);
		this.setOpaque(true);
		if (column == wholeCol) {
			checkIfWholeCol(table, column, component);
			return component;
		} else if (selectedColumn == 0) {
			if (isSelected) {
				chechIfSelected(row, component);
				return component;
			}
		} else {
			if (isSelected && hasFocus) {
				checkIfHasFocus(table, row, column, component);
				return component;
			}
		}
		return tryNumericValue(component, temporalValue, columnName);
	}

	@Override
	public String getText() {
		return description;
	}

	private String makeDescription(final JTable table, final int row, final int column, String description) {
		description = description + "Transition Changes:" + globalDataKeeper.getAllPlutarchParallelLivesTables().get(finalRows[row][0])
				.getTableChanges().getAtomicChangeForSpecificTransition(Integer.parseInt(table.getColumnName(column))).size() + "\n";
		description = description + "Additions:" + globalDataKeeper.getAllPlutarchParallelLivesTables().get(finalRows[row][0])
				.getAdditionNumberOfSpecificTransition(Integer.parseInt(table.getColumnName(column))) + "\n";
		description = description + "Deletions:" + globalDataKeeper.getAllPlutarchParallelLivesTables().get(finalRows[row][0])
				.getDeletionNumberOfSpecificTransition(Integer.parseInt(table.getColumnName(column))) + "\n";
		description = description + "Updates:" + globalDataKeeper.getAllPlutarchParallelLivesTables().get(finalRows[row][0])
				.getUpdateNumberOfSpecificTransition(Integer.parseInt(table.getColumnName(column))) + "\n";
		return description;
	}

	public void setSelCol(final int selectedColumn) {
		this.selectedColumn = selectedColumn;
	}

	public void setWholeColumn(final int wholeColumn) {
		this.wholeCol = wholeColumn;
	}

	private Component tryNumericValue(final Component component, final String temporalValue, final String columnName) {
		try {
			final int numericValue = Integer.parseInt(temporalValue);
			Color insersionColor = null;
			setToolTipText(Integer.toString(numericValue));
			insersionColor = checkIfNumericValue(numericValue);
			component.setBackground(insersionColor);
			return component;
		} catch (NumberFormatException e) {
			if (temporalValue.equals("")) {
				component.setBackground(Color.GRAY);
				return component;
			} else {
				if (columnName.contains("v")) {
					component.setBackground(Color.lightGray);
					setToolTipText(columnName);
				} else {
					final Color tableNameColor = new Color(205, 175, 149);
					component.setBackground(tableNameColor);
				}
				return component;
			}
		}
	}

}
