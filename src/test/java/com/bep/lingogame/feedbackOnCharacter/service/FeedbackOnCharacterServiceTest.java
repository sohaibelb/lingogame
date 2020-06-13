package com.bep.lingogame.feedbackOnCharacter.service;

import com.bep.lingogame.feedbackOnCharacter.model.FeedbackOnCharacter;
import com.bep.lingogame.feedbackOnCharacter.repository.FeedbackOnCharacterRepository;
import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurn;
import com.bep.lingogame.word.model.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class FeedbackOnCharacterServiceTest {

    @Mock
    private FeedbackOnCharacterRepository mockFeedbackOnCharacterRepository;

    private FeedbackOnCharacterService feedbackOnCharacterServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        feedbackOnCharacterServiceUnderTest = new FeedbackOnCharacterService(mockFeedbackOnCharacterRepository);
    }

    public static Stream<Arguments> validArgs() {
        return Stream.of(
                Arguments.of("baard", new ArrayList<>(Arrays.asList( //A guess that should be full correct
                        new FeedbackOnCharacter(0, 0, "b", "Correct"),
                        new FeedbackOnCharacter(1, 0, "a", "Correct"),
                        new FeedbackOnCharacter(2, 0, "a", "Correct"),
                        new FeedbackOnCharacter(3, 0, "r", "Correct"),
                        new FeedbackOnCharacter(4, 0, "d", "Correct")
                        ))),
                Arguments.of("zeker", new ArrayList<>(Arrays.asList( //A guess that should be invalid
                        new FeedbackOnCharacter(0, 0, "z", "Invalid"),
                        new FeedbackOnCharacter(1, 0, "e", "Invalid"),
                        new FeedbackOnCharacter(2, 0, "k", "Invalid"),
                        new FeedbackOnCharacter(3, 0, "e", "Invalid"),
                        new FeedbackOnCharacter(4, 0, "r", "Invalid")
                        ))),
                Arguments.of("buaaa", new ArrayList<>(Arrays.asList( //A gues that have multiple feedbacks
                        new FeedbackOnCharacter(0, 0, "b", "Correct"),
                        new FeedbackOnCharacter(1, 0, "u", "Absent"),
                        new FeedbackOnCharacter(2, 0, "a", "Correct"),
                        new FeedbackOnCharacter(3, 0, "a", "Present"),
                        new FeedbackOnCharacter(4, 0, "a", "Absent")
                        )))

                );
    }

    @ParameterizedTest
    @MethodSource({ "validArgs" })
    public void testCalculate(String guess, ArrayList<FeedbackOnCharacter> feedbackOnCharacterArrayList) {
        final Word word = new Word(0, "baard");
        final FeedbackOnTurn feedbackOnTurn = new FeedbackOnTurn(0, 0, feedbackOnCharacterArrayList);
        final List<FeedbackOnCharacter> expectedResult = feedbackOnCharacterArrayList;

        when(mockFeedbackOnCharacterRepository.findFeedbackOnCharacterId()).thenReturn(0);
        when(mockFeedbackOnCharacterRepository.create(new FeedbackOnCharacter(0, 0, "character", "feedback"))).thenReturn(0);

        final List<FeedbackOnCharacter> actualResult = feedbackOnCharacterServiceUnderTest.checkWord(word, guess, feedbackOnTurn);

        assertEquals(actualResult, expectedResult);
    }

}
