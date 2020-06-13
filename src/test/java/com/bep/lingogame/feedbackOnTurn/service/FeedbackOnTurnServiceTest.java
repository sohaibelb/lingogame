package com.bep.lingogame.feedbackOnTurn.service;

import com.bep.lingogame.feedbackOnCharacter.model.FeedbackOnCharacter;
import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurn;
import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurnRequest;
import com.bep.lingogame.feedbackOnTurn.repository.FeedbackOnTurnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class FeedbackOnTurnServiceTest {

    @Mock
    private FeedbackOnTurnRepository mockFeedbackOnTurnRepository;

    private FeedbackOnTurnService feedbackOnTurnServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        feedbackOnTurnServiceUnderTest = new FeedbackOnTurnService(mockFeedbackOnTurnRepository);
    }

    @Test
    void testCreateFeedbackOnTurn() {
        when(mockFeedbackOnTurnRepository.create(new FeedbackOnTurnRequest(0))).thenReturn(0);

        final int result = feedbackOnTurnServiceUnderTest.createFeedbackOnTurn(0);

        assertEquals(0, result);
    }

    @Test
    void testGetFeedbackOnTurnsByGameRound() {
        final List<FeedbackOnTurn> expectedResult = Arrays.asList(new FeedbackOnTurn(0, 0, new ArrayList<>(Arrays.asList(new FeedbackOnCharacter(0, 0, "character", "feedback")))));

        final List<FeedbackOnTurn> feedbackOnTurns = Arrays.asList(new FeedbackOnTurn(0, 0, new ArrayList<>(Arrays.asList(new FeedbackOnCharacter(0, 0, "character", "feedback")))));
        when(mockFeedbackOnTurnRepository.getByGameRound(0)).thenReturn(feedbackOnTurns);

        final List<FeedbackOnTurn> result = feedbackOnTurnServiceUnderTest.getFeedbackOnTurnsByGameRound(0);

        assertEquals(expectedResult, result);
    }
}
