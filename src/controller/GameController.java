package controller;

import model.Field;
import model.Figures;
import model.IField;
import util.Observable;

import java.awt.*;
import java.util.ArrayList;

    /**
     * User: jahoefne
     * Creation Date: 03.11.13
     * Time: 11:38
     * The main controller of the game, all relevant logic
     * this class also serves as the 'Game Object' instantiate a GameController object in order
     * to start a new game.
     */
    public class GameController extends Observable implements IGameController{

        private static GameController instance=null;
        private boolean gameOver=false;
        private static final int FIELD_LENGTH = 8;

        public static IGameController getInstance() {
            if (instance == null) {
                instance = new GameController();
            }
            return instance;
        }

        private IField field = new Field();
        private MoveValidator validator = new MoveValidator();

        public void resetGame() {
            this.field = new Field();
            this.gameOver=false;
            this.validator = new MoveValidator();
            notifyObservers();
        }

        public Point[] getPossibleMoves(Point p) {
            ArrayList<Point> list = new ArrayList<Point>();

            if(gameOver){
                return list.toArray(new Point[list.size()]);
            }

            for (int y = 0; y < FIELD_LENGTH; y++) {
                for (int x = 0; x < FIELD_LENGTH; x++) {
                    if (validator.isValid(p, new Point(x, y), field)) {
                        list.add(new Point(x, y));
                    }
                }
            }
            return list.toArray(new Point[list.size()]);
        }


        public boolean move(Point x, Point y) {
        	
        	ArrayList<Point> enemy_possible_moves = get_turns_possible_moves(positions_colour());
        	
            boolean retval = !gameOver && validator.moveIfValid(x, y, field);
            
            if(retval){
            	
            	// Need to switch turn otherwise are no moves valid for the moved figure
            	field.toggleWhiteOrBlack();
            	Point[] possible_moves_moved_Figure = getPossibleMoves(y);
            	for(Point possibility : possible_moves_moved_Figure){
            		enemy_possible_moves.add(possibility);
            		System.out.println(possibility);
            	}
            	field.toggleWhiteOrBlack();
            	notifyObservers();
            }
            
            Point king_position = get_king_position((byte)6 * (byte)field.getWhiteOrBlack());
            
        	if(king_in_check(king_position, enemy_possible_moves)){
        		
        		if(enemy_possible_moves.containsAll(moves_king_in_check(king_position))){
        			System.out.println("Schach Matt");
        			gameOver = true;
        		}else{
        			System.out.println("Schach");
        		}
        	}
            return retval;
            
            
        }

        public String getUnicode(Point x) {
            return Figures.lookUpID(field.getCell(x)).getMask().getUnicode();
        }

        public byte getID(Point x) {
            return Figures.lookUpID(field.getCell(x)).getMask().getId();
        }

        public boolean whitesTurn() {
            return field.getWhiteOrBlack() > 0;
        }

        public boolean isGameOver(){
            // the game is over if there are not 2 kings on the board anymore
           int numberOfKings=0;
           for (int y = 0; y < FIELD_LENGTH; y++) {
               for (int x = 0; x < FIELD_LENGTH; x++) {
                   if (Math.abs(field.getCell(x,y))==Figures.KingWhite.id()){
                        numberOfKings++;
                   }
               }
           }
            if(numberOfKings!=2){
                 this.gameOver=true;
                 notifyObservers();
            }
            
            return gameOver;
        }
        
        private ArrayList<Point> moves_king_in_check(Point king_position){
        	Point[] moves = getPossibleMoves(king_position);
        	ArrayList<Point> retList = new ArrayList<Point>();
        	for(Point p : moves){
        		retList.add(p);
        	}
        	return retList;
        }
        
        private ArrayList<Point> get_turns_possible_moves(ArrayList<Point> enemy_positions){
        	ArrayList<Point> enemy_possible_moves = new ArrayList<Point>();
        	for(Point p : enemy_positions){
        		Point[] possiblities = getPossibleMoves(p);
        		for(Point possible_move : possiblities){
        			enemy_possible_moves.add(possible_move);
        		}
        	}
        	return enemy_possible_moves;
        }
        
        // check if king is in check
        private boolean king_in_check(Point king_position, ArrayList<Point> enemy_possible_moves){
        	return enemy_possible_moves.contains(king_position);
        }
        
        //gets all possible positions of colour on turn
        private ArrayList<Point> positions_colour(){
        	byte turn = field.getWhiteOrBlack();
        	
        	ArrayList<Point> positions = new ArrayList<Point>();
        	for (int y = 0; y < FIELD_LENGTH; y++) {
                for (int x = 0; x < FIELD_LENGTH; x++) {
                	if(turn > 0){
	                    if(field.getCell(x, y) > 0){
	                    	positions.add(new Point(x,y));
	                    }
                	}else{
                		if(field.getCell(x, y) < 0){
	                    	positions.add(new Point(x,y));
	                    }
                	}
                }
            }
        	return positions;
        }

        //gets Position of king
        private Point get_king_position(int b){
        	for (int y = 0; y < FIELD_LENGTH; y++) {
                for (int x = 0; x < FIELD_LENGTH; x++) {
                    if(instance.field.getCell(x, y) == (byte)b ){
                    	return new Point(x,y);
                    }
                }
            }
        	return null;
        }

        @Override
        public String toString() {
            return field.toString();
        }
        
        

}
