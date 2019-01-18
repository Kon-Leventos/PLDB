package gui.tableElements.tableRenderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class HeaderTableRendererInsertDeleteUpdate extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public HeaderTableRendererInsertDeleteUpdate() {
		setFont(new Font("Consolas", Font.BOLD, 14));
		setOpaque(true);
		setForeground(Color.WHITE);
		setBackground(Color.GRAY);
		setBorder(BorderFactory.createEtchedBorder());
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus,
			final int row, final int column) {
		setText(value.toString());
		return this;
	}

}
