package escape;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.Test;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.piece.EscapePiece;
import escape.piece.PieceName;
import escape.piece.Player;

public class OrthoGameManagerTests {


	
	@Test
	public void testOrthoSquareGameManager() throws Exception{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/OrthoEscapeGame.xml"));
		EscapeGameManager<OrthoSquareCoordinate> gameManager =  (EscapeGameManager<OrthoSquareCoordinate>) gameBuilder.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(OrthoSquareCoordinate.makeCoordinate(2, 2), gameManager.makeCoordinate(2, 2));
		assertEquals(EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE), gameManager.getPieceAt(gameManager.makeCoordinate(2, 2)));
	}
	
	@Test
	public void OrthoSquareOrthoMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/edgeTests/OrthoSquareOrtho.xml"));
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
	
	@Test
	public void OrthoSquareOmniMasterTest() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/edgeTests/OrthoSquareOmni.xml"));
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
	    
	    gameManager = gameBuilder.makeGameManager();//reset board
	    //can't pass over an exit
	    assertFalse(gameManager.move(gameManager.makeCoordinate(10, 1), gameManager.makeCoordinate(10, 5)));
	    
	    //FLY -> can pass over an exit
	    assertTrue(gameManager.move(gameManager.makeCoordinate(9, 1), gameManager.makeCoordinate(9, 6)));
	    
	}
	
	@Test
	public void OrthoSquareLinearMasterTest() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/edgeTests/OrthoSquareLinear.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		// Exercise the game now: make moves, check the board, etc.
		
		//jump over two pieces -> false
		assertFalse(emg.move(emg.makeCoordinate(1, 2), emg.makeCoordinate(4, 2)));
		
		//jump over one piece at time -> true
		assertNull(emg.getPieceAt(emg.makeCoordinate(4, 2)));
		assertTrue(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(4, 2)));
		assertNotNull(emg.getPieceAt(emg.makeCoordinate(4, 2)));
		emg = egb.makeGameManager();//reset board
		
		//jump over one piece at time, multi times -> true
		assertTrue(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(6, 2)));
		
		emg = egb.makeGameManager();//reset board
		//capture enemy piece ->
		assertTrue(emg.move(emg.makeCoordinate(2, 2), emg.makeCoordinate(3, 2)));
		
		emg = egb.makeGameManager();//reset board
		//unblock false -> can't land on block
		assertFalse(emg.move(emg.makeCoordinate(1, 2), emg.makeCoordinate(1, 3)));
		
		//unblock false -> can't pass over block
		assertFalse(emg.move(emg.makeCoordinate(1, 2), emg.makeCoordinate(1, 4)));
		
		//jump over one piece to then capture enemy piece -> true
	 	assertTrue(emg.move(emg.makeCoordinate(1, 2), emg.makeCoordinate(3, 2)));
		
		emg = egb.makeGameManager();//reset board
		//unblock true -> can't land on block
		assertFalse(emg.move(emg.makeCoordinate(3, 2), emg.makeCoordinate(3, 3)));
		
		//unblock true -> can pass over  block
		assertTrue(emg.move(emg.makeCoordinate(3, 2), emg.makeCoordinate(3, 5)));
		
		//jump false -> can't jump
		assertFalse(emg.move(emg.makeCoordinate(2, 7), emg.makeCoordinate(4, 7)));
		
		//Fly -> can't end on block
		assertFalse(emg.move(emg.makeCoordinate(1, 7), emg.makeCoordinate(1, 6)));
		
		//Fly -> can jump many pieces 
	    assertTrue(emg.move(emg.makeCoordinate(1, 7), emg.makeCoordinate(4, 7)));
		
	    emg = egb.makeGameManager();//reset board
	    //Fly -> can't go past distance set (5)
	    assertFalse(emg.move(emg.makeCoordinate(1, 7), emg.makeCoordinate(8, 7)));
	    
	    //Fly -> can go to max distance set (5)
	    assertTrue(emg.move(emg.makeCoordinate(1, 7), emg.makeCoordinate(6, 7)));
	   
	    //Distance -> can't go past distance set (4)
	    assertFalse(emg.move(emg.makeCoordinate(8, 1), emg.makeCoordinate(8, 8)));
	    
	    //Distance -> can go to max distance set (4)
	    assertTrue(emg.move(emg.makeCoordinate(8, 1), emg.makeCoordinate(8, 5)));
	    
	    //Exit removes piece
	    assertNull(emg.getPieceAt(emg.makeCoordinate(8, 5)));
	    
	    //cant do multi directions
	    assertFalse(emg.move(emg.makeCoordinate(8, 1), emg.makeCoordinate(6, 4)));
	    assertFalse(emg.move(emg.makeCoordinate(8, 6), emg.makeCoordinate(4, 5)));
	    assertFalse(emg.move(emg.makeCoordinate(8, 1), emg.makeCoordinate(4, 5)));
	    
	    //can't move diagonal
	    assertFalse(emg.move(emg.makeCoordinate(4, 4), emg.makeCoordinate(8, 8)));
	    
	    emg = egb.makeGameManager();//reset board
	    //can't pass over an exit
	    assertFalse(emg.move(emg.makeCoordinate(10, 1), emg.makeCoordinate(10, 5)));
	    
	    //FLY -> can pass over an exit
	    assertTrue(emg.move(emg.makeCoordinate(9, 1), emg.makeCoordinate(9, 6)));
	    
	}
	
}
