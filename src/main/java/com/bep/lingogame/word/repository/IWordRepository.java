package com.bep.lingogame.word.repository;

import com.bep.lingogame.word.model.Word;

public interface IWordRepository {
    Word findWordByLength(int lengthWord);
    Word findWordById(int word_id);
}
