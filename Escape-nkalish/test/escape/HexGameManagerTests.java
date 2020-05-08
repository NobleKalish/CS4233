package escape;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import escape.board.coordinate.HexCoordinate;
import escape.exception.EscapeException;
import escape.gameManager.EscapeGameManager;
import escape.piece.EscapePiece;
import escape.piece.PieceName;
import escape.piece.Player;

@SuppressWarnings({
		"rawtypes", "unchecked"
})
public class HexGameManagerTests {

	@Test
	public void testHexGameManager() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/HexEscapeGame.xml"));
		EscapeGameManager<HexCoordinate> gameManager = (EscapeGameManager<HexCoordinate>) gameBuilder
				.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(HexCoordinate.makeCoordinate(2, 2),
				gameManager.makeCoordinate(2, 2));
		assertEquals(EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE),
				gameManager.getPieceAt(gameManager.makeCoordinate(2, 2)));
	}

	@Test
	public void testOmniJump() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexOmni.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(2, 0)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(2, 0)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, 0)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(2, 0)));
		gameManager = gameBuilder.makeGameManager();// reset board

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, 2)));

		gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(2, -1)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(-1, 2),
				gameManager.makeCoordinate(-3, 4)));
	}

	@Test
	public void testOmniFly() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexOmni.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(1, 0)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(-3, 4)));

		gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(0, -1)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(-3, 8)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(-3, 5)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 3),
				gameManager.makeCoordinate(5, 3)));
	}

	@Test
	public void testOmniUnblock() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexOmni.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(1, -3)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(0, -3)));

		gameManager = gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -1),
				gameManager.makeCoordinate(3, -1)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -1),
				gameManager.makeCoordinate(0, -1)));

	}

	@Test
	public void HexOmniMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexOmni.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, -1)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(-1, 0),
				gameManager.makeCoordinate(-6, 5)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(-1, 0),
				gameManager.makeCoordinate(-5, 0)));
		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(-5, 0)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(6, 2)));
	}

	@Test
	public void testLinearJump() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexLinear.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(2, 0)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(2, 0)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, 0)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(2, 0)));
		gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, 2)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(-1, 2),
				gameManager.makeCoordinate(-3, 4)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(2, -1)));

		gameManager = gameBuilder.makeGameManager();// reset board
	}

	@Test
	public void testLinearFly() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexLinear.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(1, 0)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(-3, 4)));

		gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(0, -1)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(-6, 7)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(-5, 6)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 3),
				gameManager.makeCoordinate(5, 3)));
	}

	@Test
	public void testLinearUnblock() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexLinear.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(1, -3)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(0, -3)));

		gameManager = gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -1),
				gameManager.makeCoordinate(3, -1)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -1),
				gameManager.makeCoordinate(0, -1)));

	}

	@Test
	public void HexLinearMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexLinear.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, -1)));

		gameManager = gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(-1, 0),
				gameManager.makeCoordinate(-6, 5)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(-1, 0),
				gameManager.makeCoordinate(-5, 0)));
		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(-5, 0)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(6, 2)));

		gameManager = gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(-2, -1),
				gameManager.makeCoordinate(-1, 0)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(-2, -1),
				gameManager.makeCoordinate(-3, 1)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(-2, -1),
				gameManager.makeCoordinate(-3, -2)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(-2, -1),
				gameManager.makeCoordinate(-1, -3)));

	}
	
	@Test
	public void testBadHexConfig() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/Bad.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();
		assertThrows(EscapeException.class, () -> gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(2, 3)));
		assertThrows(EscapeException.class, () -> gameManager.move(gameManager.makeCoordinate(5, 5),
				gameManager.makeCoordinate(4, 4)));
		assertThrows(EscapeException.class, () -> gameManager.move(gameManager.makeCoordinate(1, 1),
				gameManager.makeCoordinate(4, 4)));
	}
}
