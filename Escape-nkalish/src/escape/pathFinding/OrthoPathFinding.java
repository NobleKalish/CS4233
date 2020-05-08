package escape.pathFinding;

import java.util.ArrayList;
import escape.board.LocationType;
import escape.board.OrthoSquareBoard;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.board.coordinate.SquareCoordinate;
import escape.directions.OrthoDirections;
import escape.exception.EscapeException;
import escape.piece.PieceAttributeID;
import escape.piece.Player;
import escape.util.PieceTypeInitializer.PieceAttribute;

public class OrthoPathFinding {
	OrthoSquareBoard board;
	ArrayList<ArrayList<OrthoSquareCoordinate>> fringes;

	public OrthoPathFinding(OrthoSquareBoard board) {
		this.board = board;
	}

	/**
	 * Function used to determine the path finding for a PieceType with the movement pattern of orthogonal or omni
	 * @param from The originating coordinate
	 * @param to The coordinate the piece is going to.
	 * @param attributes The attributes associated with the PieceType
	 * @return A boolean on if a piece can go from the From coordinate to the To coordinate.
	 */
	public boolean orthogonalPathFinding(OrthoSquareCoordinate from,
			OrthoSquareCoordinate to, PieceAttribute[] attributes) {
		int distance = 0;
		Player player = this.board.getPieceAt(from).getPlayer();
		ArrayList<PieceAttributeID> attributeID = new ArrayList<>();
		ArrayList<OrthoSquareCoordinate> visited = new ArrayList<>();
		visited.add(from);
		distance = this.addAttributes(attributeID, distance, attributes);
		if (distance > 0) {
			fringes = new ArrayList<>();
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
					for (OrthoDirections direction : OrthoDirections
							.getOrthoDirections()) {
						OrthoSquareCoordinate neighbor = OrthoDirections
								.getNeighbor(coordinate, direction);
						checkCoordinate(to, neighbor, visited, nextFringes,
								attributeID, player, direction, x);
						if (visited.contains(to)) {
							return true;
						}
					}
				}
				if (oldVisitedSize == visited.size()) {
					return false;
				}
				if (fringes.size() == x + 1) {
					fringes.get(x).addAll(nextFringes);
				} else {
					fringes.add(nextFringes);
				}
			}
		} else {
			throw new EscapeException("No distance or fly attribute dectected");
		}
		return false;
	}

	/**
	 * Function used to determine the path finding for a PieceType with the movement pattern of linear
	 * @param from The originating coordinate
	 * @param to The coordinate the piece is going to.
	 * @param attributes The attributes associated with the PieceType
	 * @return A boolean on if a piece can go from the From coordinate to the To coordinate.
	 */
	public boolean linearPathFinding(OrthoSquareCoordinate from,
			OrthoSquareCoordinate to, PieceAttribute[] attributes) {
		int distance = 0;
		Player player = this.board.getPieceAt(from).getPlayer();
		ArrayList<PieceAttributeID> attributeID = new ArrayList<>();
		ArrayList<OrthoSquareCoordinate> visited = new ArrayList<>();
		visited.add(from);
		distance = this.addAttributes(attributeID, distance, attributes);
		if (distance > 0) {
			fringes = new ArrayList<>();
			ArrayList<OrthoSquareCoordinate> first = new ArrayList<>();
			first.add(from);
			fringes.add(first);
			for (OrthoDirections direction : OrthoDirections.getOrthoDirections()) {
				OrthoSquareCoordinate start = from;
				for (int x = 1; x <= distance; x++) {
					int oldVisitedSize = visited.size();
					ArrayList<OrthoSquareCoordinate> nextFringes = new ArrayList<>();
					OrthoSquareCoordinate neighbor = OrthoDirections
							.getNeighbor(start, direction);
					start = checkCoordinate(to, neighbor, visited, nextFringes,
							attributeID, player, direction, x);
					if (oldVisitedSize == visited.size()) {
						break;
					}
					if (visited.contains(to)) {
						return true;
					}
				}
			}
		} else {
			throw new EscapeException("No distance or fly attribute dectected");
		}
		return false;
	}

	private int addAttributes(ArrayList<PieceAttributeID> attributeID, int distance,
			PieceAttribute[] attributes) {
		for (PieceAttribute attribute : attributes) {
			switch (attribute.getId()) {
				case DISTANCE:
					if (distance != 0) {
						throw new EscapeException("You cannot have a fly and distane attribute!");
					}
					distance = attribute.getIntValue();
					break;
				case FLY:
					if (distance != 0) {
						throw new EscapeException("You cannot have a fly and distane attribute!");
					}
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
			}
		}
		return distance;
	}

	private OrthoSquareCoordinate checkCoordinate(OrthoSquareCoordinate to,
			OrthoSquareCoordinate start, ArrayList<OrthoSquareCoordinate> visited,
			ArrayList<OrthoSquareCoordinate> nextFringes,
			ArrayList<PieceAttributeID> attributeID, Player player,
			OrthoDirections direction, int distance) {
		if (this.canPieceGoToCoordinate(to, start, attributeID, player)) {
			nextFringes.add(start);
			if (!visited.contains(start)) {
				visited.add(start);
			}
		} else if (!start.equals(to) && this.board.getPieceAt(start) != null
				&& attributeID.contains(PieceAttributeID.JUMP)
				&& this.canJump(to, start, direction, player, attributeID)) {
			ArrayList<OrthoSquareCoordinate> jumpedCoordinate = new ArrayList<>();
			OrthoSquareCoordinate neighbor = OrthoDirections.getNeighbor(start,
					direction);
			jumpedCoordinate.add(neighbor);
			if (fringes.size() == distance + 1) {
				fringes.get(distance).addAll(jumpedCoordinate);
			} else {
				fringes.add(jumpedCoordinate);
			}
			if (!visited.contains(neighbor)) {
				visited.add(neighbor);
			}
			return neighbor;
		}
		return start;
	}

	private boolean canJump(OrthoSquareCoordinate to, OrthoSquareCoordinate start,
			OrthoDirections direction, Player player,
			ArrayList<PieceAttributeID> attributeID) {
		OrthoSquareCoordinate neighbor = OrthoDirections.getNeighbor(start,
				direction);
		return ((this.board.getPieceAt(neighbor) == null
				|| this.canCapturePiece(to, neighbor, player))
				&& (this.board.getLocationType(neighbor) != LocationType.BLOCK
						|| attributeID.contains(PieceAttributeID.UNBLOCK)));
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
