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
    
    private static final int  NEW_COMMAND_LENGTH = 2;
    private static final int MOVE_COMMAND_LENGTH = 3;
    private static final int MIN_FIELD = 1;
    private static final int MAX_FIELD = 8;
    private static final int TOINT = 65;
    private static final int ONE = 1;
    private static final int TWO = 2;

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
            logger.info("Error reading input");
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
        
        if (inputSplitted.length == ONE){
        	return help_or_exit(input);
        }else{
        	return new_or_move(inputSplitted);
        }
    }
    private boolean new_or_move(String[] inputSplitted){
    	
    	if (inputSplitted[0].equalsIgnoreCase("NEW")
                && inputSplitted[1].equalsIgnoreCase("GAME")
                && inputSplitted.length == NEW_COMMAND_LENGTH) {
            gameController.resetGame();
            return true;

        }
        if (inputSplitted[0].equalsIgnoreCase("MOVE") &&
                validPoint(inputSplitted[1]) &&
                validPoint(inputSplitted[2]) &&
                inputSplitted.length == MOVE_COMMAND_LENGTH) {
            executeMove(inputSplitted[1], inputSplitted[2]);
            return true;
        }
        return invalid_command();
    }
    
    private boolean help_or_exit(String input){
    	if (input.equals("HELP")) {
            displayHelp();
            return true;
        }
    	if (input.equals("EXIT")) {
            logger.info("Exiting Game");
            return false;
        }
    	return invalid_command();
    }
    private boolean invalid_command(){
    	logger.info("Invalid command, type HELP for help");
    	return true;
    }

    private static boolean validPoint(String point) {
        int a = (int) point.charAt(0);
        int b = point.charAt(1) - '0';
        boolean correct_char = a >= (int) 'A' && a <= (int) 'H';
        boolean correct_number = b >= MIN_FIELD  && b <= MAX_FIELD;
        return correct_char && correct_number && (point.length() == TWO);

    }

    private void executeMove(String from, String to) {
        int fromX = from.charAt(0) - TOINT;
        int fromY = MAX_FIELD - (from.charAt(1) - '0');
        int toX = to.charAt(0) - TOINT;
        int toY = MAX_FIELD - (to.charAt(1) - '0');
        if (gameController.move(new Point(fromX, fromY), new Point(toX, toY))) {
            logger.info("Last move was valid!");
        } else {
            logger.info("Last move was invalid!");
        }
    }

    @Override
    public void update(Event e) {
        printTUI();
    }
}
