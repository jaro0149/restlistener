package fiit.restlistener.controller;

import fiit.restlistener.view.FrameWindow;

public class FrameController {

	private final FrameWindow frameWindow = new FrameWindow();
	
	@SuppressWarnings("unused")
	public FrameController() {
		frameWindow.getMainBox().addActionShowTopology(e -> {
			String ipv4AndPort = frameWindow.getMainBox().getControllerIp();
			TopologyController topologyController = new TopologyController(frameWindow,ipv4AndPort);
		});
	}
	
	public void showWindow(boolean visible) {
		frameWindow.setVisible(visible);
	}
	
}
