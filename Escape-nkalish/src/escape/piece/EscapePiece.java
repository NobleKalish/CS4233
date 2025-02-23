/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape.piece;

/**
 * This is a class for Pieces. You may change this class except for the signature of the
 * static factory method makePiece() and the getter methods for the name and player.
 * 
 * @version Mar 28, 2020
 */
public class EscapePiece {
	private final PieceName name;
	private final Player player;
	private int value;

	/**
	 * Constructor that takes the player and piece name.
	 * 
	 * @param player
	 * @param name
	 */
	public EscapePiece(Player player, PieceName name) {
		this.player = player;
		this.name = name;
	}

	/**
	 * Static factory method. This creates and returns the specified Escape piece for the
	 * current game version. DO NOT CHANGE THE SIGNATURE.
	 * 
	 * @param player
	 *            the player the piece belongs to
	 * @param name
	 *            the piee name
	 * @return the piece
	 */
	public static EscapePiece makePiece(Player player, PieceName name) {
		return new EscapePiece(player, name);
	}

	/**
	 * @return the name
	 */
	public PieceName getName() {
		return name;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EscapePiece other = (EscapePiece) obj;
		if (name != other.name)
			return false;
		if (player != other.player)
			return false;
		return true;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
