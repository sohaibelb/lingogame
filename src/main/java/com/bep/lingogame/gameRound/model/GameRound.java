package com.bep.lingogame.gameRound.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRound {
    private int gameRound_id;
    private int game_id;
    private int word_id;
}
