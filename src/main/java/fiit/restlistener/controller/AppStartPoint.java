package fiit.restlistener.controller;

import java.awt.EventQueue;

public class AppStartPoint {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				FrameController mainWindow = new FrameController();
				mainWindow.showWindow(true);
			} catch (Exception e) {
				new ErrorMessage(e.getMessage());
			}
		});
	}
	
}
