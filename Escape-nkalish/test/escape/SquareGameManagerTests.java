package escape;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.Test;
import escape.board.coordinate.SquareCoordinate;
import escape.gameManager.EscapeGameManager;
import escape.piece.EscapePiece;
import escape.piece.PieceName;
import escape.piece.Player;

@SuppressWarnings({
		"rawtypes", "unchecked"
})
public class SquareGameManagerTests {
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

	@Test
	public void testDiagonalMovement() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareDiagonal.xml"));
		EscapeGameManager<SquareCoordinate> gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

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
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 1),
				gameManager.makeCoordinate(4, 4)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(16, 1),
				gameManager.makeCoordinate(21, 6)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

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

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(3, 3)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(16, 1),
				gameManager.makeCoordinate(20, 5)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(20, 5)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(30, 1),
				gameManager.makeCoordinate(34, 5)));
	}

	@Test
	public void testDiagonalFlyMovement() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareDiagonal.xml"));
		EscapeGameManager<SquareCoordinate> gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(7, 9)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(7, 9)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(10, 12)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(14, 14)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(13, 13)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(22, 1),
				gameManager.makeCoordinate(27, 6)));

	}

	@Test
	public void testDiagonalUnblockMovement() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareDiagonal.xml"));
		EscapeGameManager<SquareCoordinate> gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(1, 3)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 6),
				gameManager.makeCoordinate(4, 8)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 3),
				gameManager.makeCoordinate(2, 4)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 3),
				gameManager.makeCoordinate(1, 5)));
	}

	@Test
	public void testDiagonalJump() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareDiagonal.xml"));
		EscapeGameManager<SquareCoordinate> gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 1),
				gameManager.makeCoordinate(3, 3)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(7, 3)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(9, 9),
				gameManager.makeCoordinate(11, 11)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 4)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(4, 4)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 4)));
	}

	@Test
	public void SquareOmniTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareOmni.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(3, 2)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(6, 1),
				gameManager.makeCoordinate(8, 8)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(6, 1),
				gameManager.makeCoordinate(8, 5)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(8, 5)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(11, 1),
				gameManager.makeCoordinate(11, 5)));

	}

	@Test
	public void testOmniFly() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareOmni.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(1, 6)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(4, 7)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(8, 2)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(6, 2)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(9, 1),
				gameManager.makeCoordinate(9, 6)));
	}

	@Test
	public void testOmniJump() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareOmni.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(4, 2)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(4, 2)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(6, 2)));

		gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(3, 2)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 7),
				gameManager.makeCoordinate(4, 7)));
	}

	@Test
	public void testOmniUnblock() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareOmni.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 3)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 4)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 3)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 5)));
	}

	@Test
	public void testOrthoJump() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareOrtho.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(4, 2)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(4, 2)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(6, 2)));

		gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(3, 2)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 7),
				gameManager.makeCoordinate(4, 7)));
	}

	@Test
	public void testOrthoFly() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareOrtho.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(1, 6)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(4, 7)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(8, 7)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(3, 5)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(9, 1),
				gameManager.makeCoordinate(9, 6)));
	}

	@Test
	public void testOrthoUnblock() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareOrtho.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 3)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 4)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 3)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 5)));
	}

	@Test
	public void SquareOrthoTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareOrtho.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(3, 2)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(6, 3),
				gameManager.makeCoordinate(8, 8)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(6, 3),
				gameManager.makeCoordinate(8, 5)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(8, 5)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(4, 4),
				gameManager.makeCoordinate(8, 8)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(10, 1),
				gameManager.makeCoordinate(10, 5)));

	}

	@Test
	public void testLinearJump() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareLinear.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(4, 2)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(4, 2)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(6, 2)));

		gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(3, 2)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 7),
				gameManager.makeCoordinate(4, 7)));
	}

	@Test
	public void testLinearUnblock() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareLinear.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 3)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 4)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 3)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 5)));

	}

	@Test
	public void testLinearFly() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareLinear.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(1, 6)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(4, 7)));

		gameManager = gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(8, 7)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(6, 2)));
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(9, 1),
				gameManager.makeCoordinate(9, 6)));
	}

	@Test
	public void SquareLinearTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/SquareLinear.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();


		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(3, 2)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 1),
				gameManager.makeCoordinate(8, 8)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(8, 1),
				gameManager.makeCoordinate(8, 5)));
		
		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(8, 5)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 1),
				gameManager.makeCoordinate(6, 4)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 6),
				gameManager.makeCoordinate(4, 5)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(4, 4),
				gameManager.makeCoordinate(8, 8)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(10, 1),
				gameManager.makeCoordinate(10, 5)));
	}
}
