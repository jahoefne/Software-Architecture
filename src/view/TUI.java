package view;

import controller.IGameController;
import org.apache.log4j.Logger;
import util.Event;
import util.IObserver;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public final class TUI implements IObserver{
	
	private static String newLine = System.getProperty("line.separator");
	private static final String enter_command = "Enter your move or HELP:";
	private String message = null;
	private static Logger logger = Logger.getLogger("TUI.class");
    
    private IGameController GAME_CONTROLLER;


    public TUI(IGameController controller) {
    	this.GAME_CONTROLLER = controller;
    	controller.addObserver(this);
    }

    public void printTUI(){
    	logger.info(GAME_CONTROLLER);
    	if(message != null){
    		logger.info(message+newLine);
    	}
    	message = null;
    	logger.info(enter_command+newLine);
    }
    
    public boolean read_input(){
		 String input = null;
	    
	     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	     
	     try {
			input = br.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
	    return handle_input(input);
    }
    
    private void display_help(){
    	String helptext = "----valid commands----\n" +
    			"NEW GAME - Starts a new game of chess\n"+
    			"MOVE A B - Moves a piece from point A to point B\n"+
    			"           Example: MOVE A2 A4\n"+
    			"EXIT     - Close the game\n"+
    			"HELP     - Display help\n";
    	logger.info(helptext);
    	logger.info("Enter your move or HELP:"+newLine);
    	read_input();

    }
    private boolean handle_input(String input){
    	
    	String[] input_splitted = input.split(" ");

    	if(input.equals("HELP") && input_splitted.length == 1){
        	display_help();
        }else if(input_splitted[0].equalsIgnoreCase("NEW")
    			&& input_splitted[1].equalsIgnoreCase("GAME")
    			&& input_splitted.length == 2){
    		GAME_CONTROLLER.resetGame();
    		
    	}else if(input_splitted[0].equalsIgnoreCase("MOVE") &&
    			valid_point(input_splitted[1])&&
    			valid_point(input_splitted[2])&&
    			input_splitted.length == 3){
        		execute_move(input_splitted[1], input_splitted[2]);
        		
    	}else if(input.equals("EXIT")){
    		logger.info("Exiting Game");
    		return false;
    	}else{
    		logger.info("Invalid command, type HELP for help");
    	}
    	return true;
    }
    
    private static boolean valid_point(String point){
    	int a = (int) point.charAt(0);
    	int b = point.charAt(1) - '0';
    	if(a >= (int)'A' && a <= (int)'H'
			&& b >= 1 && b <= 8
			&& point.length() == 2){
    		return true;   		
    	}else{
    		return false;
    	}
    }
    private void execute_move(String from, String to){
    	int from_x = from.charAt(0) - 65;
		int from_y = 8 - (from.charAt(1) - '0');		
		int to_x = to.charAt(0) - 65;
		int to_y = 8 - (to.charAt(1) - '0');
		if(GAME_CONTROLLER.move(new Point(from_x,from_y), new Point(to_x, to_y))){
			logger.info("Last move was valid!");
		}else{
			logger.info("Last move was invalid!");
		}
		
    }

	@Override
	public void update(Event e) {
		
        System.out.println("notified!");
		printTUI();
	}
}
