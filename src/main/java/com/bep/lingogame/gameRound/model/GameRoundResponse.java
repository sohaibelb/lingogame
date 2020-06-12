package com.bep.lingogame.gameRound.model;

import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurn;
import com.bep.lingogame.word.model.Word;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRoundResponse {
    private int gameRound_id;
    private int game_id;
    private Word word;
    private ArrayList<FeedbackOnTurn> feedbackOnTurns;

    public GameRoundResponse(int gameRound_id, int game_id, Word word) {
        this.gameRound_id = gameRound_id;
        this.game_id = game_id;
        this.word = word;
    }
}
