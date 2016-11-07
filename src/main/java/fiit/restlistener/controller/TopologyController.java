package fiit.restlistener.controller;

import fiit.restlistener.view.FrameWindow;
import fiit.restlistener.view.TopologyPanel;

public class TopologyController {

	private TopologyPanel topologyPanel; 
	private FrameWindow frameWindow;
	private String ipv4AndPort;
	
	public TopologyController(FrameWindow frameWindow, String ipv4AndPort) {
		setFrameWindow(frameWindow);		
		setIpv4AndPort(ipv4AndPort);
		setTopologyPanel();
		registerTopologyPanel();
	}
	
	private void setTopologyPanel() {
		try {
			topologyPanel = new TopologyPanel(ipv4AndPort);
		} catch (Exception e) {
			new ErrorMessage(e.getMessage());
		}
	}
	
	private void setFrameWindow(FrameWindow frameWindow) {
		this.frameWindow = frameWindow;
	}
	
	private void setIpv4AndPort(String ipv4AndPort) {
		this.ipv4AndPort = ipv4AndPort;
	}
	
	private void registerTopologyPanel() {
		frameWindow.changeVisualisationPanel(topologyPanel);
	}
	
}
