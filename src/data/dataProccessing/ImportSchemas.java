package data.dataProccessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import gr.uoi.cs.daintiness.hecate.diff.Delta;
import gr.uoi.cs.daintiness.hecate.diff.DiffResult;
import gr.uoi.cs.daintiness.hecate.parser.HecateParser;
import gr.uoi.cs.daintiness.hecate.sql.Schema;
import gr.uoi.cs.daintiness.hecate.transitions.Deletion;
import gr.uoi.cs.daintiness.hecate.transitions.Insersion;
import gr.uoi.cs.daintiness.hecate.transitions.TransitionList;
import gr.uoi.cs.daintiness.hecate.transitions.Transitions;
import gr.uoi.cs.daintiness.hecate.transitions.Update;

public class ImportSchemas {
	private static ArrayList<Schema> allSchemas;
	private static ArrayList<TransitionList> allTransitions;
	private String filepath;
	private String transitionsFile;

	public ImportSchemas(final String filepath, final String transitionsFile) {
		allTransitions = new ArrayList<TransitionList>();
		allSchemas = new ArrayList<Schema>();
		this.filepath = filepath;
		this.transitionsFile = transitionsFile;
	}

	private Transitions calculateTransitions(final ArrayList<String> allSchemas, final String standardString) {
		final Transitions transition = new Transitions();
		for (int index = 0; index < allSchemas.size(); index++) {
			if (index == allSchemas.size() - 1) {
				final String string = standardString + allSchemas.get(index);
				final Schema schema = HecateParser.parse(string);
				ImportSchemas.allSchemas.add(schema);
				break;
			}
			final String initialString = standardString + allSchemas.get(index).trim();
			ImportSchemas.allSchemas.add(HecateParser.parse(initialString));
			final String finalString = standardString + allSchemas.get(index + 1).trim();
			final Schema oldSchema = HecateParser.parse(initialString);
			final Schema newSchema = HecateParser.parse(finalString);
			TransitionList transitionList = new TransitionList();
			DiffResult differenceResult = new DiffResult();
			differenceResult = Delta.minus(oldSchema, newSchema);
			transitionList = differenceResult.tl;
			transition.add(transitionList);
		}
		return transition;
	}

	public ArrayList<Schema> getAllHecateSchemas() {
		return allSchemas;
	}

	public ArrayList<TransitionList> getAllTransitions() {
		return allTransitions;
	}

	public void loadDataset() throws IOException {
		final BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
		File file = new File(filepath);
		final String dataset = (file.getName().split("\\."))[0];
		final String parentDataset = file.getParent();
		file = new File(parentDataset);
		final String path = file.getAbsolutePath() + File.separator + dataset;
		final ArrayList<String> allSchemas = new ArrayList<String>();
		String line;
		while (true) {
			line = bufferedReader.readLine();
			if (line == null) {
				break;
			}
			allSchemas.add(line);
		}
		final String standardString = path + File.separator;
		final Transitions transitions = calculateTransitions(allSchemas, standardString);
		bufferedReader.close();
		makeTransitions(transitions);
	}

	public void makeTransitions(final Transitions transition) throws IOException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Update.class, Deletion.class, Insersion.class, TransitionList.class, Transitions.class);
			final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(transition, new FileOutputStream(transitionsFile));
			jaxbContext = JAXBContext.newInstance(Update.class, Deletion.class, Insersion.class, TransitionList.class, Transitions.class);
			final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			final File inputFile = new File(transitionsFile);
			final Transitions root = (Transitions) unmarshaller.unmarshal(inputFile);
			allTransitions = (ArrayList<TransitionList>) root.getList();
		} catch (JAXBException e) {
			JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
			return;
		}
	}

}
