package com.bep.lingogame.feedbackOnTurn.service;

import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurn;
import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurnRequest;
import com.bep.lingogame.feedbackOnTurn.repository.FeedbackOnTurnRepository;
import com.bep.lingogame.feedbackOnTurn.repository.IFeedbackOnTurnRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackOnTurnService {
    private final IFeedbackOnTurnRepository iFeedbackOnTurnRepository;

    public FeedbackOnTurnService(FeedbackOnTurnRepository feedbackOnTurnRepository) {
        this.iFeedbackOnTurnRepository = feedbackOnTurnRepository;
    }

    public int createFeedbackOnTurn(int gameRoundId) {
        FeedbackOnTurnRequest feedbackOnTurnRequest = new FeedbackOnTurnRequest(gameRoundId);
        int result = 0;
        for (int i = 0; i < 5; i++) {
            result = iFeedbackOnTurnRepository.create(feedbackOnTurnRequest);
        }
        return result;
    }

    public List<FeedbackOnTurn> getFeedbackOnTurnsByGameRound(int gameRoundId) {
        return iFeedbackOnTurnRepository.getByGameRound(gameRoundId);
    }
}
