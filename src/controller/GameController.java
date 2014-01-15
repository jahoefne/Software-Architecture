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
            boolean retval = !gameOver && validator.moveIfValid(x, y, field);
            notifyObservers();
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
                 
            }
            notifyObservers();
            return gameOver;
        }

        @Override
        public String toString() {
            return field.toString();
        }

}
