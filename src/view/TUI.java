package view;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import controller.GameController;
import org.apache.log4j.*;


public final class TUI {
	
	private static String newLine = System.getProperty("line.separator");
	private static Logger logger = Logger.getLogger("TUI.class");
    
    private static GameController GAME_CONTROLLER = GameController.getInstance();
    
    // useless but to satisfy sonar..
    private TUI() {}

    public static void main(String[] args) {
    	PropertyConfigurator.configure("log4j.properties");
        GAME_CONTROLLER.resetGame();
        GUI.main(null);
        read_input();
       
    }
    private static void read_input(){
		GAME_CONTROLLER = GameController.getInstance();
		logger.info(GAME_CONTROLLER);
		 String input = null;
	    
	     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	     
	     try {
			input = br.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
	    handle_input(input);
    }
    
    private static void display_help(){
    	String helptext = "----valid commands----\n" +
    			"NEW GAME - Starts a new game of chess\n"+
    			"MOVE A B - Moves a piece from point A to point B\n"+
    			"           Example: MOVE A2 A4\n"+
    			"EXIT     - Close the game\n"+
    			"HELP     - Display help\n";
    	System.out.println(helptext);
    }
    private static void handle_input(String input){
    	
    	String[] input_splitted = input.split(" ");

    	if(input.equals("HELP") && input_splitted.length == 1){
        	display_help();
        }else if(input_splitted[0].equalsIgnoreCase("NEW")
    			&& input_splitted[1].equalsIgnoreCase("GAME")
    			&& input_splitted.length == 2){
    		GAME_CONTROLLER.resetGame();
    		read_input();
    	}else if(input_splitted[0].equalsIgnoreCase("MOVE") &&
    			valid_point(input_splitted[1])&&
    			valid_point(input_splitted[2])&&
    			input_splitted.length == 3){
        		execute_move(input_splitted[1], input_splitted[2]);
        		read_input();
    	}else if(input.equals("EXIT")){
    		logger.info("Exiting Game");
    		System.exit(0);
    	}else{
    		logger.info("Invalid command, type HELP for help");
    	}
    	
    	read_input();
    	
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
    private static void execute_move(String from, String to){
    	int from_x = from.charAt(0) - 65;
		int from_y = 8 - (from.charAt(1) - '0');		
		int to_x = to.charAt(0) - 65;
		int to_y = 8 - (to.charAt(1) - '0');
		if(GAME_CONTROLLER.move(new Point(from_x,from_y), new Point(to_x, to_y))){
			logger.info("Successful MOVE");
		}else{
			logger.info("Invalid MOVE");
		}
    }
}
