package escape;

import escape.board.LocationType;
import escape.board.SquareBoard;
import escape.board.coordinate.SquareCoordinate;
import escape.piece.EscapePiece;
import escape.util.LocationInitializer;
import escape.util.PieceTypeInitializer;

public class SquareGameManager implements EscapeGameManager<SquareCoordinate> {
	private PieceTypeInitializer[] pieceTypes;
	private SquareBoard board;

	public SquareGameManager(int xMax, int yMax,
			LocationInitializer[] locationInitializers,
			PieceTypeInitializer[] pieceTypes) {
		this.board = new SquareBoard();
		board.setXMax(xMax);
		board.setYMax(yMax);
		this.makeSquareBoard(board, locationInitializers);
		this.pieceTypes = pieceTypes;
	}

	@Override
	public boolean move(SquareCoordinate from, SquareCoordinate to) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EscapePiece getPieceAt(SquareCoordinate coordinate) {
		return this.board.getPieceAt(coordinate);
	}

	@Override
	public SquareCoordinate makeCoordinate(int x, int y) {
		return SquareCoordinate.makeCoordinate(x, y);
	}
	
	private void makeSquareBoard(SquareBoard board, LocationInitializer[] locationInitializers) {
		if (locationInitializers != null) {
			for (LocationInitializer li : locationInitializers) {
				SquareCoordinate coord = SquareCoordinate.makeCoordinate(li.x, li.y);
				if (li.pieceName != null) {
					board.putPieceAt(new EscapePiece(li.player, li.pieceName),
							coord);
				}
				if (li.locationType != null
						&& li.locationType != LocationType.CLEAR) {
					board.setLocationType(coord, li.locationType);
				}
			}
		}
	}
}
