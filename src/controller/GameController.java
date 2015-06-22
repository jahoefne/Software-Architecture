package controller;

import model.Field;
import model.Figures;
import model.IField;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import util.Observable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: jahoefne
 * Creation Date: 03.11.13
 * Time: 11:38
 * The main controller of the game, all relevant logic
 * this class also serves as the 'Game Object' instantiate a GameController object in order
 * to start a new game.
 */
@Entity
public class GameController extends Observable implements IGameController, Serializable {

    /**
     * Added the following attributes for compatibility with hte webtech project
     */
    @JsonProperty("_id")
    @Id
    private String gameID = java.util.UUID.randomUUID().toString();

    @JsonProperty("_rev")
    private String rev;

    private String createdBy;
    private Date createdOn;
    private String blackPlayerID;
    private String whitePlayerID;

    private boolean gameOver = false;

    private static final int FIELD_LENGTH = 8;

     /* Check is a reserved word in SQL which makes hibernate fail! */
    @Column(name = "checkBool")
    private boolean check = false;

    private static final int SIX = 6;

    @JsonDeserialize(as=Field.class)

    @OneToOne
    private Field field = new Field();

    private static MoveValidator validator = new MoveValidator();

    public GameController(){}

    public GameController(boolean gameOver, boolean check, Field field){
        this.gameOver=gameOver;
        this.check=check;
        this.field=field;
    }

    public IField getField(){
        return field;
    }

    public static int getFieldLength() {
        return FIELD_LENGTH;
    }

    public void resetGame() {
        this.field = new Field();
        this.gameOver = false;
        this.validator = new MoveValidator();
        notifyObservers();
    }

    public Point[] getPossibleMoves(Point p) {
        ArrayList<Point> list = new ArrayList<Point>();

        if (gameOver) {
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
        List<Point> enemyPossibleMoves = getTurnsPossibleMoves(positionsColour());

        boolean returnValue = !gameOver && validator.moveIfValid(x, y, field);

        if (returnValue) {
            // Need to switch turn otherwise are no moves valid for the moved figure
            field.toggleWhiteOrBlack();
            Point[] possibleMovesMovedFigure = getPossibleMoves(y);
            for (Point possibility : possibleMovesMovedFigure) {
                enemyPossibleMoves.add(possibility);
            }
            field.toggleWhiteOrBlack();
            notifyObservers();
        }

        Point kingPosition = getKingPosition((byte) SIX * (byte) field.getWhiteOrBlack());

        if (kingInCheck(kingPosition, enemyPossibleMoves)) {
            this.check = true;
            notifyObservers();
        }
        return returnValue;
    }

    public String getUnicode(Point x) {
        return Figures.lookUpID(field.getCell(x)).getMask().getUnicode();
    }

    public int getID(Point x) {
        return Figures.lookUpID(field.getCell(x)).getMask().getId();
    }

    public boolean whitesTurn() {
        return field.getWhiteOrBlack() > 0;
    }

    public boolean isGameOver() {
        // the game is over if there are not 2 kings on the board anymore
        int numberOfKings = 0;
        for (int y = 0; y < FIELD_LENGTH; y++) {
            for (int x = 0; x < FIELD_LENGTH; x++) {
                if (Math.abs(field.getCell(x, y)) == Figures.KingWhite.id()) {
                    numberOfKings++;
                }
            }
        }
        if (numberOfKings != 2) {
            this.gameOver = true;
            notifyObservers();
        }

        return gameOver;
    }

    private List<Point> getTurnsPossibleMoves(List<Point> enemyPositions) {
        List<Point> enemyPossibleMoves = new ArrayList<Point>();
        for (Point p : enemyPositions) {
            Point[] possibilities = getPossibleMoves(p);
            for (Point possibleMove : possibilities) {
                enemyPossibleMoves.add(possibleMove);
            }
        }
        return enemyPossibleMoves;
    }

    // check if king is in check
    private boolean kingInCheck(Point kingPosition, List<Point> enemyPossibleMoves) {
        return enemyPossibleMoves.contains(kingPosition);
    }

    //gets all possible positions of colour on turn
    private List<Point> positionsColour() {
        byte turn = field.getWhiteOrBlack();

        List<Point> positions = new ArrayList<Point>();
        for (int y = 0; y < FIELD_LENGTH; y++) {
            for (int x = 0; x < FIELD_LENGTH; x++) {
                if (turn > 0) {
                    if (field.getCell(x, y) > 0) {
                        positions.add(new Point(x, y));
                    }
                } else {
                    if (field.getCell(x, y) < 0) {
                        positions.add(new Point(x, y));
                    }
                }
            }
        }
        return positions;
    }

    //gets Position of king
    private Point getKingPosition(int b) {
        for (int y = 0; y < FIELD_LENGTH; y++) {
            for (int x = 0; x < FIELD_LENGTH; x++) {
                if (this.field.getCell(x, y) == (byte) b) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    public boolean getCheck() {
        return this.check;
    }

    public void setCheck() {
        this.check = false;
    }

    @Override
    public String toString() {
        return field.toString();
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getWhitePlayerID() {
        return whitePlayerID;
    }

    public void setWhitePlayerID(String whitePlayerID) {
        this.whitePlayerID = whitePlayerID;
    }

    public String getBlackPlayerID() {
        return blackPlayerID;
    }

    public void setBlackPlayerID(String blackPlayerID) {
        this.blackPlayerID = blackPlayerID;
    }
}
