package escape.rule;

import java.util.ArrayList;
import escape.HexDirections;
import escape.board.HexBoard;
import escape.board.LocationType;
import escape.board.coordinate.HexCoordinate;
import escape.board.coordinate.OrthoSquareCoordinate;
import escape.exception.EscapeException;
import escape.piece.PieceAttributeID;
import escape.piece.Player;
import escape.util.PieceTypeInitializer.PieceAttribute;

public class HexPathFinding {
	HexBoard board;
	ArrayList<ArrayList<HexCoordinate>> fringes;

	public HexPathFinding(HexBoard board) {
		this.board = board;
	}

	public boolean omniPathFinding(HexCoordinate from, HexCoordinate to,
			PieceAttribute[] attributes) {
		int distance = 0;
		Player player = this.board.getPieceAt(from).getPlayer();
		ArrayList<PieceAttributeID> attributeID = new ArrayList<>();
		ArrayList<HexCoordinate> visited = new ArrayList<>();
		visited.add(from);
		distance = this.addAttributes(attributeID, distance, attributes);
		if (distance > 0) {
			fringes = new ArrayList<>();
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
					for (HexDirections direction : HexDirections
							.getHexDirections()) {
						HexCoordinate neighbor = HexDirections
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

	public boolean linearPathFinding(HexCoordinate from, HexCoordinate to,
			PieceAttribute[] attributes) {
		int distance = 0;
		Player player = this.board.getPieceAt(from).getPlayer();
		ArrayList<PieceAttributeID> attributeID = new ArrayList<>();
		ArrayList<HexCoordinate> visited = new ArrayList<>();
		visited.add(from);
		distance = this.addAttributes(attributeID, distance, attributes);
		if (distance > 0) {
			fringes = new ArrayList<>();
			ArrayList<HexCoordinate> first = new ArrayList<>();
			first.add(from);
			fringes.add(first);
			for (HexDirections direction : HexDirections.getHexDirections()) {
				HexCoordinate start = from;
				for (int x = 1; x <= distance; x++) {
					int oldVisitedSize = visited.size();
					ArrayList<HexCoordinate> nextFringes = new ArrayList<>();
					HexCoordinate neighbor = HexDirections.getNeighbor(start,
							direction);
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

	private HexCoordinate checkCoordinate(HexCoordinate to, HexCoordinate start,
			ArrayList<HexCoordinate> visited, ArrayList<HexCoordinate> nextFringes,
			ArrayList<PieceAttributeID> attributeID, Player player,
			HexDirections direction, int distance) {
		if (this.canPieceGoToCoordinate(to, start, attributeID, player)) {
			nextFringes.add(start);
			if (!visited.contains(start)) {
				visited.add(start);
			}
		} else if (!start.equals(to) && this.board.getPieceAt(start) != null
				&& attributeID.contains(PieceAttributeID.JUMP)
				&& this.canJump(to, start, direction, player, attributeID)) {
			ArrayList<HexCoordinate> jumpedCoordinate = new ArrayList<>();
			HexCoordinate neighbor = HexDirections.getNeighbor(start, direction);
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

	private boolean canPieceGoToCoordinate(HexCoordinate to, HexCoordinate start,
			ArrayList<PieceAttributeID> attributeID, Player player) {
		if ((this.board.getXMax() != 0 && start.getX() < 0)
				&& (this.board.getYMax() != 0 && start.getY() < 0)) {
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

	private boolean canJump(HexCoordinate to, HexCoordinate start,
			HexDirections direction, Player player,
			ArrayList<PieceAttributeID> attributeID) {
		HexCoordinate neighbor = HexDirections.getNeighbor(start, direction);
		return ((this.board.getPieceAt(neighbor) == null
				|| this.canCapturePiece(to, neighbor, player))
				&& (this.board.getLocationType(neighbor) != LocationType.BLOCK
						|| attributeID.contains(PieceAttributeID.UNBLOCK)));
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
					if (distance != 0) {
						throw new EscapeException(
								"You cannot have a fly and distane attribute!");
					}
					distance = attribute.getIntValue();
					break;
				case FLY:
					if (distance != 0) {
						throw new EscapeException(
								"You cannot have a fly and distane attribute!");
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

}
