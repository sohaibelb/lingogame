package com.bep.lingogame.game.repository;

import com.bep.lingogame.game.model.Game;
import com.bep.lingogame.game.model.GameRequest;

public interface IGameRepository {
    int create(GameRequest gameRequest);
    Game findByUser_id(int user_id);
    Game getHighScoreOfUser(int user_id);
    int saveScore(int pressNumber, int gameId);
}
