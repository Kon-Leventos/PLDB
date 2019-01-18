package logic.mouseAdapters;

import java.awt.event.MouseAdapter;
import gui.mainEngine.Gui;

public final class MouseAdapterFactory {
	
	private MouseAdapterFactory() {}

	public static MouseAdapter makeMouseAdapter(final String name, final Gui theGui) {
		switch (name) {
			case "Enlarge":
				return new EnlargeMouse();
			case "Time":
				return new OverTimeMouse();
			case "Width":
				return new SameWidthMouse();
			case "Undo":
				return new UndoMouse(theGui);
			case "In":
				return new ZoomInMouse();
			case "Out":
				return new ZoomOutMouse();
			default:
				return null;
		}
	}

}
