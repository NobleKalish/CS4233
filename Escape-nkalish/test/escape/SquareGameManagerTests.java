package escape;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import escape.board.coordinate.HexCoordinate;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.board.coordinate.SquareCoordinate;
import escape.piece.EscapePiece;
import escape.piece.PieceName;
import escape.piece.Player;

public class SquareGameManagerTests {
	
	@Test
	public void testSquareGameManager() throws Exception{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/SquareEscapeGame.xml"));
		EscapeGameManager<SquareCoordinate> gameManager =  (EscapeGameManager<SquareCoordinate>) gameBuilder.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(SquareCoordinate.makeCoordinate(2, 2), gameManager.makeCoordinate(2, 2));
		assertEquals(EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE), gameManager.getPieceAt(gameManager.makeCoordinate(2, 2)));
	}
	
	@Test
	public void testOrthoSquareGameManager() throws Exception{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/OrthoEscapeGame.xml"));
		EscapeGameManager<OrthoSquareCoordinate> gameManager =  (EscapeGameManager<OrthoSquareCoordinate>) gameBuilder.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(OrthoSquareCoordinate.makeCoordinate(2, 2), gameManager.makeCoordinate(2, 2));
		assertEquals(EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE), gameManager.getPieceAt(gameManager.makeCoordinate(2, 2)));
	}
	
	@Test
	public void testHexGameManager() throws Exception{
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(new File("config/HexEscapeGame.xml"));
		EscapeGameManager<HexCoordinate> gameManager =  (EscapeGameManager<HexCoordinate>) gameBuilder.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(HexCoordinate.makeCoordinate(2, 2), gameManager.makeCoordinate(2, 2));
		assertEquals(EscapePiece.makePiece(Player.PLAYER1, PieceName.HORSE), gameManager.getPieceAt(gameManager.makeCoordinate(2, 2)));
	}
	
}
