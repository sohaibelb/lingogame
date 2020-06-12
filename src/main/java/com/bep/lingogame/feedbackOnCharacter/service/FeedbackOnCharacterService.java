package com.bep.lingogame.feedbackOnCharacter.service;

import com.bep.lingogame.feedbackOnCharacter.model.FeedbackOnCharacter;
import com.bep.lingogame.feedbackOnCharacter.repository.FeedbackOnCharacterRepository;
import com.bep.lingogame.feedbackOnCharacter.repository.IFeedbackOnCharacterRepository;
import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurn;
import com.bep.lingogame.word.model.Word;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackOnCharacterService {
    private final IFeedbackOnCharacterRepository iFeedbackOnCharacterRepository;

    public FeedbackOnCharacterService(FeedbackOnCharacterRepository feedbackOnCharacterRepository) {
        this.iFeedbackOnCharacterRepository = feedbackOnCharacterRepository;
    }

    public List<FeedbackOnCharacter> checkWord(Word word, String guess, FeedbackOnTurn feedbackOnTurn) {
        int feedbackOnCharacterId = iFeedbackOnCharacterRepository.findFeedbackOnCharacterId();

        //Converting the words
        char[] wordAsChar = word.getName().toCharArray();
        char[] guessAsChar = guess.toCharArray();

        //Making a list for all characters
        List<FeedbackOnCharacter> feedbackOnCharacterList = new ArrayList<FeedbackOnCharacter>();

        //Checking if the first letter is not correct
        if (guessAsChar[0] != wordAsChar[0]) {
            for (char l : guessAsChar) {
                //create a feedback with status invalid if its not correct
                FeedbackOnCharacter feedbackOnCharacter = new FeedbackOnCharacter(feedbackOnCharacterId, feedbackOnTurn.getFeedbackOnTurn_id(), Character.toString(l), "Invalid");
                feedbackOnCharacterList.add(feedbackOnCharacter);
                feedbackOnCharacterId++;
            }
            //If its invalid create the feedbacks in database
            for (FeedbackOnCharacter feedbackOnCharacter : feedbackOnCharacterList) {
                iFeedbackOnCharacterRepository.create(feedbackOnCharacter);
            }
            return feedbackOnCharacterList;

        } else {
            //continue with checking
            for (char l : guessAsChar) {
                //create a feedback with status absent
                FeedbackOnCharacter feedbackOnCharacter = new FeedbackOnCharacter(feedbackOnCharacterId, feedbackOnTurn.getFeedbackOnTurn_id(), Character.toString(l), "Absent");
                feedbackOnCharacterList.add(feedbackOnCharacter);
                feedbackOnCharacterId++;
            }
        }

        //List that contains characters that are already checked
        List<FeedbackOnCharacter> checkedFeedbackOnCharacters = new ArrayList<FeedbackOnCharacter>();

        //Searching for letters that are correct
        int i = 0;
        for (FeedbackOnCharacter feedbackOnCharacter : feedbackOnCharacterList) {
            if (wordAsChar[i] == feedbackOnCharacter.getCharacter().charAt(0)) {
                feedbackOnCharacter.setFeedback("Correct");
                checkedFeedbackOnCharacters.add(feedbackOnCharacter);
                wordAsChar[i] = 0;
            }
            i++;
        }

        //Searching for letters that are present
        for (char c : wordAsChar) {
            for (FeedbackOnCharacter feedbackOnCharacter : feedbackOnCharacterList) {
                if (feedbackOnCharacter.getCharacter().charAt(0) == c && feedbackOnCharacter.getFeedback() != "Correct" && !(checkedFeedbackOnCharacters.contains(feedbackOnCharacter))) {
                    //update the feedback with status present
                    feedbackOnCharacter.setFeedback("Present");
                    checkedFeedbackOnCharacters.add(feedbackOnCharacter);
                    c = 0;
                }
            }
        }

        //create the feedbacks in database
        for (FeedbackOnCharacter feedbackOnCharacter : feedbackOnCharacterList) {
            iFeedbackOnCharacterRepository.create(feedbackOnCharacter);
        }

        return feedbackOnCharacterList;
    }
}
