package com.amoureflare.model;

import com.amoureflare.enums.GameStatus;
import com.amoureflare.enums.HangmanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void init() {
        game = new Game();
    }

    @Test
    public void addWisherShouldInitWisher() {
        // Arrange
        Wisher wisher = new Wisher("nickname");

        // Act
        game.addWisher(wisher);

        // Assert
        assertNotNull(game.getWisher());
        assertEquals(wisher, game.getWisher());
    }

    @Test
    public void addWisherWhenGameAlreadyStartedShouldThrow() {
        // Arrange
        Wisher wisher = new Wisher("nickname1");
        Guesser guesser = new Guesser("nickname2");
        wisher.requestStartGame(game, new Word("wish"));
        guesser.requestStartGame(game);
        game.start();

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            game.addWisher(wisher);
        });

        // Assert
        assertEquals("Game already started", exception.getMessage());
    }

    @Test
    public void addGuesserShouldInitGuesser() {
        // Arrange
        Guesser guesser = new Guesser("nickname");

        // Act
        game.addGuesser(guesser);

        // Assert
        assertNotNull(game.getGuesser());
        assertEquals(guesser, game.getGuesser());
    }

    @Test
    public void addGuesserWhenGameAlreadyStartedShouldThrow() {
        // Arrange
        Wisher wisher = new Wisher("nickname1");
        Guesser guesser = new Guesser("nickname2");
        wisher.requestStartGame(game, new Word("wish"));
        guesser.requestStartGame(game);
        game.start();

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            game.addGuesser(guesser);
        });

        // Assert
        assertEquals("Game already started", exception.getMessage());
    }

    @Test
    public void initWord() {
        // Arrange
        Wisher wisher = new Wisher("nickname1");
        Guesser guesser = new Guesser("nickname2");
        wisher.requestStartGame(game, new Word("wish"));
        guesser.requestStartGame(game);
        Word word = new Word("word");
        game.start();
        int wordLength = word.getWord().length();

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            game.initWord(word);
        });

        // Assert
        assertEquals("Game already started", exception.getMessage());
        assertNotNull(game.getGuessedWordPart());
        assertFalse(game.getGuessedWordPart().isEmpty());
        assertEquals(wordLength, game.getGuessedWordPart().getWord().length());
        assertEquals(Game.MISS_SYMBOL.toString().repeat(wordLength), game.getGuessedWordPart().getWord());
    }

    @Test
    public void initWordWhenGameStartedShouldThrow() {
        // Arrange
        Word word = new Word("word");

        // Act
        game.initWord(word);

        // Assert
        assertNotNull(game.getHiddenWord());
        assertEquals(word.toString(), game.getHiddenWord().toString());
    }

    @Test
    public void startShouldChangeGameState() {
        // Arrange
        Wisher wisher = new Wisher("nickname1");
        Guesser guesser = new Guesser("nickname2");
        wisher.requestStartGame(game, new Word("wish"));
        guesser.requestStartGame(game);

        // Act
        game.start();

        // Assert
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertFalse(game.isNotStarted());
    }

    @Test
    public void startWhenGameAlreadyStartedShouldThrow() {
        // Arrange
        Wisher wisher = new Wisher("nickname1");
        Guesser guesser = new Guesser("nickname2");
        wisher.requestStartGame(game, new Word("wish"));
        guesser.requestStartGame(game);
        game.start();

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            game.start();
        });

        // Assert
        assertEquals("Game already started", exception.getMessage());
    }

    @Test
    public void startWhenNotGotAllRequestsShouldThrow() {
        // Arrange
        Wisher wisher = new Wisher("nickname1");
        wisher.requestStartGame(game, new Word("wish"));

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            game.start();
        });

        // Assert
        assertEquals("Game is not ready to start", exception.getMessage());
    }

    @Test
    public void startWhenNotInitHiddenWordShouldThrow() {
        // Arrange
        Guesser guesser = new Guesser("nickname2");
        guesser.requestStartGame(game);

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            game.start();
        });

        // Assert
        assertEquals("Game is not ready to start", exception.getMessage());
    }

    @Test
    public void guessLetter() {
        // Arrange
        Wisher wisher = new Wisher("nickname1");
        Guesser guesser = new Guesser("nickname2");
        wisher.requestStartGame(game, new Word("wish"));
        guesser.requestStartGame(game);
        game.start();


        // Act
        game.guessLetter('w');

        // Assert
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("w___", game.getGuessedWordPart().getWord());
        assertTrue(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.EMPTY, game.getHangmanStatus());
    }

    @Test
    public void guessLetterWhenNotGuessedShouldSaveGuessedWordAndChangeState() {
        // Arrange
        Wisher wisher = new Wisher("nickname1");
        Guesser guesser = new Guesser("nickname2");
        wisher.requestStartGame(game, new Word("wish"));
        guesser.requestStartGame(game);
        game.start();


        // Act
        game.guessLetter('d');

        // Assert
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("____", game.getGuessedWordPart().getWord());
        assertFalse(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.HEAD, game.getHangmanStatus());
    }

    @Test
    public void guessLetterWhenLostAllAttemptsShouldEndGame() {
        // Arrange
        Wisher wisher = new Wisher("nickname1");
        Guesser guesser = new Guesser("nickname2");
        wisher.requestStartGame(game, new Word("wish"));
        guesser.requestStartGame(game);
        game.start();


        // Act & Assert
        game.guessLetter('d');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("____", game.getGuessedWordPart().getWord());
        assertFalse(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.HEAD, game.getHangmanStatus());

        game.guessLetter('d');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("____", game.getGuessedWordPart().getWord());
        assertFalse(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.LEFT_HAND, game.getHangmanStatus());

        game.guessLetter('d');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("____", game.getGuessedWordPart().getWord());
        assertFalse(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.RIGHT_HAND, game.getHangmanStatus());


        game.guessLetter('d');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("____", game.getGuessedWordPart().getWord());
        assertFalse(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.BODY, game.getHangmanStatus());


        game.guessLetter('d');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("____", game.getGuessedWordPart().getWord());
        assertFalse(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.LEFT_LEG, game.getHangmanStatus());


        game.guessLetter('d');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("____", game.getGuessedWordPart().getWord());
        assertFalse(game.isLastLetterGuessed());
        assertEquals(GameStatus.WISHER_WON, game.getGameStatus());
        assertEquals(HangmanStatus.RIGHT_LEG, game.getHangmanStatus());
    }

    @Test
    public void guessLetterWhenWordIsGuessedShouldEndGame() {
        // Arrange
        Wisher wisher = new Wisher("nickname1");
        Guesser guesser = new Guesser("nickname2");
        wisher.requestStartGame(game, new Word("wish"));
        guesser.requestStartGame(game);
        game.start();


        // Act & Assert
        game.guessLetter('w');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("w___", game.getGuessedWordPart().getWord());
        assertTrue(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.EMPTY, game.getHangmanStatus());

        game.guessLetter('d');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("w___", game.getGuessedWordPart().getWord());
        assertFalse(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.HEAD, game.getHangmanStatus());

        game.guessLetter('e');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("w___", game.getGuessedWordPart().getWord());
        assertFalse(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.LEFT_HAND, game.getHangmanStatus());


        game.guessLetter('s');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("w_s_", game.getGuessedWordPart().getWord());
        assertTrue(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.LEFT_HAND, game.getHangmanStatus());


        game.guessLetter('h');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("w_sh", game.getGuessedWordPart().getWord());
        assertTrue(game.isLastLetterGuessed());
        assertEquals(GameStatus.CONTINUING, game.getGameStatus());
        assertEquals(HangmanStatus.LEFT_HAND, game.getHangmanStatus());


        game.guessLetter('i');
        assertEquals(game.getHiddenWord().getWord().length(), game.getGuessedWordPart().getWord().length());
        assertEquals("wish", game.getGuessedWordPart().getWord());
        assertTrue(game.isLastLetterGuessed());
        assertEquals(GameStatus.GUESSER_WON, game.getGameStatus());
        assertEquals(HangmanStatus.LEFT_HAND, game.getHangmanStatus());
    }
}
