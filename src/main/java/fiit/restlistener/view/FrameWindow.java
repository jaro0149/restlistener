package fiit.restlistener.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class FrameWindow extends JFrame {

	private static final String TITLE = "Ryu REST Listener 1.0";
	public static final int HEIGHT = 600;
	public static final int WIDTH = 900;
	
	private final MainBox mainBox = new MainBox(BoxLayout.X_AXIS);
	private JPanel actualPanel = null;
	
	public FrameWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(true);
		setTitle(TITLE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		add(mainBox,BorderLayout.NORTH);
	}
	
	public void changeVisualisationPanel(JPanel panel) {		
		SwingUtilities.invokeLater(() -> {
			invalidate();
			if(actualPanel!=null) {
				this.remove(actualPanel);
			}
			this.add(panel);
			validate();
		});
	}
	
	public MainBox getMainBox() {
		return mainBox;
	}
	
}
