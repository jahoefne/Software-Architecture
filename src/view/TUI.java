package view;

import controller.IGameController;
import org.apache.log4j.Logger;
import util.Event;
import util.IObserver;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public final class TUI implements IObserver {

    private static String newLine = System.getProperty("line.separator");
    private static final String ENTER_COMMAND = "Enter your move or HELP:";
    private String message = null;
    private static Logger logger = Logger.getLogger("TUI.class");

    private static IGameController gameController;


    public TUI(IGameController controller) {
        this.gameController = controller;
        controller.addObserver(this);
    }

    public void printTUI() {
        logger.info(gameController);
        if (message != null) {
            logger.info(message + newLine);
        }
        message = null;
        logger.info(ENTER_COMMAND + newLine);
    }

    public boolean readInput() {
        String input = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            input = br.readLine();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return handleInput(input);
    }

    private void displayHelp() {
        String helpText = "----valid commands----\n" +
                "NEW GAME - Starts a new game of Chess\n" +
                "MOVE A B - Moves a piece from point A to point B\n" +
                "           Example: MOVE A2 A4\n" +
                "EXIT     - Close the game\n" +
                "HELP     - Display help\n";
        logger.info(helpText);
        logger.info("Enter your move or HELP:" + newLine);
        readInput();

    }

    private boolean handleInput(String input) {
        String[] inputSplitted = input.split(" ");

        if (input.equals("HELP") && inputSplitted.length == 1) {
            displayHelp();
        } else if (inputSplitted[0].equalsIgnoreCase("NEW")
                && inputSplitted[1].equalsIgnoreCase("GAME")
                && inputSplitted.length == 2) {
            gameController.resetGame();

        } else if (inputSplitted[0].equalsIgnoreCase("MOVE") &&
                validPoint(inputSplitted[1]) &&
                validPoint(inputSplitted[2]) &&
                inputSplitted.length == 3) {
            executeMove(inputSplitted[1], inputSplitted[2]);

        } else if (input.equals("EXIT")) {
            logger.info("Exiting Game");
            return false;
        }

        logger.info("Invalid command, type HELP for help");
        return true;
    }

    private static boolean validPoint(String point) {
        int a = (int) point.charAt(0);
        int b = point.charAt(1) - '0';
        if (a >= (int) 'A' && a <= (int) 'H'
                && b >= 1 && b <= 8
                && point.length() == 2) {
            return true;
        } else {
            return false;
        }
    }

    private void executeMove(String from, String to) {
        int fromX = from.charAt(0) - 65;
        int fromY = 8 - (from.charAt(1) - '0');
        int toX = to.charAt(0) - 65;
        int toY = 8 - (to.charAt(1) - '0');
        if (gameController.move(new Point(fromX, fromY), new Point(toX, toY))) {
            logger.info("Last move was valid!");
        } else {
            logger.info("Last move was invalid!");
        }
    }

    @Override
    public void update(Event e) {
        System.out.println("notified!");
        printTUI();
    }
}
