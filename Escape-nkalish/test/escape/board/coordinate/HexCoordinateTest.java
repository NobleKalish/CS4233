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

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * Description
 * @version Apr 17, 2020
 */
public class HexCoordinateTest {

	@ParameterizedTest
    @MethodSource("hexCoordinateDistanceProvider")
    public void testHexCoordinateDistance(HexCoordinate start, HexCoordinate end,
            int expectedDistance) {
        assertEquals(start.distanceTo(end), expectedDistance);
    }

    static Stream<Arguments> hexCoordinateDistanceProvider() {
        return Stream.of(
                Arguments.of(HexCoordinate.makeCoordinate(1, 1),
                        HexCoordinate.makeCoordinate(2, 2), 2),
                Arguments.of(HexCoordinate.makeCoordinate(1, 2),
                        HexCoordinate.makeCoordinate(3, 5), 5),
                Arguments.of(HexCoordinate.makeCoordinate(1, 2),
                        HexCoordinate.makeCoordinate(5, 5), 7),
        		Arguments.of(HexCoordinate.makeCoordinate(1, 2),
        				HexCoordinate.makeCoordinate(-5, -3), 11),
                Arguments.of(HexCoordinate.makeCoordinate(1, 2),
                        HexCoordinate.makeCoordinate(-5, 7), 6));
    }
    
    @Test
    public void testEquals() {
    	assertFalse(HexCoordinate.makeCoordinate(1, 1).equals(HexCoordinate.makeCoordinate(1, 3)));
    }
}
