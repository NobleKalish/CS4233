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

package escape.board;

import static escape.board.LocationType.CLEAR;
import escape.board.coordinate.SquareCoordinate;
import escape.piece.EscapePiece;
import escape.util.*;

/**
 * Description
 * @version Apr 8, 2020
 */
public enum Boards implements BoardRules {
    SQUAREBOARD(bi -> {
        SquareBoard sb = new SquareBoard(bi.getxMax(), bi.getyMax());
        addLocations(sb, bi.getLocationInitializers());
        return sb;
    }),
    ORTHOBOARD(bi -> {
        OrthoSquareBoard ob = new OrthoSquareBoard(bi.getxMax(), bi.getyMax());
        addLocations(ob, bi.getLocationInitializers());
        return ob;
    });
    
    private BoardRules initalizer;
    
    /**
     * Description
     * @param object
     */
    Boards(BoardRules initalizer) {
        this.initalizer = initalizer;
    }
    
    /*
     * @see escape.board.BoardRules#initalizeBoard(escape.util.BoardInitializer)
     */
    @Override
    public Board initalizeBoard(BoardInitializer bi) {
        return this.initalizer.initalizeBoard(bi);
    }

    private static void addLocations(Board b, LocationInitializer... initializers) {
        for (LocationInitializer li : initializers) {
            SquareCoordinate c = SquareCoordinate.makeCoordinate(li.x, li.y);
            if (li.pieceName != null) {
                b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
            }

            if (li.locationType != null && li.locationType != CLEAR) {
                b.setLocationType(c, li.locationType);
            }
        }
    }
}
