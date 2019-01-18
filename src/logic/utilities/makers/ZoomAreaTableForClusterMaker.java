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
import logic.utilities.viewers.SelectionViewerFactory;

class ZoomAreaTableForClusterMaker implements IMaker {

	public ZoomAreaTableForClusterMaker() {}

	public void makeSpecialtyTable(final String[] columnsNull, final String[][] rowsNull, final boolean levelNull, final Gui theGui) {
		CentralLinker.setShowingPld(false);
		final int numberOfColumns = CentralLinker.getFinalRowsZoomArea()[0].length;
		final int numberOfRows = CentralLinker.getFinalRowsZoomArea().length;
		theGui.getUndoButton().setVisible(true);
		final String[][] rowsZoom = new String[numberOfRows][numberOfColumns];
		for (int index = 0; index < numberOfRows; index++) {
			rowsZoom[index][0] = CentralLinker.getFinalRowsZoomArea()[index][0];
		}
		CentralLinker.setZoomModel(new MyTableModel(CentralLinker.getFinalColumnsZoomArea(), rowsZoom));
		final JvTable zoomTable = new JvTable(CentralLinker.getZoomModel());
		zoomTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		zoomTable.setShowGrid(false);
		zoomTable.setIntercellSpacing(new Dimension(0, 0));
		for (int i = 0; i < zoomTable.getColumnCount(); i++) {
			if (i == 0) {
				zoomTable.getColumnModel().getColumn(0).setPreferredWidth(150);
			} else {
				zoomTable.getColumnModel().getColumn(i).setPreferredWidth(10);
				zoomTable.getColumnModel().getColumn(i).setMaxWidth(10);
				zoomTable.getColumnModel().getColumn(i).setMinWidth(70);
			}
		}
		zoomTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(final JTable table, final Object value, 
					final boolean isSelected, final boolean hasFocus, final int row, final int column) {
				final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				final String tmpValue = CentralLinker.getFinalRowsZoomArea()[row][column];
				final String columnName = table.getColumnName(column);
				final Color frame = new Color(0, 0, 0);
				component.setForeground(frame);
				if (column == CentralLinker.getWholeColZoomArea() && CentralLinker.getWholeColZoomArea() != 0) {
					final Color color = setFirstDescription(theGui, table, column);
					component.setBackground(color);
					return component;
				} else if (CentralLinker.getSelectedColumnZoomArea() == 0) {
					if (isSelected) {
						setSecondDescription(theGui, row, component);
						return component;
					}
				} else {
					if (isSelected && hasFocus) {
						setThirdDescription(theGui, table, row, column, tmpValue);
						final Color color = new Color(255, 69, 0, 100);
						component.setBackground(color);
						return component;
					}
				}
				try {
					tryBlock(component, tmpValue);
					return component;
				} catch (Exception e) {
					if (tmpValue.equals("")) {
						component.setBackground(Color.DARK_GRAY);
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

			private void tryBlock(final Component component, final String tmpValue) {
				final int numericValue = Integer.parseInt(tmpValue);
				Color insertionColor = null;
				setToolTipText(Integer.toString(numericValue));
				if (numericValue == 0) {
					insertionColor = new Color(0, 100, 0);
				} else if (numericValue > 0 && numericValue <= CentralLinker.getSegmentSizeZoomArea()[3]) {
					insertionColor = new Color(176, 226, 255);
				} else if (numericValue > CentralLinker.getSegmentSizeZoomArea()[3]
						&& numericValue <= 2 * CentralLinker.getSegmentSizeZoomArea()[3]) {
					insertionColor = new Color(92, 172, 238);
				} else if (numericValue > 2 * CentralLinker.getSegmentSizeZoomArea()[3]
						&& numericValue <= 3 * CentralLinker.getSegmentSizeZoomArea()[3]) {
					insertionColor = new Color(28, 134, 238);
				} else {
					insertionColor = new Color(16, 78, 139);
				}
				component.setBackground(insertionColor);
			}

			private void setThirdDescription(final Gui theGui, final JTable table, final int row, final int column,
					final String tmpValue) {
				String description = "";
				if (!table.getColumnName(column).contains("Table name")) {
					description = "Transition " + table.getColumnName(column) + "\n";
					description = description + "Old Version:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
							.get(Integer.parseInt(table.getColumnName(column))).getOldVersionName() + "\n";
					description = description + "New Version:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
							.get(Integer.parseInt(table.getColumnName(column))).getNewVersionName() + "\n\n";
					description = description + "Table:" + CentralLinker.getFinalRowsZoomArea()[row][0] + "\n";
					description = description + "Birth Version Name:" + CentralLinker.getGlobalDataKeeper()
							.getAllPlutarchParallelLivesTables().get(CentralLinker.getFinalRowsZoomArea()[row][0]).getBirth() + "\n";
					description = description + "Birth Version ID:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
							.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getBirthVersionIdentification() + "\n";
					description = description + "Death Version Name:" + CentralLinker.getGlobalDataKeeper()
							.getAllPlutarchParallelLivesTables().get(CentralLinker.getFinalRowsZoomArea()[row][0]).getDeath() + "\n";
					description = description + "Death Version ID:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
							.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getDeathVersionIdentification() + "\n";
					description = description + "Total Changes For This Phase:" + tmpValue + "\n";
					theGui.getDescriptionText().setText(description);
				}
			}

			private void setSecondDescription(final Gui theGui, final int row, final Component component) {
				String description = "Table:" + CentralLinker.getFinalRowsZoomArea()[row][0] + "\n";
				description = description + "Birth Version Name:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getBirth() + "\n";
				description = description + "Birth Version ID:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getBirthVersionIdentification() + "\n";
				description = description + "Death Version Name:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getDeath() + "\n";
				description = description + "Death Version ID:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getDeathVersionIdentification() + "\n";
				description = description + "Total Changes:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRowsZoomArea()[row][0]).getTotalChanges() + "\n";
				theGui.getDescriptionText().setText(description);
				final Color color = new Color(255, 69, 0, 100);
				component.setBackground(color);
			}

			private Color setFirstDescription(final Gui theGui, final JTable table, final int column) {
				String description = table.getColumnName(column) + "\n";
				description = description + "First Transition ID:"
						+ CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(column - 1).getStartPos() + "\n";
				description = description + "Last Transition ID:"
						+ CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(column - 1).getEndPosition() + "\n";
				description = description + "Total Changes For This Phase:"
						+ CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(column - 1).getTotalUpdates() + "\n";
				description = description + "Additions For This Phase:"
						+ CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(column - 1).getTotalAdditionsOfPhase()
						+ "\n";
				description = description + "Deletions For This Phase:"
						+ CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(column - 1).getTotalDeletionsOfPhase()
						+ "\n";
				description = description + "Updates For This Phase:"
						+ CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(column - 1).getTotalUpdatesOfPhase()
						+ "\n";
				theGui.getDescriptionText().setText(description);
				final Color color = new Color(255, 69, 0, 100);
				return color;
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
					firstPart(mouseEvent);
					for (int rowsSelected = 0; rowsSelected < CentralLinker.getSelectedRowsFromMouse().length; rowsSelected++) {
						CentralLinker.getTablesSelected()
								.add((String) zoomTable.getValueAt(CentralLinker.getSelectedRowsFromMouse()[rowsSelected], 0));
						System.out.println(CentralLinker.getTablesSelected().get(rowsSelected));
					}
					if (zoomTable.getColumnName(CentralLinker.getSelectedColumnZoomArea()).contains("Phase")) {
						final JPopupMenu popupMenu = new JPopupMenu();
						final JMenuItem showDetailsItem = new JMenuItem("Show Details");
						showDetailsItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(final ActionEvent actionEvent) {
								CentralLinker.setFirstLevelUndoColumnsZoomArea(CentralLinker.getFinalColumnsZoomArea());
								CentralLinker.setFirstLevelUndoRowsZoomArea(CentralLinker.getFinalRowsZoomArea());
								SelectionViewerFactory.makeTableConstruction("Zoom").showSelection(theGui, CentralLinker.getSelectedColumnZoomArea());
							}
						});
						popupMenu.add(showDetailsItem);
						popupMenu.show(zoomTable, mouseEvent.getX(), mouseEvent.getY());
					}
				}
			}

			private void firstPart(final MouseEvent mouseEvent) {
				System.out.println("Right Click");
				final JTable firstTarget = (JTable) mouseEvent.getSource();
				CentralLinker.setSelectedColumnZoomArea(firstTarget.getSelectedColumn());
				CentralLinker.setSelectedRowsFromMouse(firstTarget.getSelectedRows());
				System.out.println(firstTarget.getSelectedColumn());
				System.out.println(firstTarget.getSelectedRow());
				CentralLinker.setTablesSelected(new ArrayList<String>());
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

}
