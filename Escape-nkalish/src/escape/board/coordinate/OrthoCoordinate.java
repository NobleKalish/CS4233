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

/**
 * Description
 * @version Apr 8, 2020
 */
public class OrthoCoordinate implements Coordinate{
    private final int x;
    private final int y;

    private OrthoCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /*
     * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
     */
    @Override
    public int distanceTo(Coordinate c) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    public static OrthoCoordinate makeCoordinate(int x, int y) {
        return new OrthoCoordinate(x,y);
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }

}
