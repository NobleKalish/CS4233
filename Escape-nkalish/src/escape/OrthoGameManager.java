package escape;

import escape.board.LocationType;
import escape.board.OrthoSquareBoard;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.piece.MovementPatternID;
import escape.rule.OrthoPathFinding;
import escape.util.LocationInitializer;
import escape.util.PieceTypeInitializer;
import escape.util.PieceTypeInitializer.PieceAttribute;

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
		if (from.equals(to)) {
			return false;
		}
		EscapePiece movingPiece = this.getPieceAt(from);
		PieceAttribute[] attributes = null;
		MovementPatternID movementPattern = null;
		OrthoPathFinding pathFinding = new OrthoPathFinding(this.board);
		if (movingPiece != null) {
			if (this.getPieceAt(to) != null
					&& this.getPieceAt(to).getPlayer() == movingPiece.getPlayer()) {
				return false;
			}
			if (this.board.getLocationType(to) != null
					&& this.board.getLocationType(to).equals(LocationType.BLOCK)) {
				return false;
			}
			for (PieceTypeInitializer pieceType : this.pieceTypes) {
				if (pieceType.getPieceName() == movingPiece.getName()) {
					attributes = pieceType.getAttributes();
					movementPattern = pieceType.getMovementPattern();
				}
			}
			switch (movementPattern) {
				case DIAGONAL:
					throw new EscapeException("Movement Pattern is not allowed on Orthogonal board!");
				case LINEAR:
					if (pathFinding.linearPathFinding(from, to, attributes)) {
						if (this.board.getLocationType(to) == LocationType.EXIT) {
							this.board.putPieceAt(null, from);
						} else {
							this.board.putPieceAt(movingPiece, to);
							this.board.putPieceAt(null, from);
						}
						return true;
					}
					return false;
				case OMNI:
				case ORTHOGONAL:
					if (pathFinding.orthogonalPathFinding(from, to, attributes)) {
						if (this.board.getLocationType(to) == LocationType.EXIT) {
							this.board.putPieceAt(null, from);
						} else {
							this.board.putPieceAt(movingPiece, to);
							this.board.putPieceAt(null, from);
						}
						return true;
					}
					return false;
			}
		}
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
