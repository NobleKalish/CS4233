/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package escape.board.coordinate;

import java.util.Objects;

/**
 * This is an example of how a SquareCoordinate might be organized.
 * 
 * @version Mar 27, 2020
 */
public class SquareCoordinate implements Coordinate {
    private final int x;
    private final int y;

    private SquareCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new SquareCoordinate
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return a new SquareCoordinate
     */
    public static SquareCoordinate makeCoordinate(int x, int y) {
        return new SquareCoordinate(x, y);
    }

    /*
     * @see
     * escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
     */
    @Override
    public int distanceTo(Coordinate c) {
        SquareCoordinate to = (SquareCoordinate) c;
        int xDistance = Math.abs(to.getX() - this.getX());
        int yDistance = Math.abs(to.getY() - this.getY());
        int distance = 0;
        while (xDistance != 0 && yDistance !=0) {
            distance++;
            xDistance--;
            yDistance--;
        }
        distance += xDistance;
        distance += yDistance;
        return distance;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
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
        if (!(obj instanceof SquareCoordinate)) {
            return false;
        }
        SquareCoordinate other = (SquareCoordinate) obj;
        return x == other.x && y == other.y;
    }

    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
