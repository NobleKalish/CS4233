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
 * Describes the movement rules for each piece. Only pawns use different rules based on
 * color.
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
        return (kingExpectedMoves(from, to, board));
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

    /**
     * Creates a PieceMovementRule with a PieceRules rule and variable argument of
     * ChessPieceDescriptor
     * 
     * @param rule
     *            The PieceRules lmabda that supplies the method for validMove
     * @param descriptor
     *            A variable argument of ChessPieceDescriptors used to define what pieces
     *            each rule applies to
     */
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

    private static boolean noPiecesInDiagonalPath(Coordinate from, Coordinate distance,
            Board board) {
        if (distance.getX() > 0 && distance.getY() > 0) {
            for (int i = 1; i < distance.getX(); i++) {
                Coordinate newCoord = makeCoordinate((i + from.getRow()),
                        (i + from.getColumn()));
                if (board.getPieceAt(newCoord) != null) {
                    return false;
                }
            }
        } else if (distance.getX() < 0 && distance.getY() < 0) {
            for (int i = -1; i > distance.getX(); i--) {
                Coordinate newCoord = makeCoordinate((i + from.getRow()),
                        (i + from.getColumn()));
                if (board.getPieceAt(newCoord) != null) {
                    return false;
                }
            }
        } else if (distance.getX() > 0 && distance.getY() < 0) {
            for (int i = 1; i < distance.getX(); i++) {
                Coordinate newCoord = makeCoordinate((i + from.getRow()),
                        (i - from.getColumn()));
                if (board.getPieceAt(newCoord) != null) {
                    return false;
                }
            }
        } else {
            for (int i = -1; i > distance.getX(); i--) {
                Coordinate newCoord = makeCoordinate((i + from.getRow()),
                        (i - from.getColumn()));
                if (board.getPieceAt(newCoord) != null) {
                    return false;
                }
            }
        }
        return true;
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
        if (distance.getRow() == 0) {
            return isPieceInVerticalPath(distance, from, board);
        } else if (distance.getColumn() == 0){
            return(isPieceInHorizontalPath(distance, from, board));
        } else {
            return(noPiecesInDiagonalPath(from, distance, board));
        }
    }

    private static boolean isPieceInVerticalPath(Coordinate distance,
            Coordinate from, Board board) {
        if (distance.getY() > 0) {
            for (int y = 1; y < distance.getColumn(); y++) {
                Coordinate newCoord = makeCoordinate((from.getRow()),
                        (y + from.getColumn()));
                if (board.getPieceAt(newCoord) != null) {
                    return false;
                }
            }
        } else {
            for (int y = -1; y > distance.getColumn(); y--) {
                Coordinate newCoord = makeCoordinate((from.getRow()),
                        (y + from.getColumn()));
                if (board.getPieceAt(newCoord) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isPieceInHorizontalPath(Coordinate distance,
            Coordinate from, Board board) {
        if (distance.getX() > 0) {
            for (int x = 1; x < distance.getRow(); x++) {
                Coordinate newCoord = makeCoordinate((x + from.getRow()),
                        (from.getColumn()));
                if (board.getPieceAt(newCoord) != null) {
                    return false;
                }
            }
        } else {
            for (int x = -1; x > distance.getRow(); x--) {
                Coordinate newCoord = makeCoordinate((x + from.getRow()),
                        (from.getColumn()));
                if (board.getPieceAt(newCoord) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean kingExpectedMoves(Coordinate from, Coordinate to,
            Board b) {
        Coordinate distance = from.distance(to);
        return ((distance.getColumn() <= 1 && distance.getColumn() >= -1
                && (distance.getRow() >= -1 && distance.getColumn() <= 1))
                || isCastling(from, to, distance, b));
    }

    private static boolean isCastling(Coordinate from, Coordinate to,
            Coordinate distance, Board b) {
        return ((distance.getColumn() == 0
                && (distance.getRow() == 2 || distance.getRow() == -2))
                && isKingFirstMove(from, b) && isRookFirstMove(distance, from, b)
                && noOtherPiecesInWay(distance, from, b));
    }

    private static boolean noOtherPiecesInWay(Coordinate distance, Coordinate from, Board b) {
        if (distance.getRow() == 2) {
            for (int x = 6; x < b.nRows-1; x++) {
                if (b.getPieceAt(makeCoordinate(x, from.getColumn())) != null) {
                    return false;
                }
            }
        } else {
            for (int x = 2; x < 5; x++) {
                if (b.getPieceAt(makeCoordinate(x, from.getColumn())) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isRookFirstMove(Coordinate distance, Coordinate from, Board b) {
        if (from.getColumn() == 1) {
            if (distance.getRow() == 2) {
                return (b.getPieceAt(makeCoordinate(7, 1)) != null);
            } else {
                return (b.getPieceAt(makeCoordinate(1,1)) != null);
            }
        } else {
            if (distance.getRow() == 2) {
                return (b.getPieceAt(makeCoordinate(7, b.getnColumns())) != null);
            } else {
                return (b.getPieceAt(makeCoordinate(1, b.getnColumns())) != null);
            }
        }
    }

    private static boolean isKingFirstMove(Coordinate from, Board b) {
        return (from.getColumn() == 1 || from.getColumn() == b.getnColumns())
                && (from.getRow() == 5);
    }

    private static boolean isPawnFirstMove(Coordinate distance, Coordinate from,
            Board board) {
        return ((distance.getColumn() == 2 || distance.getColumn() == -2)
                && distance.getRow() == 0
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
                        || isPawnFirstMove(distance, from, board));
    }

    private static boolean blackPawnExpectedMoves(Coordinate from, Coordinate to,
            Board board) {
        Coordinate distance = from.distance(to);
        return (distance.getColumn() == -1 && distance.getRow() == 0)
                || (canBlackPawnAttack(distance, to, board)
                        || isPawnFirstMove(distance, from, board));
    }

    private static boolean canBlackPawnAttack(Coordinate distance, Coordinate to,
            Board board) {
        return (distance.getColumn() == -1 && distance.getRow() == -1
                && board.getPieceAt(to) != null);

    }

    /**
     * @return rules
     */
    public PieceRules getRules() {
        return this.rule;
    }

    /**
     * @return descriptors
     */
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
     * 
     * @return a stream of all values in enum
     */
    public static Stream<PieceMovementRules> stream() {
        return Stream.of(PieceMovementRules.values());
    }
}
