package fiit.restlistener.model;

import java.awt.Paint;
import java.util.List;

public interface Node {

	public List<String> printContextMenu();
	public String getHostName();
	public Paint getColor();
	
}
