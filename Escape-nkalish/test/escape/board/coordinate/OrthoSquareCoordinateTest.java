/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape.board.coordinate;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * Tests for various coordinates
 * 
 * @version Mar 28, 2020
 */
class OrthoSquareCoordinateTest {
    
    @ParameterizedTest
    @MethodSource("orthoSquareCoordinateDistanceProvider")
    public void testOrthoCoordinateDistance(OrthoSquareCoordinate start, OrthoSquareCoordinate end,
            int expectedDistance) {
        assertEquals(start.distanceTo(end), expectedDistance);
    }

    static Stream<Arguments> orthoSquareCoordinateDistanceProvider() {
        return Stream.of(
                Arguments.of(OrthoSquareCoordinate.makeCoordinate(1, 1),
                        OrthoSquareCoordinate.makeCoordinate(2, 2), 2),
                Arguments.of(OrthoSquareCoordinate.makeCoordinate(1, 2),
                        OrthoSquareCoordinate.makeCoordinate(3, 5), 5),
                Arguments.of(OrthoSquareCoordinate.makeCoordinate(1, 2),
                        OrthoSquareCoordinate.makeCoordinate(5, 5), 7));
    }

}
