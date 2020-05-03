package escape;

import java.util.Arrays;
import java.util.List;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.exception.EscapeException;

public enum OrthoDirections {
	RIGHT, BOTTOM, LEFT, TOP;
	
	static public List<OrthoDirections> getOrthoDirections() {
		return Arrays.asList(RIGHT, TOP, LEFT, BOTTOM);
	}
	
	static public OrthoSquareCoordinate getNeighbor(OrthoSquareCoordinate start, OrthoDirections direction) {
		switch (direction) {
			case BOTTOM:
				return OrthoSquareCoordinate.makeCoordinate(start.getX(), start.getY() - 1);
			case LEFT:
				return OrthoSquareCoordinate.makeCoordinate(start.getX() - 1, start.getY());
			case RIGHT:
				return OrthoSquareCoordinate.makeCoordinate(start.getX() + 1, start.getY());
			case TOP:
				return OrthoSquareCoordinate.makeCoordinate(start.getX(), start.getY() + 1);
			default:
				throw new EscapeException("Direction doesn't exist!");
		}
	}
}
