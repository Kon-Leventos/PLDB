package logic.actionListeners;

import java.awt.event.ActionListener;
import gui.mainEngine.Gui;

public final class ActionListenerFactory {

	private ActionListenerFactory() {}

	public static ActionListener makeActionListener(final String name, final Gui theGui) {
		switch (name) {
			case "Create":
				return new CreateProjectAction(theGui);
			case "Edit":
				return new EditProjectAction(theGui);
			case "Help":
				return new HelpAction();
			case "Info":
				return new InfoAction();
			case "Load":
				return new LoadProjectAction(theGui);
			case "FullDetailed":
				return new ShowFullDetailedLifetimeTableAction(theGui);
			case "PhasesPLD":
				return new ShowPhasesDiagramAction(theGui);
			case "PhasesClusters":
				return new ShowPhasesWithClustersDiagramAction(theGui);
			case "PLD":
				return new ShowDiagramAction(theGui);
			default:
				return null;
		}
	}

}
