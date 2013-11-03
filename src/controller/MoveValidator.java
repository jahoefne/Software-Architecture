package controller;

import java.awt.Point;

import model.*;

public class MoveValidator {
    //o p a b c
    //n - - - d
    //m - x - e
    //l - - - f
    //k j i h g
    public static Vector2D[] directions ={     // denotes the 16 possible direction vectors
            new Vector2D(0,2),    // x -> a
            new Vector2D(1,2),    // x -> b
            new Vector2D(2,2),    // x -> c
            new Vector2D(2,1),    // x -> d
            new Vector2D(2,0),    // x -> e
            new Vector2D(2,-1),   // x -> f
            new Vector2D(2,-2),   // x -> g
            new Vector2D(1,-2),   // x -> h
            new Vector2D(0,-2),   // x -> i
            new Vector2D(-1,-2),  // x -> j
            new Vector2D(-2,-2),  // x -> k
            new Vector2D(-2,-1),  // x -> l
            new Vector2D(-2,0),  // x -> m
            new Vector2D(-2,1),  // x -> n
            new Vector2D(-2,2),  // x -> o
            new Vector2D(-1,2),  // x -> p

    };

	private int whiteOrBlack=1;
	// Which player can move next -> 
	//						negative => Black
	//						positive => White
	
	// Moves the figure at p1 to p2 if the move is valid
	public byte[][] move(Point p1, Point p2, byte[][]field){
		if(isValid(p1,p2,field)){		// if the move is valid
				field[p2.x][p2.y]= field[p1.x][p1.y]; // clear p1 move p1 to p2
				field[p1.x][p1.y] = Figures.empty.id();
				whiteOrBlack*=-1;		// change color for next move
		}
		return field;			
	}

	// checks if the move is valid
	public boolean isValid(Point p1, Point p2, byte[][]field){
		
		FigureMask f1 = Figures.getMap().get(field[p1.x][p1.y]);
		FigureMask f2 = Figures.getMap().get(field[p2.x][p2.y]);
		
		if(!(f1.id*whiteOrBlack>0)) // is the right player trying to move
			return false;
		
		
	//	double d =this.getDegrees(p1, p2);
	//	System.out.println(d);
		
		return false;
	}
}
