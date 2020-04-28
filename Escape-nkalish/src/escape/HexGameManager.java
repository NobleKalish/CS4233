package escape;

import escape.board.HexBoard;
import escape.board.LocationType;
import escape.board.coordinate.HexCoordinate;
import escape.piece.EscapePiece;
import escape.util.LocationInitializer;
import escape.util.PieceTypeInitializer;

public class HexGameManager implements EscapeGameManager<HexCoordinate> {
	private PieceTypeInitializer[] pieceTypes;
	private HexBoard board;
	
	public HexGameManager(int xMax, int yMax,
			LocationInitializer[] locationInitializers,
			PieceTypeInitializer[] pieceTypes) {
		this.board = new HexBoard();
		board.setXMax(xMax);
		board.setYMax(yMax);
		this.makeHexBoard(board, locationInitializers);
		this.pieceTypes = pieceTypes;
	}

	@Override
	public boolean move(HexCoordinate from, HexCoordinate to) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EscapePiece getPieceAt(HexCoordinate coordinate) {
		return this.board.getPieceAt(coordinate);
	}

	@Override
	public HexCoordinate makeCoordinate(int x, int y) {
		return HexCoordinate.makeCoordinate(x, y);
	}
	
	private void makeHexBoard(HexBoard board, LocationInitializer[] locationInitializers) {
		if (locationInitializers != null) {
			for (LocationInitializer li : locationInitializers) {
				HexCoordinate coord = HexCoordinate.makeCoordinate(li.x, li.y);
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
