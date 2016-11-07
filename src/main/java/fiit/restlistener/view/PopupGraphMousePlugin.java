package fiit.restlistener.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.JPopupMenu;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import fiit.restlistener.model.Edge;
import fiit.restlistener.model.Node;

public class PopupGraphMousePlugin extends AbstractPopupGraphMousePlugin implements MouseListener {

	public PopupGraphMousePlugin() {
		this(MouseEvent.BUTTON3_MASK);
	}

	public PopupGraphMousePlugin(int modifiers) {
		super(modifiers);
	}

	@SuppressWarnings("unchecked")
	protected void handlePopup(MouseEvent e) {
		final VisualizationViewer<Node, Edge> vv = (VisualizationViewer<Node, Edge>) e.getSource();
		final Point2D p = e.getPoint();
		GraphElementAccessor<Node, Edge> pickSupport = vv.getPickSupport();
		if (pickSupport != null) {
			final Node station = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
			if (station != null) {
				JPopupMenu popup = new JPopupMenu();
				station.printContextMenu().forEach(popup::add);
				popup.show(vv, e.getX(), e.getY());
			}
		}
	}
}