package escape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import escape.board.coordinate.SquareCoordinate;
import escape.exception.EscapeException;

public enum SquareDirections {
	TOP, TOPRIGHT, RIGHT, BOTTOMRIGHT, BOTTOM, BOTTOMLEFT, LEFT, TOPLEFT;
	
	static public List<SquareDirections> getOrthogonalDirections() {
		return Arrays.asList(RIGHT, LEFT, TOP, BOTTOM);
	}
	
	static public List<SquareDirections> getOmniDirections() {
		return Arrays.asList(RIGHT, LEFT, BOTTOM, TOP, TOPRIGHT, TOPLEFT, BOTTOMRIGHT, BOTTOMLEFT);
	}
	
	static public List<SquareDirections> getDiagonalDirections() {
		return Arrays.asList(TOPRIGHT, TOPLEFT, BOTTOMRIGHT, BOTTOMLEFT);
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
