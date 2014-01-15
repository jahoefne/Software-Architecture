package view;

import controller.GameController;
import controller.IGameController;
import org.apache.log4j.PropertyConfigurator;

public class Chess {

    public static void main(String[] args) {

        PropertyConfigurator.configure("log4j.properties");

        IGameController controller = GameController.getInstance();

        @SuppressWarnings("unused")
        GUI gui = new GUI(controller);
        TUI tui = new TUI(controller);

        tui.printTUI();

        boolean continu = true;
        while (continu) {
            continu = tui.readInput();
        }
    }

}
