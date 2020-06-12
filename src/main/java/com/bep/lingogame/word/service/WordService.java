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

    public Word getWordByWordId(int word_id) {
        return iWordRepository.findWordById(word_id);
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
}
