/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package escape.board;

import java.io.*;
import java.util.Optional;
import java.util.stream.Stream;
import javax.xml.bind.*;
import com.google.inject.Inject;
import escape.exception.EscapeException;
import escape.util.*;
import escape.board.annotations.*;

/**
 * A Builder class for creating Boards. It is only an example and builds just Square
 * boards. If you choose to use this
 * 
 * @version Apr 2, 2020
 */
public class BoardBuilder {
	private BoardInitializer bi;
	private BoardFactory factory;

	/**
	 * The constructor for this takes a file name. It is either an absolute path or a path
	 * relative to the beginning of this project.
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public BoardBuilder(File fileName, @BoardFactoryAnnotation BoardFactory factory) throws Exception {
		JAXBContext contextObj = JAXBContext.newInstance(BoardInitializer.class);
		Unmarshaller mub = contextObj.createUnmarshaller();
		bi = (BoardInitializer) mub.unmarshal(new FileReader(fileName));
		this.factory = factory;
	}

	public Board makeBoard() throws EscapeException {
		return this.factory.buildBoard(bi);
	}

	@Inject
	public Board initializeSquareBoard(@SquareBoardAnnotation Board board) {
		return null;
	}
}
