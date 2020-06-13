package com.bep.lingogame.game.service;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.account.service.AccountService;
import com.bep.lingogame.game.model.Game;
import com.bep.lingogame.game.model.GameRequest;
import com.bep.lingogame.game.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GameServiceTest {

    @Mock
    private GameRepository mockGameRepository;
    @Mock
    private AccountService mockAccountService;

    private GameService gameServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        gameServiceUnderTest = new GameService(mockGameRepository, mockAccountService);
    }

    @Test
    void testCreateGame() {
        final Account accountOfUser = new Account(0, "username", "password");
        when(mockGameRepository.create(new GameRequest(0, 0))).thenReturn(0);

        final int result = gameServiceUnderTest.createGame(accountOfUser);

        assertEquals(0, result);
    }

    @Test
    void testGetGameByUser_id() {
        final Game expectedResult = new Game(0, 0, 0);
        when(mockGameRepository.findByUser_id(0)).thenReturn(new Game(0, 0, 0));

        final Game result = gameServiceUnderTest.getGameByUser_id(0);

        assertEquals(expectedResult, result);
    }

    @Test
    void testGetHighScoreOfUser() {
        // Setup
        final Game expectedResult = new Game(0, 0, 0);
        when(mockAccountService.getAccountByUsername("username")).thenReturn(new Account(0, "username", "password"));
        when(mockGameRepository.findHighScoreOfUser(0)).thenReturn(new Game(0, 0, 0));

        final Game result = gameServiceUnderTest.getHighScoreOfUser("username");

        assertEquals(expectedResult, result);
    }

    @Test
    void testSaveScore() {
        when(mockGameRepository.saveScore(0, 0)).thenReturn(0);

        final int result = gameServiceUnderTest.saveScore(0, 0);

        assertEquals(0, result);
    }
}
