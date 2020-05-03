package escape;

import java.util.Arrays;
import java.util.List;
import escape.board.coordinate.HexCoordinate;
import escape.exception.EscapeException;

public enum HexDirections {
	TOP, TOPRIGHT, BOTTOMRIGHT, BOTTOM, BOTTOMLEFT, TOPLEFT;
	
	static public List<HexDirections> getHexDirections() {
		return Arrays.asList(TOPRIGHT, TOP, BOTTOMRIGHT, BOTTOM, BOTTOMLEFT, TOPLEFT);
	}
	
	static public HexCoordinate getNeighbor(HexCoordinate start, HexDirections direction) {
		switch (direction) {
			case BOTTOM:
				return HexCoordinate.makeCoordinate(start.getX(), start.getY() - 1);
			case BOTTOMLEFT:
				return HexCoordinate.makeCoordinate(start.getX() - 1, start.getY());
			case BOTTOMRIGHT:
				return HexCoordinate.makeCoordinate(start.getX() + 1, start.getY() - 1);
			case TOP:
				return HexCoordinate.makeCoordinate(start.getX(), start.getY() + 1);
			case TOPLEFT:
				return HexCoordinate.makeCoordinate(start.getX() - 1, start.getY() + 1);
			case TOPRIGHT:
				return HexCoordinate.makeCoordinate(start.getX() + 1, start.getY());
			default:
				throw new EscapeException("Direction doesn't exist!");
		}
	}
	
}
