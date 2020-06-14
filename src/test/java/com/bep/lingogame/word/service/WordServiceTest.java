package com.bep.lingogame.word.service;

import com.bep.lingogame.word.model.Word;
import com.bep.lingogame.word.repository.WordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class WordServiceTest {

    @Mock
    private WordRepository mockWordRepository;

    private WordService wordServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        wordServiceUnderTest = new WordService(mockWordRepository);
    }

    @Test
    void testGetWordByWordLength() {
        final Word expectedResult = new Word(0, "name");
        when(mockWordRepository.findWordByLength(0)).thenReturn(new Word(0, "name"));

        final Word result = wordServiceUnderTest.getWordByWordLength(0);

        assertEquals(expectedResult, result);
    }

    @Test
    void testGetWordByWordId() {
        final Word expectedResult = new Word(0, "name");
        when(mockWordRepository.findWordById(0)).thenReturn(new Word(0, "name"));

        final Word result = wordServiceUnderTest.getWordByWordId(0);

        assertEquals(expectedResult, result);
    }

    //Setting up for checking the regex
    public static Stream<Arguments> argsRegexCheck() {
        return Stream.of(
                Arguments.of("baard", true), //correct word, 5 letters
                Arguments.of("bedden", true), //correct word, 6 letters
                Arguments.of("schrift", true), //correct word, 7 letters
                Arguments.of("bËdden", false), //incorrect word , has Ë
                Arguments.of("sp-elen",false), //incorrect word, has -
                Arguments.of("tak",false),//incorrect word, shorter than 5 letters
                Arguments.of("superlangewoord",false) //incorrect word, longer than 7 letters
        );
    }

    //Checking if the regex works for the given words
    @ParameterizedTest
    @MethodSource({"argsRegexCheck"})
    void testCheckWordRegex(String guessWord, boolean expectedResult) {
        final boolean actualResult = wordServiceUnderTest.checkWordRegex(guessWord);

         assertEquals(actualResult, expectedResult);
    }

    //The follow two tests are testing the function: checkWordLength()
    @Test
    void testCheckCorrectWordLength() { //A Word with a correct length
        final Word word = new Word(0, "words");

        final boolean testCorrectLengthWord = wordServiceUnderTest.checkWordLength("guess", word);

        assertTrue(testCorrectLengthWord);
    }

    @Test
    void testCheckWrongWordLength() { //A Word with an incorrect length
        final Word word = new Word(0, "words");

        final boolean testWrongLengthWord = wordServiceUnderTest.checkWordLength("guessWord", word);

        assertFalse(testWrongLengthWord);
    }

    @Test
    void testGetNewGameRoundWord() {
        final Word expectedResult = new Word(1, "words");
        when(mockWordRepository.findWordById(0)).thenReturn(new Word(0, "name"));
        when(mockWordRepository.findWordByLength(5)).thenReturn(new Word(1, "words"));

        final Word result = wordServiceUnderTest.getNewGameRoundWord(0);

        assertEquals(expectedResult, result);
    }

    public static Stream<Arguments> validArgs() {
        return Stream.of(
                Arguments.of(new Word(0, "guess"), new Word(3, "bedden")),//a word with 5 letters, will expect a word with 6 letters
                Arguments.of(new Word(1, "bedden"), new Word(4, "schrift")),// .. 6 letters > 7
                Arguments.of(new Word(2, "schrift"), new Word(5, "guess")) // .. 7 letters > a word with 5 letters
        );
    }

    @ParameterizedTest
    @MethodSource({"validArgs"})
    public void testGetNewGameRoundWord(Word word, Word expectedWord) {
        when(mockWordRepository.findWordById(word.getWordId())).thenReturn(word);
        if (word.getName().length() == 7) {
            when(mockWordRepository.findWordByLength(5)).thenReturn(expectedWord);
        } else {
            when(mockWordRepository.findWordByLength(word.getName().length() + 1)).thenReturn(expectedWord);
        }

        final Word actualResult = wordServiceUnderTest.getNewGameRoundWord(word.getWordId());

        assertEquals(actualResult, expectedWord);
    }
}
