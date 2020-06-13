package com.bep.lingogame.feedbackOnCharacter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackOnCharacter {
    private int feedbackOnCharacterId;
    private int feedbackOnTurnId;
    private String character;
    private String feedback;
}
