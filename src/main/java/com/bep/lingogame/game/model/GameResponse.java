package com.bep.lingogame.game.model;

import com.bep.lingogame.gameRound.model.GameRoundResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResponse {
    private int game_id;
    private int score;
    private int user_id;
    private GameRoundResponse round;
}

