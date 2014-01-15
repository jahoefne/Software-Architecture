package view;
import org.apache.log4j.PropertyConfigurator;

import controller.GameController;
import controller.IGameController;
import view.TUI;

public class chess {

	public static void main(String[] args) {
		
		PropertyConfigurator.configure("log4j.properties");
		
		IGameController controller = new GameController();
		
		TUI tui = new TUI(controller);
		tui.printTUI();

	}

}
