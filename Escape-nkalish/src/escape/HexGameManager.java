package escape;

import java.util.ArrayList;
import escape.board.HexBoard;
import escape.board.LocationType;
import escape.board.coordinate.HexCoordinate;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.piece.MovementPatternID;
import escape.piece.PieceAttributeID;
import escape.piece.Player;
import escape.rule.HexPathFinding;
import escape.util.LocationInitializer;
import escape.util.PieceTypeInitializer;
import escape.util.PieceTypeInitializer.PieceAttribute;

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
		EscapePiece movingPiece = this.getPieceAt(from);
		PieceAttribute[] attributes = null;
		MovementPatternID movementPattern = null;
		HexPathFinding pathFinding = new HexPathFinding(this.board);
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
					throw new EscapeException(
							"Movement Pattern is not allowed on Hex board!");
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
					if (pathFinding.omniPathFinding(from, to, attributes)) {
						if (this.board.getLocationType(to) == LocationType.EXIT) {
							this.board.putPieceAt(null, from);
						} else {
							this.board.putPieceAt(movingPiece, to);
							this.board.putPieceAt(null, from);
						}
						return true;
					}
					return false;
				case ORTHOGONAL:
					throw new EscapeException(
							"Movement Pattern is not allowed on Hex board!");
				default:
					throw new EscapeException("Unknown movement pattern");
			}
		}
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

	private void makeHexBoard(HexBoard board,
			LocationInitializer[] locationInitializers) {
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
