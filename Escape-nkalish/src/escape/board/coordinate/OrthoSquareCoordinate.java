/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape.board.coordinate;

import java.util.Objects;

/**
 * Description
 * @version Apr 8, 2020
 */
public class OrthoSquareCoordinate implements Coordinate{
    private final int x;
    private final int y;

    private OrthoSquareCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /*
     * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
     */
    @Override
    public int distanceTo(Coordinate c) {
    	OrthoSquareCoordinate coord = (OrthoSquareCoordinate) c;
    	int distance = 0;
    	distance += (coord.getX() - this.getX());
    	distance += (coord.getY() - this.getY());
        return distance;
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
        if (!(obj instanceof OrthoSquareCoordinate)) {
            return false;
        }
        OrthoSquareCoordinate other = (OrthoSquareCoordinate) obj;
        return x == other.x && y == other.y;
    }
    
    public static OrthoSquareCoordinate makeCoordinate(int x, int y) {
        return new OrthoSquareCoordinate(x,y);
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }

}
