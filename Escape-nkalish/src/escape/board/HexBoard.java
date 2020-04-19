/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape.board;

import java.util.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * Description
 * 
 * @version Apr 17, 2020
 */
public class HexBoard implements Board<HexCoordinate> {
	private Map<HexCoordinate, LocationType> hexes;
	private Map<HexCoordinate, EscapePiece> pieces;

	private int xMax;
	private int yMax;

	public HexBoard() {
		pieces = new HashMap<>();
		hexes = new HashMap<>();
	}

	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(HexCoordinate coord) {
		return this.pieces.get(coord);
	}

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece,
	 * escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, HexCoordinate coord) {
		if (this.coordsInBound(coord)) {
			this.pieces.put(coord, p);
		} else {
			throw new EscapeException("Coordinates are not in bounds!");
		}
	}

	/**
	 * Set the location type at a given coordinate with given location type
	 * @param c Coordinate to set the location type at
	 * @param lt LocationType to set at the given coordinate
	 */
	public void setLocationType(HexCoordinate c, LocationType lt) {
		if (coordsInBound(c)) {
			this.hexes.put(c, lt);
		} else {
			throw new EscapeException("Coordinates are not in bounds!");
		}
	}
	
	private boolean coordsInBound(HexCoordinate c) {
		if (c.getX() < 0 || c.getY() < 0) {
			return false;
		}
		if (this.xMax == 0 && this.yMax == 0) {
			return true;
		} else if (Math.abs(c.getX()) <= this.xMax && Math.abs(c.getY()) <= this.yMax) {
			return true;
		}
		return false;
	}

	public LocationType getLocationType(HexCoordinate c) {
		return this.hexes.get(c);
	}

	public void setXMax(int xMax) {
		this.xMax = xMax;
	}

	public void setYMax(int yMax) {
		this.yMax = yMax;
	}

	public int getXMax() {
		return this.xMax;
	}

	public int getYMax() {
		return this.yMax;
	}

}
