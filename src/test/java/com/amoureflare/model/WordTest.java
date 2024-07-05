package com.amoureflare.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordTest {

    @Test
    public void isEmpty() {
        // Arrange
        Word word1 = new Word("word");
        Word word2 = new Word("");

        // Act
        boolean isWord1Empty = word1.isEmpty();
        boolean isWord2Empty = word2.isEmpty();

        // Assert
        assertFalse(isWord1Empty);
        assertTrue(isWord2Empty);
    }

    @Test
    public void containsLetter() {
        // Arrange
        Word word = new Word("word");
        Character letter1 = 'd';
        Character letter2 = 's';

        // Act
        boolean isLetter1Containing = word.containsLetter(letter1);
        boolean isLetter2Containing = word.containsLetter(letter2);

        // Assert
        assertTrue(isLetter1Containing);
        assertFalse(isLetter2Containing);
    }

}
