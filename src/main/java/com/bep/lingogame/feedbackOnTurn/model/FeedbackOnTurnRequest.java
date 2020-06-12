package com.bep.lingogame.feedbackOnTurn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackOnTurnRequest {
    private int gameRound_id;
}
