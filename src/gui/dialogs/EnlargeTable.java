package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import gui.tableElements.commons.JvTable;
import gui.tableElements.commons.MyTableModel;

public class EnlargeTable extends JDialog {
	private static final long serialVersionUID = 1L;
	private int columnWidth = 1;
	private final JPanel contentPanel = new JPanel();
	private String[] finalColumnsZoomArea;
	private String[][] finalRowsZoomArea;
	private int rowHeight = 10;
	private Integer[] segmentSize = new Integer[4];
	private JvTable table;
	private JScrollPane scrollPane;

	public EnlargeTable(final String[][] rows, final String[] columns, final Integer[] segment) {
		finalRowsZoomArea = rows;
		finalColumnsZoomArea = columns;
		segmentSize = segment;
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		final JvTable generalTable = makeJvTable();
		generalTable.setDefaultRenderer(Object.class, makeDefaultTableCellRenderer());
		table = generalTable;
		table.setBounds(0, 0, 1100, 580);
		makeTemporalScrollPane();
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		final JButton zoomInButton = new JButton("Zoom In");
		zoomInButton.setBounds(1000, 0, 100, 25);
		zoomInButton.addMouseListener(makeZoomInMouse());
		final JButton zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.setBounds(1110, 0, 100, 25);
		zoomOutButton.addMouseListener(makeZoomOutMouse());
		final JPanel subpane = new JPanel();
		subpane.add(zoomInButton);
		subpane.add(zoomOutButton);
		contentPanel.add(subpane, BorderLayout.NORTH);
	}

	private Color calculateValue(final int numericValue) {
		final Color insertionColor;
		if (numericValue == 0) {
			insertionColor = new Color(154, 205, 50, 200);
		} else if (numericValue > 0 && numericValue <= segmentSize[3]) {
			insertionColor = new Color(176, 226, 255);
		} else if (numericValue > segmentSize[3] && numericValue <= 2 * segmentSize[3]) {
			insertionColor = new Color(92, 172, 238);
		} else if (numericValue > 2 * segmentSize[3] && numericValue <= 3 * segmentSize[3]) {
			insertionColor = new Color(28, 134, 238);
		} else {
			insertionColor = new Color(16, 78, 139);
		}
		return insertionColor;
	}

	private DefaultTableCellRenderer makeDefaultTableCellRenderer() {
		return new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus,
					final int row, final int column) {
				final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				final String stringValue = finalRowsZoomArea[row][column];
				final String columnName = table.getColumnName(column);
				final Color color = new Color(0, 0, 0);
				component.setForeground(color);
				setOpaque(true);
				return tryComponent(this, component, stringValue, columnName);
			}
		};
	}

	private JvTable makeJvTable() {
		final int numberOfColumns = finalRowsZoomArea[0].length;
		final int numberOfRows = finalRowsZoomArea.length;
		final String[][] rows = new String[numberOfRows][numberOfColumns];
		for (int index = 0; index < numberOfRows; index++) {
			rows[index][0] = finalRowsZoomArea[index][0];
		}
		final MyTableModel zoomModel = new MyTableModel(finalColumnsZoomArea, rows);
		final JvTable generalTable = new JvTable(zoomModel);
		generalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int index = 0; index < generalTable.getRowCount(); index++) {
			generalTable.setRowHeight(index, rowHeight);
		}
		generalTable.setShowGrid(false);
		generalTable.setIntercellSpacing(new Dimension(0, 0));
		for (int index = 0; index < generalTable.getColumnCount(); index++) {
			if (index == 0) {
				generalTable.getColumnModel().getColumn(0).setPreferredWidth(60);
			} else {
				generalTable.getColumnModel().getColumn(index).setPreferredWidth(columnWidth);
			}
		}
		return generalTable;
	}

	private void makeTemporalScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		scrollPane.setAlignmentX(0);
		scrollPane.setAlignmentY(0);
		scrollPane.setBounds(0, 30, 1270, 620);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	}

	private MouseAdapter makeZoomInMouse() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				rowHeight = rowHeight + 1;
				columnWidth = columnWidth + 1;
				table.setZoom(rowHeight, columnWidth);

			}
		};
	}

	private MouseAdapter makeZoomOutMouse() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				rowHeight = rowHeight - 1;
				columnWidth = columnWidth - 1;
				if (rowHeight < 1) {
					rowHeight = 1;
				}
				if (columnWidth < 1) {
					columnWidth = 1;
				}
				table.setZoom(rowHeight, columnWidth);
			}
		};
	}

	private Component tryComponent(final DefaultTableCellRenderer tableCell, final Component component, final String value, final String columnName) {
		try {
			final int numericValue = Integer.parseInt(value);
			Color insertionColor = null;
			tableCell.setToolTipText(Integer.toString(numericValue));
			insertionColor = calculateValue(numericValue);
			component.setBackground(insertionColor);
			return component;
		} catch (NumberFormatException e) {
			if (value.equals("")) {
				component.setBackground(Color.GRAY);
				return component;
			} else {
				if (columnName.contains("v")) {
					component.setBackground(Color.lightGray);
					tableCell.setToolTipText(columnName);
				} else {
					final Color tableNameColor = new Color(205, 175, 149);
					component.setBackground(tableNameColor);
				}
				return component;
			}
		}
	}

}
