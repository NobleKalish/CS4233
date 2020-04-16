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
        this.pieces.put(coord, p);
    }

    public void setLocationType(SquareCoordinate c, LocationType lt) {
        squares.put(c, lt);
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
