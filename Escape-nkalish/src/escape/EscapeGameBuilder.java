/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape;

import java.io.*;
import javax.xml.bind.*;
import escape.exception.EscapeException;
import escape.gameManager.EscapeGameManager;
import escape.gameManager.HexGameManager;
import escape.gameManager.OrthoGameManager;
import escape.gameManager.SquareGameManager;
import escape.util.EscapeGameInitializer;

/**
 * This class is what a client will use to creat an instance of a game, given an Escape
 * game configuration file. The configuration file contains the information needed to
 * create an instance of the Escape game.
 * 
 * @version Apr 22, 2020
 */
public class EscapeGameBuilder {
	private EscapeGameInitializer gameInitializer;

	/**
	 * The constructor takes a file that points to the Escape game configuration file. It
	 * should get the necessary information to be ready to create the game manager
	 * specified by the configuration file and other configuration files that it links to.
	 * 
	 * @param fileName
	 *            the file for the Escape game configuration file.
	 * @throws Exception
	 */
	public EscapeGameBuilder(File fileName) throws Exception {
		JAXBContext contextObj = JAXBContext
				.newInstance(EscapeGameInitializer.class);
		Unmarshaller mub = contextObj.createUnmarshaller();
		gameInitializer = (EscapeGameInitializer) mub
				.unmarshal(new FileReader(fileName));
	}

	/**
	 * Once the builder is constructed, this method creates the EscapeGameManager
	 * instance.
	 * 
	 * @return
	 */
	public EscapeGameManager<?> makeGameManager() throws Exception {
		switch (gameInitializer.getCoordinateType()) {
			case HEX:
				return new HexGameManager(gameInitializer.getxMax(),
						gameInitializer.getyMax(),
						gameInitializer.getLocationInitializers(),
						gameInitializer.getPieceTypes(), gameInitializer.getRules());
			case ORTHOSQUARE:
				return new OrthoGameManager(gameInitializer.getxMax(),
						gameInitializer.getyMax(),
						gameInitializer.getLocationInitializers(),
						gameInitializer.getPieceTypes(), gameInitializer.getRules());
			case SQUARE:
				return new SquareGameManager(gameInitializer.getxMax(),
						gameInitializer.getyMax(),
						gameInitializer.getLocationInitializers(),
						gameInitializer.getPieceTypes(), gameInitializer.getRules());
		}
		return null;
	}
}
