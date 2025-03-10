package escape;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.Test;
import escape.board.coordinate.HexCoordinate;
import escape.exception.EscapeException;
import escape.gameManager.HexGameManager;
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
		HexGameManager gameManager = (HexGameManager) gameBuilder
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
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(2, 0)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(2, 0)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, 0)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(2, 0)));
		gameManager = (HexGameManager) gameBuilder.makeGameManager();// reset board

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, 2)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(2, -1)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(-1, 2),
				gameManager.makeCoordinate(-3, 4)));
	}

	@Test
	public void testOmniFly() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexOmni.xml"));
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(1, 0)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(-3, 4)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(0, -1)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();

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
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(1, -3)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(0, -3)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -1),
				gameManager.makeCoordinate(3, -1)));

		gameManager.setIsPlayer1Turn();
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -1),
				gameManager.makeCoordinate(0, -1)));

	}

	@Test
	public void HexOmniMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexOmni.xml"));
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, -1)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(-1, 0),
				gameManager.makeCoordinate(-6, 5)));

		gameManager.setIsPlayer1Turn();
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
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(2, 0)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(2, 0)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, 0)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(2, 0)));
		gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, 2)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(-1, 2),
				gameManager.makeCoordinate(-3, 4)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(2, -1)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();// reset board
	}

	@Test
	public void testLinearFly() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexLinear.xml"));
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(1, 0)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(-3, 4)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(0, -1)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();

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
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(1, -3)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -3),
				gameManager.makeCoordinate(0, -3)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, -1),
				gameManager.makeCoordinate(3, -1)));

		gameManager.setIsPlayer1Turn();
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -1),
				gameManager.makeCoordinate(0, -1)));

	}

	@Test
	public void HexLinearMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/HexLinear.xml"));
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, -2),
				gameManager.makeCoordinate(2, -1)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();

		gameManager.setIsPlayer1Turn();
		assertFalse(gameManager.move(gameManager.makeCoordinate(-1, 0),
				gameManager.makeCoordinate(-6, 5)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(-1, 0),
				gameManager.makeCoordinate(-5, 0)));
		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(-5, 0)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(0, 1),
				gameManager.makeCoordinate(6, 2)));

		gameManager = (HexGameManager) gameBuilder.makeGameManager();
		
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
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();
		assertThrows(EscapeException.class, () -> gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(2, 3)));
		gameManager.setIsPlayer1Turn();
		assertThrows(EscapeException.class, () -> gameManager.move(gameManager.makeCoordinate(5, 5),
				gameManager.makeCoordinate(4, 4)));
		assertThrows(EscapeException.class, () -> gameManager.move(gameManager.makeCoordinate(1, 1),
				gameManager.makeCoordinate(4, 4)));
	}
	
	@Test
	public void squareNoRules() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/hexTests/doesNotHaveREMOVE.xml"));
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();
		gameManager.addGameObserver(new TestObserver());
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(5, -3), gameManager.makeCoordinate(0, 0)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, -3), gameManager.makeCoordinate(1, 2)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(0, 0), gameManager.makeCoordinate(1, 2)));
	} 
	
	@Test
	public void squareScoreLimit() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/hexTests/hasSCORE.xml"));
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();
		gameManager.addGameObserver(new TestObserver());
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(7, 7)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(10, 11), gameManager.makeCoordinate(7, 7)));
	}
	
	@Test
	public void squareHasRemove() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/hexTests/hasREMOVE.xml"));
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, -3), gameManager.makeCoordinate(0, 0)));
	}
	
	@Test
	public void squareHasPointConflict() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/hexTests/hasPOINT_CONFLICT.xml"));
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();
		gameManager.addGameObserver(new TestObserver());
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(12, 6)));
		assertEquals(gameManager.getPieceAt(gameManager.makeCoordinate(12, 6)), EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE));
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 8), gameManager.makeCoordinate(10, 10)));
		assertEquals(gameManager.getPieceAt(gameManager.makeCoordinate(10, 10)), EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE));
		
		gameManager = (HexGameManager) gameBuilder.makeGameManager();
		assertTrue(gameManager.move(gameManager.makeCoordinate(10, 10), gameManager.makeCoordinate(5, 8)));
		assertEquals(gameManager.getPieceAt(gameManager.makeCoordinate(5, 8)), EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE));
	}
	
	@Test
	public void squareHasTurnLimit() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/hexTests/hasTURN_LIMIT.xml"));
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();
		GameObserver testObserver = new TestObserver();
		gameManager.addGameObserver(testObserver);
		gameManager.removeObserver(testObserver);
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(2, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(10, 11), gameManager.makeCoordinate(10, 10)));
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(5, 5)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(10, 10), gameManager.makeCoordinate(10, 11)));
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(2, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(10, 11), gameManager.makeCoordinate(10, 10)));
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(5, 5)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(10, 10), gameManager.makeCoordinate(10, 11)));
	}
	
	@Test
	public void squareCoverageTests() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/hexTests/SampleEscapeGame.xml"));
		HexGameManager gameManager = (HexGameManager) gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(5, 3), gameManager.makeCoordinate(7, 7)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(10, 11), gameManager.makeCoordinate(10, 10)));
	}
}
