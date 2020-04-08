/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package escape.board;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.board.coordinate.SquareCoordinate;
import escape.piece.PieceName;

/**
 * Description
 * 
 * @version Apr 2, 2020
 */
class BoardTest {

    private BoardBuilder bb;
    private SquareBoard board;

    @BeforeEach
    public void setupTest() throws Exception {
        bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
        board = (SquareBoard) bb.makeBoard();
        assertNotNull(board);
    }

    @Test
    void buildSquareBoard1() throws Exception {
        // Now I will do some tests on this board and its contents.
        assertEquals(board.getMaxX(), 8);
        assertEquals(board.getMaxY(), 8);
    }

    @Test
    void testPutPieceAt() throws Exception {
        SquareCoordinate sc = SquareCoordinate.makeCoordinate(2, 2);
        assertNotNull(board.getPieceAt(sc));
        assertEquals(PieceName.HORSE, board.getPieceAt(sc).getName());
    }

    @Test
    void testPutLocationAt() throws Exception {
        SquareCoordinate sc = SquareCoordinate.makeCoordinate(3, 5);
        assertNotNull(board.getLocationType(sc));
        assertEquals(LocationType.BLOCK, board.getLocationType(sc));
    }
}
