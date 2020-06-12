package com.bep.lingogame.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculateControllerTest {

    private CalculateController calculateControllerUnderTest;

    @BeforeEach
    void setUp() {
        calculateControllerUnderTest = new CalculateController();
    }

    @Test
    void testCalculate() {
        // Setup

        // Run the test
        final Integer result = calculateControllerUnderTest.calculate(0, 0);

        // Verify the results
        assertEquals(0, result);
    }

    public static Stream<Arguments> validArgs() {
        return Stream.of(
                Arguments.of(1, 2, 3),
                Arguments.of(1, 2, 3),
                Arguments.of(1, 2, 3),
                Arguments.of(1, 2, 3),
                Arguments.of(1, 2, 3)
        );
    }

    @ParameterizedTest
    @MethodSource({ "validArgs" })
    public void testCalculate(Integer a, Integer b, Integer expectedResult) {
        Integer actualResult = calculateControllerUnderTest.calculate(a, b);
        assertEquals(actualResult, expectedResult);
    }

}
