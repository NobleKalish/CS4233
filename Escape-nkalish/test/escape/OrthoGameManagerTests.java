package escape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
}
