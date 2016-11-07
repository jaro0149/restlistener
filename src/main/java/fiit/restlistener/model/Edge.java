package fiit.restlistener.model;

import java.util.ArrayList;
import java.util.List;

public class Edge {

	private Node srcNode;
	private Node dstNode;
	private String description;
	
	public Edge(Node srcNode, Node dstNode, String description) {
		setSrcNode(srcNode);
		setDstNode(dstNode);
		setDescription(description);
	}
	
	private void setSrcNode(Node srcNode) {
		this.srcNode = srcNode;
	}
	
	private void setDstNode(Node dstNode) {
		this.dstNode = dstNode;
	}
	
	private void setDescription(String description) {
		this.description = description;
	}
	
	public List<Node> getEdgeList() {
		List<Node> list = new ArrayList<>();
		list.add(srcNode);
		list.add(dstNode);
		return list;
	}
	
	public String getDescription() {
		return this.description;
	}
	
}
