package fiit.restlistener.model;

import java.util.LinkedHashSet;

public class ModelLink {

	private ModelPort src;
	private ModelPort dst;
	
	public ModelLink(ModelPort src, ModelPort dst) {
		setSrc(src);
		setDst(dst);
	}
	
	private void setSrc(ModelPort src) {
		this.src = src;
	}
	private void setDst(ModelPort dst) {
		this.dst = dst;
	}
	
	public Edge getEdge(LinkedHashSet<ModelSwitch> switches) {
		Node srcNode = switches.stream()
			.filter(s -> s.containsPort(src))
			.findAny().get();
		Node dstNode = switches.stream()
			.filter(s -> s.containsPort(dst))
			.findAny().get();
		String description = src.getName() + ":" + dst.getName();
		return new Edge(srcNode,dstNode,description);
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		result ^= src.hashCode();
		result ^= dst.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != ModelLink.class) {
			return false;
	    }
		ModelLink other = (ModelLink) obj;
	    if (this.hashCode()!=other.hashCode()) {
	    	return false;
	    }
	    return true;
	}
	
}
