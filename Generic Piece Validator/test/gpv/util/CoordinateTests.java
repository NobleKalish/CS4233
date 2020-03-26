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

package gpv.util;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static gpv.util.Coordinate.makeCoordinate;
import static org.junit.Assert.assertEquals;

/**
 * Description
 * @version Mar 25, 2020
 */
public class CoordinateTests {
    
    @ParameterizedTest
    @MethodSource("distanceProvider")
    void testDistanceMethod(Coordinate expected, Coordinate from, Coordinate to) {
        assertEquals(expected, from.distance(to));
    }
    
    // Helper Method
    static Stream<Arguments> distanceProvider() {
        return Stream.of(
                Arguments.of(makeCoordinate(0,0), makeCoordinate(1,1), makeCoordinate(1,1)),
                Arguments.of(makeCoordinate(1,0), makeCoordinate(1,1), makeCoordinate(2,1)),
                Arguments.of(makeCoordinate(0,1), makeCoordinate(1,1), makeCoordinate(1,2)),
                Arguments.of(makeCoordinate(-1,-1), makeCoordinate(2,2), makeCoordinate(1,1)),
                Arguments.of(makeCoordinate(2,1), makeCoordinate(1,2), makeCoordinate(3,3))
                );
    }
}
