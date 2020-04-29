package escape;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import escape.board.coordinate.SquareCoordinate;
import escape.piece.EscapePiece;
import escape.piece.PieceName;
import escape.piece.Player;

public class SquareGameManagerTests {

	@SuppressWarnings("unchecked")
	@Test
	public void testSquareGameManager() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/SquareEscapeGame.xml"));
		EscapeGameManager<SquareCoordinate> gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(SquareCoordinate.makeCoordinate(2, 2),
				gameManager.makeCoordinate(2, 2));
		assertEquals(EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE),
				gameManager.getPieceAt(gameManager.makeCoordinate(2, 2)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDiagonalMovement() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareDiagonal.xml"));
		EscapeGameManager<SquareCoordinate> gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();
		
		// General Diagonal movement
		assertTrue(gameManager.move(SquareCoordinate.makeCoordinate(8, 1),
				SquareCoordinate.makeCoordinate(7, 2)));
		assertTrue(gameManager.move(SquareCoordinate.makeCoordinate(7, 2),
				SquareCoordinate.makeCoordinate(8, 3)));
		assertTrue(gameManager.move(SquareCoordinate.makeCoordinate(8, 3),
				SquareCoordinate.makeCoordinate(7, 4)));
		assertTrue(gameManager.move(SquareCoordinate.makeCoordinate(7, 4),
				SquareCoordinate.makeCoordinate(5, 4)));
		assertFalse(gameManager.move(SquareCoordinate.makeCoordinate(5, 4),
				SquareCoordinate.makeCoordinate(6, 4)));
		assertFalse(gameManager.move(SquareCoordinate.makeCoordinate(5, 4),
				SquareCoordinate.makeCoordinate(9, 9)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// Blocked by pieces
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 1),
				gameManager.makeCoordinate(4, 4)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// Distance -> can't go past distance set (4)
		assertFalse(gameManager.move(gameManager.makeCoordinate(16, 1),
				gameManager.makeCoordinate(21, 6)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// can't move non-diagonal
		assertFalse(gameManager.move(gameManager.makeCoordinate(7, 2),
				gameManager.makeCoordinate(7, 1)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(7, 2),
				gameManager.makeCoordinate(8, 2)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(7, 2),
				gameManager.makeCoordinate(7, 3)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(7, 2),
				gameManager.makeCoordinate(6, 2)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// capture enemy piece ->
//		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
//				gameManager.makeCoordinate(3, 3)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();
		
		// Distance -> can go to max distance set (4)
				assertTrue(gameManager.move(gameManager.makeCoordinate(16, 1),
						gameManager.makeCoordinate(20, 5)));

		// Exit removes piece
		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(20, 5)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// can't pass over an exit
		assertFalse(gameManager.move(gameManager.makeCoordinate(30, 1),
				gameManager.makeCoordinate(34, 5)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDiagonalFlyMovement() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareDiagonal.xml"));
		EscapeGameManager<SquareCoordinate> gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();
		// Fly -> can't end on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(7, 9)));

		// Fly -> can't end on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(7, 9)));

		// Fly -> can jump many pieces
		assertTrue(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(10, 12)));

		// Fly -> can't go past distance set (5)
		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(14, 14)));

		// Fly -> can go to max distance set (5)
		assertTrue(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(13, 13)));

		// FLY -> can pass over an exit
		assertTrue(gameManager.move(gameManager.makeCoordinate(22, 1),
				gameManager.makeCoordinate(27, 6)));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDiagonalUnblockMovement() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareDiagonal.xml"));
		EscapeGameManager<SquareCoordinate> gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();
		// unblock false -> can't land on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(1, 3)));

		// unblock false -> can't pass over block
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 6),
				gameManager.makeCoordinate(4, 8)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();
		
		// unblock true -> can't land on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 3),
				gameManager.makeCoordinate(2, 4)));

		// unblock true -> can pass over block
		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 3),
				gameManager.makeCoordinate(1, 5)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDiagonalJump() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareDiagonal.xml"));
		EscapeGameManager<SquareCoordinate> gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();
		// jump over one piece to then capture enemy piece -> true
		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 1),
				gameManager.makeCoordinate(3, 3)));

		// jump over one piece at time, multi times -> true
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(7, 3)));

		// jump false -> can't jump
		assertFalse(gameManager.move(gameManager.makeCoordinate(9, 9),
				gameManager.makeCoordinate(11, 11)));

		// jump over one piece at time -> true
		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 4)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(4, 4)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 4)));
	}
}
