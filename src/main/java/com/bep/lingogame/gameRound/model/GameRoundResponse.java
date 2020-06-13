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
    private int gameRoundId;
    private int gameId;
    private Word word;
    private ArrayList<FeedbackOnTurn> feedbackOnTurns;
}
