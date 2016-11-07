package fiit.restlistener.view;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MainBox extends Box {

	private static final String[] TEXTS = {"Ryu controller [IPv4:port]: ","Show topology","192.168.153.130:8080"};
	private final JLabel lblControllerIp = new JLabel(TEXTS[0]);
	private final JTextField txtControllerIp = new JTextField(TEXTS[2]);	
	private final JButton btnShowTopology = new JButton(TEXTS[1]);
	
	public MainBox(int axis) {
		super(axis);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(lblControllerIp);
		add(txtControllerIp);
		add(btnShowTopology);
	}
	
	public void addActionShowTopology(ActionListener listener) {
		btnShowTopology.addActionListener(listener);
	}
	
	public String getControllerIp() {
		return txtControllerIp.getText().trim();
	}
	
}
