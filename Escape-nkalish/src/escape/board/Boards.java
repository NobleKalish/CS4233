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
import escape.board.coordinate.*;
import escape.piece.EscapePiece;
import escape.util.*;

/**
 * Description
 * @version Apr 8, 2020
 */
public enum Boards implements BoardRules {
    SQUAREBOARD(bi -> {
        SquareBoard sb = new SquareBoard(bi.getxMax(), bi.getyMax());
        
        for (LocationInitializer li : bi.getLocationInitializers()) {
            SquareCoordinate c = SquareCoordinate.makeCoordinate(li.x, li.y);
            if (li.pieceName != null) {
                sb.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
            }
            if (li.locationType != null && li.locationType != CLEAR) {
                sb.setLocationType(c, li.locationType);
            }
        }        
        return sb;
    }, CoordinateID.SQUARE),
    
    ORTHOBOARD(bi -> {
        OrthoSquareBoard ob = new OrthoSquareBoard(bi.getxMax(), bi.getyMax());
        
        for (LocationInitializer li : bi.getLocationInitializers()) {
            OrthoCoordinate c = OrthoCoordinate.makeCoordinate(li.x, li.y);
            if (li.pieceName != null) {
                ob.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
            }
            if (li.locationType != null && li.locationType != CLEAR) {
                ob.setLocationType(c, li.locationType);
            }
        }        
        return ob;
    }, CoordinateID.ORTHOSQUARE);
    
    private BoardRules initalizer;
    private CoordinateID id;
    
    /**
     * Description
     * @param object
     */
    Boards(BoardRules initalizer, CoordinateID id) {
        this.initalizer = initalizer;
        this.id = id;
    }
    
    /*
     * @see escape.board.BoardRules#initalizeBoard(escape.util.BoardInitializer)
     */
    @Override
    public Board initalizeBoard(BoardInitializer bi) {
        return this.initalizer.initalizeBoard(bi);
    }
    
    public CoordinateID getID() {
        return this.id;
    }
}
