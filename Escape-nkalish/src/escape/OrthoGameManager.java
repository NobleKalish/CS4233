package escape;

import escape.board.LocationType;
import escape.board.OrthoSquareBoard;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.piece.EscapePiece;
import escape.util.LocationInitializer;
import escape.util.PieceTypeInitializer;

public class OrthoGameManager implements EscapeGameManager<OrthoSquareCoordinate>{
	private PieceTypeInitializer[] pieceTypes;
	private OrthoSquareBoard board;

	
	public OrthoGameManager(int xMax, int yMax,
			LocationInitializer[] locationInitializers,
			PieceTypeInitializer[] pieceTypes) {
		this.board = new OrthoSquareBoard();
		board.setXMax(xMax);
		board.setYMax(yMax);
		this.makeOrthoBoard(board, locationInitializers);
		this.pieceTypes = pieceTypes;
	}	
	
	@Override
	public boolean move(OrthoSquareCoordinate from, OrthoSquareCoordinate to) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EscapePiece getPieceAt(OrthoSquareCoordinate coordinate) {
		return this.board.getPieceAt(coordinate);
	}

	@Override
	public OrthoSquareCoordinate makeCoordinate(int x, int y) {
		return OrthoSquareCoordinate.makeCoordinate(x, y);
	}
	
	private void makeOrthoBoard(OrthoSquareBoard board, LocationInitializer[] locationInitializers) {
		if (locationInitializers != null) {
			for (LocationInitializer li : locationInitializers) {
				OrthoSquareCoordinate coord = OrthoSquareCoordinate.makeCoordinate(li.x, li.y);
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
