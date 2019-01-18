package data.dataProccessing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import data.dataPPL.pplTransition.AtomicChange;
import gr.uoi.cs.daintiness.hecate.sql.Attribute;
import gr.uoi.cs.daintiness.hecate.transitions.Deletion;
import gr.uoi.cs.daintiness.hecate.transitions.Insersion;
import gr.uoi.cs.daintiness.hecate.transitions.Transition;
import gr.uoi.cs.daintiness.hecate.transitions.TransitionList;

public class AtomicChangeConstruction {
	private static ArrayList<TransitionList> allTransitions = new ArrayList<TransitionList>();
	private static ArrayList<AtomicChange> atomicChanges;

	public AtomicChangeConstruction(final ArrayList<TransitionList> allTransitions) {
		atomicChanges = new ArrayList<AtomicChange>();
		AtomicChangeConstruction.allTransitions = allTransitions;
	}

	private String calculateTemporalType(final ArrayList<Transition> currentTransitions, final int index) {
		final String toReturn;
		if (currentTransitions.get(index) instanceof Insersion) {
			if (currentTransitions.get(index).getType().equals("UpdateTable")) {
				toReturn = "Addition";
			} else {
				toReturn = "Addition of New Table";
			}
		} else if (currentTransitions.get(index) instanceof Deletion) {
			if (currentTransitions.get(index).getType().equals("UpdateTable")) {
				toReturn = "Deletion";
			} else {
				toReturn = "Deletion of whole Table";
			}
		} else {
			if (currentTransitions.get(index).getType().equals("TypeChange")) {
				toReturn = "Type Change";
			} else {
				toReturn = "Key Change";
			}
		}
		return toReturn;
	}

	public ArrayList<AtomicChange> getAtomicChanges() {
		return atomicChanges;
	}

	public void makeAtomicChanges() {
		for (int outerIndex = 0; outerIndex < allTransitions.size(); outerIndex++) {
			final TransitionList currentTransitionList = allTransitions.get(outerIndex);
			final String oldVersion = currentTransitionList.getOldVersion();
			final String newVersion = currentTransitionList.getNewVersion();
			final ArrayList<Transition> currentTransitions = currentTransitionList.getList();
			for (int innerIndex = 0; innerIndex < currentTransitions.size(); innerIndex++) {
				final Collection<Attribute> affectedAttributes = currentTransitions.get(innerIndex).getAffAttributes();
				final Iterator<Attribute> attributesIterator = affectedAttributes.iterator();
				while (attributesIterator.hasNext()) {
					final Attribute hecateAttribute = attributesIterator.next();
					final String temporalType = calculateTemporalType(currentTransitions, innerIndex);
					final AtomicChange atomicChange = new AtomicChange(currentTransitions.get(innerIndex).getAffTable().getName(),
							hecateAttribute.getName(), temporalType, oldVersion, newVersion, outerIndex);
					atomicChanges.add(atomicChange);
				}
			}
		}
	}

}
