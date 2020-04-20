/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package escape.board;

import java.io.*;
import javax.xml.bind.*;
import com.google.inject.Inject;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.util.*;
import escape.board.annotations.*;
import escape.board.coordinate.*;

/**
 * A Builder class for creating Boards. It is only an example and builds just Square
 * boards. If you choose to use this
 * 
 * @version Apr 2, 2020
 */
public class BoardBuilder {
	private BoardInitializer bi;
	private Board<SquareCoordinate> squareBoard;
	private Board<OrthoSquareCoordinate> orthoSquareBoard;
	private Board<HexCoordinate> hexBoard;

	/**
	 * The constructor for this takes a file name. It is either an absolute path or a path
	 * relative to the beginning of this project.
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public BoardBuilder() throws Exception {
	}

	/**
	 * Sets the BoardInitializer for the board by supplying a file
	 * @param fileName
	 * @throws Exception
	 */
	public void setBuildInitializer(File fileName) throws Exception {
		JAXBContext contextObj = JAXBContext.newInstance(BoardInitializer.class);
		Unmarshaller mub = contextObj.createUnmarshaller();
		bi = (BoardInitializer) mub.unmarshal(new FileReader(fileName));
	}

	/**
	 * Makes the board based on the config provided by the board initializer
	 * @return the board used to play the game on
	 * @throws EscapeException if board provided by config file doesn't exist
	 */
	public Board makeBoard() throws EscapeException {
		switch (bi.getCoordinateId()) {
			case HEX:
				return makeHexBoard();
			case ORTHOSQUARE:
				return makeOrthoBoard();
			case SQUARE:
				return makeSquareBoard();
			default:
				throw new EscapeException("Board does not exist!");
		}
	}

	private Board makeHexBoard() {
		HexBoard board = (HexBoard) this.hexBoard;
		board.setXMax(bi.getxMax());
		board.setYMax(bi.getyMax());
		for (LocationInitializer li : bi.getLocationInitializers()) {
			HexCoordinate coord = HexCoordinate.makeCoordinate(li.x, li.y);
			if (li.pieceName != null) {
				board.putPieceAt(new EscapePiece(li.player, li.pieceName), coord);
			}
			if (li.locationType != null && li.locationType != LocationType.CLEAR) {
				board.setLocationType(coord, li.locationType);
			}
		}
		return board;
	}

	private Board makeSquareBoard() {
		SquareBoard board = (SquareBoard) this.squareBoard;
		board.setXMax(bi.getxMax());
		board.setYMax(bi.getyMax());
		for (LocationInitializer li : bi.getLocationInitializers()) {
			SquareCoordinate coord = SquareCoordinate.makeCoordinate(li.x, li.y);
			if (li.pieceName != null) {
				board.putPieceAt(new EscapePiece(li.player, li.pieceName), coord);
			}
			if (li.locationType != null && li.locationType != LocationType.CLEAR) {
				board.setLocationType(coord, li.locationType);
			}
		}
		return board;
	}

	private Board makeOrthoBoard() {
		OrthoSquareBoard board = (OrthoSquareBoard) this.orthoSquareBoard;
		board.setXMax(bi.getxMax());
		board.setYMax(bi.getyMax());
		for (LocationInitializer li : bi.getLocationInitializers()) {
			OrthoSquareCoordinate coord = OrthoSquareCoordinate.makeCoordinate(li.x,
					li.y);
			if (li.pieceName != null) {
				board.putPieceAt(new EscapePiece(li.player, li.pieceName), coord);
			}
			if (li.locationType != null && li.locationType != LocationType.CLEAR) {
				board.setLocationType(coord, li.locationType);
			}
		}
		return board;
	}

	/**
	 * Sets the square board field using guice injection
	 * @param board
	 */
	@Inject
	public void setSquareBoard(@SquareBoardAnnotation Board board) {
		this.squareBoard = board;
	}

	/**
	 * Sets the ortho board field using guice injection
	 * @param board
	 */
	@Inject
	public void setOrthoSquareBoard(@OrthoBoardAnnotation Board board) {
		this.orthoSquareBoard = board;
	}
	
	/**
	 * Sets the hex board field using guice injection
	 * @param board
	 */
	@Inject
	public void setHexBoard(@HexBoardAnnotation Board board) {
		this.hexBoard = board;
	}
}
