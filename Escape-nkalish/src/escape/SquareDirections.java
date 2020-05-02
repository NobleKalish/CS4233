package escape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import escape.board.coordinate.SquareCoordinate;
import escape.exception.EscapeException;

public enum SquareDirections {
	TOP, TOPRIGHT, RIGHT, BOTTOMRIGHT, BOTTOM, BOTTOMLEFT, LEFT, TOPLEFT;
	
	static public ArrayList<SquareDirections> getOrthogonalDirections() {
		List<SquareDirections> orthoList =  Arrays.asList(RIGHT, LEFT, TOP, BOTTOM);
		ArrayList<SquareDirections> allOrtho =  new ArrayList<SquareDirections>();
		allOrtho.addAll(orthoList);
		return allOrtho;
	}
	
	static public ArrayList<SquareDirections> getOmniDirections() {
		List<SquareDirections> orthoList =  Arrays.asList(RIGHT, LEFT, BOTTOM, TOP, TOPRIGHT, TOPLEFT, BOTTOMRIGHT, BOTTOMLEFT);
		ArrayList<SquareDirections> allOrtho =  new ArrayList<SquareDirections>();
		allOrtho.addAll(orthoList);
		return allOrtho;
	}
	
	static public ArrayList<SquareDirections> getDiagonalDirections() {
		List<SquareDirections> orthoList =  Arrays.asList(TOPRIGHT, TOPLEFT, BOTTOMRIGHT, BOTTOMLEFT);
		ArrayList<SquareDirections> allOrtho =  new ArrayList<SquareDirections>();
		allOrtho.addAll(orthoList);
		return allOrtho;
	}
	
	static public SquareCoordinate getNeighbor(SquareCoordinate start, SquareDirections direction) {
		switch(direction) {
			case BOTTOM:
				return SquareCoordinate.makeCoordinate(start.getX(), start.getY() - 1);
			case BOTTOMLEFT:
				return SquareCoordinate.makeCoordinate(start.getX() - 1, start.getY() - 1);
			case BOTTOMRIGHT:
				return SquareCoordinate.makeCoordinate(start.getX() + 1, start.getY() - 1);
			case LEFT:
				return SquareCoordinate.makeCoordinate(start.getX() - 1, start.getY());
			case RIGHT:
				return SquareCoordinate.makeCoordinate(start.getX() + 1, start.getY());
			case TOP:
				return SquareCoordinate.makeCoordinate(start.getX(), start.getY() + 1);
			case TOPLEFT:
				return SquareCoordinate.makeCoordinate(start.getX() - 1, start.getY() + 1);
			case TOPRIGHT:
				return SquareCoordinate.makeCoordinate(start.getX() + 1, start.getY() + 1);
			default:
				throw new EscapeException("Direction is unavailable");
		}
	}
}
