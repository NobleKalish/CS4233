package escape;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(3, 3)));
		
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
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// Fly -> can't end on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(7, 9)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// Fly -> can jump many pieces
		assertTrue(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(10, 12)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// Fly -> can't go past distance set (5)
		assertFalse(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(14, 14)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// Fly -> can go to max distance set (5)
		assertTrue(gameManager.move(gameManager.makeCoordinate(8, 8),
				gameManager.makeCoordinate(13, 13)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

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
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// unblock false -> can't pass over block
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 6),
				gameManager.makeCoordinate(4, 8)));

		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();
		
		// unblock true -> can't land on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 3),
				gameManager.makeCoordinate(2, 4)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

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
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// jump over one piece at time, multi times -> true
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(7, 3)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// jump false -> can't jump
		assertFalse(gameManager.move(gameManager.makeCoordinate(9, 9),
				gameManager.makeCoordinate(11, 11)));
		
		gameManager = (EscapeGameManager<SquareCoordinate>) gameBuilder
				.makeGameManager();

		// jump over one piece at time -> true
		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 4)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2),
				gameManager.makeCoordinate(4, 4)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 4)));
	}
	
	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	@Test
	public void SquareOmniMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/edgeTests/SquareOmni.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();
		// Exercise the game now: make moves, check the board, etc.
		
		//jump over two pieces -> false
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(4, 2)));
		
		//jump over one piece at time -> true
		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(4, 2)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		gameManager = gameBuilder.makeGameManager();//reset board
		
		//jump over one piece at time, multi times -> true
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(6, 2)));
		
		gameManager = gameBuilder.makeGameManager();//reset board
		//capture enemy piece ->
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(3, 2)));
		
		gameManager = gameBuilder.makeGameManager();//reset board
		//unblock false -> can't land on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(1, 3)));
		
		//unblock false -> can't pass over block
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(1, 4)));
		
		//jump over one piece to then capture enemy piece -> true
	 	assertTrue(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(3, 2)));
		
		gameManager = gameBuilder.makeGameManager();//reset board
		//unblock true -> can't end on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 2), gameManager.makeCoordinate(3, 3)));
		
		//unblock true -> can pass over  block
		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 2), gameManager.makeCoordinate(3, 5)));
		
		//jump false -> can't jump
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 7), gameManager.makeCoordinate(4, 7)));
		
		//Fly -> can't end on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(1, 6)));
		
		//Fly -> can jump many pieces 
	    assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(4, 7)));
		
	    gameManager = gameBuilder.makeGameManager();//reset board
	    //Fly -> can't go past distance set (5)
	    assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(8, 2)));
	    
	    //Fly -> can go to max distance set (5)
	    assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(6, 2)));
	    
	    //Distance -> can't go past distance set (4)
	    assertFalse(gameManager.move(gameManager.makeCoordinate(6, 1), gameManager.makeCoordinate(8, 8)));
	    
	    //Distance -> can go to max distance set (4)
	    assertTrue(gameManager.move(gameManager.makeCoordinate(6, 1), gameManager.makeCoordinate(8, 5)));
	    //Exit removes piece
	    assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(8, 5)));
	    
	    //can't pass over an exit
	    assertFalse(gameManager.move(gameManager.makeCoordinate(11, 1), gameManager.makeCoordinate(11, 5)));
	    
	    //FLY -> can pass over an exit
	    assertTrue(gameManager.move(gameManager.makeCoordinate(9, 1), gameManager.makeCoordinate(9, 6)));
	    
	}
	
	@SuppressWarnings({
		"rawtypes", "unchecked"
	})
	@Test
	public void SquareOrthoMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/edgeTests/SquareOrtho.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();
		// Exercise the game now: make moves, check the board, etc.
		
		//jump over two pieces -> false
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(4, 2)));
		
		//jump over one piece at time -> true
		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(4, 2)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		gameManager = gameBuilder.makeGameManager();//reset board
		
		//jump over one piece at time, multi times -> true
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(6, 2)));
		
		gameManager = gameBuilder.makeGameManager();//reset board
		//capture enemy piece ->
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(3, 2)));
		
		gameManager = gameBuilder.makeGameManager();//reset board
		//unblock false -> can't land on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(1, 3)));
		
		//unblock false -> can't pass over block
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(1, 4)));
		
		//jump over one piece to then capture enemy piece -> true
	 	assertTrue(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(3, 2)));
		
		gameManager = gameBuilder.makeGameManager();//reset board
		//unblock true -> can't land on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 2), gameManager.makeCoordinate(3, 3)));
		
		//unblock true -> can pass over  block
		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 2), gameManager.makeCoordinate(3, 5)));
		
		//jump false -> can't jump
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 7), gameManager.makeCoordinate(4, 7)));
		
		//Fly -> can't end on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(1, 6)));
		
		//Fly -> can jump many pieces 
	    assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(4, 7)));
		
	    gameManager = gameBuilder.makeGameManager();//reset board
	    //Fly -> can't go past distance set (5)
	    assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(8, 7)));
	    
	    //Fly -> can go to max distance set (5)
	    assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(3, 5)));
	   
	    //Distance -> can't go past distance set (4)
	    assertFalse(gameManager.move(gameManager.makeCoordinate(6, 3), gameManager.makeCoordinate(8, 8)));
	    
	    //Distance -> can go to max distance set (4)
	    assertTrue(gameManager.move(gameManager.makeCoordinate(6, 3), gameManager.makeCoordinate(8, 5)));
	    
	    //Exit removes piece
	    assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(8, 5)));
	    
	    //can't move diagonal
	    assertFalse(gameManager.move(gameManager.makeCoordinate(4, 4), gameManager.makeCoordinate(8, 8)));
	    
	    //can't pass over an exit
	    assertFalse(gameManager.move(gameManager.makeCoordinate(10, 1), gameManager.makeCoordinate(10, 5)));
	    
	    //FLY -> can pass over an exit
	    assertTrue(gameManager.move(gameManager.makeCoordinate(9, 1), gameManager.makeCoordinate(9, 6)));
	}
	
	@SuppressWarnings({
		"rawtypes", "unchecked"
	})
	@Test
	public void SquareLinearMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/edgeTests/SquareLinear.xml"));
		EscapeGameManager gameManager = gameBuilder.makeGameManager();
		// Exercise the game now: make moves, check the board, etc.
		
		//jump over two pieces -> false
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(4, 2)));
		
		//jump over one piece at time -> true
		assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(4, 2)));
		assertNotNull(gameManager.getPieceAt(gameManager.makeCoordinate(4, 2)));
		gameManager = gameBuilder.makeGameManager();//reset board
		
		//jump over one piece at time, multi times -> true
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(6, 2)));
		
		gameManager = gameBuilder.makeGameManager();//reset board
		//capture enemy piece ->
		assertTrue(gameManager.move(gameManager.makeCoordinate(2, 2), gameManager.makeCoordinate(3, 2)));
		
		gameManager = gameBuilder.makeGameManager();//reset board
		//unblock false -> can't land on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(1, 3)));
		
		//unblock false -> can't pass over block
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(1, 4)));
		
		//jump over one piece to then capture enemy piece -> true
	 	assertTrue(gameManager.move(gameManager.makeCoordinate(1, 2), gameManager.makeCoordinate(3, 2)));
		
		gameManager = gameBuilder.makeGameManager();//reset board
		//unblock true -> can't land on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(3, 2), gameManager.makeCoordinate(3, 3)));
		
		//unblock true -> can pass over  block
		assertTrue(gameManager.move(gameManager.makeCoordinate(3, 2), gameManager.makeCoordinate(3, 5)));
		
		//jump false -> can't jump
		assertFalse(gameManager.move(gameManager.makeCoordinate(2, 7), gameManager.makeCoordinate(4, 7)));
		
		//Fly -> can't end on block
		assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(1, 6)));
		
		//Fly -> can jump many pieces 
	    assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(4, 7)));
		
	    gameManager = gameBuilder.makeGameManager();//reset board
	    //Fly -> can't go past distance set (5)
	    assertFalse(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(8, 7)));
	    
	    //Fly -> can go to max distance set (5)
	    assertTrue(gameManager.move(gameManager.makeCoordinate(1, 7), gameManager.makeCoordinate(6, 2)));
	   
	    //Distance -> can't go past distance set (4)
	    assertFalse(gameManager.move(gameManager.makeCoordinate(8, 1), gameManager.makeCoordinate(8, 8)));
	    
	    //Distance -> can go to max distance set (4)
	    assertTrue(gameManager.move(gameManager.makeCoordinate(8, 1), gameManager.makeCoordinate(8, 5)));
	    //Exit removes piece
	    assertNull(gameManager.getPieceAt(gameManager.makeCoordinate(8, 5)));
	    
	    //cant do multi directions
	    assertFalse(gameManager.move(gameManager.makeCoordinate(8, 1), gameManager.makeCoordinate(6, 4)));
	    assertFalse(gameManager.move(gameManager.makeCoordinate(8, 6), gameManager.makeCoordinate(4, 5)));
	    
	    //can move diagonal
	    assertTrue(gameManager.move(gameManager.makeCoordinate(4, 4), gameManager.makeCoordinate(8, 8)));
	    
	    //can't pass over an exit
	    assertFalse(gameManager.move(gameManager.makeCoordinate(10, 1), gameManager.makeCoordinate(10, 5)));
	    
	    //FLY -> can pass over an exit
	    assertTrue(gameManager.move(gameManager.makeCoordinate(9, 1), gameManager.makeCoordinate(9, 6)));
	}
}
