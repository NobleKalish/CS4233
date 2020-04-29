package escape;

import java.util.ArrayList;
import escape.board.LocationType;
import escape.board.SquareBoard;
import escape.board.coordinate.SquareCoordinate;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.piece.MovementPatternID;
import escape.piece.PieceAttributeID;
import escape.util.LocationInitializer;
import escape.util.PieceTypeInitializer;
import escape.util.PieceTypeInitializer.PieceAttribute;

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
		EscapePiece movingPiece = this.getPieceAt(from);
		PieceAttribute[] attributes = null;
		MovementPatternID movementPattern = null;
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
					if (diagonalPathFinding(from, to, attributes)) {
						if (this.board.getLocationType(to) == LocationType.EXIT) {
							this.board.putPieceAt(null, from);
						} else {
							this.board.putPieceAt(movingPiece, to);
							this.board.putPieceAt(null, from);
						}
						return true;
					}
					return false;
				case LINEAR:
					if (linearPathFinding(from, to, attributes)) {
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
					if (omniPathFinding(from, to, attributes)) {
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
					if (orthogonalPathFinding(from, to, attributes)) {
						if (this.board.getLocationType(to) == LocationType.EXIT) {
							this.board.putPieceAt(null, from);
						} else {
							this.board.putPieceAt(movingPiece, to);
							this.board.putPieceAt(null, from);
						}
						return true;
					}
					return false;
				default:
					throw new EscapeException("Unknown movement pattern");
			}
		}
		return false;
	}

	private boolean orthogonalPathFinding(SquareCoordinate from, SquareCoordinate to,
			PieceAttribute[] attributes) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean omniPathFinding(SquareCoordinate from, SquareCoordinate to,
			PieceAttribute[] attributes) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean linearPathFinding(SquareCoordinate from, SquareCoordinate to,
			PieceAttribute[] attributes) {
		int distance = 0, fly = 0;
		ArrayList<PieceAttributeID> attributeID = new ArrayList<>();
		ArrayList<SquareCoordinate> visted = new ArrayList<>();
		visted.add(from);
		ArrayList<SquareCoordinate> fringes = new ArrayList<>();
		fringes.add(from);
		for (PieceAttribute attribute : attributes) {
			switch (attribute.getId()) {
				case DISTANCE:
					distance = attribute.getIntValue();
					break;
				case FLY:
					fly = attribute.getIntValue();
					break;
				case JUMP:
					if (attribute.isBooleanValue()) {
						attributeID.add(attribute.getId());
					}
					break;
				case UNBLOCK:
					if (attribute.isBooleanValue()) {
						attributeID.add(attribute.getId());
					}
				case VALUE:
					break;
				default:
					throw new EscapeException("Attribute ID does not exist");
			}
		}
		if (fly > 0) {
			// TODO make sure it approves linear movement pattern
			return false;
		}
		return false;
	}

	private boolean diagonalPathFinding(SquareCoordinate from, SquareCoordinate to,
			PieceAttribute[] attributes) {
		int distance = 0;
		ArrayList<PieceAttributeID> attributeID = new ArrayList<>();
		ArrayList<SquareCoordinate> visited = new ArrayList<>();
		for (PieceAttribute attribute : attributes) {
			switch (attribute.getId()) {
				case DISTANCE:
					distance = attribute.getIntValue();
					break;
				case FLY:
					distance = attribute.getIntValue();
					attributeID.add(attribute.getId());
					break;
				case JUMP:
					if (attribute.isBooleanValue()) {
						attributeID.add(attribute.getId());
					}
					break;
				case UNBLOCK:
					if (attribute.isBooleanValue()) {
						attributeID.add(attribute.getId());
					}
				case VALUE:
					break;
				default:
					throw new EscapeException("Attribute ID does not exist");
			}
		}
		if (distance > 0) {
			ArrayList<ArrayList<SquareCoordinate>> fringes = new ArrayList<>();
			ArrayList<SquareCoordinate> first = new ArrayList<>();
			first.add(from);
			fringes.add(first);
			for (int x = 1; x <= distance; x++) {
				ArrayList<SquareCoordinate> nextFringes = new ArrayList<>();
				if (fringes.size() < x) {
					break;
				}
				for (SquareCoordinate coordinate : fringes.get(x - 1)) {
					int xDir = coordinate.getX();
					int yDir = coordinate.getY();
					SquareCoordinate topRight = this.makeCoordinate(xDir + 1,
							yDir + 1);
					SquareCoordinate topLeft = this.makeCoordinate(xDir - 1,
							yDir + 1);
					SquareCoordinate bottomRight = this.makeCoordinate(xDir + 1,
							yDir - 1);
					SquareCoordinate bottomLeft = this.makeCoordinate(xDir - 1,
							yDir - 1);

					if (this.canPieceGoToCoordinate(topRight, attributeID)) {
						nextFringes.add(topRight);
						if (!visited.contains(topRight)) {
							visited.add(topRight);
						}
					} else if (!topRight.equals(to)
							&& this.board.getPieceAt(topRight) != null
							&& attributeID.contains(PieceAttributeID.JUMP)
							&& this.canJumpDiagonal(topRight, 1)) {
						nextFringes.add(topRight);
						if (!visited.contains(topRight)) {
							visited.add(topRight);
						}
					}

					if (this.canPieceGoToCoordinate(topLeft, attributeID)) {
						nextFringes.add(topLeft);
						if (!visited.contains(topLeft)) {
							visited.add(topLeft);
						}
					} else if (!topRight.equals(to)
							&& this.board.getPieceAt(topLeft) != null
							&& attributeID.contains(PieceAttributeID.JUMP)
							&& this.canJumpDiagonal(topLeft, 4)) {
						nextFringes.add(topRight);
						if (!visited.contains(topRight)) {
							visited.add(topRight);
						}
					}

					if (this.canPieceGoToCoordinate(bottomRight, attributeID)) {
						nextFringes.add(bottomRight);
						if (!visited.contains(bottomRight)) {
							visited.add(bottomRight);
						}
					} else if (!topRight.equals(to)
							&& this.board.getPieceAt(bottomRight) != null
							&& attributeID.contains(PieceAttributeID.JUMP)
							&& this.canJumpDiagonal(bottomRight, 2)) {
						nextFringes.add(topRight);
						if (!visited.contains(topRight)) {
							visited.add(topRight);
						}
					}

					if (this.canPieceGoToCoordinate(bottomLeft, attributeID)) {
						nextFringes.add(bottomLeft);
						if (!visited.contains(bottomLeft)) {
							visited.add(bottomLeft);
						}
					} else if (!topRight.equals(to)
							&& this.board.getPieceAt(bottomLeft) != null
							&& attributeID.contains(PieceAttributeID.JUMP)
							&& this.canJumpDiagonal(bottomLeft, 3)) {
						nextFringes.add(topRight);
						if (!visited.contains(topRight)) {
							visited.add(topRight);
						}
					}
				}
				fringes.add(nextFringes);
			}
		} else {
			throw new EscapeException("No distance or fly attribute dectected");
		}

		if (visited.contains(to)) {
			return true;
		}

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

	private void makeSquareBoard(SquareBoard board,
			LocationInitializer[] locationInitializers) {
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

	private boolean canPieceGoToCoordinate(SquareCoordinate coordinate,
			ArrayList<PieceAttributeID> attributeID) {
		if (coordinate.getX() <= 0 || coordinate.getY() <= 0) {
			return false;
		}
		if ((this.board.getLocationType(coordinate) == null
				|| this.board.getLocationType(coordinate) != LocationType.BLOCK)
				&& this.board.getPieceAt(coordinate) == null) {
			return true;
		} else if (attributeID.contains(PieceAttributeID.FLY)
				|| attributeID.contains(PieceAttributeID.UNBLOCK)) {
			return true;
		}
		return false;
	}

	private boolean canJumpDiagonal(SquareCoordinate from, int direction) {
		switch (direction) {
			case (1):
				SquareCoordinate topRight = this.makeCoordinate(from.getX() + 1,
						from.getY() + 1);
				return (this.board.getPieceAt(topRight) == null);
			case (2):
				SquareCoordinate bottomRight = this.makeCoordinate(from.getX() + 1,
						from.getY() - 1);
				return (this.board.getPieceAt(bottomRight) == null);
			case (3):
				SquareCoordinate bottomLeft = this.makeCoordinate(from.getX() - 1,
						from.getY() - 1);
				return (this.board.getPieceAt(bottomLeft) == null);
			case (4):
				SquareCoordinate topLeft = this.makeCoordinate(from.getX() - 1,
						from.getY() + 1);
				return (this.board.getPieceAt(topLeft) == null);
		}
		return false;
	}
}
