/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package gpv.chess;

import static gpv.chess.ChessPieceDescriptor.*;
import static org.junit.Assert.*;
import java.util.stream.Stream;
import static gpv.util.Coordinate.makeCoordinate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import gpv.util.*;

/**
 * Tests to ensure that pieces are created correctly and that all pieces get created.
 * 
 * @version Feb 23, 2020
 */
class ChessPieceTests {
    private static ChessPieceFactory factory = null;
    private Board board;

    @BeforeAll
    public static void setupBeforeTests() {
        factory = new ChessPieceFactory();
    }

    @BeforeEach
    public void setupTest() {
        board = new Board(8, 8);
    }

    @Test
    void makePiece() {
        ChessPiece pawn = factory.makePiece(WHITEPAWN);
        assertNotNull(pawn);
    }

    /**
     * This type of test loops through each value in the Enum and one by one feeds it as
     * an argument to the test method. It's worth looking at the different types of
     * parameterized tests in JUnit:
     * https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests
     * 
     * @param d
     *            the Enum value
     */
    @ParameterizedTest
    @EnumSource(ChessPieceDescriptor.class)
    void makeOneOfEach(ChessPieceDescriptor d) {
        ChessPiece p = factory.makePiece(d);
        assertNotNull(p);
        assertEquals(d.getColor(), p.getColor());
        assertEquals(d.getName(), p.getName());
    }

    @Test
    void placeOnePiece() {
        ChessPiece p = factory.makePiece(BLACKPAWN);
        board.putPieceAt(p, makeCoordinate(2, 2));
        assertEquals(p, board.getPieceAt(makeCoordinate(2, 2)));
    }

    @Test
    void placeTwoPieces() {
        ChessPiece bn = factory.makePiece(BLACKKNIGHT);
        ChessPiece wb = factory.makePiece(WHITEBISHOP);
        board.putPieceAt(bn, makeCoordinate(3, 5));
        board.putPieceAt(wb, makeCoordinate(2, 6));
        assertEquals(bn, board.getPieceAt(makeCoordinate(3, 5)));
        assertEquals(wb, board.getPieceAt(makeCoordinate(2, 6)));
    }

    @Test
    void checkForPieceHasMoved() {
        ChessPiece bq = factory.makePiece(BLACKQUEEN);
        assertFalse(bq.hasMoved());
        bq.setHasMoved();
        assertTrue(bq.hasMoved());
    }

    @ParameterizedTest
    @MethodSource("pieceMoveProvider")
    void allPieceMoveTest(boolean expected, Coordinate from, Coordinate to,
            ChessPieceDescriptor piece) {
        ChessPiece whitePiece = factory.makePiece(piece);
        board.putPieceAt(whitePiece, from);
        assertEquals(whitePiece.canMove(from, to, board), expected);
    }

    // Helper Function
    static Stream<Arguments> pieceMoveProvider() {
        return Stream.of(
                Arguments.of(false, makeCoordinate(1, 2), makeCoordinate(1, 1),
                        WHITEPAWN),
                Arguments.of(false, makeCoordinate(2, 1), makeCoordinate(0, 1),
                        WHITEPAWN),
                Arguments.of(false, makeCoordinate(2, 1), makeCoordinate(1, 0),
                        WHITEPAWN),
                Arguments.of(false, makeCoordinate(0, 1), makeCoordinate(1, 1),
                        WHITEPAWN),
                Arguments.of(false, makeCoordinate(2, 0), makeCoordinate(1, 1),
                        WHITEPAWN),
                Arguments.of(false, makeCoordinate(9, 1), makeCoordinate(1, 1),
                        WHITEPAWN),
                Arguments.of(false, makeCoordinate(2, 9), makeCoordinate(1, 1),
                        WHITEPAWN),
                Arguments.of(false, makeCoordinate(2, 1), makeCoordinate(9, 1),
                        WHITEPAWN),
                Arguments.of(false, makeCoordinate(2, 1), makeCoordinate(1, 9),
                        WHITEPAWN),
                Arguments.of(true, makeCoordinate(1, 2), makeCoordinate(1, 4),
                        WHITEPAWN),
                Arguments.of(true, makeCoordinate(1, 2), makeCoordinate(1, 3),
                        WHITEPAWN),
                Arguments.of(true, makeCoordinate(1, 1), makeCoordinate(2, 1),
                        WHITEROOK),
                Arguments.of(true, makeCoordinate(1, 1), makeCoordinate(8, 1),
                        WHITEROOK),
                Arguments.of(true, makeCoordinate(8, 1), makeCoordinate(1, 1),
                        WHITEROOK),
                Arguments.of(true, makeCoordinate(1, 1), makeCoordinate(1, 8),
                        WHITEROOK),
                Arguments.of(true, makeCoordinate(1, 8), makeCoordinate(1, 1),
                        WHITEROOK),
                Arguments.of(false, makeCoordinate(1, 1), makeCoordinate(2, 2),
                        WHITEROOK),
                Arguments.of(true, makeCoordinate(1, 2), makeCoordinate(3, 1),
                        WHITEKNIGHT),
                Arguments.of(true, makeCoordinate(1, 2), makeCoordinate(3, 3),
                        WHITEKNIGHT),
                Arguments.of(true, makeCoordinate(3, 1), makeCoordinate(4, 3),
                        WHITEKNIGHT),
                Arguments.of(true, makeCoordinate(3, 3), makeCoordinate(2, 5),
                        WHITEKNIGHT),
                Arguments.of(false, makeCoordinate(1, 2), makeCoordinate(1, 3),
                        WHITEKNIGHT),
                Arguments.of(true, makeCoordinate(1, 3), makeCoordinate(2, 4),
                        WHITEBISHOP),
                Arguments.of(true, makeCoordinate(1, 3), makeCoordinate(4, 6),
                        WHITEBISHOP),
                Arguments.of(true, makeCoordinate(1, 4), makeCoordinate(2, 4),
                        WHITEKING),
                Arguments.of(true, makeCoordinate(1, 4), makeCoordinate(1, 3),
                        WHITEKING),
                Arguments.of(true, makeCoordinate(1, 4), makeCoordinate(1, 5),
                        WHITEKING),
                Arguments.of(true, makeCoordinate(2, 4), makeCoordinate(1, 4),
                        WHITEKING),
                Arguments.of(true, makeCoordinate(1, 4), makeCoordinate(2, 4),
                        WHITEQUEEN),
                Arguments.of(true, makeCoordinate(1, 4), makeCoordinate(1, 3),
                        WHITEQUEEN),
                Arguments.of(true, makeCoordinate(1, 4), makeCoordinate(1, 5),
                        WHITEQUEEN),
                Arguments.of(true, makeCoordinate(2, 4), makeCoordinate(1, 4),
                        WHITEQUEEN),
                Arguments.of(true, makeCoordinate(1, 4), makeCoordinate(3, 4),
                        WHITEQUEEN),
                Arguments.of(true, makeCoordinate(1, 5), makeCoordinate(2, 6),
                        WHITEQUEEN));
    }

    // Special Pawn Moves
    @Test
    void pawnMoveTwoMovesAfterFirstMove() {
        ChessPiece whitePawn = factory.makePiece(WHITEPAWN);
        board.putPieceAt(whitePawn, makeCoordinate(1, 3));
        whitePawn.setHasMoved();
        assertFalse(whitePawn.canMove(makeCoordinate(1, 3), makeCoordinate(1, 5),
                board));
    }

    @Test
    void pawnMoveOneWithPieceInTheWay() {
        ChessPiece whitePawn = factory.makePiece(WHITEPAWN);
        ChessPiece whitePawn2 = factory.makePiece(WHITEPAWN);
        board.putPieceAt(whitePawn, makeCoordinate(1, 2));
        board.putPieceAt(whitePawn2, makeCoordinate(1, 3));
        assertFalse(whitePawn.canMove(makeCoordinate(1, 2), makeCoordinate(1, 3),
                board));
    }

    @Test
    void rookMoveWIthPieceInWay() {
        ChessPiece whitePawn = factory.makePiece(WHITEPAWN);
        ChessPiece whiteRook = factory.makePiece(WHITEROOK);
        board.putPieceAt(whitePawn, makeCoordinate(2, 1));
        board.putPieceAt(whiteRook, makeCoordinate(1, 1));
        assertFalse(whitePawn.canMove(makeCoordinate(1, 1), makeCoordinate(8, 1),
                board));
    }

    @Test
    void knightMoveWithPieceInWay() {
        ChessPiece whitePawn = factory.makePiece(WHITEPAWN);
        ChessPiece whiteKnight = factory.makePiece(WHITEKNIGHT);
        board.putPieceAt(whitePawn, makeCoordinate(3, 3));
        board.putPieceAt(whiteKnight, makeCoordinate(1, 2));
        assertFalse(whitePawn.canMove(makeCoordinate(1, 1), makeCoordinate(8, 1),
                board));
    }

    @ParameterizedTest
    @MethodSource("captureMoveProvider")
    void testCaptureMoves(boolean expected, Coordinate from, Coordinate to,
            ChessPieceDescriptor piece) {
        ChessPiece whitePiece = factory.makePiece(piece);
        ChessPiece blackPawn = factory.makePiece(BLACKPAWN);
        board.putPieceAt(whitePiece, from);
        board.putPieceAt(blackPawn, to);
        assertEquals(expected, whitePiece.canMove(from, to, board));
    }

    // Helper Class
    static Stream<Arguments> captureMoveProvider() {
        return Stream.of(
                Arguments.of(true, makeCoordinate(2, 1), makeCoordinate(3, 2),
                        WHITEPAWN),
                Arguments.of(false, makeCoordinate(2, 1), makeCoordinate(3, 1),
                        WHITEPAWN),
                Arguments.of(true, makeCoordinate(1, 2), makeCoordinate(3, 3),
                        WHITEKNIGHT),
                Arguments.of(true, makeCoordinate(1, 3), makeCoordinate(2, 2),
                        WHITEBISHOP),
                Arguments.of(true, makeCoordinate(1, 2), makeCoordinate(1, 7),
                        WHITEROOK),
                Arguments.of(true, makeCoordinate(1, 2), makeCoordinate(2, 3),
                        WHITEKING),
                Arguments.of(true, makeCoordinate(1, 2), makeCoordinate(3, 4),
                        WHITEQUEEN));
    }
}
