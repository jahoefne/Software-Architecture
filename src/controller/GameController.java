package controller;

import model.Field;
import model.Figures;
import model.IField;

import java.awt.*;
import java.util.ArrayList;

    /**
     * User: jahoefne
     * Creation Date: 03.11.13
     * Time: 11:38
     * <p/>
     * The main controller of the game, all relevant logic
     */
    public class GameController {

        private static GameController instance;
        private static final int FIELD_LENGTH = 8;

        public static GameController getInstance() {
            if (instance == null) {
                instance = new GameController();
            }
            return instance;
        }

        private IField field = new Field();
        private MoveValidator validator = new MoveValidator();

        public void resetGame() {
            this.field = new Field();
            this.validator = new MoveValidator();
        }

        public Point[] getPossibleMoves(Point p) {
            ArrayList<Point> list = new ArrayList<Point>();
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
            return validator.moveIfValid(x, y, field);
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

        @Override
        public String toString() {
            return field.toString();
        }

}
