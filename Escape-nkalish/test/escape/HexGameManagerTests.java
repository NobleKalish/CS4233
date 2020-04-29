package escape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
}
