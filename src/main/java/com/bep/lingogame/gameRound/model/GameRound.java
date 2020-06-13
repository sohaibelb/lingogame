package com.bep.lingogame.gameRound.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRound {
    private int gameRoundId;
    private int gameId;
    private int wordId;
}
