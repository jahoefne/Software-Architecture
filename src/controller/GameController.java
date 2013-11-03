package controller;

import model.Field;

import java.awt.*;

/**
 * User: jahoefne
 * Creation Date: 03.11.13
 * Time: 11:38
 *
 * The main controller of the game, all relevant logic
 */
public class GameController {

    Field field = new Field();
    MoveValidator validator = new MoveValidator();

    public void reset(){
        field.reset();
    }

    public boolean move(Point x, Point y){
        return false;
    }
}
