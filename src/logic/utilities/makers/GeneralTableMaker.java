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
import data.dataSorters.DatasetRowSorter;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;
import gui.tableElements.commons.MyTableModel;
import gui.tableElements.tableRenderers.HeaderTableRendererInsertDeleteUpdate;
import gui.tableElements.tableRenderers.TableRendererInsertDeleteUpdate;
import logic.centralLogic.CentralLinker;

class GeneralTableMaker implements IMaker {

	public GeneralTableMaker() {}

	public void makeSpecialtyTable(final String[] columnsNull, final String[][] rowsNull, final boolean levelNull, final Gui theGui) {
		final DatasetRowSorter sorter = new DatasetRowSorter(CentralLinker.getFinalRowsZoomArea(), CentralLinker.getGlobalDataKeeper());
		CentralLinker.setFinalRowsZoomArea(sorter.sortRows());
		CentralLinker.setShowingPld(true);
		configureGui(theGui);
		final int numberOfColumns = CentralLinker.getFinalRowsZoomArea()[0].length;
		final int numberOfRows = CentralLinker.getFinalRowsZoomArea().length;
		CentralLinker.setSelectedRows(new ArrayList<Integer>());
		final String[][] rows = new String[numberOfRows][numberOfColumns];
		for (int index = 0; index < numberOfRows; index++) {
			rows[index][0] = CentralLinker.getFinalRowsZoomArea()[index][0];
		}
		CentralLinker.setZoomModel(new MyTableModel(CentralLinker.getFinalColumnsZoomArea(), rows));
		final JvTable generalTable = new JvTable(CentralLinker.getZoomModel());
		generalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		checkHeight();
		checkWidth();
		for (int index = 0; index < generalTable.getRowCount(); index++) {
			generalTable.setRowHeight(index, CentralLinker.getRowHeight());
		}
		generalTable.setShowGrid(false);
		generalTable.setIntercellSpacing(new Dimension(0, 0));
		setPrefferedWidth(generalTable);
		int start = -1;
		int end = -1;
		if (CentralLinker.getGlobalDataKeeper().getPhaseCollectors() != null && CentralLinker.getWholeCol() != -1
				&& CentralLinker.getWholeCol() != 0) {
			start = CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(CentralLinker.getWholeCol() - 1).getStartPos();
			end = CentralLinker.getGlobalDataKeeper().getPhaseCollectors().get(0).getPhases().get(CentralLinker.getWholeCol() - 1).getEndPosition();
		}
		checkWholeCol(generalTable, start, end);
		final TableRendererInsertDeleteUpdate renderer = new TableRendererInsertDeleteUpdate(theGui, CentralLinker.getFinalRowsZoomArea(),
				CentralLinker.getGlobalDataKeeper(), CentralLinker.getSegmentSize());
		generalTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(final JTable table, final Object value, 
					final boolean isSelected, final boolean hasFocus, final int row, final int column) {
				final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				final String temporaryValue = CentralLinker.getFinalRowsZoomArea()[row][column];
				final String columnName = table.getColumnName(column);
				final Color frame = new Color(0, 0, 0);
				component.setForeground(frame);
				setOpaque(true);
				if (column == CentralLinker.getWholeColZoomArea() && CentralLinker.getWholeColZoomArea() != 0) {
					final String description = setFirstDescription(table, column);
					theGui.getDescriptionText().setText(description);
					component.setBackground(new Color(255, 69, 0, 100));
					return component;
				} else if (CentralLinker.getSelectedColumnZoomArea() == 0) {
					if (isSelected) {
						final String description = setSecondDescription(row, component);
						theGui.getDescriptionText().setText(description);
						return component;
					}
				} else {
					if (CentralLinker.getSelectedFromTree().contains(CentralLinker.getFinalRowsZoomArea()[row][0])) {
						component.setBackground(new Color(255, 69, 0, 100));
						return component;
					}
					if (isSelected && hasFocus) {
						String description = "";
						if (!table.getColumnName(column).contains("Table name")) {
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
								description = setFourthDescription(description);
							}
							theGui.getDescriptionText().setText(description);
						}
						Color orangeColor = new Color(255, 69, 0, 100);
						component.setBackground(orangeColor);
						return component;
					}
				}
				try {
					final int numericValue = Integer.parseInt(temporaryValue);
					final Color insersionColor = checkNumericValue(numericValue);
					component.setBackground(insersionColor);
					return component;
				} catch (Exception e) {
					if (temporaryValue.equals("")) {
						component.setBackground(Color.GRAY);
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
				Color insersionColor = null;
				setToolTipText(Integer.toString(numericValue));
				if (numericValue == 0) {
					insersionColor = new Color(154, 205, 50, 200);
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

			private String setFourthDescription(String description) {
				description = description + "Transition Changes:0" + "\n";
				description = description + "Additions:0" + "\n";
				description = description + "Deletions:0" + "\n";
				description = description + "Updates:0" + "\n";
				return description;
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

			private String setSecondDescription(final int row, final Component component) {
				component.setBackground(new Color(255, 69, 0, 100));
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
				return description;
			}

			private String setFirstDescription(final JTable table, final int column) {
				String description = "Transition ID:" + table.getColumnName(column) + "\n";
				description = description + "Old Version Name:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getOldVersionName() + "\n";
				description = description + "New Version Name:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getNewVersionName() + "\n";
				description = description + "Transition Changes:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getChangeNumberOfSpecificTransition() + "\n";
				description = description + "Additions:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getAdditionNumberOfSpecificTransition() + "\n";
				description = description + "Deletions:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getDeletionNumberOfSpecificTransition() + "\n";
				description = description + "Updates:" + CentralLinker.getGlobalDataKeeper().getAllPlutarchParallelLivesTransitions()
						.get(Integer.parseInt(table.getColumnName(column))).getUpdateNumberOfSpecificTransition() + "\n";
				return description;
			}
		});
		generalTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					CentralLinker.setSelectedRowsFromMouse(target.getSelectedRows());
					CentralLinker.setSelectedColumnZoomArea(target.getSelectedColumn());
					renderer.setSelCol(CentralLinker.getSelectedColumnZoomArea());
					target.getSelectedColumns();
					CentralLinker.getZoomAreaTable().repaint();
				}
			}
		});
		generalTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(final MouseEvent mouseEvent) {
				if (SwingUtilities.isRightMouseButton(mouseEvent)) {
					System.out.println("Right Click");
					final JTable firstTarget = (JTable) mouseEvent.getSource();
					firstTarget.getSelectedColumns();
					CentralLinker.setSelectedRowsFromMouse(firstTarget.getSelectedRows());
					System.out.println(firstTarget.getSelectedColumns().length);
					System.out.println(firstTarget.getSelectedRow());
					for (int rowsSelected = 0; rowsSelected < CentralLinker.getSelectedRowsFromMouse().length; rowsSelected++) {
						System.out.println(generalTable.getValueAt(CentralLinker.getSelectedRowsFromMouse()[rowsSelected], 0));
					}
					final JPopupMenu popupMenu = new JPopupMenu();
					final JMenuItem showDetailsItem = new JMenuItem("Clear Selection");
					showDetailsItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent actionEvent) {
							CentralLinker.setSelectedFromTree(new ArrayList<String>());
							CentralLinker.getZoomAreaTable().repaint();
						}
					});
					popupMenu.add(showDetailsItem);
					popupMenu.show(generalTable, mouseEvent.getX(), mouseEvent.getY());
				}
			}
		});
		generalTable.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent mouseEvent) {
				CentralLinker.setWholeColZoomArea(generalTable.columnAtPoint(mouseEvent.getPoint()));
				renderer.setWholeColumn(generalTable.columnAtPoint(mouseEvent.getPoint()));
				generalTable.repaint();
			}
		});
		generalTable.getTableHeader().addMouseListener(new MouseAdapter() {
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
							renderer.setWholeColumn(CentralLinker.getWholeColZoomArea());
							generalTable.repaint();
						}
					});
					popupMenu.add(showDetailsItem);
					popupMenu.show(generalTable, mouseEvent.getX(), mouseEvent.getY());
				}
			}
		});
		CentralLinker.setZoomAreaTable(generalTable);
		theGui.getTemporalScrollPaneZoomArea().setViewportView(CentralLinker.getZoomAreaTable());
		theGui.getTemporalScrollPaneZoomArea().setAlignmentX(0);
		theGui.getTemporalScrollPaneZoomArea().setAlignmentY(0);
		theGui.getTemporalScrollPaneZoomArea().setBounds(300, 300, 950, 250);
		theGui.getTemporalScrollPaneZoomArea().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		theGui.getTemporalScrollPaneZoomArea().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		theGui.getLifeTimePanel().setCursor(theGui.getCursor());
		theGui.getLifeTimePanel().add(theGui.getTemporalScrollPaneZoomArea());
	}

	private static void checkWholeCol(final JvTable generalTable, final int start, final int end) {
		if (CentralLinker.getWholeCol() != -1) {
			for (int index = 0; index < generalTable.getColumnCount(); index++) {
				if (!(generalTable.getColumnName(index).equals("Table name"))) {
					if (Integer.parseInt(generalTable.getColumnName(index)) >= start && Integer.parseInt(generalTable.getColumnName(index)) <= end) {
						generalTable.getColumnModel().getColumn(index).setHeaderRenderer(new HeaderTableRendererInsertDeleteUpdate());
					}
				}
			}
		}
	}

	private static void setPrefferedWidth(final JvTable generalTable) {
		for (int index = 0; index < generalTable.getColumnCount(); index++) {
			if (index == 0) {
				generalTable.getColumnModel().getColumn(0).setPreferredWidth(CentralLinker.getColumnWidth());
			} else {
				generalTable.getColumnModel().getColumn(index).setPreferredWidth(CentralLinker.getColumnWidth());
			}
		}
	}

	private static void checkWidth() {
		if (CentralLinker.getColumnWidth() < 1) {
			CentralLinker.setColumnWidth(1);
		}
	}

	private static void checkHeight() {
		if (CentralLinker.getRowHeight() < 1) {
			CentralLinker.setRowHeight(1);
		}
	}

	private static void configureGui(final Gui theGui) {
		theGui.getZoomInButton().setVisible(true);
		theGui.getZoomOutButton().setVisible(true);
		theGui.getShowThisToPopup().setVisible(true);
	}
}
