/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape.board.coordinate;

import java.util.Objects;
import java.math.*;

/**
 * Description
 * 
 * @version Apr 17, 2020
 */
public class HexCoordinate implements Coordinate {
	private final int x;
	private final int y;

	private HexCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * @see
	 * escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c) {
		HexCoordinate coord = (HexCoordinate) c;
		/*
		 * Algorithm taken from Redblobgames at url:
		 * https://www.redblobgames.com/grids/hexagons/#distances
		 */
		return (Math.abs(this.getX() - coord.getX())
				+ Math.abs(this.x + this.y - coord.getX() - coord.getY())
				+ Math.abs(this.y - coord.getY())) / 2;
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof HexCoordinate)) {
			return false;
		}
		HexCoordinate other = (HexCoordinate) obj;
		return x == other.x && y == other.y;
	}

	public static HexCoordinate makeCoordinate(int x, int y) {
		return new HexCoordinate(x, y);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
