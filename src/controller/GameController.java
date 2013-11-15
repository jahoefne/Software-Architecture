package controller;

import model.Field;
import model.Figures;

import java.awt.*;
import java.util.ArrayList;

/**
 * User: jahoefne
 * Creation Date: 03.11.13
 * Time: 11:38
 *
 * The main controller of the game, all relevant logic
 */
public class GameController {

	private static GameController instance;
	
	public static GameController GetInstance() {
		if(instance==null)
			instance = new GameController();
		return instance;
	}
	
	private GameController()
	{
		//init code
	}
	
    private Field field = new Field();
    private MoveValidator validator = new MoveValidator();

    public void resetGame(){
    	this.field=new Field();
    	this.validator=new MoveValidator();
    }
    
    // checks whether field is in checkmate state
    public boolean isCheckMate(Field field){
    	// iterate over field to find kings
    	Point[] kings  =  field.getKingsPositions();
    	for(Point king : kings){
    		// check stuff
    	}
    	return false;
    }
    
    
    public Point[] getPossibleMoves(Point p){
        ArrayList<Point> list= new ArrayList<Point>();
        for(int y=0;y<8;y++){
            for(int x=0;x<8;x++){
                 if(validator.isValid(p,new Point(x,y),field))
                     list.add(new Point(x,y));

            }
        }
        return list.toArray(new Point[list.size()]);
    }


    public boolean move(Point x, Point y){
        return validator.moveIfValid(x, y, field);
    }

    public String getUnicode(Point x){
           return Figures.lookUpID(field.getCell(x)).getMask().unicode;
    }

    public byte getID(Point x){
        return Figures.lookUpID(field.getCell(x)).getMask().id;
    }

    public boolean whitesTurn(){
        return field.getWhiteOrBlack()>0;
    }

    @Override
    public String toString(){
        return field.toString();
    }

}
