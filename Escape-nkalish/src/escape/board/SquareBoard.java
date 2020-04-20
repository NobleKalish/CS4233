/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package escape.board;

import java.util.*;
import escape.board.coordinate.SquareCoordinate;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * An example of how a Board might be implemented. This board has square coordinates and
 * finite bounds, represented by xMax and yMax. All methods required by the Board
 * interface have been implemented. Students would naturally add methods based upon their
 * design.
 * 
 * @version Apr 2, 2020
 */
public class SquareBoard implements Board<SquareCoordinate> {
	private Map<SquareCoordinate, LocationType> squares;
	private Map<SquareCoordinate, EscapePiece> pieces;

	private int xMax;
	private int yMax;

	public SquareBoard() {
		pieces = new HashMap<>();
		squares = new HashMap<>();
	}

	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(SquareCoordinate coord) {
		return this.pieces.get(coord);
	}

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece,
	 * escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, SquareCoordinate coord) {
		if (coordsInBound(coord)) {
			if (p == null) {
				this.pieces.remove(coord);
			} else {
				this.pieces.put(coord, p);
			}		} else {
			throw new EscapeException("Coordinates are not in bounds!");
		}
	}

	public void setLocationType(SquareCoordinate c, LocationType lt) {
		if (coordsInBound(c)) {
			this.squares.put(c, lt);
		} else {
			throw new EscapeException("Coordinates are not in bounds!");
		}
	}

	private boolean coordsInBound(SquareCoordinate c) {
		if (c.getX() <= 0 || c.getY() <= 0) {
			return false;
		}
		return (c.getX() <= this.xMax && c.getY() <= this.yMax);
	}

	public LocationType getLocationType(SquareCoordinate c) {
		return this.squares.get(c);
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
