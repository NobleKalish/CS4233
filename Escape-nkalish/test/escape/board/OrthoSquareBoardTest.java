package escape.board;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

/**
 * Description
 * @version Apr 8, 2020
 */
public class OrthoSquareBoardTest {
    private BoardBuilder bb;
    private OrthoSquareBoard board;

    @BeforeEach
    public void setupTest() throws Exception {
    	BoardBuilder bb = new BoardBuilder(new File("config/board/OrthoBoard.xml"));
        board = (OrthoSquareBoard) bb.makeBoard();
        assertNotNull(board);
    }

    @Test
    void buildOrthoSquareBoard() throws Exception {
        // Now I will do some tests on this board and its contents.
        assertEquals(board.getXMax(), 8);
        assertEquals(board.getYMax(), 8);
        assertTrue(board.getClass() == OrthoSquareBoard.class);
    }

    @Test
    void testPutPieceAt() throws Exception {
        OrthoSquareCoordinate oc = OrthoSquareCoordinate.makeCoordinate(2, 2);
        assertNotNull(board.getPieceAt(oc));
        assertEquals(PieceName.HORSE, board.getPieceAt(oc).getName());
    }

    @Test
    void testPutLocationAt() throws Exception {
        OrthoSquareCoordinate oc = OrthoSquareCoordinate.makeCoordinate(3, 5);
        assertNotNull(board.getLocationType(oc));
        assertEquals(LocationType.BLOCK, board.getLocationType(oc));
    }
    
    @Test
	void testBadFile() throws Exception {
    	bb = new BoardBuilder(new File("config/board/BadOrthoBoard.xml"));
		try {
			board = (OrthoSquareBoard) bb.makeBoard();
		} catch (EscapeException e) {
			assertNotNull(e);
			assertEquals(e.getMessage(), "Coordinates are not in bounds!");
		}
	}
    
    @ParameterizedTest
    @MethodSource("coordinateTestProvider")
    public void testPutPieceOnBoard(OrthoSquareCoordinate start, EscapePiece p) {
		assertThrows(EscapeException.class, () -> board.putPieceAt(p, start));
    }

    static Stream<Arguments> coordinateTestProvider() {
        return Stream.of(
                Arguments.of(OrthoSquareCoordinate.makeCoordinate(-1, 1), EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE)),
                Arguments.of(OrthoSquareCoordinate.makeCoordinate(10, 1), EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE)));
    }
    
    @ParameterizedTest
    @MethodSource("setLocationTypeTestProvider")
    public void testPutLocationOnBoard(OrthoSquareCoordinate start, LocationType lt) {
		assertThrows(EscapeException.class, () -> board.setLocationType(start, lt));
    }

    static Stream<Arguments> setLocationTypeTestProvider() {
        return Stream.of(
                Arguments.of(OrthoSquareCoordinate.makeCoordinate(-1, 1), LocationType.BLOCK),
                Arguments.of(OrthoSquareCoordinate.makeCoordinate(10, 1), LocationType.BLOCK));
    }
    
    @Test
	void testRemovingPiece() {
		EscapePiece p = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		board.putPieceAt(p, OrthoSquareCoordinate.makeCoordinate(5, 5));
		assertEquals(p, board.getPieceAt(OrthoSquareCoordinate.makeCoordinate(5, 5)));
		board.putPieceAt(null, OrthoSquareCoordinate.makeCoordinate(5, 5));
		assertEquals(null, board.getPieceAt(OrthoSquareCoordinate.makeCoordinate(5, 5)));
	}

}
