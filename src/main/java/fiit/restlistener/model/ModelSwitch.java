package fiit.restlistener.model;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ModelSwitch implements Node {

	public static final String HOST_ID = "s";
	private static final Paint COLOR = Color.BLUE;
	private LinkedHashSet<ModelPort> ports;
	private String dpid;
	
	public ModelSwitch(List<ModelPort> ports, String dpid) {
		setPorts(ports);
		setDpid(dpid);
	}
	
	private void setPorts(List<ModelPort> ports) {
		this.ports = new LinkedHashSet<>(ports);
	}
	private void setDpid(String dpid) {
		this.dpid = dpid;
	}
	
	public boolean containsPort(ModelPort port) {
		return ports.contains(port);
	}
	
	@Override
	public String getHostName() {
		return new String(HOST_ID + Integer.parseInt(dpid,16));
	}
	
	@Override
	public Paint getColor() {
		return COLOR;
	}
	
	@Override
	public List<String> printContextMenu() {
		List<String> list = new ArrayList<>();
		list.add("dpid: " + Integer.parseInt(dpid,16));
		ports.forEach(p -> list.add(p.getName() + " - num " + p.getPortNo() + " - " + p.getHw_addr()));
		return list;
	}
	
	@Override
	public int hashCode() {
		return dpid.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != ModelSwitch.class) {
			return false;
	    }
		ModelSwitch other = (ModelSwitch) obj;
	    if (this.hashCode()!=other.hashCode()) {
	    	return false;
	    }
	    return true;
	}
	
}
