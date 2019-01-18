package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class ParametersJDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final int COLUMN_WIDTH = 10;
	private Double birthWeight;
	private JTextField birthWeightTxtF;
	private final ButtonGroup upperButtonGroup = new ButtonGroup();
	private final ButtonGroup lowerButtonGroup = new ButtonGroup();
	private Float changeWeight;
	private Double changeWeightClear;
	private JTextField changeWeightTextField;
	private boolean confirm;
	private final JPanel contentPanel = new JPanel();
	private Double deathWeight;
	private JTextField deathWeightTextField;
	private JTextField giveClustersTextFields;
	private Integer numberOfClusters;
	private Integer numberOfPhases;
	private Boolean preprocessingChange;
	private Boolean preprocessingTime;
	private JTextField textField;
	private Float timeWeight;

	@SuppressWarnings("rawtypes")
	public ParametersJDialog(final boolean clusters) {
		setBounds(100, 100, 509, 300);
		getContentPane().setLayout(new BorderLayout());
		setContnetPanelBorder();
		final JComboBox initialComboBox = makeComboBox();
		final JComboBox additionalComboBox = makeComboBox();
		final JRadioButton radioButtonOn = new JRadioButton("ON");
		upperButtonGroup.add(radioButtonOn);
		final JRadioButton radionButtonOff = new JRadioButton("OFF");
		radionButtonOff.setSelected(true);
		upperButtonGroup.add(radionButtonOff);
		final JRadioButton extraRadioButtonOn = new JRadioButton("ON");
		lowerButtonGroup.add(extraRadioButtonOn);
		final JRadioButton extraRadioButtonOff = new JRadioButton("OFF");
		extraRadioButtonOff.setSelected(true);
		lowerButtonGroup.add(extraRadioButtonOff);
		makeAllFields();
		final JLabel labelChooseTimeWeight = new JLabel("Choose Time Weight");
		final JLabel labelChooseChangeWeight = new JLabel("Choose Change Weight");
		final JLabel labelTimePreprocessing = new JLabel("Time PreProcessing");
		final JLabel labelChangePreprocessing = new JLabel("Change PreProcessing");
		final JLabel labelNumberOfPhases = new JLabel("Give Number of Phases");
		makeAllLabels(clusters, labelChooseTimeWeight, initialComboBox, labelChooseChangeWeight, additionalComboBox, labelTimePreprocessing,
				radioButtonOn, radionButtonOff, labelChangePreprocessing, extraRadioButtonOn, extraRadioButtonOff, labelNumberOfPhases);
	}

	@SuppressWarnings("rawtypes")
	private void assignHorizontalGroup(final JLabel labelChooseTimeWeight, final JComboBox comboBox, final JLabel labelChooseChangeWeight,
			final JComboBox extraComboBox, final JLabel labelTimePreprocessing, final JRadioButton radioButtonOn, final JRadioButton radionButtonOff,
			final JLabel labelNewLabel, final JRadioButton extraRadioButtonOn, final JRadioButton extraRadioButtonOff, final JLabel labelGiveNumberOf,
			final JLabel extraNewLabel, final JLabel labelBirthWeight, final JLabel labelDeathWeight, final JLabel labelChangeWeight,
			final GroupLayout groupLayoutContentPanel) {
		groupLayoutContentPanel.setHorizontalGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayoutContentPanel.createSequentialGroup().addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayoutContentPanel.createSequentialGroup()
								.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(labelChooseChangeWeight, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelChooseTimeWeight, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(labelTimePreprocessing, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(35))
						.addGroup(groupLayoutContentPanel.createSequentialGroup()
								.addComponent(labelNewLabel, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING).addGroup(groupLayoutContentPanel
								.createSequentialGroup().addGap(0)
								.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING, false).addGroup(groupLayoutContentPanel
										.createSequentialGroup()
										.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING, false)
												.addComponent(extraComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(comboBox, 0, 41, Short.MAX_VALUE))
										.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayoutContentPanel
														.createSequentialGroup().addGap(90)
														.addComponent(textField, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
												.addGroup(groupLayoutContentPanel.createSequentialGroup().addGap(33).addGroup(groupLayoutContentPanel
														.createParallelGroup(Alignment.LEADING).addComponent(labelGiveNumberOf)
														.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.TRAILING)
																.addGroup(groupLayoutContentPanel.createSequentialGroup()
																		.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
																				.addComponent(labelChangeWeight)
																				.addGroup(groupLayoutContentPanel.createSequentialGroup()
																						.addPreferredGap(ComponentPlacement.RELATED)
																						.addGroup(groupLayoutContentPanel
																								.createParallelGroup(Alignment.LEADING)
																								.addComponent(labelDeathWeight)
																								.addComponent(labelBirthWeight))))
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
																				.addGroup(groupLayoutContentPanel.createSequentialGroup()
																						.addGroup(groupLayoutContentPanel
																								.createParallelGroup(Alignment.TRAILING)
																								.addComponent(birthWeightTxtF,
																										GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
																								.addComponent(deathWeightTextField,
																										GroupLayout.DEFAULT_SIZE, 55,
																										Short.MAX_VALUE))
																						.addPreferredGap(ComponentPlacement.RELATED))
																				.addComponent(changeWeightTextField, GroupLayout.DEFAULT_SIZE, 54,
																						Short.MAX_VALUE)))
																.addComponent(extraNewLabel, Alignment.LEADING)))))
										.addGap(83))
										.addGroup(groupLayoutContentPanel.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING).addComponent(radioButtonOn)
														.addComponent(radionButtonOff).addComponent(extraRadioButtonOn))
												.addGap(111)
												.addComponent(giveClustersTextFields, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))))
								.addGroup(groupLayoutContentPanel.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(extraRadioButtonOff)))
						.addContainerGap()));
	}

	@SuppressWarnings("rawtypes")
	private void assignVerticalGroup(final JLabel labelChooseTimeWeight, final JComboBox comboBox, final JLabel labelChooseChangeWeight,
			final JComboBox extraComboBox, final JLabel labelTimePreprocessing, final JRadioButton radioButtonOn, final JRadioButton radioButtonOff,
			final JLabel labelNewLabel, final JRadioButton extraRadioButtonOn, final JRadioButton extraRadioButtonOff, final JLabel labelGiveNumberOf,
			final JLabel extraNewLabel, final JLabel labelBirthWeight, final JLabel labelDeathWeight, final JLabel labelChangeWeight,
			final GroupLayout groupLayoutContentPanel) {
		groupLayoutContentPanel.setVerticalGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayoutContentPanel.createSequentialGroup()
						.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(labelChooseTimeWeight, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(labelGiveNumberOf, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addGroup(
								groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayoutContentPanel.createSequentialGroup().addGap(18)
												.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(labelChooseChangeWeight, GroupLayout.PREFERRED_SIZE, 17,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(extraComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(
												groupLayoutContentPanel
														.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayoutContentPanel.createSequentialGroup().addGap(29)
										.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE).addComponent(labelTimePreprocessing)
												.addComponent(radioButtonOn))
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(radioButtonOff)
										.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayoutContentPanel.createSequentialGroup().addGap(33).addComponent(labelNewLabel))
												.addGroup(groupLayoutContentPanel.createSequentialGroup().addGap(18).addComponent(extraRadioButtonOn)
														.addPreferredGap(ComponentPlacement.RELATED).addComponent(extraRadioButtonOff))))
								.addGroup(groupLayoutContentPanel.createSequentialGroup().addGap(7).addComponent(extraNewLabel)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(giveClustersTextFields, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE).addComponent(labelBirthWeight)
												.addComponent(birthWeightTxtF, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(
												groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(deathWeightTextField, GroupLayout.PREFERRED_SIZE, 28,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelDeathWeight))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayoutContentPanel.createParallelGroup(Alignment.BASELINE).addComponent(labelChangeWeight)
												.addComponent(changeWeightTextField, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))))
						.addGap(6)));
	}

	private void checkIfClusters(final boolean clusters, final JLabel newLabel, final JLabel labelBirthWeight, final JLabel labelDeathWeight,
			final JLabel labelChangeWeight) {
		if (!clusters) {
			giveClustersTextFields.setVisible(false);
			newLabel.setVisible(false);
			labelChangeWeight.setVisible(false);
			labelBirthWeight.setVisible(false);
			labelDeathWeight.setVisible(false);
			changeWeightTextField.setVisible(false);
			birthWeightTxtF.setVisible(false);
			deathWeightTextField.setVisible(false);
		} else {
			giveClustersTextFields.setVisible(true);
			newLabel.setVisible(true);
			labelChangeWeight.setVisible(true);
			labelBirthWeight.setVisible(true);
			labelDeathWeight.setVisible(true);
			changeWeightTextField.setVisible(true);
			birthWeightTxtF.setVisible(true);
			deathWeightTextField.setVisible(true);
		}
	}

	private void checkIfTextFieldEmpty(final JRadioButton radioButtonOn, final JRadioButton radioButtonOff, final JRadioButton extraRadioButtonOn,
			final JRadioButton extraRadioButtonOff) {
		if (!textField.getText().isEmpty()) {
			numberOfPhases = Integer.parseInt(textField.getText());
		}
		if (!giveClustersTextFields.getText().isEmpty()) {
			numberOfClusters = Integer.parseInt(giveClustersTextFields.getText());
		}
		checkIfWeightEmpty();
		if (radioButtonOn.isSelected()) {
			preprocessingTime = true;
		} else if (radioButtonOff.isSelected()) {
			preprocessingTime = false;
		}
		if (extraRadioButtonOn.isSelected()) {
			preprocessingChange = true;
		} else if (extraRadioButtonOff.isSelected()) {
			preprocessingChange = false;
		}
	}

	private void checkIfWeightEmpty() {
		if (!birthWeightTxtF.getText().isEmpty()) {
			birthWeight = Double.parseDouble(birthWeightTxtF.getText());
		}
		if (!deathWeightTextField.getText().isEmpty()) {
			deathWeight = Double.parseDouble(deathWeightTextField.getText());
		}
		if (!changeWeightTextField.getText().isEmpty()) {
			changeWeightClear = Double.parseDouble(changeWeightTextField.getText());
		}
	}

	public Double geBirthWeight() {
		return birthWeight;
	}

	public float getChangeWeight() {
		return changeWeight;
	}

	public Double getChangeWeightCluster() {
		return changeWeightClear;
	}

	public boolean getConfirmation() {
		return confirm;
	}

	public Double getDeathWeight() {
		return deathWeight;
	}

	public Integer getNumberOfClusters() {
		return numberOfClusters;
	}

	public Integer getNumberOfPhases() {
		return numberOfPhases;
	}

	public boolean getPreProcessingChange() {
		return preprocessingChange;
	}

	public boolean getPreProcessingTime() {
		return preprocessingTime;
	}

	public float getTimeWeight() {
		return timeWeight;
	}

	private void makeAllFields() {
		textField = new JTextField();
		textField.setText("56");
		textField.setColumns(COLUMN_WIDTH);
		giveClustersTextFields = new JTextField();
		giveClustersTextFields.setText("14");
		giveClustersTextFields.setColumns(COLUMN_WIDTH);
	}

	@SuppressWarnings("rawtypes")
	private void makeAllLabels(final boolean clusters, final JLabel labelChooseTimeWeight, final JComboBox initialComboBox,
			final JLabel labelChooseChangeWeight, final JComboBox additionalComboBox, final JLabel labelTimePreprocessing,
			final JRadioButton radioButtonOn, final JRadioButton radioButtonOff, final JLabel labelChoosePreprocessing,
			final JRadioButton extraRadioButtonOn, final JRadioButton extraRadioButtonOff, final JLabel numberOfClusters) {
		final JLabel labelNumberOfClusters = new JLabel("Give Number of Clusters");
		final JLabel labelBirthWeight = new JLabel("Birth Weight");
		final JLabel labelDeathWeight = new JLabel("Death Weight");
		final JLabel labelChangeWeight = new JLabel("Change Weight");
		makeAllWeights();
		checkIfClusters(clusters, labelNumberOfClusters, labelBirthWeight, labelDeathWeight, labelChangeWeight);
		makeGroupPanel(labelChooseTimeWeight, initialComboBox, labelChooseChangeWeight, additionalComboBox, labelTimePreprocessing, radioButtonOn,
				radioButtonOff, labelChoosePreprocessing, extraRadioButtonOn, extraRadioButtonOff, numberOfClusters, labelNumberOfClusters,
				labelBirthWeight, labelDeathWeight, labelChangeWeight);
		makeButtonPane(initialComboBox, additionalComboBox, radioButtonOn, radioButtonOff, extraRadioButtonOn, extraRadioButtonOff);
	}

	private void makeAllWeights() {
		birthWeightTxtF = new JTextField();
		birthWeightTxtF.setText("0.333");
		birthWeightTxtF.setColumns(COLUMN_WIDTH);
		changeWeightTextField = new JTextField();
		changeWeightTextField.setText("0.333");
		changeWeightTextField.setColumns(COLUMN_WIDTH);
		deathWeightTextField = new JTextField();
		deathWeightTextField.setText("0.333");
		deathWeightTextField.setColumns(COLUMN_WIDTH);
	}

	@SuppressWarnings("rawtypes")
	private void makeButtonPane(final JComboBox comboBox, final JComboBox extraComboBox, final JRadioButton radioButtonOn,
			final JRadioButton radioButtonOff, final JRadioButton extraRadioButtonOn, final JRadioButton extraRadioButtonOff) {
		final JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		makeOkButton(comboBox, extraComboBox, radioButtonOn, radioButtonOff, extraRadioButtonOn, extraRadioButtonOff, buttonPane);
		makeCancelButton(buttonPane);
	}

	private ActionListener makeCancelAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				confirm = false;
				setVisible(false);
			}
		};
	}

	private void makeCancelButton(final JPanel buttonPane) {
		final JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(makeCancelAction());
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox makeComboBox() {
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "0.0", "0.5", "1.0" }));
		comboBox.setSelectedIndex(1);
		return comboBox;
	}

	@SuppressWarnings("rawtypes")
	private void makeGroupPanel(final JLabel labelChooseTimeWeight, final JComboBox initialComboBox, final JLabel labelChooseChangeWeight,
			final JComboBox additionalComboBox, final JLabel labelTimePreprocessing, final JRadioButton radioButtonOn,
			final JRadioButton radioButtonOff, final JLabel labelNewLabel, final JRadioButton extraRadioButtonOn,
			final JRadioButton extraRadioButtonOff, final JLabel labelGiveNumberOf, final JLabel extraNewLabel, final JLabel labelBirthWeight,
			final JLabel labelDeathWeight, final JLabel labelChangeWeight) {
		final GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		assignHorizontalGroup(labelChooseTimeWeight, initialComboBox, labelChooseChangeWeight, additionalComboBox, labelTimePreprocessing,
				radioButtonOn, radioButtonOff, labelNewLabel, extraRadioButtonOn, extraRadioButtonOff, labelGiveNumberOf, extraNewLabel,
				labelBirthWeight, labelDeathWeight, labelChangeWeight, gl_contentPanel);
		assignVerticalGroup(labelChooseTimeWeight, initialComboBox, labelChooseChangeWeight, additionalComboBox, labelTimePreprocessing,
				radioButtonOn, radioButtonOff, labelNewLabel, extraRadioButtonOn, extraRadioButtonOff, labelGiveNumberOf, extraNewLabel,
				labelBirthWeight, labelDeathWeight, labelChangeWeight, gl_contentPanel);
		contentPanel.setLayout(gl_contentPanel);
	}

	@SuppressWarnings("rawtypes")
	private ActionListener makeOkAction(final JComboBox comboBox, final JComboBox extraComboBox, final JRadioButton radioButtonOn,
			final JRadioButton radioButtonOff, final JRadioButton extraRadioButtonOn, final JRadioButton extraRadioButtonOff) {
		return new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				changeWeight = Float.valueOf((String) comboBox.getSelectedItem());
				timeWeight = Float.valueOf((String) extraComboBox.getSelectedItem());
				checkIfTextFieldEmpty(radioButtonOn, radioButtonOff, extraRadioButtonOn, extraRadioButtonOff);
				if (changeWeight != null && timeWeight != null && preprocessingTime != null && preprocessingChange != null
						&& numberOfPhases != null) {
					confirm = true;
					setVisible(false);
				} else {
					timeWeight = null;
					changeWeight = null;
					preprocessingTime = null;
					preprocessingChange = null;
					JOptionPane.showMessageDialog(null, "You have to fill every field!");
					confirm = false;
				}
			}
		};
	}

	@SuppressWarnings("rawtypes")
	private void makeOkButton(final JComboBox comboBox, final JComboBox extraComboBox, final JRadioButton radioButtonOn,
			final JRadioButton radioButtonOff, final JRadioButton extraRadioButtonOn, final JRadioButton extraRadioButtonOff,
			final JPanel buttonPane) {
		final JButton okButton = new JButton("OK");
		okButton.addActionListener(makeOkAction(comboBox, extraComboBox, radioButtonOn, radioButtonOff, extraRadioButtonOn, extraRadioButtonOff));
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	}

	private void setContnetPanelBorder() {
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
	}

}
