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
import escape.util.BoardInitializer;

/**
 * Description
 * 
 * @version Apr 8, 2020
 */
public class OrthoSquareBoard implements Board<OrthoSquareCoordinate> {
    
    Map<OrthoSquareCoordinate, LocationType> squares;
    Map<OrthoSquareCoordinate, EscapePiece> pieces;

    private final int xMax;
    private final int yMax;
    
    public OrthoSquareBoard(int xMax, int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
        this.squares = new HashMap<>();
        this.pieces = new HashMap<>();
    }

    /*
     * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
     */
    @Override
    public EscapePiece getPieceAt(OrthoSquareCoordinate coord) {
        return this.pieces.get(coord);
    }

    /*
     * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
     */
    @Override
    public void putPieceAt(EscapePiece p, OrthoSquareCoordinate coord) {
        this.pieces.put(coord, p);
    }

    /*
     * @see escape.board.Board#getLocationType(escape.board.coordinate.Coordinate)
     */
    @Override
    public LocationType getLocationType(OrthoSquareCoordinate coord) {
        return this.squares.get(coord);
    }

    /*
     * @see escape.board.Board#setLocationType(escape.board.coordinate.Coordinate, escape.board.LocationType)
     */
    @Override
    public void setLocationType(OrthoSquareCoordinate coord, LocationType lt) {
        this.squares.put(coord, lt);
    }

    public int getMaxX() {
        return this.xMax;
    }

    public int getMaxY() {
        return this.yMax;
    }

	/*
	 * @see escape.board.Board#initalizeBoard(escape.util.BoardInitializer)
	 */
	@Override
	public void initalizeBoard(BoardInitializer bi) {
		// TODO Auto-generated method stub
		
	}
}
