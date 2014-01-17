package controller;

import model.Field;
import model.Figures;
import model.IField;
import util.Observable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        private boolean check = false;
        private static final int SIX=6;

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
             System.out.println(x+"    "+y);
            List<Point> enemyPossibleMoves = getTurnsPossibleMoves(positionsColour());
        	
            boolean returnValue = !gameOver && validator.moveIfValid(x, y, field);
            
            if(returnValue){
            	// Need to switch turn otherwise are no moves valid for the moved figure
            	field.toggleWhiteOrBlack();
            	Point[] possibleMovesMovedFigure = getPossibleMoves(y);
            	for(Point possibility : possibleMovesMovedFigure){
            		enemyPossibleMoves.add(possibility);
            	}
            	field.toggleWhiteOrBlack();
            	notifyObservers();
            }

            Point kingPosition = getKingPosition((byte) SIX * (byte) field.getWhiteOrBlack());
            
        	if(kingInCheck(kingPosition, enemyPossibleMoves)){
        		this.check = true;
        		notifyObservers();
        	}
            return returnValue;
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
        
        private List<Point> getTurnsPossibleMoves(List<Point> enemyPositions){
            List<Point> enemyPossibleMoves = new ArrayList<Point>();
        	for(Point p : enemyPositions){
        		Point[] possibilities = getPossibleMoves(p);
        		for(Point possibleMove : possibilities){
        			enemyPossibleMoves.add(possibleMove);
        		}
        	}
        	return enemyPossibleMoves;
        }
        
        // check if king is in check
        private boolean kingInCheck(Point kingPosition, List<Point> enemyPossibleMoves){
        	return enemyPossibleMoves.contains(kingPosition);
        }
        
        //gets all possible positions of colour on turn
        private List<Point> positionsColour(){
        	byte turn = field.getWhiteOrBlack();

            List<Point> positions = new ArrayList<Point>();
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
        private Point getKingPosition(int b){
        	for (int y = 0; y < FIELD_LENGTH; y++) {
                for (int x = 0; x < FIELD_LENGTH; x++) {
                    if(instance.field.getCell(x, y) == (byte)b ){
                    	return new Point(x,y);
                    }
                }
            }
        	return null;
        }
        
        public boolean getCheck(){
        	return this.check;
        }
        public void setCheck(){
        	this.check = false;
        }

        @Override
        public String toString() {
            return field.toString();
        }
}
