package com.bep.lingogame.controller;

import com.bep.lingogame.feedbackOnCharacter.model.FeedbackOnCharacter;
import com.bep.lingogame.feedbackOnCharacter.service.FeedbackOnCharacterService;
import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurn;
import com.bep.lingogame.gameRound.model.GameRound;
import com.bep.lingogame.gameRound.service.GameRoundService;
import com.bep.lingogame.word.model.Word;
import com.bep.lingogame.word.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private GameRoundService gameRoundService;

    @Autowired
    private WordService wordService;

    @Autowired
    private FeedbackOnCharacterService feedbackOnCharacterService;

    @PostMapping("/{guess}")
    public Object checkWord(@PathVariable String guess, @RequestBody FeedbackOnTurn feedbackOnTurn) {
        //Get the to be guessed word
        GameRound gameRound = gameRoundService.findGameRoundById(feedbackOnTurn.getGameRoundId());
        Word word = wordService.getWordByWordId(gameRound.getWordId());

        //Check if guessed word is longer than the to be guessed word
        if (!wordService.checkWordLength(guess, word)) {
            return new Error("Guesses must be " + word.getName().length() + " letters!");
        }

        //Checking the word
        List<FeedbackOnCharacter> feedbackOnCharacters = feedbackOnCharacterService.checkWord(word, guess, feedbackOnTurn);

        //Adding the feedback of the characters to the turn feedback
        feedbackOnTurn.setFeedbackOnCharacterArrayList((ArrayList<FeedbackOnCharacter>) feedbackOnCharacters);

        return feedbackOnTurn;
    }
}
