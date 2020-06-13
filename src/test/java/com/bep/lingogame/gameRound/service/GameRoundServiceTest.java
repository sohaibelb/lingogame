package com.bep.lingogame.gameRound.service;

import com.bep.lingogame.game.model.Game;
import com.bep.lingogame.game.service.GameService;
import com.bep.lingogame.gameRound.model.GameRound;
import com.bep.lingogame.gameRound.repository.GameRoundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GameRoundServiceTest {

    @Mock
    private GameRoundRepository mockGameRoundRepository;
    @Mock
    private GameService mockGameService;

    private GameRoundService gameRoundServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        gameRoundServiceUnderTest = new GameRoundService(mockGameRoundRepository, mockGameService);
    }

    @Test
    void testCreateGameRound() {
        final GameRound gameRound = new GameRound(0, 0, 0);
        when(mockGameRoundRepository.create(new GameRound(0, 0, 0))).thenReturn(0);

        final int result = gameRoundServiceUnderTest.createGameRound(gameRound);

        assertEquals(0, result);
    }

    @Test
    void testFindGameRoundId() {
        when(mockGameRoundRepository.findGameRoundId()).thenReturn(0);

        final int result = gameRoundServiceUnderTest.findGameRoundId();

        assertEquals(0, result);
    }

    @Test
    void testFindGameRoundById() {
        final GameRound expectedResult = new GameRound(0, 0, 0);
        when(mockGameRoundRepository.findById(0)).thenReturn(new GameRound(0, 0, 0));

        final GameRound result = gameRoundServiceUnderTest.findGameRoundById(0);

        assertEquals(expectedResult, result);
    }

    @Test
    void testGetLatestOfGameRoundOfGameUser() {
        final GameRound expectedResult = new GameRound(0, 0, 0);
        when(mockGameService.getGameByUser_id(0)).thenReturn(new Game(0, 0, 0));
        when(mockGameRoundRepository.getLatestOfGame(0)).thenReturn(new GameRound(0, 0, 0));

        final GameRound result = gameRoundServiceUnderTest.getLatestOfGameRoundOfGameUser(0);

        assertEquals(expectedResult, result);
    }

    @Test
    void testGetGameIdOfGameRound() {
        when(mockGameRoundRepository.findById(0)).thenReturn(new GameRound(0, 0, 0));

        final int result = gameRoundServiceUnderTest.getGameIdOfGameRound(0);

        assertEquals(0, result);
    }
}
