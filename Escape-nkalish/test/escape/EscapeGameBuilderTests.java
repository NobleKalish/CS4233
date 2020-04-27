package escape;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;

public class EscapeGameBuilderTests {
	private EscapeGameManager<?> gameManager;

	@Test
	public void testSquareEscapeGame() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/SquareEscapeGame.xml"));
		gameManager = gameBuilder.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(SquareGameManager.class, gameManager.getClass());
	}

	@Test
	public void testOrthoEscapeGame() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/OrthoEscapeGame.xml"));
		gameManager = gameBuilder.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(OrthoGameManager.class, gameManager.getClass());
	}

	@Test
	public void testHexEscapeGame() throws Exception {
		EscapeGameBuilder gameBuilder = new EscapeGameBuilder(
				new File("config/HexEscapeGame.xml"));
		gameManager = gameBuilder.makeGameManager();
		assertNotNull(gameManager);
		assertEquals(HexGameManager.class, gameManager.getClass());
	}
}
