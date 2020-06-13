package com.bep.lingogame.word.service;

import com.bep.lingogame.word.model.Word;
import com.bep.lingogame.word.repository.IWordRepository;
import com.bep.lingogame.word.repository.WordRepository;
import org.springframework.stereotype.Service;

@Service
public class WordService {
    private final IWordRepository iWordRepository;

    public WordService(WordRepository wordRepository) {
        this.iWordRepository = wordRepository;
    }

    public Word getWordByWordLength(int length) {
        return iWordRepository.findWordByLength(length);
    }

    public Word getWordByWordId(int wordId) {
        return iWordRepository.findWordById(wordId);
    }

    public boolean checkWordLength(String guessWord, Word word) {
        //Get length of word
        int lengthOfWord = word.getName().length();

        //Check if guessed word is longer than the to be guessed word
        if (guessWord.length() != lengthOfWord) {
            return false;
        }
        return true;
    }

    public Word getNewGameRoundWord(int wordId) {
        Word word = iWordRepository.findWordById(wordId);

        int wordLength = word.getName().length() + 1;

        //If last gameround wordlength was 7 then reset to 5
        if (wordLength == 8) {
            wordLength = 5;
        }

        return iWordRepository.findWordByLength(wordLength);
    }
}
