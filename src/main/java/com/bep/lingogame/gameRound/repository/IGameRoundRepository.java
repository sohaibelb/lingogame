package com.bep.lingogame.gameRound.repository;

import com.bep.lingogame.gameRound.model.GameRound;

public interface IGameRoundRepository {
        int create(GameRound gameRound);
        GameRound findById(int gameRound_id);
        int findGameRoundId();
        GameRound getLatestOfGame(int game_id);
}
