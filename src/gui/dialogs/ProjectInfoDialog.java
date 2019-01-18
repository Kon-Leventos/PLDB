package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class ProjectInfoDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public ProjectInfoDialog(final String project, final String dataset, final String inputCsv, final String transitionsXml,
			final int numberOfSchemas, final int numberOfTransitions, final int numberOfTables) {
		setBounds(100, 100, 479, 276);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		final JLabel labelSchemas = makeLabelSchemas();
		final JLabel labelNumberOfSchemas = makeLabelNumberOfSchema(numberOfSchemas);
		final JLabel labelTransitions = makeLabelTransitions();
		final JLabel labelNumberOfTransitions = makeLabelNumberOfTransitions(numberOfTransitions);
		final JLabel labelTables = makeLabelTables();
		final JLabel labelNumberOfTables = makeLabelNumberOfTables(numberOfTables);
		final JLabel labelProjectName = makeLabelProjectName();
		final JLabel extraProjectName = makeExtraProjectName(project);
		final JLabel labelDatasetTxt = makeLabelDatesetText();
		final JLabel labelDataTxt = makeLabelDataText(dataset);
		final JLabel labelInput = makeLabelInput();
		final JLabel labelInputCsv = makeLabelInputCsv(inputCsv);
		final JLabel labelTransitionsFile = makelabelTransitionsFile();
		final JLabel extraTransitionsFile = makeExtraTransitionsFile(transitionsXml);
		makeContentPanel(labelSchemas, labelNumberOfSchemas, labelTransitions, labelNumberOfTransitions, labelTables, labelNumberOfTables,
				labelProjectName, extraProjectName, labelDatasetTxt, labelDataTxt, labelInput, labelInputCsv, labelTransitionsFile,
				extraTransitionsFile);
		makeButtonPane();
	}

	private void assignHorizontalGroup(final JLabel labelSchemas, final JLabel labelNumberOfSchemas, final JLabel labelTransitions,
			final JLabel labelNumberOfTransitions, final JLabel labelTables, final JLabel labelNumberOfTables, final JLabel labelProjectName,
			final JLabel extraProjectName, final JLabel labelDatasetTxt, final JLabel labelDataTxt, final JLabel labelInput,
			final JLabel labelInputCsv, final JLabel labelTransitionsFile, final JLabel extraTransitionsFile,
			final GroupLayout groupLayoutContentPanel) {
		groupLayoutContentPanel.setHorizontalGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayoutContentPanel.createSequentialGroup().addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(labelSchemas, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayoutContentPanel.createSequentialGroup().addGroup(groupLayoutContentPanel
								.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayoutContentPanel.createSequentialGroup()
										.addComponent(labelTables, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE).addGap(196))
								.addGroup(groupLayoutContentPanel.createSequentialGroup().addGroup(groupLayoutContentPanel
										.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(labelTransitions, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(labelProjectName, Alignment.LEADING).addComponent(labelDatasetTxt, Alignment.LEADING)
										.addComponent(labelInput, Alignment.LEADING).addComponent(labelTransitionsFile, Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGap(13)
										.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(extraProjectName, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
												.addComponent(labelDataTxt, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
												.addGroup(groupLayoutContentPanel.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
																.addComponent(extraTransitionsFile, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
																.addComponent(labelInputCsv, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
																.addComponent(labelNumberOfSchemas, GroupLayout.PREFERRED_SIZE, 56,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(labelNumberOfTransitions, GroupLayout.PREFERRED_SIZE, 85,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(labelNumberOfTables, GroupLayout.PREFERRED_SIZE, 62,
																		GroupLayout.PREFERRED_SIZE))))))
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGap(0)));
	}

	private void assignVerticalGroup(final JLabel labelSchemas, final JLabel labelNumberOfSchemas, final JLabel labelTransitions,
			final JLabel labelNumberOfTransitions, final JLabel labelTables, final JLabel labelNumberOfTables, final JLabel labelProjectName,
			final JLabel extraProjectName, final JLabel labelDatasetTxt, final JLabel labelDataTxt, final JLabel labelInput,
			final JLabel labelInputCsv, final JLabel labelTransitionsFile, final JLabel extraTransitionsFile,
			final GroupLayout groupLayoutContentPanel) {
		groupLayoutContentPanel.setVerticalGroup(groupLayoutContentPanel.createParallelGroup(Alignment.TRAILING).addGroup(groupLayoutContentPanel
				.createSequentialGroup()
				.addGroup(
						groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE).addComponent(labelProjectName).addComponent(extraProjectName))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE).addComponent(labelDatasetTxt).addComponent(labelDataTxt))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE).addComponent(labelInputCsv).addComponent(labelInput))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayoutContentPanel
						.createParallelGroup(Alignment.BASELINE).addComponent(extraTransitionsFile).addComponent(labelTransitionsFile))
				.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
				.addGroup(
						groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE).addComponent(labelSchemas).addComponent(labelNumberOfSchemas))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE).addComponent(labelTransitions)
						.addComponent(labelNumberOfTransitions))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE).addComponent(labelTables).addComponent(labelNumberOfTables))
				.addGap(27)));
	}

	private void makeButtonPane() {
		final JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		makeOkButton(buttonPane);
	}

	private void makeContentPanel(final JLabel labelSchemas, final JLabel labelNumberOfSchemas, final JLabel labelTransitions,
			final JLabel labelNumberOfTransitions, final JLabel labelTables, final JLabel labelNumberOfTables, final JLabel labelProjectName,
			final JLabel extraProjectName, final JLabel labelDatasetTxt, final JLabel labelDataTxt, final JLabel labelInput,
			final JLabel labelInputCsv, final JLabel labelTransitionsFile, final JLabel extraTransitionsFile) {
		final GroupLayout groupLayoutContentPanel = new GroupLayout(contentPanel);
		assignHorizontalGroup(labelSchemas, labelNumberOfSchemas, labelTransitions, labelNumberOfTransitions, labelTables, labelNumberOfTables,
				labelProjectName, extraProjectName, labelDatasetTxt, labelDataTxt, labelInput, labelInputCsv, labelTransitionsFile,
				extraTransitionsFile, groupLayoutContentPanel);
		assignVerticalGroup(labelSchemas, labelNumberOfSchemas, labelTransitions, labelNumberOfTransitions, labelTables, labelNumberOfTables,
				labelProjectName, extraProjectName, labelDatasetTxt, labelDataTxt, labelInput, labelInputCsv, labelTransitionsFile,
				extraTransitionsFile, groupLayoutContentPanel);
		contentPanel.setLayout(groupLayoutContentPanel);
	}

	private JLabel makeExtraProjectName(final String project) {
		final JLabel extraProjectName = new JLabel("-");
		extraProjectName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		extraProjectName.setText(project);
		return extraProjectName;
	}

	private JLabel makeExtraTransitionsFile(final String transitionsXml) {
		final JLabel extraTransitionsFile = new JLabel("-");
		extraTransitionsFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
		extraTransitionsFile.setText(transitionsXml);
		return extraTransitionsFile;
	}

	private JLabel makeLabelDataText(final String dataset) {
		final JLabel labelDataTxt = new JLabel("-");
		labelDataTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelDataTxt.setText(dataset);
		return labelDataTxt;
	}

	private JLabel makeLabelDatesetText() {
		final JLabel labelDatasetTxt = new JLabel("Dataset Txt");
		labelDatasetTxt.setFont(new Font("Tahoma", Font.BOLD, 12));
		return labelDatasetTxt;
	}

	private JLabel makeLabelInput() {
		final JLabel labelInput = new JLabel("Input  Csv");
		labelInput.setFont(new Font("Tahoma", Font.BOLD, 12));
		return labelInput;
	}

	private JLabel makeLabelInputCsv(final String inputCsv) {
		final JLabel labelInputCsv = new JLabel("-");
		labelInputCsv.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelInputCsv.setText(inputCsv);
		return labelInputCsv;
	}

	private JLabel makeLabelNumberOfSchema(final int numberOfSchemas) {
		final JLabel labelNumberOfSchemas = new JLabel("-");
		labelNumberOfSchemas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelNumberOfSchemas.setText(Integer.toString(numberOfSchemas));
		return labelNumberOfSchemas;
	}

	private JLabel makeLabelNumberOfTables(final int numberOfTables) {
		final JLabel labelNumberOfTables = new JLabel("-");
		labelNumberOfTables.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelNumberOfTables.setText(Integer.toString(numberOfTables));
		return labelNumberOfTables;
	}

	private JLabel makeLabelNumberOfTransitions(final int numberOfTransitions) {
		final JLabel labelNumberOfTransitions = new JLabel("-");
		labelNumberOfTransitions.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelNumberOfTransitions.setText(Integer.toString(numberOfTransitions));
		return labelNumberOfTransitions;
	}

	private JLabel makeLabelProjectName() {
		final JLabel labelProjectName = new JLabel("Project Name");
		labelProjectName.setFont(new Font("Tahoma", Font.BOLD, 12));
		return labelProjectName;
	}

	private JLabel makeLabelSchemas() {
		final JLabel labelSchemas = new JLabel("Schemas:");
		labelSchemas.setFont(new Font("Tahoma", Font.BOLD, 12));
		return labelSchemas;
	}

	private JLabel makeLabelTables() {
		final JLabel labelTables = new JLabel("Tables:");
		labelTables.setFont(new Font("Tahoma", Font.BOLD, 12));
		return labelTables;
	}

	private JLabel makeLabelTransitions() {
		final JLabel labelTransitions = new JLabel("Transitions:");
		labelTransitions.setFont(new Font("Tahoma", Font.BOLD, 12));
		return labelTransitions;
	}

	private JLabel makelabelTransitionsFile() {
		final JLabel labelTransitionsFile = new JLabel("Transitions File");
		labelTransitionsFile.setFont(new Font("Tahoma", Font.BOLD, 12));
		return labelTransitionsFile;
	}

	private ActionListener makeOkAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				setVisible(false);
			}
		};
	}

	private void makeOkButton(final JPanel buttonPane) {
		final JButton okButton = new JButton("OK");
		okButton.addActionListener(makeOkAction());
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	}

}
