package com.bep.lingogame.feedbackOnTurn.model;


import com.bep.lingogame.feedbackOnCharacter.model.FeedbackOnCharacter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackOnTurn {
    private int feedbackOnTurn_id;
    private int gameRound_id;
    private ArrayList<FeedbackOnCharacter> feedbackOnCharacterArrayList;

    public FeedbackOnTurn(int feedbackOnTurn_id, int gameRound_id) {
        this.feedbackOnTurn_id = feedbackOnTurn_id;
        this.gameRound_id = gameRound_id;
    }
}
