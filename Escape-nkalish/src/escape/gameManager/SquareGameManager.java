package escape.gameManager;

import java.util.ArrayList;
import escape.GameObserver;
import escape.board.LocationType;
import escape.board.SquareBoard;
import escape.board.coordinate.SquareCoordinate;
import escape.exception.EscapeException;
import escape.pathFinding.SquarePathFinding;
import escape.piece.EscapePiece;
import escape.piece.MovementPatternID;
import escape.piece.PieceAttributeID;
import escape.piece.Player;
import escape.rule.Rule;
import escape.rule.RuleID;
import escape.util.LocationInitializer;
import escape.util.PieceTypeInitializer;
import escape.util.PieceTypeInitializer.PieceAttribute;

public class SquareGameManager implements EscapeGameManager<SquareCoordinate> {
	private PieceTypeInitializer[] pieceTypes;
	private SquareBoard board;
	private Rule[] rules;
	private boolean isPlayer1Turn = true;
	private int player1Points = 0;
	private int player2Points = 0;
	private ArrayList<GameObserver> observers;
	private int turns = 1;

	public SquareGameManager(int xMax, int yMax,
			LocationInitializer[] locationInitializers,
			PieceTypeInitializer[] pieceTypes, Rule[] rules) {
		this.board = new SquareBoard();
		board.setXMax(xMax);
		board.setYMax(yMax);
		this.makeSquareBoard(board, locationInitializers);
		this.pieceTypes = pieceTypes;
		this.rules = rules;
		this.observers = new ArrayList<>();
	}

	@Override
	public EscapePiece getPieceAt(SquareCoordinate coordinate) {
		return this.board.getPieceAt(coordinate);
	}

	@Override
	public SquareCoordinate makeCoordinate(int x, int y) {
		return SquareCoordinate.makeCoordinate(x, y);
	}
	
	public void setIsPlayer1Turn() {
		this.isPlayer1Turn = !this.isPlayer1Turn;
	}

	@Override
	public boolean move(SquareCoordinate from, SquareCoordinate to) {
		int value = 0;
		if (from.equals(to)) {
			this.notifyObservers("Piece cannot move to location it started in!");
			return false;
		}
		EscapePiece movingPiece = this.getPieceAt(from);
		if (movingPiece == null) {
			this.notifyObservers("Piece must exist!");
			return false;
		}
		 if ((movingPiece.getPlayer() == Player.PLAYER1) != isPlayer1Turn) {
		 this.notifyObservers("Piece does not belong to player!");
		 return false;
		 }
		value = this.getValue(movingPiece);
		PieceAttribute[] attributes = null;
		MovementPatternID movementPattern = null;
		SquarePathFinding pathFinding = new SquarePathFinding(this.board);
		if (this.getPieceAt(to) != null
				&& this.getPieceAt(to).getPlayer() == movingPiece.getPlayer()) {
			this.notifyObservers("Cannot capture piece belonging to same player!");
			return false;
		}
		if (this.board.getLocationType(to) != null
				&& this.board.getLocationType(to).equals(LocationType.BLOCK)) {
			this.notifyObservers("Cannot land in location that is a block type!");
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
				this.checkEndOfGame(movingPiece, player1Points, player2Points,
						turns);
				if (pathFinding.diagonalPathFinding(from, to, attributes)) {
					if (this.board.getLocationType(to) == LocationType.EXIT) {
						this.board.putPieceAt(null, from);
						this.addPlayerPoints(movingPiece, value);
					} else {
						if (this.canRemovePieces()) {
							if (this.checkCanCaputure(to, value)) {
								this.board.putPieceAt(movingPiece, to);
								this.board.putPieceAt(null, from);
							} else {
								this.board.putPieceAt(null, from);
							}
						} else {
							this.notifyObservers("Piece cannot move to location!");
							return false;
						}
						this.board.putPieceAt(movingPiece, to);
						this.board.putPieceAt(null, from);
					}
					if (movingPiece.getPlayer() == Player.PLAYER2) {
						turns++;
					}
					isPlayer1Turn = !isPlayer1Turn;
					return true;
				}
				this.notifyObservers("Piece cannot move to location!");
				return false;
			case LINEAR:
				this.checkEndOfGame(movingPiece, player1Points, player2Points,
						turns);
				if (pathFinding.linearPathFinding(from, to, attributes)) {
					if (this.board.getLocationType(to) == LocationType.EXIT) {
						this.board.putPieceAt(null, from);
						this.addPlayerPoints(movingPiece, value);
					} else {
						if (this.canRemovePieces()) {
							if (this.checkCanCaputure(to, value)) {
								this.board.putPieceAt(movingPiece, to);
								this.board.putPieceAt(null, from);
							} else {
								this.board.putPieceAt(null, from);
							}
						} else {
							this.notifyObservers("Piece cannot move to location!");
							return false;
						}
						this.board.putPieceAt(movingPiece, to);
						this.board.putPieceAt(null, from);
					}
					if (movingPiece.getPlayer() == Player.PLAYER2) {
						turns++;
					}
					isPlayer1Turn = !isPlayer1Turn;
					return true;
				}
				this.notifyObservers("Piece cannot move to location!");
				return false;
			case OMNI:
				this.checkEndOfGame(movingPiece, player1Points, player2Points,
						turns);
				if (pathFinding.omniPathFinding(from, to, attributes)) {
					if (this.board.getLocationType(to) == LocationType.EXIT) {
						this.board.putPieceAt(null, from);
						this.addPlayerPoints(movingPiece, value);
					} else {
						if (this.canRemovePieces()) {
							if (this.checkCanCaputure(to, value)) {
								this.board.putPieceAt(movingPiece, to);
								this.board.putPieceAt(null, from);
							} else {
								this.board.putPieceAt(null, from);
							}
						} else {
							this.notifyObservers("Piece cannot move to location!");
							return false;
						}
						this.board.putPieceAt(movingPiece, to);
						this.board.putPieceAt(null, from);
					}
					if (movingPiece.getPlayer() == Player.PLAYER2) {
						turns++;
					}
					isPlayer1Turn = !isPlayer1Turn;
					return true;
				}
				this.notifyObservers("Piece cannot move to location!");
				return false;
			case ORTHOGONAL:
				this.checkEndOfGame(movingPiece, player1Points, player2Points,
						turns);
				if (pathFinding.orthogonalPathFinding(from, to, attributes)) {
					if (this.board.getLocationType(to) == LocationType.EXIT) {
						this.board.putPieceAt(null, from);
						this.addPlayerPoints(movingPiece, value);
					} else {
						if (this.canRemovePieces()) {
							if (this.checkCanCaputure(to, value)) {
								this.board.putPieceAt(movingPiece, to);
								this.board.putPieceAt(null, from);
							} else {
								this.board.putPieceAt(null, from);
							}
						} else {
							this.notifyObservers("Piece cannot move to location!");
							return false;
						}
						this.board.putPieceAt(movingPiece, to);
						this.board.putPieceAt(null, from);
					}
					if (movingPiece.getPlayer() == Player.PLAYER2) {
						turns++;
					}
					isPlayer1Turn = !isPlayer1Turn;
					return true;
				}
				this.notifyObservers("Piece cannot move to location!");
				return false;
			default:
				this.notifyObservers("Unknown movement pattern");
				return false;
		}
	}

	private boolean checkCanCaputure(SquareCoordinate to, int value) {
		int defendingPieceValue = 0;
		for (Rule rule : this.rules) {
			if (rule.getId() == RuleID.REMOVE) {
				return true;
			} else if (rule.getId() == RuleID.POINT_CONFLICT) {
				EscapePiece defendingPiece = this.board.getPieceAt(to);
				if (defendingPiece != null) {
					defendingPieceValue = this.getValue(defendingPiece);
					if (defendingPieceValue > value) {
						return false;
					} else if (defendingPieceValue < value) {
						return true;
					} else {
						this.board.putPieceAt(null, to);
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	private boolean canRemovePieces() {
		if (this.rules != null) {
			for (Rule rule : this.rules) {
				if (rule.getId() == RuleID.REMOVE
						|| rule.getId() == RuleID.POINT_CONFLICT) {
					return true;
				}
			}
		}
		return false;
	}

	private void checkEndOfGame(EscapePiece movingPiece, int player1Points2,
			int player2Points2, int turns2) {
		if (movingPiece.getPlayer() == Player.PLAYER2) {
			if (this.checkTurnLimit(turns)) {
				this.calculateWinner(player1Points2, player2Points2);
			} else if (this.checkPointLimit(player1Points, player2Points)) {
				this.calculateWinner(player1Points2, player2Points2);
			}
		}
	}

	private void calculateWinner(int player1Points2, int player2Points2) {
		if (player1Points > player2Points) {
			this.notifyObservers("Game ended! Player 1 has won!");
		} else if (player1Points < player2Points) {
			this.notifyObservers("Game ended! Player 2 has won!");
		} else {
			this.notifyObservers("Game ended! It is a tie!");
		}
	}

	private boolean checkPointLimit(int player1Points2, int player2Points2) {
		for (Rule rule : this.rules) {
			if (rule.getId() == RuleID.SCORE) {
				if (rule.getIntValue() <= turns) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkTurnLimit(int turns) {
		for (Rule rule : this.rules) {
			if (rule.getId() == RuleID.TURN_LIMIT) {
				if (rule.getIntValue() <= turns) {
					return true;
				}
			}
		}
		return false;
	}

	public GameObserver addGameObserver(GameObserver observer) {
		this.observers.add(observer);
		return observer;
	}

	public GameObserver removeObserver(GameObserver observer) {
		this.observers.remove(observer);
		return observer;
	}

	private void notifyObservers(String message) {
		if (!this.observers.isEmpty()) {
			this.observers.forEach((observer) -> observer.notify(message));
		}
	}

	private int getValue(EscapePiece movingPiece) {
		for (PieceTypeInitializer pieceType : pieceTypes) {
			if (pieceType.getPieceName() == movingPiece.getName()) {
				for (PieceAttribute attribute : pieceType.getAttributes()) {
					if (attribute.getId() == PieceAttributeID.VALUE) {
						return attribute.getIntValue();
					}
				}
			}
		}
		this.notifyObservers("Piece does not have a value");
		return 0;
	}

	private void addPlayerPoints(EscapePiece movingPiece, int value) {
		if (movingPiece.getPlayer() == Player.PLAYER1) {
			this.player1Points += value;
		} else {
			this.player2Points += value;
		}
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
}
