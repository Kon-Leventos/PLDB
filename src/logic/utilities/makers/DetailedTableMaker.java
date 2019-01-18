package logic.utilities.makers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableCellRenderer;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;
import gui.tableElements.commons.MyTableModel;
import logic.centralLogic.CentralLinker;
import logic.centralLogic.ColumnListener;
import logic.centralLogic.RowListener;

class DetailedTableMaker implements IMaker {
	
	public DetailedTableMaker() {}

	public void makeSpecialtyTable(final String[] columns, final String[][] rows, final boolean levelized, final Gui theGui) {
		CentralLinker.setDetailedModel(new MyTableModel(columns, rows));
		final JvTable temporaryLifeTimeTable = new JvTable(CentralLinker.getDetailedModel());
		temporaryLifeTimeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		if (levelized) {
			firstCheckIfZero(temporaryLifeTimeTable);
		} else {
			secondCheckIfZero(temporaryLifeTimeTable);
		}
		temporaryLifeTimeTable.setName("LifeTimeTable");
		temporaryLifeTimeTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(final JTable table, final Object value, 
					final boolean isSelected, final boolean hasFocus, final int row, final int column) {
				final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				final String temporaryValue = (String) table.getValueAt(row, column);
				final String columnName = table.getColumnName(column);
				final Color frame = new Color(0, 0, 0);
				component.setForeground(frame);
				if (CentralLinker.getSelectedColumn() == 0) {
					if (isSelected) {
						final Color color = new Color(255, 69, 0, 100);
						component.setBackground(color);
						return component;
					}
				} else {
					if (isSelected && hasFocus) {
						component.setBackground(Color.YELLOW);
						return component;
					}
				}
				try {
					final int numericValue = Integer.parseInt(temporaryValue);
					checkComumnName(component, columnName, numericValue);
					return component;
				} catch (Exception event) {
					return checkTemporaryValue(levelized, component, temporaryValue, columnName);
				}
			}

			private Component checkTemporaryValue(final boolean levelized, final Component component,
					final String temporaryValue, final String columnName) {
				if (temporaryValue.equals("")) {
					component.setBackground(Color.black);
					return component;
				} else {
					if (columnName.contains("v")) {
						component.setBackground(Color.lightGray);
						if (!levelized) {
							setToolTipText(columnName);
						}
					} else {
						final Color tableNameColor = new Color(205, 175, 149);
						component.setBackground(tableNameColor);
					}
					return component;
				}
			}

			private void checkComumnName(final Component component, final String columnName, final int numericValue) {
				checkColumnName(component, columnName, numericValue);
			}

			private void checkColumnName(final Component component, final String columnName, final int numericValue) {
				Color insertionColor;
				if (columnName.equals("I")) {
					insertionColor = checkNumericValue(numericValue);
					component.setBackground(insertionColor);
				}
				if (columnName.equals("U")) {
					insertionColor = checkSecondNumericValue(numericValue);
					component.setBackground(insertionColor);
				}
				if (columnName.equals("D")) {
					insertionColor = checkThirdNumericValue(numericValue);
					component.setBackground(insertionColor);
				}
			}

			private Color checkThirdNumericValue(final int numericValue) {
				final Color insertionColor;
				if (numericValue == 0) {
					insertionColor = new Color(255, 231, 186);
				} else if (numericValue > 0 && numericValue <= CentralLinker.getSegmentSizeDetailedTable()[2]) {
					insertionColor = new Color(255, 106, 106);
				} else if (numericValue > CentralLinker.getSegmentSizeDetailedTable()[2]
						&& numericValue <= 2 * CentralLinker.getSegmentSizeDetailedTable()[2]) {
					insertionColor = new Color(255, 0, 0);
				} else if (numericValue > 2 * CentralLinker.getSegmentSizeDetailedTable()[2]
						&& numericValue <= 3 * CentralLinker.getSegmentSizeDetailedTable()[2]) {
					insertionColor = new Color(205, 0, 0);
				} else {
					insertionColor = new Color(139, 0, 0);
				}
				return insertionColor;
			}

			private Color checkSecondNumericValue(final int numericValue) {
				final Color insertionColor;
				if (numericValue == 0) {
					insertionColor = new Color(255, 231, 186);
				} else if (numericValue > 0 && numericValue <= CentralLinker.getSegmentSizeDetailedTable()[1]) {
					insertionColor = new Color(176, 226, 255);
				} else if (numericValue > CentralLinker.getSegmentSizeDetailedTable()[1]
						&& numericValue <= 2 * CentralLinker.getSegmentSizeDetailedTable()[1]) {
					insertionColor = new Color(92, 172, 238);
				} else if (numericValue > 2 * CentralLinker.getSegmentSizeDetailedTable()[1]
						&& numericValue <= 3 * CentralLinker.getSegmentSizeDetailedTable()[1]) {
					insertionColor = new Color(28, 134, 238);
				} else {
					insertionColor = new Color(16, 78, 139);
				}
				return insertionColor;
			}

			private Color checkNumericValue(final int numericValue) {
				final Color insertionColor;
				if (numericValue == 0) {
					insertionColor = new Color(255, 231, 186);
				} else if (numericValue > 0 && numericValue <= CentralLinker.getSegmentSizeDetailedTable()[0]) {
					insertionColor = new Color(193, 255, 193);
				} else if (numericValue > CentralLinker.getSegmentSizeDetailedTable()[0]
						&& numericValue <= 2 * CentralLinker.getSegmentSizeDetailedTable()[0]) {
					insertionColor = new Color(84, 255, 159);
				} else if (numericValue > 2 * CentralLinker.getSegmentSizeDetailedTable()[0]
						&& numericValue <= 3 * CentralLinker.getSegmentSizeDetailedTable()[0]) {
					insertionColor = new Color(0, 201, 87);
				} else {
					insertionColor = new Color(0, 100, 0);
				}
				return insertionColor;
			}
		});
		managePanel(theGui, temporaryLifeTimeTable);
	}

	private static void managePanel(final Gui theGui, final JvTable temporaryLifeTimeTable) {
		temporaryLifeTimeTable.setOpaque(true);
		temporaryLifeTimeTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		temporaryLifeTimeTable.getSelectionModel().addListSelectionListener(new RowListener());
		temporaryLifeTimeTable.getColumnModel().getSelectionModel().addListSelectionListener(new ColumnListener());
		final JScrollPane detailedScrollPane = new JScrollPane();
		detailedScrollPane.setViewportView(temporaryLifeTimeTable);
		detailedScrollPane.setAlignmentX(0);
		detailedScrollPane.setAlignmentY(0);
		detailedScrollPane.setBounds(0, 0, 1280, 650);
		detailedScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		detailedScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		detailedScrollPane.setCursor(theGui.getCursor());
		final JDialog detailedDialog = new JDialog();
		detailedDialog.setBounds(100, 100, 1300, 700);
		final JPanel panelToAdd = new JPanel();
		final GroupLayout glPanel = new GroupLayout(panelToAdd);
		glPanel.setHorizontalGroup(glPanel.createParallelGroup(Alignment.LEADING));
		glPanel.setVerticalGroup(glPanel.createParallelGroup(Alignment.LEADING));
		panelToAdd.setLayout(glPanel);
		panelToAdd.add(detailedScrollPane);
		detailedDialog.getContentPane().add(panelToAdd);
		detailedDialog.setVisible(true);
	}

	private static void secondCheckIfZero(final JvTable temporaryLifeTimeTable) {
		for (int index = 0; index < temporaryLifeTimeTable.getColumnCount(); index++) {
			if (index == 0) {
				temporaryLifeTimeTable.getColumnModel().getColumn(0).setPreferredWidth(150);
				temporaryLifeTimeTable.getColumnModel().getColumn(0).setMaxWidth(150);
				temporaryLifeTimeTable.getColumnModel().getColumn(0).setMinWidth(150);
			} else {

				temporaryLifeTimeTable.getColumnModel().getColumn(index).setPreferredWidth(20);
				temporaryLifeTimeTable.getColumnModel().getColumn(index).setMaxWidth(20);
				temporaryLifeTimeTable.getColumnModel().getColumn(index).setMinWidth(20);
			}
		}
	}

	private static void firstCheckIfZero(final JvTable temporaryLifeTimeTable) {
		for (int index = 0; index < temporaryLifeTimeTable.getColumnCount(); index++) {
			if (index == 0) {
				temporaryLifeTimeTable.getColumnModel().getColumn(0).setPreferredWidth(150);
				temporaryLifeTimeTable.getColumnModel().getColumn(0).setMaxWidth(150);
				temporaryLifeTimeTable.getColumnModel().getColumn(0).setMinWidth(150);
			} else {
				if (temporaryLifeTimeTable.getColumnName(index).contains("v")) {
					temporaryLifeTimeTable.getColumnModel().getColumn(index).setPreferredWidth(100);
					temporaryLifeTimeTable.getColumnModel().getColumn(index).setMaxWidth(100);
					temporaryLifeTimeTable.getColumnModel().getColumn(index).setMinWidth(100);
				} else {
					temporaryLifeTimeTable.getColumnModel().getColumn(index).setPreferredWidth(25);
					temporaryLifeTimeTable.getColumnModel().getColumn(index).setMaxWidth(25);
					temporaryLifeTimeTable.getColumnModel().getColumn(index).setMinWidth(25);
				}
			}
		}
	}

}
