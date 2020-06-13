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
    private int feedbackOnTurnId;
    private int gameRoundId;
    private ArrayList<FeedbackOnCharacter> feedbackOnCharacterArrayList;

    public FeedbackOnTurn(int feedbackOnTurnId, int gameRoundId) {
        this.feedbackOnTurnId = feedbackOnTurnId;
        this.gameRoundId = gameRoundId;
    }
}
