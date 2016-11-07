package fiit.restlistener.model;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ModelHost implements Node {
	
	public static final String HOST_ID = "h";
	private static final Paint COLOR = Color.GREEN;
	private ModelPort port;
	private String mac;
	private LinkedHashSet<String> ipv4;
	private LinkedHashSet<String> ipv6;
	private String hostName;
	
	public ModelHost(ModelPort port, String mac, List<String> ipv4, List<String> ipv6) {
		setPort(port);
		setMac(mac);
		setIpv4(ipv4);
		setIpv6(ipv6);
	}
	
	private void setPort(ModelPort port) {
		this.port = port;
	}
	private void setMac(String mac) {
		this.mac = mac;
	}
	private void setIpv4(List<String> ipv4) {
		this.ipv4 = new LinkedHashSet<>(ipv4);
	}
	private void setIpv6(List<String> ipv6) {
		this.ipv6 = new LinkedHashSet<>(ipv6);
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public Edge getEdge(LinkedHashSet<ModelSwitch> switches) {
		if(port!=null) {
			ModelSwitch dstNode = switches.stream()
				.filter(s -> s.containsPort(port))
				.findAny().get();
			String description = hostName + "-eth0 : " + port.getName();
			return new Edge(this,dstNode,description);
		} else {
			return null;
		}
	}
	
	public ModelPort getLinkedSwitchPort() {
		return port;
	}
	
	@Override
	public String getHostName() {
		return hostName;
	}

	@Override
	public Paint getColor() {
		return COLOR;
	}
	
	@Override
	public List<String> printContextMenu() {
		List<String> list = new ArrayList<>();
		list.add("mac: " + mac);
		ipv4.forEach(ip -> list.add("ipv4: " + ip));
		ipv6.forEach(ip -> list.add("ipv6: " + ip));
		return list;
	}
	
	@Override
	public int hashCode() {
		return mac.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != ModelHost.class) {
			return false;
	    }
		ModelHost other = (ModelHost) obj;
	    if (this.hashCode()!=other.hashCode()) {
	    	return false;
	    }
	    return true;
	}
	
}
