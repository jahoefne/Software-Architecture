package view;

import controller.GameController;
import controller.IGameController;
import controller.IPlugin;
import org.apache.log4j.PropertyConfigurator;
import plugin.OneHundredMovesOrLess;

import java.util.ArrayList;
import java.util.List;

public final class Chess {
	
	private Chess(){}

    public static void main(String[] args) {

        PropertyConfigurator.configure("log4j.properties");

        List<IPlugin> plugins = new ArrayList<IPlugin>();
        //plugins.add(new FrenchRevolutionPlugin());
        plugins.add(new OneHundredMovesOrLess());
        IGameController controller = new GameController(plugins);

        @SuppressWarnings("unused")
        GUI gui = new GUI(controller);
        gui.init();

        TUI tui = new TUI(controller);

        tui.printTUI();

        boolean continu = true;
        while (continu) {
        		 continu = tui.readInput(); 
        }
    }
}
