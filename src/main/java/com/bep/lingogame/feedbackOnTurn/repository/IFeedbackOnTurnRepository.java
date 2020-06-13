package com.bep.lingogame.feedbackOnTurn.repository;


import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurn;
import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurnRequest;

import java.util.List;

public interface IFeedbackOnTurnRepository {
    int create(FeedbackOnTurnRequest feedbackOnTurnRequest);
    List<FeedbackOnTurn> getByGameRound(int gameRoundId);
}
