/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package gpv.chess;

import java.util.*;
import java.util.stream.Stream;
import gpv.util.*;
import static gpv.util.Coordinate.makeCoordinate;

/**
 * Description
 * 
 * @version Mar 25, 2020
 */
public enum PieceMovementRules implements PieceRules {
    WHITEPAWNRULES((Coordinate from, Coordinate to, Board board) -> {
        return (whitePawnExpectedMoves(from, to, board));
    }, ChessPieceDescriptor.WHITEPAWN),

    BLACKPAWNRULES((Coordinate from, Coordinate to, Board board) -> {
        return (blackPawnExpectedMoves(from, to, board));
    }, ChessPieceDescriptor.BLACKPAWN),

    KINGRULES((Coordinate from, Coordinate to, Board board) -> {
        return (kingExpectedMoves(from, to));
    }, ChessPieceDescriptor.WHITEKING, ChessPieceDescriptor.BLACKKING),

    QUEENRULES((Coordinate from, Coordinate to, Board board) -> {
        return (queenExpectedMoves(from, to, board));
    }, ChessPieceDescriptor.WHITEQUEEN, ChessPieceDescriptor.BLACKQUEEN),

    BISHOPRULES((Coordinate from, Coordinate to, Board board) -> {
        return (bishopExpectedMoves(from, to, board));
    }, ChessPieceDescriptor.WHITEBISHOP, ChessPieceDescriptor.BLACKBISHOP),

    KNIGHTRULES((Coordinate from, Coordinate to, Board board) -> {
        return (knightExpectedMoves(from, to));
    }, ChessPieceDescriptor.WHITEKNIGHT, ChessPieceDescriptor.BLACKKNIGHT),

    ROOKRULES((Coordinate from, Coordinate to, Board board) -> {
        return (rookExpectedMoves(from, to, board));
    }, ChessPieceDescriptor.WHITEROOK, ChessPieceDescriptor.BLACKROOK);

    private List<ChessPieceDescriptor> descriptors;
    private PieceRules rule;

    PieceMovementRules(PieceRules rule, ChessPieceDescriptor... descriptor) {
        this.descriptors = Arrays.asList(descriptor);
        this.rule = rule;
    }

    private static boolean rookExpectedMoves(Coordinate from, Coordinate to,
            Board board) {
        Coordinate distance = from.distance(to);
        return (distance.getColumn() == 0 || distance.getRow() == 0)
                && noPiecesInPath(from, distance, board);
    }

    private static boolean knightExpectedMoves(Coordinate from, Coordinate to) {
        Coordinate distance = from.distance(to);
        return (((distance.getColumn() == 2 || distance.getColumn() == -2)
                && (distance.getRow() == 1 || distance.getRow() == -1))
                || ((distance.getColumn() == 1 || distance.getColumn() == -1)
                        && (distance.getRow() == 2 || distance.getRow() == -2)));
    }

    private static boolean bishopExpectedMoves(Coordinate from, Coordinate to,
            Board board) {
        Coordinate distance = from.distance(to);
        return ((distance.getColumn() == distance.getRow()
                || (distance.getColumn() + distance.getRow() == 0))
                && noPiecesInPath(from, distance, board));
    }

    private static boolean queenExpectedMoves(Coordinate from, Coordinate to,
            Board board) {
        Coordinate distance = from.distance(to);
        return ((distance.getColumn() == distance.getRow()
                || distance.getColumn() == 0 || distance.getRow() == 0)
                && noPiecesInPath(from, distance, board));
    }

    private static boolean noPiecesInPath(Coordinate from, Coordinate distance,
            Board board) {
        return (isPieceInHorizontalFirstPath(distance, from, board) || isPieceInVerticalFirstPath(distance,from, board));
    }

    private static boolean isPieceInVerticalFirstPath(Coordinate distance, Coordinate from,
            Board board) {
        for (int y = 1; y < distance.getColumn(); y++) {
            for (int x = 1; x < distance.getRow(); x++) {
                Coordinate newCoord = makeCoordinate((x + from.getRow()),
                        (y + from.getColumn()));
                if (board.getPieceAt(newCoord) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isPieceInHorizontalFirstPath(Coordinate distance, Coordinate from,
            Board board) {
        for (int x = 1; x < distance.getRow(); x++) {
            for (int y = 1; y < distance.getColumn(); y++) {
                Coordinate newCoord = makeCoordinate((x + from.getRow()), (y + from.getColumn()));
                if (board.getPieceAt(newCoord) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean kingExpectedMoves(Coordinate from, Coordinate to) {
        Coordinate distance = from.distance(to);
        return !(distance.getColumn() > 1 && distance.getRow() > 1);
    }

    private static boolean isFirstMove(Coordinate distance, Coordinate from,
            Board board) {
        return (distance.getColumn() == 2 && distance.getRow() == 0
                && (from.getColumn() == 2 || from.getColumn() == board.nRows - 1));
    }

    private static boolean canWhitePawnAttack(Coordinate distance, Coordinate to,
            Board board) {
        return (distance.getColumn() == 1 && distance.getRow() == 1
                && board.getPieceAt(to) != null);
    }

    private static boolean whitePawnExpectedMoves(Coordinate from, Coordinate to,
            Board board) {
        Coordinate distance = from.distance(to);
        return (distance.getColumn() == 1 && distance.getRow() == 0)
                || (canWhitePawnAttack(distance, to, board)
                        || isFirstMove(distance, from, board));
    }

    private static boolean blackPawnExpectedMoves(Coordinate from, Coordinate to,
            Board board) {
        Coordinate distance = from.distance(to);
        return (distance.getColumn() == -1 && distance.getRow() == 0)
                || (canBlackPawnAttack(distance, to, board));
    }

    private static boolean canBlackPawnAttack(Coordinate distance, Coordinate to,
            Board board) {
        return (distance.getColumn() == -1 && distance.getRow() == -1
                && board.getPieceAt(to) != null);

    }

    public PieceRules getRules() {
        return this.rule;
    }

    public List<ChessPieceDescriptor> getDescriptors() {
        return this.descriptors;
    }

    /*
     * @see gpv.chess.PieceRules#validMove(gpv.util.Coordinate, gpv.util.Coordinate,
     * gpv.util.Board)
     */
    @Override
    public boolean validMove(Coordinate from, Coordinate to, Board board) {
        return this.rule.validMove(from, to, board);
    }

    /**
     * Get all values in the enum
     * @return a stream of all values in enum
     */
    public static Stream<PieceMovementRules> stream() {
        return Stream.of(PieceMovementRules.values());
    }
}
