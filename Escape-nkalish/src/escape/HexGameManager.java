package escape;

import java.util.ArrayList;
import escape.board.HexBoard;
import escape.board.LocationType;
import escape.board.coordinate.HexCoordinate;
import escape.board.coordinate.SquareCoordinate;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.piece.MovementPatternID;
import escape.piece.PieceAttributeID;
import escape.piece.Player;
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
					throw new EscapeException(
							"Movement Pattern is not allowed on Hex board!");
				default:
					throw new EscapeException("Unknown movement pattern");
			}
		}
		return false;
	}

	private boolean omniPathFinding(HexCoordinate from, HexCoordinate to,
			PieceAttribute[] attributes) {
		int distance = 0;
		Player player = this.board.getPieceAt(from).getPlayer();
		ArrayList<PieceAttributeID> attributeID = new ArrayList<>();
		ArrayList<HexCoordinate> visited = new ArrayList<>();
		visited.add(from);
		distance = this.addAttributes(attributeID, distance, attributes);
		if (distance > 0) {
			ArrayList<ArrayList<HexCoordinate>> fringes = new ArrayList<>();
			ArrayList<HexCoordinate> first = new ArrayList<>();
			first.add(from);
			fringes.add(first);
			for (int x = 1; x <= distance; x++) {
				int oldVisitedSize = visited.size();
				ArrayList<HexCoordinate> nextFringes = new ArrayList<>();
				if (fringes.size() < x) {
					break;
				}
				for (HexCoordinate coordinate : fringes.get(x - 1)) {
					int xDir = coordinate.getX();
					int yDir = coordinate.getY();
					for (int direction = 1; direction <= 6; direction++) {
						switch (direction) {
							case (1):
								HexCoordinate top = this.makeCoordinate(xDir,
										yDir + 1);
								hexCheckCoordinates(to, top, visited, nextFringes,
										attributeID, player, direction);
								if (visited.contains(to)) {
									return true;
								}
								break;
							case (2):
								HexCoordinate topRight = this
										.makeCoordinate(xDir + 1, yDir);
								hexCheckCoordinates(to, topRight, visited,
										nextFringes, attributeID, player, direction);
								if (visited.contains(to)) {
									return true;
								}
								break;
							case (3):
								HexCoordinate bottomRight = this
										.makeCoordinate(xDir + 1, yDir - 1);
								hexCheckCoordinates(to, bottomRight, visited,
										nextFringes, attributeID, player, direction);
								if (visited.contains(to)) {
									return true;
								}
								break;
							case (4):
								HexCoordinate bottom = this.makeCoordinate(xDir,
										yDir - 1);
								hexCheckCoordinates(to, bottom, visited, nextFringes,
										attributeID, player, direction);
								if (visited.contains(to)) {
									return true;
								}
								break;
							case (5):
								HexCoordinate bottomLeft = this
										.makeCoordinate(xDir - 1, yDir);
								hexCheckCoordinates(to, bottomLeft, visited,
										nextFringes, attributeID, player, direction);
								if (visited.contains(to)) {
									return true;
								}
								break;
							case (6):
								HexCoordinate topLeft = this.makeCoordinate(xDir - 1,
										yDir + 1);
								hexCheckCoordinates(to, topLeft, visited,
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

	private boolean linearPathFinding(HexCoordinate from, HexCoordinate to,
			PieceAttribute[] attributes) {
		int distance = 0;
		Player player = this.board.getPieceAt(from).getPlayer();
		ArrayList<PieceAttributeID> attributeID = new ArrayList<>();
		ArrayList<HexCoordinate> visited = new ArrayList<>();
		visited.add(from);
		distance = this.addAttributes(attributeID, distance, attributes);
		if (distance > 0) {
			ArrayList<ArrayList<HexCoordinate>> fringes = new ArrayList<>();
			ArrayList<HexCoordinate> first = new ArrayList<>();
			first.add(from);
			fringes.add(first);
			int xDir = from.getX();
			int yDir = from.getY();
			for (int direction = 1; direction <= 8; direction++) {
				switch (direction) {
					case (1):
						for (int x = 1; x <= distance; x++) {
							int oldVisitedSize = visited.size();
							ArrayList<HexCoordinate> nextFringes = new ArrayList<>();
							HexCoordinate top = this.makeCoordinate(xDir,
									yDir + x);
							hexCheckCoordinates(to, top, visited,
									nextFringes, attributeID, player, direction);
							if (oldVisitedSize == visited.size()) {
								break;
							}
							if (visited.contains(to)) {
								return true;
							}
						}
						break;
					case (2):
						for (int x = 1; x <= distance; x++) {
							int oldVisitedSize = visited.size();
							ArrayList<HexCoordinate> nextFringes = new ArrayList<>();
							HexCoordinate topRight = this
									.makeCoordinate(xDir + x, yDir);
							hexCheckCoordinates(to, topRight, visited,
									nextFringes, attributeID, player, direction);
							if (oldVisitedSize == visited.size()) {
								break;
							}
							if (visited.contains(to)) {
								return true;
							}
						}
						break;
					case (3):
						for (int x = 1; x <= distance; x++) {
							int oldVisitedSize = visited.size();
							ArrayList<HexCoordinate> nextFringes = new ArrayList<>();
							HexCoordinate bottomRight = this
									.makeCoordinate(xDir + x, yDir - x);
							hexCheckCoordinates(to, bottomRight, visited,
									nextFringes, attributeID, player, direction);
							if (oldVisitedSize == visited.size()) {
								break;
							}
							if (visited.contains(to)) {
								return true;
							}
						}
						break;
					case (4):
						for (int x = 1; x <= distance; x++) {
							int oldVisitedSize = visited.size();
							ArrayList<HexCoordinate> nextFringes = new ArrayList<>();
							HexCoordinate bottom = this.makeCoordinate(xDir,
									yDir - x);
							hexCheckCoordinates(to, bottom, visited,
									nextFringes, attributeID, player, direction);
							if (oldVisitedSize == visited.size()) {
								break;
							}
							if (visited.contains(to)) {
								return true;
							}
						}
						break;
					case (5):
						for (int x = 1; x <= distance; x++) {
							int oldVisitedSize = visited.size();
							ArrayList<HexCoordinate> nextFringes = new ArrayList<>();
							HexCoordinate bottomLeft = this.makeCoordinate(xDir - x,
									yDir);
							hexCheckCoordinates(to, bottomLeft, visited,
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
							ArrayList<HexCoordinate> nextFringes = new ArrayList<>();
							HexCoordinate topLeft = this.makeCoordinate(xDir - x,
									yDir + x);
							hexCheckCoordinates(to, topLeft, visited,
									nextFringes, attributeID, player, direction);
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

	private void hexCheckCoordinates(HexCoordinate to, HexCoordinate start,
			ArrayList<HexCoordinate> visited, ArrayList<HexCoordinate> nextFringes,
			ArrayList<PieceAttributeID> attributeID, Player player, int direction) {
		if (this.canPieceGoToCoordinate(to, start, attributeID, player)) {
			nextFringes.add(start);
			if (!visited.contains(start)) {
				visited.add(start);
			}
		} else if (!start.equals(to) && this.board.getPieceAt(start) != null
				&& attributeID.contains(PieceAttributeID.JUMP)
				&& this.canJumpHex(to, start, direction, player)) {
			nextFringes.add(start);
			if (!visited.contains(start)) {
				visited.add(start);
			}
		}
	}

	private boolean canPieceGoToCoordinate(HexCoordinate to, HexCoordinate start,
			ArrayList<PieceAttributeID> attributeID, Player player) {
		if ((this.board.getXMax() != 0 && start.getX() <= 0)
				&& (this.board.getYMax() != 0 && start.getY() <= 0)) {
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

	private boolean canMoveOverExit(HexCoordinate to, HexCoordinate start,
			ArrayList<PieceAttributeID> attributeID) {
		return (this.board.getLocationType(start) == LocationType.EXIT
				&& (attributeID.contains(PieceAttributeID.FLY) || start.equals(to)));
	}

	private boolean canJumpHex(HexCoordinate to, HexCoordinate start, int direction,
			Player player) {
		switch (direction) {
			case (1):
				HexCoordinate top = this.makeCoordinate(start.getX(),
						start.getY() + 1);
				return (this.board.getPieceAt(top) == null
						|| this.canCapturePiece(to, top, player));
			case (2):
				HexCoordinate topRight = this.makeCoordinate(start.getX() + 1,
						start.getY());
				return (this.board.getPieceAt(topRight) == null
						|| this.canCapturePiece(to, topRight, player));
			case (3):
				HexCoordinate bottomRight = this.makeCoordinate(start.getX() + 1,
						start.getY() - 1);
				return (this.board.getPieceAt(bottomRight) == null
						|| this.canCapturePiece(to, bottomRight, player));
			case (4):
				HexCoordinate bottom = this.makeCoordinate(start.getX(),
						start.getY() - 1);
				return (this.board.getPieceAt(bottom) == null
						|| this.canCapturePiece(to, bottom, player));
			case (5):
				HexCoordinate bottomLeft = this.makeCoordinate(start.getX() - 1,
						start.getY());
				return (this.board.getPieceAt(bottomLeft) == null
						|| this.canCapturePiece(to, bottomLeft, player));
			case (6):
				HexCoordinate topLeft = this.makeCoordinate(start.getX() - 1,
						start.getY() + 1);
				return (this.board.getPieceAt(topLeft) == null
						|| this.canCapturePiece(to, topLeft, player));

		}
		return false;
	}

	private boolean canCapturePiece(HexCoordinate to, HexCoordinate start,
			Player player) {
		return (start.equals(to) && this.board.getPieceAt(to) != null
				&& this.board.getPieceAt(to).getPlayer() != player);
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

}
