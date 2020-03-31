/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package gpv.chess;

import gpv.*;
import gpv.util.*;

/**
 * The chess piece is a piece with some special properties that are used for determining
 * whether a piece can move. It implements the Piece interface and adds properties and
 * methods that are necessary for the chess-specific behavior.
 * 
 * @version Feb 21, 2020
 */
public class ChessPiece implements Piece<ChessPieceDescriptor> {
    private final ChessPieceDescriptor descriptor;
    private boolean hasMoved; // true if this piece has moved

    /**
     * The only constructor for a ChessPiece instance. Requires a descriptor.
     * 
     * @param descriptor
     */
    public ChessPiece(ChessPieceDescriptor descriptor) {
        this.descriptor = descriptor;
        hasMoved = false;
    }

    /*
     * @see gpv.Piece#getDescriptor()
     */
    @Override
    public ChessPieceDescriptor getDescriptor() {
        return descriptor;
    }

    /**
     * @return the color
     */
    public PlayerColor getColor() {
        return descriptor.getColor();
    }

    /**
     * @return the name
     */
    public PieceName getName() {
        return descriptor.getName();
    }

    /*
     * @see gpv.Piece#canMove(gpv.util.Coordinate, gpv.util.Coordinate, gpv.util.Board)
     */
    @Override
    public boolean canMove(Coordinate from, Coordinate to, Board b) {
        return (validPieceMovement(from, to, b) && validCoordinates(from, to, b)
                && isPieceFromCoordinate(from, b)
                && canPieceLandAtToCoordinate(to, b));
    }

    private boolean validPieceMovement(Coordinate from, Coordinate to, Board b) {
        return PieceMovementRules.stream()
                .filter(rules -> rules.getDescriptors().contains(this.descriptor))
                .filter(rule -> !rule.validMove(from, to, b)).count() == 0;
    }

    private boolean canPieceLandAtToCoordinate(Coordinate to, Board b) {
        return (b.getPieceAt(to) == null || canPieceCapture(to, b));
    }

    private boolean canPieceCapture(Coordinate to, Board b) {
        ChessPieceDescriptor pieceDescriptor = (ChessPieceDescriptor) b
                .getPieceAt(to).getDescriptor();
        return (pieceDescriptor.getColor() != this.getDescriptor().getColor());
    }

    private boolean isPieceFromCoordinate(Coordinate from, Board b) {
        return (b.getPieceAt(from) == this);
    }

    /**
     * Determines if coordinates in from or to are valid
     * 
     * @param from
     *            starting coordinates
     * @param to
     *            ending coordinates
     * @param b
     *            board used in game
     * @return boolean if coordinates at from and to are valid on specified board
     */
    private boolean validCoordinates(Coordinate from, Coordinate to, Board b) {
        int numCols = b.nColumns;
        int numRows = b.nRows;
        return !((from.x > numRows || from.x <= 0)
                || (from.y > numCols || from.y <= 0) || (to.x > numRows || to.x <= 0)
                || (to.y > numCols || to.y <= 0));
    }

    /**
     * @return the hasMoved
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Once it moves, you can't change it.
     * 
     * @param hasMoved
     *            the hasMoved to set
     */
    public void setHasMoved() {
        hasMoved = true;
    }
}
