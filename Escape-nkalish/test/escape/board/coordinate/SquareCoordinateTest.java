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

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * Description
 * @version Apr 17, 2020
 */
public class SquareCoordinateTest {
	
	@ParameterizedTest
    @MethodSource("squareCoordinateDistanceProvider")
    public void testCoordinateDistance(SquareCoordinate start, SquareCoordinate end,
            int expectedDistance) {
        assertEquals(start.distanceTo(end), expectedDistance);
    }

    static Stream<Arguments> squareCoordinateDistanceProvider() {
        return Stream.of(
                Arguments.of(SquareCoordinate.makeCoordinate(1, 1),
                        SquareCoordinate.makeCoordinate(2, 2), 1),
                Arguments.of(SquareCoordinate.makeCoordinate(1, 2),
                        SquareCoordinate.makeCoordinate(3, 5), 3),
                Arguments.of(SquareCoordinate.makeCoordinate(1, 2),
                        SquareCoordinate.makeCoordinate(5, 5), 4));
    }

}
