package fiit.restlistener.model;

public class ModelPort {

	private String hw_addr;
	private String name;
	private String port_no;
	private String dpid;
	
	public ModelPort(String hw_addr, String name, String port_no, String dpid) {
		setHw_addr(hw_addr);
		setName(name);
		setPort_no(port_no);
		setDpid(dpid);
	}
	
	private void setHw_addr(String hw_addr) {
		this.hw_addr = hw_addr;
	}
	private void setName(String name) {
		this.name = name;
	}
	private void setPort_no(String port_no) {
		this.port_no = port_no;
	}
	private void setDpid(String dpid) {
		this.dpid = dpid;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getDpid() {
		return Integer.parseInt(dpid.trim(),16);
	}
	
	public Integer getPortNo() {
		return Integer.parseInt(port_no.trim(),16);
	}
	
	public String getHw_addr() {
		return hw_addr.trim();
	}
	
	@Override
	public int hashCode() {
		return hw_addr.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != ModelPort.class) {
			return false;
	    }
		ModelPort other = (ModelPort) obj;
	    if (this.hashCode()!=other.hashCode()) {
	    	return false;
	    }
	    return true;
	}
	
}
