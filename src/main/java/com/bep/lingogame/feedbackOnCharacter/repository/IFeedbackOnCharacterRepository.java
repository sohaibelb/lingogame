package com.bep.lingogame.feedbackOnCharacter.repository;

import com.bep.lingogame.feedbackOnCharacter.model.FeedbackOnCharacter;

public interface IFeedbackOnCharacterRepository {
    int create(FeedbackOnCharacter feedbackOnCharacter);
    int findFeedbackOnCharacterId();
}
