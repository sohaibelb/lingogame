package com.bep.lingogame.game.service;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.game.model.Game;
import com.bep.lingogame.game.model.GameRequest;
import com.bep.lingogame.game.repository.GameRepository;
import com.bep.lingogame.game.repository.IGameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final IGameRepository iGameRepository;

    public GameService(GameRepository gameRepository) {
        this.iGameRepository = gameRepository;
    }

    public int createGame(Account accountOfUser) {
        //Make a new game for the user
        GameRequest gameRequest = new GameRequest(0, accountOfUser.getUser_id());

        return iGameRepository.create(gameRequest);
    }

    public Game getGameByUser_id(int user_id) {
        return iGameRepository.findByUser_id(user_id);
    }

    public Game getHighScoreOfUser(int user_id) {
        return iGameRepository.getHighScoreOfUser(user_id);
    }

    public int saveScore(int pressNumber, int gameId) {
        //Getting the game id of the gameround
        return iGameRepository.saveScore(pressNumber, gameId);
    }
}
