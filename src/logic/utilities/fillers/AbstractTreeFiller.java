package logic.utilities.fillers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import gui.mainEngine.Gui;
import gui.treeElements.ITreeConstruction;
import logic.centralLogic.CentralLinker;

public abstract class AbstractTreeFiller {
	
	public AbstractTreeFiller() {}
	
	public void fillTree(final Gui theGui) {
		final ITreeConstruction treeConstruction = makeTreeConstruction();
		theGui.setTablesTree(treeConstruction.constructTree());
		theGui.getTablesTree().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(final TreeSelectionEvent event) {
				final TreePath selection = event.getPath();
				CentralLinker.getSelectedFromTree().add(selection.getLastPathComponent().toString());
				System.out.println(selection.getLastPathComponent().toString() + " is selected.");
			}
		});
		theGui.getTablesTree().addMouseListener(makeTablesMouse(theGui));
		theGui.getTreeScrollPane().setViewportView(theGui.getTablesTree());
		theGui.getTreeScrollPane().setBounds(5, 5, 250, 170);
		theGui.getTreeScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		theGui.getTreeScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		theGui.getTablesTreePanel().add(theGui.getTreeScrollPane());
		theGui.getTreeLabel().setText(setText());
		theGui.getSideMenu().revalidate();
		theGui.getSideMenu().repaint();
	}

	private MouseAdapter makeTablesMouse(final Gui theGui) {
		return new MouseAdapter() {
			@Override
			public void mouseReleased(final MouseEvent event) {
				if (SwingUtilities.isRightMouseButton(event)) {
					System.out.println("Right Click Tree");
					final JPopupMenu popupMenu = new JPopupMenu();
					final JMenuItem showDetailsItem = new JMenuItem("Show This into the Table");
					showDetailsItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(final ActionEvent event) {
							CentralLinker.getLifeTimeTable().repaint();
						}
					});
					popupMenu.add(showDetailsItem);
					popupMenu.show(theGui.getTablesTree(), event.getX(), event.getY());
				}
			}
		};
	}
	
	protected abstract ITreeConstruction makeTreeConstruction();
	
	protected abstract String setText();

}
