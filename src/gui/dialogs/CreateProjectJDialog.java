package gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class CreateProjectJDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final int COLUMN_SIZE = 10;
	private boolean confirm;
	private final JPanel contentPanel = new JPanel();
	private File fileToCreate;
	private JTextField textFieldFirstAssessment;
	private JTextField textFieldSecondAssessment;
	private JTextField textFieldDatasetText;
	private JTextField textFieldInputCsv;
	private JTextField textFieldProjectName;
	private JTextField textFieldTransitionXml;

	public CreateProjectJDialog(final String projectName, final String datasetText, final String inputCsv, final String firstAssessment,
			final String secondAssessment, final String transitionXml) {
		this.textFieldProjectName.setText(projectName);
		this.textFieldDatasetText.setText(datasetText);
		this.textFieldInputCsv.setText(inputCsv);
		this.textFieldFirstAssessment.setText(firstAssessment);
		this.textFieldSecondAssessment.setText(secondAssessment);
		this.textFieldTransitionXml.setText(transitionXml);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		final JLabel projectNameLabel = new JLabel("Project Name:");
		final JLabel datasetTextLabel = new JLabel("Dataset-txt:");
		final JLabel inputCsvLabel = new JLabel("Input-csv:");
		final JLabel firstAssessmentLabel = new JLabel("Assessement1-output:");
		final JLabel secondAssessmentLabel = new JLabel("Assessement2-output:");
		final JLabel transitionXmlLabel = new JLabel("Transition-xml:");
		makeTextFields();
		makeButtons(projectNameLabel, datasetTextLabel, inputCsvLabel, firstAssessmentLabel, secondAssessmentLabel, transitionXmlLabel);
		makeDialog();
	}

	private void assignHorizontalGroup(final JLabel projectNameLabel, final JLabel datasetTextLabel, final JLabel inputCsvLabel,
			final JLabel firstAssessmentLabel, final JLabel secondAssessmentLabel, final JLabel transitionXmlLabel, final JButton buttonDatasetTxt,
			final JButton buttonInputCsv, final JButton buttonFirstAssesment, final JButton buttonSecondAssesment, final JButton buttonTransitionsXml,
			final GroupLayout groupLayoutControlPane) {
		groupLayoutControlPane
				.setHorizontalGroup(groupLayoutControlPane.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayoutControlPane.createSequentialGroup()
								.addGroup(groupLayoutControlPane.createParallelGroup(Alignment.LEADING).addComponent(projectNameLabel)
										.addComponent(datasetTextLabel).addComponent(inputCsvLabel).addComponent(firstAssessmentLabel)
										.addComponent(secondAssessmentLabel).addComponent(transitionXmlLabel))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayoutControlPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayoutControlPane.createSequentialGroup()
												.addComponent(textFieldProjectName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addGap(55))
										.addComponent(textFieldDatasetText, Alignment.LEADING)
										.addComponent(textFieldInputCsv, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
										.addComponent(textFieldFirstAssessment, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
										.addComponent(textFieldSecondAssessment, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
										.addComponent(textFieldTransitionXml, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayoutControlPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(buttonTransitionsXml, 0, 0, Short.MAX_VALUE)
										.addComponent(buttonSecondAssesment, 0, 0, Short.MAX_VALUE)
										.addComponent(buttonFirstAssesment, 0, 0, Short.MAX_VALUE).addComponent(buttonInputCsv, 0, 0, Short.MAX_VALUE)
										.addComponent(buttonDatasetTxt, GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE))));
	}

	private void assignVerticalGroup(final JLabel projectNameLabel, final JLabel datasetTextLabel, final JLabel inputCsvLabel,
			final JLabel firstAssessmentLabel, final JLabel secondAssessmentLabel, final JLabel transitionXmlLabel, final JButton buttonDatasetTxt,
			final JButton buttonInputCsv, final JButton buttonFirstAssesment, final JButton buttonSecondAssesment, final JButton buttonTransitionsXml,
			final GroupLayout groupLayoutControlPane) {
		groupLayoutControlPane
				.setVerticalGroup(groupLayoutControlPane.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayoutControlPane.createSequentialGroup().addContainerGap()
								.addGroup(groupLayoutControlPane.createParallelGroup(Alignment.BASELINE).addComponent(projectNameLabel).addComponent(
										textFieldProjectName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayoutControlPane.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayoutControlPane.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayoutControlPane.createParallelGroup(Alignment.BASELINE)
														.addComponent(datasetTextLabel).addComponent(buttonDatasetTxt)))
										.addGroup(groupLayoutControlPane.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE).addComponent(textFieldDatasetText,
														GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayoutControlPane.createParallelGroup(Alignment.BASELINE).addComponent(inputCsvLabel)
										.addComponent(textFieldInputCsv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(buttonInputCsv))
								.addGap(7)
								.addGroup(groupLayoutControlPane.createParallelGroup(Alignment.BASELINE).addComponent(firstAssessmentLabel)
										.addComponent(textFieldFirstAssessment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(buttonFirstAssesment))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayoutControlPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textFieldSecondAssessment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(secondAssessmentLabel).addComponent(buttonSecondAssesment))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayoutControlPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(textFieldTransitionXml, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(transitionXmlLabel).addComponent(buttonTransitionsXml))
								.addGap(18)));
	}

	private String calculateToWrite() {
		String toWrite = "Project-name:" + textFieldProjectName.getText() + "\n";
		toWrite = toWrite + "Dataset-txt:" + textFieldDatasetText.getText() + "\n";
		toWrite = toWrite + "Input-csv:" + textFieldInputCsv.getText() + "\n";
		toWrite = toWrite + "Assessement1-output:" + textFieldFirstAssessment.getText() + "\n";
		toWrite = toWrite + "Assessement2-output:" + textFieldSecondAssessment.getText() + "\n";
		toWrite = toWrite + "Transition-xml:" + textFieldTransitionXml.getText();
		System.out.println(toWrite);
		return toWrite;
	}

	private boolean checkIfEmpty(boolean empty) {
		empty = checkIfEmptyTop(empty);
		empty = checkIfEmptyBottom(empty);
		return empty;
	}

	private boolean checkIfEmptyBottom(boolean empty) {
		if (textFieldFirstAssessment.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Assessment 1 Output cannot be empty!");
			empty = true;
		}
		if (textFieldSecondAssessment.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Assessment 2 Output cannot be empty!");
			empty = true;
		}
		if (textFieldTransitionXml.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Transitions Xml cannot be empty!");
			empty = true;
		}
		return empty;
	}

	private boolean checkIfEmptyTop(boolean empty) {
		if (textFieldProjectName.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Project Name cannot be empty!");
			empty = true;
		}
		if (textFieldDatasetText.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Dataset Txt cannot be empty!");
			empty = true;
		}
		if (textFieldInputCsv.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Input Csv cannot be empty!");
			empty = true;
		}
		return empty;
	}

	private void checkNotEmpty() {
		confirm = true;
		setVisible(false);
		final String toWrite = calculateToWrite();
		fileToCreate = new File("filesHandler/inis/" + textFieldProjectName.getText() + ".ini");
		System.out.print(fileToCreate.getAbsolutePath());
		if (!fileToCreate.exists()) {
			try {
				fileToCreate.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		final FileWriter fileWriter;
		final BufferedWriter bufferedWriter;
		try {
			fileWriter = new FileWriter(fileToCreate.getAbsoluteFile());
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(toWrite);
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean getConfirmation() {
		return confirm;
	}

	public File getFile() {
		return fileToCreate;
	}

	private void makeButtons(final JLabel projectName, final JLabel datasetText, final JLabel inputCsv, final JLabel firstAssessment,
			final JLabel secondAssessment, final JLabel transitionXml) {
		final JButton buttonDatasetTxt = new JButton("...");
		buttonDatasetTxt.addActionListener(showDialogueAction(textFieldDatasetText));
		final JButton buttonInputCsv = new JButton("...");
		buttonInputCsv.addActionListener(showDialogueAction(textFieldInputCsv));
		final JButton buttonFirstAssesment = new JButton("...");
		buttonFirstAssesment.addActionListener(showDialogueAction(textFieldFirstAssessment));
		final JButton buttonSecondAssesment = new JButton("...");
		buttonSecondAssesment.addActionListener(showDialogueAction(textFieldSecondAssessment));
		final JButton buttonTransitionsXml = new JButton("...");
		buttonTransitionsXml.addActionListener(showDialogueAction(textFieldTransitionXml));
		final GroupLayout groupLayoutContentPanel = new GroupLayout(contentPanel);
		assignHorizontalGroup(projectName, datasetText, inputCsv, firstAssessment, secondAssessment, transitionXml, buttonDatasetTxt, buttonInputCsv,
				buttonFirstAssesment, buttonSecondAssesment, buttonTransitionsXml, groupLayoutContentPanel);
		assignVerticalGroup(projectName, datasetText, inputCsv, firstAssessment, secondAssessment, transitionXml, buttonDatasetTxt, buttonInputCsv,
				buttonFirstAssesment, buttonSecondAssesment, buttonTransitionsXml, groupLayoutContentPanel);
		contentPanel.setLayout(groupLayoutContentPanel);
	}

	private void makeDialog() {
		final JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		final JButton okButton = new JButton("OK");
		okButton.addActionListener(showOkAction());
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		final JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(showCancelAction());
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	private void makeTextFields() {
		textFieldProjectName = new JTextField();
		textFieldProjectName.setColumns(COLUMN_SIZE);
		textFieldDatasetText = new JTextField();
		textFieldDatasetText.setColumns(COLUMN_SIZE);
		textFieldInputCsv = new JTextField();
		textFieldInputCsv.setColumns(COLUMN_SIZE);
		textFieldFirstAssessment = new JTextField();
		textFieldFirstAssessment.setColumns(COLUMN_SIZE);
		textFieldSecondAssessment = new JTextField();
		textFieldSecondAssessment.setColumns(COLUMN_SIZE);
		textFieldTransitionXml = new JTextField();
		textFieldTransitionXml.setColumns(COLUMN_SIZE);
	}

	private ActionListener showCancelAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				confirm = false;
				setVisible(false);
			}
		};
	}

	private ActionListener showDialogueAction(final JTextField targetText) {
		return new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				final int dialogValue = fileChooser.showDialog(CreateProjectJDialog.this, "Create");
				if (dialogValue == JFileChooser.APPROVE_OPTION) {
					final File file = fileChooser.getSelectedFile();
					System.out.println(file.toString());
					targetText.setText(file.toString());
				}
			}
		};
	}

	private ActionListener showOkAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				boolean empty = false;
				empty = checkIfEmpty(empty);
				if (!empty) {
					checkNotEmpty();
				} else {
					confirm = false;
				}
			}
		};
	}

}
