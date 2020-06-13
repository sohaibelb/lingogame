package com.bep.lingogame.game.service;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.account.service.AccountService;
import com.bep.lingogame.game.model.Game;
import com.bep.lingogame.game.model.GameRequest;
import com.bep.lingogame.game.repository.GameRepository;
import com.bep.lingogame.game.repository.IGameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final IGameRepository iGameRepository;
    private final AccountService accountService;

    public GameService(GameRepository gameRepository, AccountService accountService) {
        this.iGameRepository = gameRepository;
        this.accountService = accountService;
    }

    public int createGame(Account accountOfUser) {
        //Make a new game for the user
        GameRequest gameRequest = new GameRequest(0, accountOfUser.getUserId());

        return iGameRepository.create(gameRequest);
    }

    public Game getGameByUser_id(int userId) {
        return iGameRepository.findByUser_id(userId);
    }

    public Game getHighScoreOfUser(String username) {
        Account user = accountService.getAccountByUsername(username);
        return iGameRepository.findHighScoreOfUser(user.getUserId());
    }

    public int saveScore(int pressNumber, int gameId) {
        //Getting the game id of the gameround
        return iGameRepository.saveScore(pressNumber, gameId);
    }
}
