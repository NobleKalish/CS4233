package escape;

import java.util.ArrayList;
import escape.board.LocationType;
import escape.board.OrthoSquareBoard;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.board.coordinate.SquareCoordinate;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.piece.MovementPatternID;
import escape.piece.PieceAttributeID;
import escape.piece.Player;
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
					throw new EscapeException("Movement Pattern is not allowed on Orthogonal board!");
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

	private boolean orthogonalPathFinding(OrthoSquareCoordinate from,
			OrthoSquareCoordinate to, PieceAttribute[] attributes) {
		int distance = 0;
		Player player = this.board.getPieceAt(from).getPlayer();
		ArrayList<PieceAttributeID> attributeID = new ArrayList<>();
		ArrayList<OrthoSquareCoordinate> visited = new ArrayList<>();
		visited.add(from);
		distance = this.addAttributes(attributeID, distance, attributes);
		if (distance > 0) {
			ArrayList<ArrayList<OrthoSquareCoordinate>> fringes = new ArrayList<>();
			ArrayList<OrthoSquareCoordinate> first = new ArrayList<>();
			first.add(from);
			fringes.add(first);
			for (int x = 1; x <= distance; x++) {
				int oldVisitedSize = visited.size();
				ArrayList<OrthoSquareCoordinate> nextFringes = new ArrayList<>();
				if (fringes.size() < x) {
					break;
				}
				for (OrthoSquareCoordinate coordinate : fringes.get(x - 1)) {
					int xDir = coordinate.getX();
					int yDir = coordinate.getY();
					for (int direction = 5; direction <= 8; direction++) {
						switch (direction) {
							case (5):
								OrthoSquareCoordinate right = this
										.makeCoordinate(xDir + 1, yDir);
								orthogonalCheckCoordinates(to, right, visited,
										nextFringes, attributeID, player, direction);
								if (visited.contains(to)) {
									return true;
								}
								break;
							case (6):
								OrthoSquareCoordinate bottom = this.makeCoordinate(xDir,
										yDir - 1);
								orthogonalCheckCoordinates(to, bottom, visited,
										nextFringes, attributeID, player, direction);
								if (visited.contains(to)) {
									return true;
								}
								break;
							case (7):
								OrthoSquareCoordinate left = this.makeCoordinate(xDir - 1,
										yDir);
								orthogonalCheckCoordinates(to, left, visited,
										nextFringes, attributeID, player, direction);
								if (visited.contains(to)) {
									return true;
								}
								break;
							case (8):
								OrthoSquareCoordinate top = this.makeCoordinate(xDir,
										yDir + 1);
								orthogonalCheckCoordinates(to, top, visited,
										nextFringes, attributeID, player, direction);
								if (visited.contains(to)) {
									return true;
								}
								break;
						}
					}
				}
				if (oldVisitedSize == visited.size()) {
					return false;
				}
				fringes.add(nextFringes);
			}
		} else {
			throw new EscapeException("No distance or fly attribute dectected");
		}
		return false;
	}

	private boolean linearPathFinding(OrthoSquareCoordinate from,
			OrthoSquareCoordinate to, PieceAttribute[] attributes) {
		int distance = 0;
		Player player = this.board.getPieceAt(from).getPlayer();
		ArrayList<PieceAttributeID> attributeID = new ArrayList<>();
		ArrayList<OrthoSquareCoordinate> visited = new ArrayList<>();
		visited.add(from);
		distance = this.addAttributes(attributeID, distance, attributes);
		if (distance > 0) {
			ArrayList<ArrayList<OrthoSquareCoordinate>> fringes = new ArrayList<>();
			ArrayList<OrthoSquareCoordinate> first = new ArrayList<>();
			first.add(from);
			fringes.add(first);
			int xDir = from.getX();
			int yDir = from.getY();
			for (int direction = 1; direction <= 8; direction++) {
				switch (direction) {
					case (5):
						for (int x = 1; x <= distance; x++) {
							int oldVisitedSize = visited.size();
							ArrayList<OrthoSquareCoordinate> nextFringes = new ArrayList<>();
							OrthoSquareCoordinate right = this.makeCoordinate(xDir + x,
									yDir);
							orthogonalCheckCoordinates(to, right, visited,
									nextFringes, attributeID, player, direction);
							if (oldVisitedSize == visited.size()) {
								break;
							}
							if (visited.contains(to)) {
								return true;
							}
						}
						break;
					case (6):
						for (int x = 1; x <= distance; x++) {
							int oldVisitedSize = visited.size();
							ArrayList<OrthoSquareCoordinate> nextFringes = new ArrayList<>();
							OrthoSquareCoordinate bottom = this.makeCoordinate(xDir,
									yDir - x);
							orthogonalCheckCoordinates(to, bottom, visited,
									nextFringes, attributeID, player, direction);
							if (oldVisitedSize == visited.size()) {
								break;
							}
							if (visited.contains(to)) {
								return true;
							}
						}
						break;
					case (7):
						for (int x = 1; x <= distance; x++) {
							int oldVisitedSize = visited.size();
							ArrayList<OrthoSquareCoordinate> nextFringes = new ArrayList<>();
							OrthoSquareCoordinate left = this.makeCoordinate(xDir - x,
									yDir);
							orthogonalCheckCoordinates(to, left, visited,
									nextFringes, attributeID, player, direction);
							if (oldVisitedSize == visited.size()) {
								break;
							}
							if (visited.contains(to)) {
								return true;
							}
						}
						break;
					case (8):
						for (int x = 1; x <= distance; x++) {
							int oldVisitedSize = visited.size();
							ArrayList<OrthoSquareCoordinate> nextFringes = new ArrayList<>();
							OrthoSquareCoordinate top = this.makeCoordinate(xDir,
									yDir + x);
							orthogonalCheckCoordinates(to, top, visited, nextFringes,
									attributeID, player, direction);
							if (oldVisitedSize == visited.size()) {
								break;
							}
							if (visited.contains(to)) {
								return true;
							}
						}
						break;
				}
			}
		} else {
			throw new EscapeException("No distance or fly attribute dectected");
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
	
	private int addAttributes(ArrayList<PieceAttributeID> attributeID, int distance,
			PieceAttribute[] attributes) {
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
		return distance;
	}
	
	private void orthogonalCheckCoordinates(OrthoSquareCoordinate to,
			OrthoSquareCoordinate start, ArrayList<OrthoSquareCoordinate> visited,
			ArrayList<OrthoSquareCoordinate> nextFringes,
			ArrayList<PieceAttributeID> attributeID, Player player, int direction) {
		if (this.canPieceGoToCoordinate(to, start, attributeID, player)) {
			nextFringes.add(start);
			if (!visited.contains(start)) {
				visited.add(start);
			}
		} else if (!start.equals(to) && this.board.getPieceAt(start) != null
				&& attributeID.contains(PieceAttributeID.JUMP)
				&& this.canJumpOrthogonal(to, start, direction, player)) {
			nextFringes.add(start);
			if (!visited.contains(start)) {
				visited.add(start);
			}
		}
	}

	private boolean canJumpOrthogonal(OrthoSquareCoordinate to,
			OrthoSquareCoordinate start, int direction, Player player) {
		switch (direction) {
			case (5):
				OrthoSquareCoordinate right = this.makeCoordinate(start.getX() + 1,
						start.getY());
				return (this.board.getPieceAt(right) == null
						|| this.canCapturePiece(to, right, player));
			case (6):
				OrthoSquareCoordinate bottom = this.makeCoordinate(start.getX(),
						start.getY() - 1);
				return (this.board.getPieceAt(bottom) == null
						|| this.canCapturePiece(to, bottom, player));
			case (7):
				OrthoSquareCoordinate left = this.makeCoordinate(start.getX() - 1,
						start.getY());
				return (this.board.getPieceAt(left) == null
						|| this.canCapturePiece(to, left, player));
			case (8):
				OrthoSquareCoordinate top = this.makeCoordinate(start.getX(),
						start.getY() + 1);
				return (this.board.getPieceAt(top) == null
						|| this.canCapturePiece(to, top, player));
		}
		return false;
	}

	private boolean canCapturePiece(OrthoSquareCoordinate to,
			OrthoSquareCoordinate start, Player player) {
		return (start.equals(to) && this.board.getPieceAt(to) != null
				&& this.board.getPieceAt(to).getPlayer() != player);
	}

	private boolean canPieceGoToCoordinate(OrthoSquareCoordinate to,
			OrthoSquareCoordinate start, ArrayList<PieceAttributeID> attributeID,
			Player player) {
		if (start.getX() <= 0 || start.getY() <= 0) {
			return false;
		}
		if (this.canCapturePiece(to, start, player)) {
			return true;
		}
		if (this.canMoveOverExit(to, start, attributeID)) {
			return true;
		}
		if (this.board.getLocationType(start) == null
				&& this.board.getPieceAt(start) == null) {
			return true;
		} else if (attributeID.contains(PieceAttributeID.FLY)
				|| attributeID.contains(PieceAttributeID.UNBLOCK)) {
			return true;
		}
		return false;
	}

	private boolean canMoveOverExit(OrthoSquareCoordinate to,
			OrthoSquareCoordinate start, ArrayList<PieceAttributeID> attributeID) {
		return (this.board.getLocationType(start) == LocationType.EXIT
				&& (attributeID.contains(PieceAttributeID.FLY) || start.equals(to)));
	}

}
