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

package gpv.chess;
import java.util.*;
import java.util.stream.Stream;
import gpv.util.*;
import static gpv.util.Coordinate.makeCoordinate;

/**
 * Description
 * @version Mar 25, 2020
 */
public enum PieceMovementRules implements PieceRules{
    WHITEPAWNRULES((Coordinate from, Coordinate to, Board board) -> {
        return (whitePawnExpectedDistance(from, to, board));
    }, ChessPieceDescriptor.WHITEPAWN),
    
    BLACKPAWNRULES((Coordinate from, Coordinate to, Board board) -> {
        return (blackPawnExpectedDistance(from, to, board));
    }, ChessPieceDescriptor.BLACKPAWN),
    
    KINGRULES((Coordinate from, Coordinate to, Board board) -> {
        return false;
    }, ChessPieceDescriptor.WHITEKING, ChessPieceDescriptor.BLACKKING),
        
    QUEENRULES((Coordinate from, Coordinate to, Board board) -> {
        return false;
    }, ChessPieceDescriptor.WHITEQUEEN, ChessPieceDescriptor.BLACKQUEEN),
    
    BISHOPRULES((Coordinate from, Coordinate to, Board board) -> {
        return false;
    }, ChessPieceDescriptor.WHITEBISHOP, ChessPieceDescriptor.BLACKBISHOP),
    
    KNIGHTRULES((Coordinate from, Coordinate to, Board board) -> {
        return false;
    }, ChessPieceDescriptor.WHITEKNIGHT, ChessPieceDescriptor.BLACKKNIGHT),
    
    ROOKRULES((Coordinate from, Coordinate to, Board board) -> {
        return false;
    }, ChessPieceDescriptor.WHITEROOK, ChessPieceDescriptor.BLACKROOK);
    
    private List<ChessPieceDescriptor> descriptors;
    private PieceRules rule;
    
    PieceMovementRules(PieceRules rule, ChessPieceDescriptor... descriptor) {
        this.descriptors = Arrays.asList(descriptor);
        this.rule = rule;
    }
    
    private static boolean isFirstMove(Coordinate distance, Coordinate from, Board board) {
        return (distance.getColumn() == 2 && distance.getRow() == 0 && (from.getRow() == 1 || from.getRow() == board.nRows-1));
    }
    
    private static boolean canWhitePawnAttack(Coordinate distance, Coordinate to, Board board) {
        return (distance.getColumn() == 1 && distance.getRow() == 1 && board.getPieceAt(to) != null);
    }

    private static boolean whitePawnExpectedDistance(Coordinate from, Coordinate to, Board board) {
        Coordinate distance = from.distance(to);
        return (distance.getColumn() == 1 && distance.getRow() == 0) || (canWhitePawnAttack(distance, to, board) || isFirstMove(distance, from, board));
    }
    
    private static boolean blackPawnExpectedDistance(Coordinate from, Coordinate to, Board board) {
        Coordinate distance = from.distance(to);
        return (distance.getColumn() == -1 && distance.getRow() == 0) || (canBlackPawnAttack(distance, to, board));
    }

    private static boolean canBlackPawnAttack(Coordinate distance, Coordinate to, Board board) {
        return (distance.getColumn() == -1 && distance.getRow() == -1 && board.getPieceAt(to) != null);

    }

    public PieceRules getRules() {
        return this.rule;
    }
    
    public List<ChessPieceDescriptor> getDescriptors() {
        return this.descriptors;
    }

    /*
     * @see gpv.chess.PieceRules#validMove(gpv.util.Coordinate, gpv.util.Coordinate, gpv.util.Board)
     */
    @Override
    public boolean validMove(Coordinate from, Coordinate to, Board board) {
        return this.rule.validMove(from, to, board);
    }
    
    public static Stream<PieceMovementRules> stream() {
        return Stream.of(PieceMovementRules.values());
    }
}
