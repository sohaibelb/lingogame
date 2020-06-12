package com.bep.lingogame.gameRound.service;

import com.bep.lingogame.gameRound.model.GameRound;
import com.bep.lingogame.gameRound.repository.GameRoundRepository;
import com.bep.lingogame.gameRound.repository.IGameRoundRepository;
import org.springframework.stereotype.Service;


@Service
public class GameRoundService {
    private final IGameRoundRepository iGameRoundRepository;

    public GameRoundService(GameRoundRepository gameRoundRepository) {
        this.iGameRoundRepository = gameRoundRepository;
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

    public GameRound getLatestOfGameRoundOfGame(int game_id) {
        return iGameRoundRepository.getLatestOfGame(game_id);
    }

    public int getGameIdOfGameRound(int gameRoundId) {
        GameRound gameRound = iGameRoundRepository.findById(gameRoundId);
        return gameRound.getGame_id();
    }
}
