package escape;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import escape.board.coordinate.HexCoordinate;
import escape.piece.EscapePiece;
import escape.piece.PieceName;
import escape.piece.Player;

public class HexGameManagerTests {

	@Test
	public void testHexGameManager() throws Exception{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/HexEscapeGame.xml"));
		EscapeGameManager<HexCoordinate> gameManager =  (EscapeGameManager<HexCoordinate>) gameBuilder.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(HexCoordinate.makeCoordinate(2, 2), gameManager.makeCoordinate(2, 2));
		assertEquals(EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE), gameManager.getPieceAt(gameManager.makeCoordinate(2, 2)));
	}
	
	@Test
	public void HexOmniMasterTest() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/edgeTests/HexOmni.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		// Exercise the game now: make moves, check the board, etc.
		
		//jump over two pieces -> false
		assertFalse(emg.move(emg.makeCoordinate(2, -3), emg.makeCoordinate(2, 0)));
		
		//jump over one piece at time -> true
		assertNull(emg.getPieceAt(emg.makeCoordinate(2, 0)));
		assertTrue(emg.move(emg.makeCoordinate(2, -2), emg.makeCoordinate(2, 0)));
		assertNotNull(emg.getPieceAt(emg.makeCoordinate(2, 0)));
		emg = egb.makeGameManager();//reset board
		
		//jump over one piece at time, multi times -> true
		assertTrue(emg.move(emg.makeCoordinate(2, -2), emg.makeCoordinate(2, 2)));
		
		emg = egb.makeGameManager();//reset board
		//capture enemy piece ->
		assertTrue(emg.move(emg.makeCoordinate(2, -2), emg.makeCoordinate(2, -1)));
		
		emg = egb.makeGameManager();//reset board
		//jump over one piece to then capture enemy piece -> true
	 	assertTrue(emg.move(emg.makeCoordinate(2, -3), emg.makeCoordinate(2, -1)));
		
		emg = egb.makeGameManager();//reset board
		//unblock false -> can't land on block
		assertFalse(emg.move(emg.makeCoordinate(2, -3), emg.makeCoordinate(1, -3)));
		
		//unblock false -> can't pass over block
		assertFalse(emg.move(emg.makeCoordinate(2, -3), emg.makeCoordinate(0, -3)));
		
		
		emg = egb.makeGameManager();//reset board
		//unblock true -> can't end on block
		assertFalse(emg.move(emg.makeCoordinate(2, -1), emg.makeCoordinate(3, -1)));
		
		//unblock true -> can pass over  block
		assertTrue(emg.move(emg.makeCoordinate(2, -1), emg.makeCoordinate(0, -1)));
		
		
		//jump false -> can't jump
		assertFalse(emg.move(emg.makeCoordinate(-1, 2), emg.makeCoordinate(-3, 4)));
		
		//Fly -> can't end on block
		assertFalse(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(1, 0)));
		
		//Fly -> can jump many pieces 
	    assertTrue(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(-3, 4)));
		
	    emg = egb.makeGameManager();//reset board
	    
	    //Fly -> can jump block pieces 
	    assertTrue(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(0, -1)));
	    
	    emg = egb.makeGameManager();//reset board
	    
	    //Fly -> can't go past distance set (5)
	    assertFalse(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(-3, 8)));
	    
	    //Fly -> can go to max distance set (5)
	    assertTrue(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(-3, 5)));
	    
	    //Distance -> can't go past distance set (4)
	    assertFalse(emg.move(emg.makeCoordinate(-1, 0), emg.makeCoordinate(-6, 5)));
	    
	    //Distance -> can go to max distance set (4)
	    assertTrue(emg.move(emg.makeCoordinate(-1, 0), emg.makeCoordinate(-5, 0)));
	    //Exit removes piece
	    assertNull(emg.getPieceAt(emg.makeCoordinate(-5, 0)));
	    
	    //can't pass over an exit
	    assertFalse(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(6, 2)));
	    
	    //FLY -> can pass over an exit
	    assertTrue(emg.move(emg.makeCoordinate(0, 3), emg.makeCoordinate(5, 3)));
	}
	
	@Test
	public void HexLinearMasterTest() throws Exception {
		EscapeGameBuilder egb = new EscapeGameBuilder(new File("config/edgeTests/HexLinear.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		// Exercise the game now: make moves, check the board, etc.
		
		//jump over two pieces -> false
		assertFalse(emg.move(emg.makeCoordinate(2, -3), emg.makeCoordinate(2, 0)));
		
		//jump over one piece at time -> true
		assertNull(emg.getPieceAt(emg.makeCoordinate(2, 0)));
		assertTrue(emg.move(emg.makeCoordinate(2, -2), emg.makeCoordinate(2, 0)));
		assertNotNull(emg.getPieceAt(emg.makeCoordinate(2, 0)));
		emg = egb.makeGameManager();//reset board
		
		//jump over one piece at time, multi times -> true
		assertTrue(emg.move(emg.makeCoordinate(2, -2), emg.makeCoordinate(2, 2)));
		
		emg = egb.makeGameManager();//reset board
		//capture enemy piece ->
		assertTrue(emg.move(emg.makeCoordinate(2, -2), emg.makeCoordinate(2, -1)));
		
		emg = egb.makeGameManager();//reset board
		//jump over one piece to then capture enemy piece -> true
	 	assertTrue(emg.move(emg.makeCoordinate(2, -3), emg.makeCoordinate(2, -1)));
		
		emg = egb.makeGameManager();//reset board
		//unblock false -> can't land on block
		assertFalse(emg.move(emg.makeCoordinate(2, -3), emg.makeCoordinate(1, -3)));
		
		//unblock false -> can't pass over block
		assertFalse(emg.move(emg.makeCoordinate(2, -3), emg.makeCoordinate(0, -3)));
		
		
		emg = egb.makeGameManager();//reset board
		//unblock true -> can't end on block
		assertFalse(emg.move(emg.makeCoordinate(2, -1), emg.makeCoordinate(3, -1)));
		
		//unblock true -> can pass over  block
		assertTrue(emg.move(emg.makeCoordinate(2, -1), emg.makeCoordinate(0, -1)));
		
		
		//jump false -> can't jump
		assertFalse(emg.move(emg.makeCoordinate(-1, 2), emg.makeCoordinate(-3, 4)));
		
		//Fly -> can't end on block
		assertFalse(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(1, 0)));
		
		//Fly -> can jump many pieces 
	    assertTrue(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(-3, 4)));
		
	    emg = egb.makeGameManager();//reset board
	    
	    //Fly -> can jump block pieces 
	    assertTrue(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(0, -1)));
	    
	    emg = egb.makeGameManager();//reset board
	    
	    //Fly -> can't go past distance set (5)
	    assertFalse(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(-6, 7)));
	    
	    //Fly -> can go to max distance set (5)
	    assertTrue(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(-5, 6)));
	    
	    //Distance -> can't go past distance set (4)
	    assertFalse(emg.move(emg.makeCoordinate(-1, 0), emg.makeCoordinate(-6, 5)));
	    
	    //Distance -> can go to max distance set (4)
	    assertTrue(emg.move(emg.makeCoordinate(-1, 0), emg.makeCoordinate(-5, 0)));
	    //Exit removes piece
	    assertNull(emg.getPieceAt(emg.makeCoordinate(-5, 0)));
	    
	    //can't pass over an exit
	    assertFalse(emg.move(emg.makeCoordinate(0, 1), emg.makeCoordinate(6, 2)));
	    
	    //FLY -> can pass over an exit
	    assertTrue(emg.move(emg.makeCoordinate(0, 3), emg.makeCoordinate(5, 3)));
	    
	    emg = egb.makeGameManager();//reset board
	    
	    //can't go multi directions
	    assertFalse(emg.move(emg.makeCoordinate(-2, -1), emg.makeCoordinate(-1, 0)));
	    assertFalse(emg.move(emg.makeCoordinate(-2, -1), emg.makeCoordinate(-3, 1)));
	    assertFalse(emg.move(emg.makeCoordinate(-2, -1), emg.makeCoordinate(-3, -2)));
	    assertFalse(emg.move(emg.makeCoordinate(-2, -1), emg.makeCoordinate(-1, -3)));
		
	}
}
