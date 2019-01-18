package logic.utilities.viewers;

public class SelectionViewerFactory {

	private SelectionViewerFactory() {}

	public static AbstractSelectionViewer makeTableConstruction(final String name) {
		switch (name) {
			case "Clusters":
				return new ClusterSelectionToZoomAreaViewer();
			case "Zoom":
				return new SelectionToZoomAreaViewer();
			default:
				return null;
		}
	}
	
}
