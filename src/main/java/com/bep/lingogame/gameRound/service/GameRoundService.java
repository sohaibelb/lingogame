package com.bep.lingogame.gameRound.service;

import com.bep.lingogame.game.model.Game;
import com.bep.lingogame.game.service.GameService;
import com.bep.lingogame.gameRound.model.GameRound;
import com.bep.lingogame.gameRound.repository.GameRoundRepository;
import com.bep.lingogame.gameRound.repository.IGameRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameRoundService {
    private final IGameRoundRepository iGameRoundRepository;
    private final GameService gameService;

    public GameRoundService(GameRoundRepository gameRoundRepository, GameService gameService) {
        this.iGameRoundRepository = gameRoundRepository;
        this.gameService = gameService;
    }

    public int createGameRound(GameRound gameRound) {
        return iGameRoundRepository.create(gameRound);
    }

    public int findGameRoundId() {
        return iGameRoundRepository.findGameRoundId();
    }

    public GameRound findGameRoundById(int gameRound_id) {
        return iGameRoundRepository.findById(gameRound_id);
    }

    public GameRound getLatestOfGameRoundOfGameUser(int userId) {
        //Get the game of the user
        Game game = gameService.getGameByUser_id(userId);
        return iGameRoundRepository.getLatestOfGame(game.getGameId());
    }

    public int getGameIdOfGameRound(int gameRoundId) {
        GameRound gameRound = iGameRoundRepository.findById(gameRoundId);
        return gameRound.getGameId();
    }
}
