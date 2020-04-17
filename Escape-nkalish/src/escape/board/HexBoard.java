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
		this.pieces.put(coord, p);
	}

	public void setLocationType(HexCoordinate c, LocationType lt) {
		hexes.put(c, lt);
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
