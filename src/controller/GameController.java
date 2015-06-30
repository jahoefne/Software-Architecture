package controller;

import model.Field;
import model.Figures;
import model.IField;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import util.Observable;


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
@javax.persistence.Entity
public class GameController extends Observable implements IGameController, Serializable {

    /**
     *  _id is a naming convention from lightcouch
     */
    @JsonProperty("_id")
    @javax.persistence.Column(name = "id")
    @javax.persistence.Id
    private String _id = java.util.UUID.randomUUID().toString(); // NOSONAR


    /**
     * _rev is a namingconvention from lightcouch
     */
    @javax.persistence.Column(name = "rev")
    @JsonProperty("_rev")
    private String _rev = null; //NOSONAR

    private String createdBy;
    private Date createdOn;
    private String blackPlayerID;
    private String whitePlayerID;

    private boolean gameOver = false;

    private static final int FIELD_LENGTH = 8;

    /* Check is a reserved word in SQL which makes hibernate fail! */
    @javax.persistence.Column(name = "checkBool")
    private boolean check = false;

    private static final int SIX = 6;

    @JsonDeserialize(as = Field.class)
    @javax.persistence.OneToOne
    private Field field = new Field();

    private static MoveValidator validator = new MoveValidator();

    private List<IPlugin> plugins = new ArrayList<IPlugin>();

    public GameController(String id, String createdBy) {
        this._id = id;
        this.createdBy = createdBy;
    }
    public GameController(String id, String createdBy, List<IPlugin> plugins) {
        this._id = id;
        this.createdBy = createdBy;
        this.plugins = plugins;
        for(IPlugin p : plugins){
            p.gameCreatedPlugin(this);
        }
    }

    public GameController(List<IPlugin> plugins) {
        this.plugins = plugins;
        for(IPlugin p : plugins){
            p.gameCreatedPlugin(this);
        }
    }

    public GameController(){}

    public GameController(boolean gameOver, boolean check, Field field) {
        this.gameOver = gameOver;
        this.check = check;
        this.field = field;
        for(IPlugin p : plugins){
            p.gameCreatedPlugin(this);
        }
    }

    public GameController(boolean gameOver, boolean check, Field field,List<IPlugin> plugins) {
        this.gameOver = gameOver;
        this.check = check;
        this.field = field;
        this.plugins = plugins;
        for(IPlugin p : plugins){
            p.gameCreatedPlugin(this);
        }
    }

    public void addPlugin(IPlugin plugin){
        plugins.add(plugin);
    }

    public void removePlugin(IPlugin plugin){
        plugins.remove(plugin);
    }

    public IField getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
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

        for(IPlugin p : plugins){
            p.moveCalledPlugin(this, x, y);
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
            for(IPlugin p : plugins){
                p.gameOver(this);
            }
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

    public String get_id() { //NOSONAR
        return _id;//NOSONAR
    }

    public void set_id(String _id) { //NOSONAR
        this._id = _id; //NOSONAR
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
        if (whitePlayerID.equals(blackPlayerID)) {
            blackPlayerID = null;
        }
        this.whitePlayerID = whitePlayerID;
    }

    public void movePlayerToSpec(String playerID) {
        if (playerID.equals(blackPlayerID)) {
            blackPlayerID = null;
        }
        if (playerID.equals(whitePlayerID)) {
            whitePlayerID = null;
        }
    }

    public String getBlackPlayerID() {
        return blackPlayerID;
    }

    public void setBlackPlayerID(String blackPlayerID) {
        if (blackPlayerID.equals(whitePlayerID)) {
            whitePlayerID = null;
        }
        this.blackPlayerID = blackPlayerID;
    }

    public String get_rev() { //NOSONAR
        return _rev; //NOSONAR
    }

    public void set_rev(String _rev) {//NOSONAR
        this._rev = _rev;//NOSONAR
    }
}
