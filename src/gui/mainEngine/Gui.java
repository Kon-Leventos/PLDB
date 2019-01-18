package gui.mainEngine;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import logic.actionListeners.ActionListenerFactory;
import logic.mouseAdapters.MouseAdapterFactory;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel descriptionLabel;
	private JPanel descriptionPanel = new JPanel();
	private JLabel generalTableLabel;
	private JMenu mnProject;
	private JMenuItem menuItemInfo;
	private JLabel zoomAreaLabel;
	private JTextArea descriptionText;
	private JPanel lifeTimePanel = new JPanel();
	private JButton notUniformlyDistributedButton;
	private JButton showThisToPopup;
	private JPanel sideMenu = new JPanel();
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JTree tablesTree = new JTree();
	private JPanel tablesTreePanel = new JPanel();
	private JScrollPane tmpScrollPane = new JScrollPane();
	private JScrollPane tmpScrollPaneZoomArea = new JScrollPane();
	private JLabel treeLabel;
	private JScrollPane treeScrollPane = new JScrollPane();
	private JButton undoButton;
	private JButton uniformlyDistributedButton;
	private JButton zoomInButton;
	private JButton zoomOutButton;

	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		makeMenuBarContent();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		final GroupLayout groupLayourContentPane = makeGroupLayout();
		makeAllLabels();
		lifeTimePanel.add(zoomInButton);
		lifeTimePanel.add(undoButton);
		lifeTimePanel.add(zoomOutButton);
		lifeTimePanel.add(uniformlyDistributedButton);
		lifeTimePanel.add(notUniformlyDistributedButton);
		lifeTimePanel.add(showThisToPopup);
		lifeTimePanel.add(zoomAreaLabel);
		lifeTimePanel.add(generalTableLabel);
		contentPane.setLayout(groupLayourContentPane);
		pack();
		setBounds(30, 30, 1300, 700);
	}
	
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JTextArea getDescriptionText() {
		return descriptionText;
	}

	public JPanel getLifeTimePanel() {
		return lifeTimePanel;
	}

	public JButton getNotUniformlyDistributedButton() {
		return notUniformlyDistributedButton;
	}

	public JButton getShowThisToPopup() {
		return showThisToPopup;
	}

	public JPanel getSideMenu() {
		return sideMenu;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public JTree getTablesTree() {
		return tablesTree;
	}

	public JPanel getTablesTreePanel() {
		return tablesTreePanel;
	}

	public JScrollPane getTemporalScrollPane() {
		return tmpScrollPane;
	}

	public JScrollPane getTemporalScrollPaneZoomArea() {
		return tmpScrollPaneZoomArea;
	}

	public JLabel getTreeLabel() {
		return treeLabel;
	}

	public JScrollPane getTreeScrollPane() {
		return treeScrollPane;
	}

	public JButton getUndoButton() {
		return undoButton;
	}

	public JButton getUniformlyDistributedButton() {
		return uniformlyDistributedButton;
	}

	public JButton getZoomInButton() {
		return zoomInButton;
	}

	public JButton getZoomOutButton() {
		return zoomOutButton;
	}

	private void makeAdditionalButtons(final JMenuBar menuBar) {
		final JButton buttonHelp = new JButton("Help");
		buttonHelp.addActionListener(ActionListenerFactory.makeActionListener("Help", null));
		mnProject = new JMenu("Project");
		menuBar.add(mnProject);
		menuItemInfo = new JMenuItem("Info");
		menuItemInfo.addActionListener(ActionListenerFactory.makeActionListener("Info", null));
		mnProject.add(menuItemInfo);
		buttonHelp.setBounds(900, 900, 80, 40);
		menuBar.add(buttonHelp);
	}

	private void makeAllLabels() {
		generalTableLabel = new JLabel("Parallel Lives Diagram");
		generalTableLabel.setBounds(300, 0, 150, 30);
		generalTableLabel.setForeground(Color.BLACK);
		zoomAreaLabel = new JLabel();
		zoomAreaLabel.setText("<HTML>Z<br>o<br>o<br>m<br><br>A<br>r<br>e<br>a</HTML>");
		zoomAreaLabel.setBounds(1255, 325, 15, 300);
		zoomAreaLabel.setForeground(Color.BLACK);
		makeZoomingLabels();
		makeFunctionalLabels();
	}

	private void makeDescriptionLabel() {
		final GroupLayout groupLayoutDescriptionPanel = new GroupLayout(descriptionPanel);
		groupLayoutDescriptionPanel.setHorizontalGroup(groupLayoutDescriptionPanel.createParallelGroup(Alignment.LEADING));
		groupLayoutDescriptionPanel.setVerticalGroup(groupLayoutDescriptionPanel.createParallelGroup(Alignment.LEADING));
		descriptionPanel.setLayout(groupLayoutDescriptionPanel);
		descriptionText = new JTextArea();
		descriptionText.setBounds(5, 5, 250, 170);
		descriptionText.setForeground(Color.BLACK);
		descriptionText.setText("");
		descriptionText.setBackground(Color.LIGHT_GRAY);
		descriptionPanel.add(descriptionText);
		descriptionLabel = new JLabel();
		descriptionLabel.setBounds(10, 160, 260, 40);
		descriptionLabel.setForeground(Color.WHITE);
		descriptionLabel.setText("Description");
	}

	private void makeFunctionalLabels() {
		showThisToPopup = new JButton("Enlarge");
		showThisToPopup.setBounds(800, 560, 100, 30);
		showThisToPopup.addMouseListener(MouseAdapterFactory.makeMouseAdapter("Enlarge", null));
		showThisToPopup.setVisible(false);
		undoButton = new JButton("Undo");
		undoButton.setBounds(680, 560, 100, 30);
		undoButton.addMouseListener(MouseAdapterFactory.makeMouseAdapter("Undo", this));
		undoButton.setVisible(false);
		uniformlyDistributedButton = new JButton("Same Width");
		uniformlyDistributedButton.setBounds(980, 0, 120, 30);
		uniformlyDistributedButton.addMouseListener(MouseAdapterFactory.makeMouseAdapter("Width", null));
		uniformlyDistributedButton.setVisible(false);
		notUniformlyDistributedButton = new JButton("Over Time");
		notUniformlyDistributedButton.setBounds(1100, 0, 120, 30);
		notUniformlyDistributedButton.addMouseListener(MouseAdapterFactory.makeMouseAdapter("Time", null));
		notUniformlyDistributedButton.setVisible(false);
	}

	private GroupLayout makeGroupLayout() {
		final GroupLayout groupLayourContentPane = new GroupLayout(contentPane);
		groupLayourContentPane.setHorizontalGroup(groupLayourContentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1474, Short.MAX_VALUE));
		groupLayourContentPane.setVerticalGroup(groupLayourContentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE));
		tabbedPane.addTab("LifeTime Table", null, lifeTimePanel, null);
		final GroupLayout groupLayerLifetimePanel = new GroupLayout(lifeTimePanel);
		groupLayerLifetimePanel.setHorizontalGroup(groupLayerLifetimePanel.createParallelGroup(Alignment.LEADING).addGap(0, 1469, Short.MAX_VALUE));
		groupLayerLifetimePanel.setVerticalGroup(groupLayerLifetimePanel.createParallelGroup(Alignment.LEADING).addGap(0, 743, Short.MAX_VALUE));
		lifeTimePanel.setLayout(groupLayerLifetimePanel);
		return groupLayourContentPane;
	}

	private JMenu makeLeftMenuBar(final JMenuBar menuBar) {
		setJMenuBar(menuBar);
		final JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		final JMenuItem menuItemCreateProject = new JMenuItem("Create Project");
		menuItemCreateProject.addActionListener(ActionListenerFactory.makeActionListener("Create", this));
		menuFile.add(menuItemCreateProject);
		final JMenuItem menuItemLoadProject = new JMenuItem("Load Project");
		menuItemLoadProject.addActionListener(ActionListenerFactory.makeActionListener("Load", this));
		menuFile.add(menuItemLoadProject);
		final JMenuItem menuEditProject = new JMenuItem("Edit Project");
		menuEditProject.addActionListener(ActionListenerFactory.makeActionListener("Edit", this));
		menuFile.add(menuEditProject);
		final JMenu menuTable = new JMenu("Table");
		menuBar.add(menuTable);
		return menuTable;
	}

	private String makeLowerString(String toReturn) {
		toReturn += sideMenu.getName();
		toReturn += tabbedPane.getName();
		toReturn += tablesTree.getName();
		toReturn += tablesTreePanel.getName();
		toReturn += tmpScrollPane.getName();
		toReturn += tmpScrollPaneZoomArea.getName();
		toReturn += treeLabel.getText();
		toReturn += treeScrollPane.getName();
		toReturn += undoButton.getText();
		toReturn += uniformlyDistributedButton.getText();
		toReturn += zoomInButton.getText();
		toReturn += zoomOutButton.getText();
		return toReturn;
	}

	private JMenuBar makeMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		final JMenu menuTable = makeLeftMenuBar(menuBar);
		makeRightMenuBar(menuTable);
		return menuBar;
	}

	private void makeMenuBarContent() {
		final JMenuBar menuBar = makeMenuBar();
		sideMenu.setBounds(0, 0, 280, 600);
		sideMenu.setBackground(Color.DARK_GRAY);
		final GroupLayout groupLayerSideMenu = new GroupLayout(sideMenu);
		groupLayerSideMenu.setHorizontalGroup(groupLayerSideMenu.createParallelGroup(Alignment.LEADING));
		groupLayerSideMenu.setVerticalGroup(groupLayerSideMenu.createParallelGroup(Alignment.LEADING));
		sideMenu.setLayout(groupLayerSideMenu);
		tablesTreePanel.setBounds(10, 400, 260, 180);
		tablesTreePanel.setBackground(Color.LIGHT_GRAY);
		final GroupLayout groupLayerTablesTreePanel = new GroupLayout(tablesTreePanel);
		groupLayerTablesTreePanel.setHorizontalGroup(groupLayerTablesTreePanel.createParallelGroup(Alignment.LEADING));
		groupLayerTablesTreePanel.setVerticalGroup(groupLayerTablesTreePanel.createParallelGroup(Alignment.LEADING));
		makeTreeLabel(groupLayerTablesTreePanel);
		makeAdditionalButtons(menuBar);
	}

	private void makeRightMenuBar(final JMenu menuTable) {
		final JMenuItem menuItemShowLifetimeTable = new JMenuItem("Show Full Detailed Lifetime Table");
		menuItemShowLifetimeTable.addActionListener(ActionListenerFactory.makeActionListener("FullDetailed", this));
		final JMenuItem menuItemShowGeneralLifetimeIdu = new JMenuItem("Show PLD");
		menuItemShowGeneralLifetimeIdu.addActionListener(ActionListenerFactory.makeActionListener("PLD", this));
		menuTable.add(menuItemShowGeneralLifetimeIdu);
		final JMenuItem menuItemShowGeneralLifetimePhasesPld = new JMenuItem("Show Phases PLD");
		menuItemShowGeneralLifetimePhasesPld.addActionListener(ActionListenerFactory.makeActionListener("PhasesPLD", this));
		menuTable.add(menuItemShowGeneralLifetimePhasesPld);
		final JMenuItem menuItemShowGeneralLifetimePhasesWithClustersPld = new JMenuItem("Show Phases With Clusters PLD");
		menuItemShowGeneralLifetimePhasesWithClustersPld.addActionListener(ActionListenerFactory.makeActionListener("PhasesClusters", this));
		menuTable.add(menuItemShowGeneralLifetimePhasesWithClustersPld);
		menuTable.add(menuItemShowLifetimeTable);
	}

	private void makeTreeLabel(final GroupLayout groupLayerTablesTreePanel) {
		tablesTreePanel.setLayout(groupLayerTablesTreePanel);
		treeLabel = new JLabel();
		treeLabel.setBounds(10, 370, 260, 40);
		treeLabel.setForeground(Color.WHITE);
		treeLabel.setText("Tree");
		descriptionPanel.setBounds(10, 190, 260, 180);
		descriptionPanel.setBackground(Color.LIGHT_GRAY);
		makeDescriptionLabel();
		sideMenu.add(treeLabel);
		sideMenu.add(tablesTreePanel);
		sideMenu.add(descriptionLabel);
		sideMenu.add(descriptionPanel);
		lifeTimePanel.add(sideMenu);
	}

	private String makeUpperString(String toReturn) {
		toReturn += contentPane.getName();
		toReturn += descriptionLabel.getText();
		toReturn += descriptionPanel.getName();
		toReturn += generalTableLabel.getText();
		toReturn += mnProject.getText();
		toReturn += menuItemInfo.getText();
		toReturn += zoomAreaLabel.getText();
		toReturn += descriptionText.getText();
		toReturn += lifeTimePanel.getName();
		toReturn += notUniformlyDistributedButton.getText();
		toReturn += showThisToPopup.getText();
		return toReturn;
	}

	private void makeZoomingLabels() {
		zoomInButton = new JButton("Zoom In");
		zoomInButton.setBounds(1000, 560, 100, 30);
		zoomInButton.addMouseListener(MouseAdapterFactory.makeMouseAdapter("In", null));
		zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.setBounds(1110, 560, 100, 30);
		zoomOutButton.addMouseListener(MouseAdapterFactory.makeMouseAdapter("Out", null));
		zoomInButton.setVisible(false);
		zoomOutButton.setVisible(false);
	}

	public void setDescription(final String description) {
		descriptionText.setText(description);
	}

	public void setDescriptionText(final JTextArea descriptionText) {
		this.descriptionText = descriptionText;
	}

	public void setLifeTimePanel(final JPanel lifeTimePanel) {
		this.lifeTimePanel = lifeTimePanel;
	}

	public void setNotUniformlyDistributedButton(final JButton notUniformlyDistributedButton) {
		this.notUniformlyDistributedButton = notUniformlyDistributedButton;
	}

	public void setShowThisToPopup(final JButton showThisToPopup) {
		this.showThisToPopup = showThisToPopup;
	}

	public void setSideMenu(final JPanel sideMenu) {
		this.sideMenu = sideMenu;
	}

	public void setTabbedPane(final JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public void setTablesTree(final JTree tablesTree) {
		this.tablesTree = tablesTree;
	}

	public void setTablesTreePanel(final JPanel tablesTreePanel) {
		this.tablesTreePanel = tablesTreePanel;
	}

	public void setTemporalScrollPane(final JScrollPane temporalScrollPane) {
		this.tmpScrollPane = temporalScrollPane;
	}

	public void setTemporalScrollPaneZoomArea(final JScrollPane temporalScrollPaneZoomArea) {
		this.tmpScrollPaneZoomArea = temporalScrollPaneZoomArea;
	}

	public void setTreeLabel(final JLabel treeLabel) {
		this.treeLabel = treeLabel;
	}

	public void setTreeScrollPane(final JScrollPane treeScrollPane) {
		this.treeScrollPane = treeScrollPane;
	}

	public void setUndoButton(final JButton undoButton) {
		this.undoButton = undoButton;
	}

	public void setUniformlyDistributedButton(final JButton uniformlyDistributedButton) {
		this.uniformlyDistributedButton = uniformlyDistributedButton;
	}

	public void setZoomInButton(final JButton zoomInButton) {
		this.zoomInButton = zoomInButton;
	}

	public void setZoomOutButton(final JButton zoomOutButton) {
		this.zoomOutButton = zoomOutButton;
	}

	@Override
	public String toString() {
		String toReturn = "";
		toReturn = makeUpperString(toReturn);
		toReturn = makeLowerString(toReturn);
		return toReturn;
	}

}
