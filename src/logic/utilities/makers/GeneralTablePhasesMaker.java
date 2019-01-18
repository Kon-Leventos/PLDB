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

class GeneralTablePhasesMaker implements IMaker {

	public GeneralTablePhasesMaker() {}

	public void makeSpecialtyTable(final String[] columnsNull, final String[][] rowsNull, final boolean levelNull, final Gui theGui) {
		theGui.getUniformlyDistributedButton().setVisible(true);
		theGui.getNotUniformlyDistributedButton().setVisible(true);
		final int numberOfColumns = CentralLinker.getFinalRows()[0].length;
		final int numberOfRows = CentralLinker.getFinalRows().length;
		CentralLinker.setSelectedRows(new ArrayList<Integer>());
		final String[][] rows = new String[numberOfRows][numberOfColumns];
		for (int index = 0; index < numberOfRows; index++) {
			rows[index][0] = CentralLinker.getFinalRows()[index][0];
		}
		CentralLinker.setGeneralModel(new MyTableModel(CentralLinker.getFinalColumns(), rows));
		final JvTable generalTable = new JvTable(CentralLinker.getGeneralModel());
		generalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		generalTable.setShowGrid(false);
		generalTable.setIntercellSpacing(new Dimension(0, 0));
		for (int index = 0; index < generalTable.getColumnCount(); index++) {
			if (index == 0) {
				generalTable.getColumnModel().getColumn(0).setPreferredWidth(86);
			} else {
				generalTable.getColumnModel().getColumn(index).setPreferredWidth(1);
			}
		}
		generalTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				final String temporaryValue = CentralLinker.getFinalRows()[row][column];
				final String columnName = table.getColumnName(column);
				final Color frame = new Color(0, 0, 0);
				component.setForeground(frame);
				if (column == CentralLinker.getWholeCol() && CentralLinker.getWholeCol() != 0) {
					final Color color = setFirstDescription(theGui, table, column);
					component.setBackground(color);
					return component;
				} else if (CentralLinker.getSelectedColumn() == 0) {
					if (isSelected) {
						if (CentralLinker.getFinalRows()[row][0].contains("Cluster")) {
							final String description = setSecondDescription(row);
							theGui.getDescriptionText().setText(description);
						} else {
							final String description = setThirdDescription(row);
							theGui.getDescriptionText().setText(description);
						}
						final Color color = new Color(255, 69, 0, 100);
						component.setBackground(color);
						return component;
					}
				} else {
					if (CentralLinker.getSelectedFromTree().contains(CentralLinker.getFinalRows()[row][0])) {
						final Color color = new Color(255, 69, 0, 100);
						component.setBackground(color);
						return component;
					}
					if (isSelected && hasFocus) {
						String description = "";
						if (!table.getColumnName(column).contains("Table name")) {
							if (CentralLinker.getFinalRows()[row][0].contains("Cluster")) {
								description = setFourthDescription(table, row, column, temporaryValue);
							} else {
								description = sethFifthDescription(table, row, column, temporaryValue);
							}
							theGui.getDescriptionText().setText(description);
						}
						final Color color = new Color(255, 69, 0, 100);
						component.setBackground(color);
						return component;
					}
				}
				try {
					final int numericValue = Integer.parseInt(temporaryValue);
					Color insertionColor = null;
					setToolTipText(Integer.toString(numericValue));
					insertionColor = checkNumericValue(numericValue);
					component.setBackground(insertionColor);
					return component;
				} catch (Exception e) {
					if (temporaryValue.equals("")) {
						component.setBackground(Color.gray);
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
			}

			private Color checkNumericValue(final int numericValue) {
				final Color insertionColor;
				if (numericValue == 0) {
					insertionColor = new Color(154, 205, 50, 200);
				} else if (numericValue > 0 && numericValue <= CentralLinker.getSegmentSize()[3]) {
					insertionColor = new Color(176, 226, 255);
				} else if (numericValue > CentralLinker.getSegmentSize()[3] && numericValue <= 2 * CentralLinker.getSegmentSize()[3]) {
					insertionColor = new Color(92, 172, 238);
				} else if (numericValue > 2 * CentralLinker.getSegmentSize()[3] && numericValue <= 3 * CentralLinker.getSegmentSize()[3]) {
					insertionColor = new Color(28, 134, 238);
				} else {
					insertionColor = new Color(16, 78, 139);
				}
				return insertionColor;
			}

			private String sethFifthDescription(final JTable table, final int row, final int column, 
					final String temporaryValue) {
				String description;
				description = table.getColumnName(column) + "\n";
				description = description + "First Transition ID:"
						+ CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(column - 1).getStartPos()
						+ "\n";
				description = description + "Last Transition ID:"
						+ CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(column - 1).getEndPosition()
						+ "\n\n";
				description = description + "Table:" + CentralLinker.getFinalRows()[row][0] + "\n";
				description = description + "Birth Version Name:" + CentralLinker.getGlobalDataKeeper()
						.getAllPlutarchParallelLivesTables().get(CentralLinker.getFinalRows()[row][0]).getBirth() + "\n";
				description = description + "Birth Version ID:" + CentralLinker.getGlobalDataKeeper()
						.getAllPlutarchParallelLivesTables().get(CentralLinker.getFinalRows()[row][0]).getBirthVersionIdentification()
						+ "\n";
				description = description + "Death Version Name:" + CentralLinker.getGlobalDataKeeper()
						.getAllPlutarchParallelLivesTables().get(CentralLinker.getFinalRows()[row][0]).getDeath() + "\n";
				description = description + "Death Version ID:" + CentralLinker.getGlobalDataKeeper()
						.getAllPlutarchParallelLivesTables().get(CentralLinker.getFinalRows()[row][0]).getDeathVersionIdentification()
						+ "\n";
				description = description + "Total Changes For This Phase:" + temporaryValue + "\n";
				return description;
			}

			private String setFourthDescription(final JTable table, final int row, final int column,
					final String temporaryValue) {
				String description;
				description = CentralLinker.getFinalRows()[row][0] + "\n";
				description = description + "Tables:" + CentralLinker.getGlobalDataKeeper().getClusterCollectors().get(0)
						.getClusters().get(row).getNamesOfTables().size() + "\n\n";

				description = description + table.getColumnName(column) + "\n";
				description = description + "First Transition ID:"
						+ CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(column - 1).getStartPos()
						+ "\n";
				description = description + "Last Transition ID:"
						+ CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(column - 1).getEndPosition()
						+ "\n\n";
				description = description + "Total Changes For This Phase:" + temporaryValue + "\n";
				return description;
			}

			private String setThirdDescription(final int row) {
				String description = "Table:" + CentralLinker.getFinalRows()[row][0] + "\n";
				description = description + "Birth Version Name:" + CentralLinker.getGlobalDataKeeper()
						.getAllPlutarchParallelLivesTables().get(CentralLinker.getFinalRows()[row][0]).getBirth() + "\n";
				description = description + "Birth Version ID:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRows()[row][0]).getBirthVersionIdentification() + "\n";
				description = description + "Death Version Name:" + CentralLinker.getGlobalDataKeeper()
						.getAllPlutarchParallelLivesTables().get(CentralLinker.getFinalRows()[row][0]).getDeath() + "\n";
				description = description + "Death Version ID:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRows()[row][0]).getDeathVersionIdentification() + "\n";
				description = description + "Total Changes:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTables()
						.get(CentralLinker.getFinalRows()[row][0]).getTotalChanges() + "\n";
				return description;
			}

			private String setSecondDescription(final int row) {
				String description = "Cluster:" + CentralLinker.getFinalRows()[row][0] + "\n";
				description = description + "Birth Version Name:"
						+ CentralLinker.getGlobalDataKeeper().getClusterCollectors().get(0).getClusters().get(row).getBirthSqlFile()
						+ "\n";
				description = description + "Birth Version ID:"
						+ CentralLinker.getGlobalDataKeeper().getClusterCollectors().get(0).getClusters().get(row).getBirth() + "\n";
				description = description + "Death Version Name:"
						+ CentralLinker.getGlobalDataKeeper().getClusterCollectors().get(0).getClusters().get(row).getDeathSqlFile()
						+ "\n";
				description = description + "Death Version ID:"
						+ CentralLinker.getGlobalDataKeeper().getClusterCollectors().get(0).getClusters().get(row).getDeath() + "\n";
				description = description + "Tables:" + CentralLinker.getGlobalDataKeeper().getClusterCollectors().get(0).getClusters()
						.get(row).getNamesOfTables().size() + "\n";
				description = description + "Total Changes:"
						+ CentralLinker.getGlobalDataKeeper().getClusterCollectors().get(0).getClusters().get(row).getTotalChanges()
						+ "\n";
				return description;
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
		generalTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1) {
					final JTable target = (JTable) mouseEvent.getSource();
					CentralLinker.setSelectedRowsFromMouse(target.getSelectedRows());
					CentralLinker.setSelectedColumn(target.getSelectedColumn());
					CentralLinker.getLifeTimeTable().repaint();
				}
			}
		});
		generalTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(final MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
					System.out.println("Right Click");
					final JTable firstTarget = (JTable) mouseEvent.getSource();
					CentralLinker.setSelectedColumn(firstTarget.getSelectedColumn());
					CentralLinker.setSelectedRowsFromMouse(new int[firstTarget.getSelectedRows().length]);
					CentralLinker.setSelectedRowsFromMouse(firstTarget.getSelectedRows());
					final String selectedRow = (String) generalTable.getValueAt(firstTarget.getSelectedRow(), 0);
					CentralLinker.setTablesSelected(new ArrayList<String>());
					for (int rowsSelected = 0; rowsSelected < CentralLinker.getSelectedRowsFromMouse().length; rowsSelected++) {
						CentralLinker.getTablesSelected()
								.add((String) generalTable.getValueAt(CentralLinker.getSelectedRowsFromMouse()[rowsSelected], 0));
					}
					final JPopupMenu popupMenu = new JPopupMenu();
					final JMenuItem showDetailsItem = new JMenuItem("Show Details for the selection");
					showDetailsItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent actionEvent) {
							if (selectedRow.contains("Cluster ")) {
								SelectionViewerFactory.makeTableConstruction("Clusters").showSelection(theGui, CentralLinker.getSelectedColumn());
							} else {
								SelectionViewerFactory.makeTableConstruction("Zoom").showSelection(theGui, CentralLinker.getSelectedColumn());
							}
						}
					});
					popupMenu.add(showDetailsItem);
					final JMenuItem clearSelectionItem = new JMenuItem("Clear Selection");
					clearSelectionItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent actionEvent) {
							CentralLinker.setSelectedFromTree(new ArrayList<String>());
							CentralLinker.getLifeTimeTable().repaint();
						}
					});
					popupMenu.add(clearSelectionItem);
					popupMenu.show(generalTable, mouseEvent.getX(), mouseEvent.getY());
				}
			}
		});
		generalTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mouseEvent) {
				CentralLinker.setWholeCol(generalTable.columnAtPoint(mouseEvent.getPoint()));
				final String name = generalTable.getColumnName(CentralLinker.getWholeCol());
				System.out.println("Column index selected " + CentralLinker.getWholeCol() + " " + name);
				generalTable.repaint();
				if (CentralLinker.isShowingPld()) {
					new GeneralTableMaker().makeSpecialtyTable(null, null, false, theGui);
				}
			}
		});
		generalTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(final MouseEvent mouseEvent) {
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					System.out.println("Right Click");
					final JPopupMenu popupMenu = new JPopupMenu();
					final JMenuItem clearColumnSelectionItem = new JMenuItem("Clear Column Selection");
					clearColumnSelectionItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent actionEvent) {
							CentralLinker.setWholeCol(-1);
							generalTable.repaint();
							if (CentralLinker.isShowingPld()) {
								new GeneralTableMaker().makeSpecialtyTable(null, null, false, theGui);
							}
						}
					});
					popupMenu.add(clearColumnSelectionItem);
					final JMenuItem showDetailsItem = new JMenuItem("Show Details for this Phase");
					showDetailsItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent ctionEvent) {
							final String selectedRow = CentralLinker.getFinalRows()[0][0];
							System.out.println("?" + selectedRow);
							CentralLinker.setTablesSelected(new ArrayList<String>());
							for (int index = 0; index < CentralLinker.getFinalRows().length; index++) {
								CentralLinker.getTablesSelected().add((String) generalTable.getValueAt(index, 0));
							}
							if (!selectedRow.contains("Cluster ")) {
								SelectionViewerFactory.makeTableConstruction("Zoom").showSelection(theGui, CentralLinker.getWholeCol());
							} else {
								SelectionViewerFactory.makeTableConstruction("Clusters").showSelection(theGui, CentralLinker.getWholeCol());
							}
						}
					});
					popupMenu.add(showDetailsItem);
					popupMenu.show(generalTable, mouseEvent.getX(), mouseEvent.getY());
				}
			}
		});
		CentralLinker.setLifeTimeTable(generalTable);
		theGui.getTemporalScrollPane().setViewportView(CentralLinker.getLifeTimeTable());
		theGui.getTemporalScrollPane().setAlignmentX(0);
		theGui.getTemporalScrollPane().setAlignmentY(0);
		theGui.getTemporalScrollPane().setBounds(300, 30, 950, 265);
		theGui.getTemporalScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		theGui.getTemporalScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		theGui.getLifeTimePanel().setCursor(theGui.getCursor());
		theGui.getLifeTimePanel().add(theGui.getTemporalScrollPane());
	}

}
