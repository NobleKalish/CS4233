/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape.board;

import com.google.inject.AbstractModule;
import escape.board.annotations.*;

/**
 * Description
 * 
 * @version Apr 15, 2020
 */
public class BoardModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Board.class).annotatedWith(SquareBoardAnnotation.class)
				.to(SquareBoard.class);
		bind(Board.class).annotatedWith(OrthoBoardAnnotation.class)
				.to(OrthoSquareBoard.class);
		bind(Board.class).annotatedWith(HexBoardAnnotation.class).to(HexBoard.class);
	}
}
