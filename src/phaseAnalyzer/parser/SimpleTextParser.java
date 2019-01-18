package phaseAnalyzer.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import phaseAnalyzer.commons.TransitionHistory;
import phaseAnalyzer.commons.TransitionStats;

class SimpleTextParser implements IParser {

	@Override
	public TransitionHistory parse(String fileName, String delimeter) {
		int transitionId;
		int time;
		String oldVersionFile;
		String newVersionFile;
		int numberOfOldTables;
		int numberOfNewTables;
		int numberOfOldAtributes;
		int numberOfNewAttributes;
		int numTablesIns;
		int numTablesDel;
		int numAttrIns;
		int numAttrDel;
		int numAttrWithTypeAlt;
		int numAttrInKeyAlt;
		int numAttrInsInNewTables;
		int numAttrDelWithDelTables;
		int totalUpdatesInOneTr;
		int totalUpdates = 0;
		final int _NumFields = 16;
		Scanner inputStream = null;
		TransitionHistory transitionHistory = new TransitionHistory();
		try {
			System.out.println("csv:" + fileName);
			inputStream = new Scanner(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Problem opening files.");
			System.exit(0);
		}
		int counter = 0;
		String line = inputStream.nextLine();
		counter++;
		while (inputStream.hasNextLine()) {
			line = inputStream.nextLine();
			counter++;
			StringTokenizer tokenizer = new StringTokenizer(line, delimeter);
			if (tokenizer.countTokens() != _NumFields) {
				System.out.println(
						"Wrong Input format. I found " + tokenizer.countTokens() + " values, but I expect " + _NumFields + " values per row!");
				System.exit(0);
			}
			String stringToParse = tokenizer.nextToken();
			transitionId = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			time = Integer.parseInt(stringToParse);
			oldVersionFile = tokenizer.nextToken();
			newVersionFile = tokenizer.nextToken();
			stringToParse = tokenizer.nextToken();
			numberOfOldTables = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numberOfNewTables = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numberOfOldAtributes = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numberOfNewAttributes = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numTablesIns = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numTablesDel = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numAttrIns = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numAttrDel = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numAttrWithTypeAlt = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numAttrInKeyAlt = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numAttrInsInNewTables = Integer.parseInt(stringToParse);
			stringToParse = tokenizer.nextToken();
			numAttrDelWithDelTables = Integer.parseInt(stringToParse);
			totalUpdatesInOneTr = numAttrIns + numAttrDel + numAttrWithTypeAlt + numAttrInKeyAlt + numAttrInsInNewTables + numAttrDelWithDelTables;
			TransitionStats v = new TransitionStats(transitionId, time, oldVersionFile, newVersionFile, numberOfOldTables, numberOfNewTables,
					numberOfOldAtributes, numberOfNewAttributes, numTablesIns, numTablesDel, numAttrIns, numAttrDel, numAttrWithTypeAlt,
					numAttrInKeyAlt, numAttrInsInNewTables, numAttrDelWithDelTables, totalUpdatesInOneTr);
			transitionHistory.addValue(v);
			totalUpdates = totalUpdates + numAttrIns + numAttrDel + numAttrWithTypeAlt + numAttrInKeyAlt + numAttrInsInNewTables
					+ numAttrDelWithDelTables;
		}
		transitionHistory.setTotalUpdates(totalUpdates);
		transitionHistory.setTotalTime();
		System.out.println(counter + " lines parsed");
		inputStream.close();
		Iterator<TransitionStats> transitionsIter = transitionHistory.getValues().iterator();
		if (transitionHistory.getValues().size() == 0)
			return transitionHistory;
		TransitionStats previousTransition, currentTransition;
		currentTransition = transitionsIter.next();
		currentTransition.setTimeDistFromPrevious(0);
		previousTransition = currentTransition;
		while (transitionsIter.hasNext()) {
			currentTransition = transitionsIter.next();
			currentTransition.setTimeDistFromPrevious(currentTransition.getTime() - previousTransition.getTime());
			previousTransition = currentTransition;
		}
		return transitionHistory;
	}

}
