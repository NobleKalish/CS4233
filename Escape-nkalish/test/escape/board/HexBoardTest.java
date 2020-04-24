/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape.board;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import com.google.inject.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * Description
 * 
 * @version Apr 17, 2020
 */
public class HexBoardTest {
	private BoardBuilder bb;
	private HexBoard board;

	@BeforeEach
	public void setupTest() throws Exception {
    	BoardBuilder bb = new BoardBuilder(new File("config/board/HexBoard.xml"));
		board = (HexBoard) bb.makeBoard();
		assertNotNull(board);
	}

	@Test
	void buildOrthoSquareBoard() throws Exception {
		// Now I will do some tests on this board and its contents.
		assertEquals(board.getXMax(), 8);
		assertEquals(board.getYMax(), 8);
		assertTrue(board.getClass() == HexBoard.class);
	}

	@Test
	void testPutPieceAt() throws Exception {
		HexCoordinate hc = HexCoordinate.makeCoordinate(2, 2);
		assertNotNull(board.getPieceAt(hc));
		assertEquals(PieceName.HORSE, board.getPieceAt(hc).getName());
	}

	@Test
	void testPutLocationAt() throws Exception {
		HexCoordinate hc = HexCoordinate.makeCoordinate(3, 5);
		assertNotNull(board.getLocationType(hc));
		assertEquals(LocationType.BLOCK, board.getLocationType(hc));
	}
	
	@ParameterizedTest
    @MethodSource("coordinateTestProvider")
    public void testPutPieceOnBoard(HexCoordinate start, EscapePiece p) {
		assertThrows(EscapeException.class, () -> board.putPieceAt(p, start));
    }

    static Stream<Arguments> coordinateTestProvider() {
        return Stream.of(
                Arguments.of(HexCoordinate.makeCoordinate(-1, 1), EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE)),
                Arguments.of(HexCoordinate.makeCoordinate(10, 1), EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE)));
    }
    
    @ParameterizedTest
    @MethodSource("setLocationTypeTestProvider")
    public void testPutLocationOnBoard(HexCoordinate start, LocationType lt) {
		assertThrows(EscapeException.class, () -> board.setLocationType(start, lt));
    }

    static Stream<Arguments> setLocationTypeTestProvider() {
        return Stream.of(
                Arguments.of(HexCoordinate.makeCoordinate(-1, 1), LocationType.BLOCK),
                Arguments.of(HexCoordinate.makeCoordinate(10, 1), LocationType.BLOCK));
    }
	
	@Test
	void testBadFile() throws Exception {
		bb = new BoardBuilder(new File("config/board/BadHexBoard.xml"));
		try {
			board = (HexBoard) bb.makeBoard();
		} catch (EscapeException e) {
			assertNotNull(e);
			assertEquals(e.getMessage(), "Coordinates are not in bounds!");
		}
	}
	
	@Test
	void testRemovingPiece() {
		EscapePiece p = EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE);
		board.putPieceAt(p, HexCoordinate.makeCoordinate(5, 5));
		assertEquals(p, board.getPieceAt(HexCoordinate.makeCoordinate(5, 5)));
		board.putPieceAt(null, HexCoordinate.makeCoordinate(5, 5));
		assertEquals(null, board.getPieceAt(HexCoordinate.makeCoordinate(5, 5)));
	}

}
