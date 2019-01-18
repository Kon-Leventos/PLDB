package logic.utilities.makers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;
import gui.tableElements.commons.MyTableModel;
import logic.centralLogic.CentralLinker;

class ZoomAreaTableMaker implements IMaker {

	public ZoomAreaTableMaker() {}

	public void makeSpecialtyTable(final String[] columnsNull, final String[][] rowsNull, final boolean levelNull, final Gui theGui) {
		CentralLinker.setShowingPld(false);
		final int numberOfColumns = CentralLinker.getFinalRowsZoomArea()[0].length;
		final int numberOfRows = CentralLinker.getFinalRowsZoomArea().length;
		final String[][] rowsZoom = new String[numberOfRows][numberOfColumns];
		for (int index = 0; index < numberOfRows; index++) {
			rowsZoom[index][0] = CentralLinker.getFinalRowsZoomArea()[index][0];

		}
		CentralLinker.setZoomModel(new MyTableModel(CentralLinker.getFinalColumnsZoomArea(), rowsZoom));
		final JvTable zoomTable = setTableWidth();
		zoomTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, 
					final boolean hasFocus, final int row, final int column) {
				final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				final String temporaryValue = CentralLinker.getFinalRowsZoomArea()[row][column];
				final String columnName = table.getColumnName(column);
				final Color frame = new Color(0, 0, 0);
				component.setForeground(frame);
				if (column == CentralLinker.getWholeColZoomArea()) {
					return setFirstDescription(theGui, rowsZoom, table, column, component);
				} else if (CentralLinker.getSelectedColumnZoomArea() == 0) {
					if (isSelected) {
						final String description = setSecondDescription(table, row);
						theGui.getDescriptionText().setText(description);
						final Color color = new Color(255, 69, 0, 100);
						component.setBackground(color);
						return component;
					}
				} else {
					if (isSelected && hasFocus) {
						String description = "";
						if (!table.getColumnName(column).contains("Table name")) {
							description = setThirdDescription(table, row, column);
							theGui.getDescriptionText().setText(description);
						}
						final Color color = new Color(255, 69, 0, 100);
						component.setBackground(color);
						return component;
					}
				}
				try {
					tryBlock(component, temporaryValue);
					return component;
				} catch (Exception exception) {
					return checkTemporaryValue(component, temporaryValue, columnName);
				}
			}

			private void tryBlock(final Component component, final String temporaryValue) {
				final int numericValue = Integer.parseInt(temporaryValue);
				Color insersionColor = null;
				setToolTipText(Integer.toString(numericValue));
				insersionColor = checkNumericValue(numericValue);
				component.setBackground(insersionColor);
			}

			private Component checkTemporaryValue(final Component component, final String temporaryValue,
					final String columnName) {
				if (temporaryValue.equals("")) {
					component.setBackground(Color.DARK_GRAY);
					return component;
				} else {
					if (columnName.contains("v")) {
						component.setBackground(Color.lightGray);
						setToolTipText(columnName);
					} else {
						Color tableNameColor = new Color(205, 175, 149);
						component.setBackground(tableNameColor);
					}
					return component;
				}
			}

			private String setThirdDescription(final JTable table, final int row, final int column) {
				String description;
				description = "Table:" + CentralLinker.getFinalRowsZoomArea()[row][0] + "\n";
				description = description + "Old Version Name:" + CentralLinker.getGlobalDataKeeper()
						.getAllPlutarchParallelLivesTransitions().get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()
						+ "\n";
				description = description + "New Version Name:" + CentralLinker.getGlobalDataKeeper()
						.getAllPlutarchParallelLivesTransitions().get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()
						+ "\n";
				if (CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getTableChanges()
						.getAtomicChangeForSpecificTransition(Integer.parseInt(table.getColumnName(column))) != null) {
					description = setThirdDescription(table, row, column, description);
				} else {
					description = description + "Transition Changes:0" + "\n";
					description = description + "Additions:0" + "\n";
					description = description + "Deletions:0" + "\n";
					description = description + "Updates:0" + "\n";
				}
				return description;
			}

			private Color checkNumericValue(final int numericValue) {
				final Color insersionColor;
				if (numericValue == 0) {
					insersionColor = new Color(0, 100, 0);
				} else if (numericValue > 0 && numericValue <= CentralLinker.getSegmentSizeZoomArea()[3]) {
					insersionColor = new Color(176, 226, 255);
				} else if (numericValue > CentralLinker.getSegmentSizeZoomArea()[3]
						&& numericValue <= 2 * CentralLinker.getSegmentSizeZoomArea()[3]) {
					insersionColor = new Color(92, 172, 238);
				} else if (numericValue > 2 * CentralLinker.getSegmentSizeZoomArea()[3]
						&& numericValue <= 3 * CentralLinker.getSegmentSizeZoomArea()[3]) {
					insersionColor = new Color(28, 134, 238);
				} else {
					insersionColor = new Color(16, 78, 139);
				}
				return insersionColor;
			}

			private String setThirdDescription(final JTable table, final int row, final int column,
					String description) {
				description = description + "Transition Changes:"
						+ CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
								.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getTableChanges()
								.getAtomicChangeForSpecificTransition(Integer.parseInt(table.getColumnName(column))).size()
						+ "\n";
				description = description + "Additions:"
						+ CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
								.get(CentralLinker.getFinalRowsZoomArea()[row][0])
								.getAdditionNumberOfSpecificTransition(Integer.parseInt(table.getColumnName(column)))
						+ "\n";
				description = description + "Deletions:"
						+ CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
								.get(CentralLinker.getFinalRowsZoomArea()[row][0])
								.getDeletionNumberOfSpecificTransition(Integer.parseInt(table.getColumnName(column)))
						+ "\n";
				description = description + "Updates:"
						+ CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
								.get(CentralLinker.getFinalRowsZoomArea()[row][0])
								.getUpdateNumberOfSpecificTransition(Integer.parseInt(table.getColumnName(column)))
						+ "\n";
				return description;
			}

			private String setSecondDescription(final JTable table, final int row) {
				String description = "Table:" + CentralLinker.getFinalRowsZoomArea()[row][0] + "\n";
				description = description + "Birth Version Name:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getBirth() + "\n";
				description = description + "Birth Version ID:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getBirthVersionIdentification() + "\n";
				description = description + "Death Version Name:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getDeath() + "\n";
				description = description + "Death Version ID:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getDeathVersionIdentification() + "\n";
				description = description + "Total Changes:"
						+ CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
								.get(CentralLinker.getFinalRowsZoomArea()[row][0])
								.getTotalChangesForOnePhase(Integer.parseInt(table.getColumnName(1)),
										Integer.parseInt(table.getColumnName(table.getColumnCount() - 1)))
						+ "\n";
				return description;
			}

			private Component setFirstDescription(final Gui theGui, final String[][] rowsZoom, final JTable table,
					final int column, final Component component) {
				String description = "Transition ID:" + table.getColumnName(column) + "\n";
				description = description + "Old Version Name:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getOldVersionName() + "\n";
				description = description + "New Version Name:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getNewVersionName() + "\n";
				description = description + "Transition Changes:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getClusterChangeNumberOfSpecificTransition(rowsZoom) + "\n";
				description = description + "Additions:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getClusterAdditionNumberOfSpecificTransition(rowsZoom) + "\n";
				description = description + "Deletions:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getClusterDeletionNumberOfSpecificTransition(rowsZoom) + "\n";
				description = description + "Updates:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getClusterUpdateNumberOfSpecificTransition(rowsZoom) + "\n";
				theGui.getDescriptionText().setText(description);
				Color cl = new Color(255, 69, 0, 100);
				component.setBackground(cl);
				return component;
			}
		});
		zoomTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1) {
					final JTable target = (JTable) mouseEvent.getSource();
					CentralLinker.setSelectedRowsFromMouse(target.getSelectedRows());
					CentralLinker.setSelectedColumnZoomArea(target.getSelectedColumn());
					CentralLinker.getZoomAreaTable().repaint();
				}
			}
		});
		zoomTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(final MouseEvent mouseEvent) {
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					System.out.println("Right Click");
					final JTable firstTarget = (JTable) mouseEvent.getSource();
					CentralLinker.setSelectedColumnZoomArea(firstTarget.getSelectedColumn());
					CentralLinker.setSelectedRowsFromMouse(firstTarget.getSelectedRows());
					System.out.println(firstTarget.getSelectedColumn());
					System.out.println(firstTarget.getSelectedRow());
					final ArrayList<String> tablesSelected = new ArrayList<String>();
					for (int rowsSelected = 0; rowsSelected < CentralLinker.getSelectedRowsFromMouse().length; rowsSelected++) {
						tablesSelected.add((String) zoomTable.getValueAt(CentralLinker.getSelectedRowsFromMouse()[rowsSelected], 0));
						System.out.println(tablesSelected.get(rowsSelected));
					}
				}
			}
		});
		zoomTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mouseEvent) {
				CentralLinker.setWholeColZoomArea(zoomTable.columnAtPoint(mouseEvent.getPoint()));
				final String name = zoomTable.getColumnName(CentralLinker.getWholeColZoomArea());
				System.out.println("Column index selected " + CentralLinker.getWholeCol() + " " + name);
				zoomTable.repaint();
			}
		});
		zoomTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(final MouseEvent mouseEvent) {
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					System.out.println("Right Click");
					final JPopupMenu popupMenu = new JPopupMenu();
					final JMenuItem showDetailsItem = new JMenuItem("Clear Column Selection");
					showDetailsItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent actionEvent) {
							CentralLinker.setWholeColZoomArea(-1);
							zoomTable.repaint();
						}
					});
					popupMenu.add(showDetailsItem);
					popupMenu.show(zoomTable, mouseEvent.getX(), mouseEvent.getY());
				}
			}

		});
		CentralLinker.setZoomAreaTable(zoomTable);
		theGui.getTemporalScrollPaneZoomArea().setViewportView(CentralLinker.getZoomAreaTable());
		theGui.getTemporalScrollPaneZoomArea().setAlignmentX(0);
		theGui.getTemporalScrollPaneZoomArea().setAlignmentY(0);
		theGui.getTemporalScrollPaneZoomArea().setBounds(300, 300, 950, 250);
		theGui.getTemporalScrollPaneZoomArea().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		theGui.getTemporalScrollPaneZoomArea().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		theGui.getLifeTimePanel().setCursor(theGui.getCursor());
		theGui.getLifeTimePanel().add(theGui.getTemporalScrollPaneZoomArea());
	}

	private static JvTable setTableWidth() {
		final JvTable zoomTable = new JvTable(CentralLinker.getZoomModel());
		zoomTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		zoomTable.setShowGrid(false);
		zoomTable.setIntercellSpacing(new Dimension(0, 0));
		for (int index = 0; index < zoomTable.getColumnCount(); index++) {
			if (index == 0) {
				zoomTable.getColumnModel().getColumn(0).setPreferredWidth(150);
			} else {
				zoomTable.getColumnModel().getColumn(index).setPreferredWidth(20);
				zoomTable.getColumnModel().getColumn(index).setMaxWidth(20);
				zoomTable.getColumnModel().getColumn(index).setMinWidth(20);
			}
		}
		return zoomTable;
	}

}
