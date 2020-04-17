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
import org.junit.jupiter.api.*;
import com.google.inject.*;
import escape.board.coordinate.*;
import escape.piece.PieceName;

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
		Injector injector = Guice.createInjector(new BoardModule());
		bb = injector.getInstance(BoardBuilder.class);
		bb.setBuildInitializer(new File("config/board/HexBoard.xml"));
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

}
