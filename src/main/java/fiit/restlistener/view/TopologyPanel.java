package fiit.restlistener.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.net.MalformedURLException;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import fiit.restlistener.model.Edge;
import fiit.restlistener.model.ModelHost;
import fiit.restlistener.model.ModelLink;
import fiit.restlistener.model.ModelSwitch;
import fiit.restlistener.model.Node;
import fiit.restlistener.model.TopologyAccess;
import tech.sirwellington.alchemy.http.exceptions.AlchemyHttpException;

@SuppressWarnings("serial")
public class TopologyPanel extends JPanel {

	private final Graph<Node,Edge> graph = new UndirectedSparseMultigraph<>();
	private final JPanel modePanel = new JPanel(new GridLayout(2,1));
	private final JPanel zoomPanel = new JPanel(new GridLayout(0,1));
	private final JPanel edgesPanel = new JPanel(new GridLayout(0,1));
	private final Box controls = Box.createHorizontalBox();
	private VisualizationViewer<Node,Edge> visualisation;
	private GraphZoomScrollPane scrollZoomPanel;
	private TopologyAccess topologyAccess;
	private String ipv4AndPort;
	private LinkedHashSet<ModelSwitch> switches;
	private LinkedHashSet<ModelHost> hosts;
	private LinkedHashSet<ModelLink> links;
	private List<Edge> edges;
	
	public TopologyPanel(String ipv4AndPort) throws AlchemyHttpException, MalformedURLException, IllegalArgumentException {
		
		GridBagLayout gridLayout = new GridBagLayout();
		gridLayout.columnWidths = new int[] {800};
		gridLayout.columnWeights = new double[] {1.0};
		setLayout(gridLayout);
		
		setIpv4AndPort(ipv4AndPort);
		setTopologyAccess();
		readModels();
		addVerticesToGraph();
		setVisualisationViewer();
		setModePanel();
		setZoomPanel();
		setEdgesPanel();
		setControlBox();
		setRenderContext();
		setScrollZoomPane();
		composePanel();
	}
	
	private void setIpv4AndPort(String ipv4AndPort) {
		this.ipv4AndPort = ipv4AndPort;
	}
	
	private void setTopologyAccess() {
		topologyAccess = new TopologyAccess(ipv4AndPort);
	}
	
	private void readModels() throws AlchemyHttpException, MalformedURLException, IllegalArgumentException {
		switches = topologyAccess.getSwitches();
		hosts = topologyAccess.getHosts();
		links = topologyAccess.getLinks();
		edges = topologyAccess.getEdges(links, switches, hosts);
	}

	private void addVerticesToGraph() {
		switches.forEach(graph::addVertex);
		hosts.forEach(graph::addVertex);
		edges.forEach(e -> graph.addEdge(e,e.getEdgeList()));
	}
	
	private void setVisualisationViewer() {
		Layout<Node,Edge> layout = new KKLayout<Node,Edge>(graph);
		layout.setSize(new Dimension(FrameWindow.WIDTH,FrameWindow.HEIGHT));
		visualisation = new VisualizationViewer<Node,Edge>(layout);
		visualisation.setPreferredSize(new Dimension(FrameWindow.WIDTH,FrameWindow.HEIGHT));
	}
	
	private void setModePanel() {
		DefaultModalGraphMouse<Integer, Number> graphMouse = new DefaultModalGraphMouse<Integer, Number>();
		graphMouse.add(new PopupGraphMousePlugin());
		visualisation.setGraphMouse(graphMouse);
		modePanel.setBorder(BorderFactory.createTitledBorder("Mouse mode"));
		modePanel.add(graphMouse.getModeComboBox());
	}
	
	private void setZoomPanel() {
		ScalingControl scaler = new CrossoverScalingControl();
		JButton plus = new JButton("+");
		plus.addActionListener(e -> {
			scaler.scale(visualisation, 1.1f, visualisation.getCenter());
		});
		JButton minus = new JButton("-");
		minus.addActionListener(e -> {
			scaler.scale(visualisation, 1 / 1.1f, visualisation.getCenter());
		});
		zoomPanel.setBorder(BorderFactory.createTitledBorder("Zooming tools"));
		zoomPanel.add(plus);
		zoomPanel.add(minus);
	}
	
	private void setEdgesPanel() {
		ButtonGroup radio = new ButtonGroup();
		JRadioButton lineButton = new JRadioButton("Line");
		lineButton.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				visualisation.getRenderContext().setEdgeShapeTransformer(EdgeShape.line(graph));
				visualisation.repaint();
			}
		});
		JRadioButton quadButton = new JRadioButton("QuadCurve");
		quadButton.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				visualisation.getRenderContext().setEdgeShapeTransformer(EdgeShape.quadCurve(graph));
				visualisation.repaint();
			}
		});
		JRadioButton cubicButton = new JRadioButton("CubicCurve");
		cubicButton.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				visualisation.getRenderContext().setEdgeShapeTransformer(EdgeShape.cubicCurve(graph));
				visualisation.repaint();
			}
		});
		radio.add(lineButton);
		radio.add(quadButton);
		radio.add(cubicButton);
		edgesPanel.setBorder(BorderFactory.createTitledBorder("Edge properties"));
		edgesPanel.add(lineButton);
		edgesPanel.add(quadButton);
		edgesPanel.add(cubicButton);
	}
	
	private void setControlBox() {
		controls.add(edgesPanel);
		controls.add(zoomPanel);
		controls.add(modePanel);
	}
	
	private void setRenderContext() {
		visualisation.getRenderContext().setVertexLabelTransformer(node -> node.getHostName());
		visualisation.getRenderContext().setVertexFillPaintTransformer(node -> node.getColor());
		visualisation.getRenderContext().setEdgeLabelTransformer(edge -> edge.getDescription());
	}
	
	private void setScrollZoomPane() {
		scrollZoomPanel = new GraphZoomScrollPane(visualisation);
	}
	
	private void composePanel() {
		add(scrollZoomPanel, new GridBagTemplate(GridBagConstraints.WEST, 0, 0, 10, 10, 10, 10, 1, 1.0));
		add(controls, new GridBagTemplate(GridBagConstraints.WEST, 0, 1, 10, 10, 10, 10));
	}
	
}
