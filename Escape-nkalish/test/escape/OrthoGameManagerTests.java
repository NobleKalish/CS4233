package escape;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.Test;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.gameManager.EscapeGameManager;
import escape.gameManager.OrthoGameManager;
import escape.gameManager.SquareGameManager;
import escape.piece.EscapePiece;
import escape.piece.PieceName;
import escape.piece.Player;

@SuppressWarnings({
		"rawtypes", "unchecked"
})
public class OrthoGameManagerTests {

	@Test
	public void testOrthoSquareGameManager() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/OrthoEscapeGame.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder
				.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(OrthoSquareCoordinate.makeCoordinate(2, 2),
				gameManager.makeCoordinate(2, 2));
		assertEquals(EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE),
				gameManager.getPieceAt(gameManager.makeCoordinate(2, 2)));
	}

	@Test
	public void testOrthoJump() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareOrtho.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(4, 2)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(4, 2)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();// reset board

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(6, 2)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();// reset board

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 7),
				gameManager.makeCoordinate(4, 7)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(3, 2)));
	}

	@Test
	public void testOrthoFly() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareOrtho.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(1, 6)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(4, 7)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		
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
				new File("config/edgeTests/OrthoSquareOrtho.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 3)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 4)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 3)));

		gameManager.setIsPlayer1Turn();
		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 5)));
	}

	@Test
	public void OrthoSquareOrthoMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareOrtho.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(3, 2)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(6, 3),
				gameManager.makeCoordinate(8, 8)));

		gameManager.setIsPlayer1Turn();
		assertTrue(gameManager.move(gameManager.makeCoordinate(6, 3),
				gameManager.makeCoordinate(8, 5)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(8, 5)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(4, 4),
				gameManager.makeCoordinate(8, 8)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(10, 1),
				gameManager.makeCoordinate(10, 5)));
	}

	@Test
	public void testOmniJump() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareOmni.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(4, 2)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(4, 2)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(6, 2)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(3, 2)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 7),
				gameManager.makeCoordinate(4, 7)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 7),
				gameManager.makeCoordinate(4, 7)));
	}

	@Test
	public void testOmniFly() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareOmni.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(1, 6)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(4, 7)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(8, 7)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(3, 5)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(9, 1),
				gameManager.makeCoordinate(9, 6)));
	}

	@Test
	public void testOmniUblock() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareOmni.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 3)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 4)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 3)));

		gameManager.setIsPlayer1Turn();
		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 5)));
	}

	@Test
	public void OrthoSquareOmniMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareOmni.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(3, 2)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(6, 3),
				gameManager.makeCoordinate(8, 8)));

		gameManager.setIsPlayer1Turn();
		assertTrue(gameManager.move(gameManager.makeCoordinate(6, 3),
				gameManager.makeCoordinate(8, 5)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(8, 5)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(4, 4),
				gameManager.makeCoordinate(8, 8)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(10, 1),
				gameManager.makeCoordinate(10, 5)));
	}

	@Test
	public void testLinearJump() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareLinear.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(4, 2)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(4, 2)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(6, 2)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(3, 2)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 7),
				gameManager.makeCoordinate(4, 7)));
	}

	@Test
	public void testLinearFly() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareLinear.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(1, 6)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(4, 7)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(8, 7)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7),
				gameManager.makeCoordinate(6, 7)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(9, 1),
				gameManager.makeCoordinate(9, 6)));

	}

	@Test
	public void testLinearUnblock() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareLinear.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 3)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2),
				gameManager.makeCoordinate(1, 4)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		
		gameManager.setIsPlayer1Turn();
		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 3)));

		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 2),
				gameManager.makeCoordinate(3, 5)));
	}

	@Test
	public void OrthoSquareLinearMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/edgeTests/OrthoSquareLinear.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(3, 2)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();

		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 1),
				gameManager.makeCoordinate(8, 8)));

		gameManager.setIsPlayer1Turn();
		assertTrue(gameManager.move(gameManager.makeCoordinate(8, 1),
				gameManager.makeCoordinate(8, 5)));

		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(8, 5)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 1),
				gameManager.makeCoordinate(6, 4)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 6),
				gameManager.makeCoordinate(4, 5)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 1),
				gameManager.makeCoordinate(4, 5)));

		assertFalse(gameManager.move(gameManager.makeCoordinate(4, 4),
				gameManager.makeCoordinate(8, 8)));

		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		assertFalse(gameManager.move(gameManager.makeCoordinate(10, 1),
				gameManager.makeCoordinate(10, 5)));
	}
	
	@Test
	public void squareNoRules() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/orthoTests/doesNotHaveREMOVE.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		gameManager.addGameObserver(new TestObserver());
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(1, 1)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(1, 2)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 1), gameManager.makeCoordinate(1, 2)));
	} 
	
	@Test
	public void squareScoreLimit() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/orthoTests/hasSCORE.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		gameManager.addGameObserver(new TestObserver());
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(1, 1)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(10, 11), gameManager.makeCoordinate(1, 1)));
	}
	
	@Test
	public void squareHasRemove() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/orthoTests/hasREMOVE.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 3), gameManager.makeCoordinate(2, 2)));
	}
	
	@Test
	public void squareHasPointConflict() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/orthoTests/hasPOINT_CONFLICT.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		gameManager.addGameObserver(new TestObserver());
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(12, 6)));
		assertEquals(gameManager.getPieceAt(gameManager.makeCoordinate(12, 6)), EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE));
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 8), gameManager.makeCoordinate(10, 10)));
		assertEquals(gameManager.getPieceAt(gameManager.makeCoordinate(10, 10)), EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE));
		
		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		assertTrue(gameManager.move(gameManager.makeCoordinate(10, 10), gameManager.makeCoordinate(5, 8)));
		assertEquals(gameManager.getPieceAt(gameManager.makeCoordinate(5, 8)), EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE));
	}
	
	@Test
	public void squareHasTurnLimit() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/orthoTests/hasTURN_LIMIT.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
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
				new File("config/GamaXMLs/orthoTests/SampleEscapeGame.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(5, 3), gameManager.makeCoordinate(7, 7)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(10, 11), gameManager.makeCoordinate(3, 3)));
	}
	
	@Test
	public void squareNoRules2() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/orthoTests/doesNotHaveREMOVE2.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		gameManager.addGameObserver(new TestObserver());
		
		assertFalse(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(1, 1)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(5, 6)));
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 1), gameManager.makeCoordinate(6, 6)));
	}
	
	@Test
	public void squareHasPointConflict2() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/orthoTests/hasPOINT_CONFLICT2.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		gameManager.addGameObserver(new TestObserver());
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 5), gameManager.makeCoordinate(12, 6)));
		assertEquals(gameManager.getPieceAt(gameManager.makeCoordinate(12, 6)), EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE));
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 8), gameManager.makeCoordinate(10, 10)));
		assertEquals(gameManager.getPieceAt(gameManager.makeCoordinate(10, 10)), EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE));
		
		gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		assertTrue(gameManager.move(gameManager.makeCoordinate(10, 10), gameManager.makeCoordinate(5, 8)));
		assertEquals(gameManager.getPieceAt(gameManager.makeCoordinate(5, 8)), EscapePiece.makePiece(Player.PLAYER2, PieceName.HORSE));
	}
	
	@Test
	public void squareHasRemove2() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/GamaXMLs/orthoTests/hasREMOVE2.xml"));
		OrthoGameManager gameManager = (OrthoGameManager) gameBuilder.makeGameManager();
		
		assertTrue(gameManager.move(gameManager.makeCoordinate(5, 3), gameManager.makeCoordinate(2, 2)));
	}

}
