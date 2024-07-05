package com.amoureflare.model;

import com.amoureflare.enums.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GuesserTest {

    Guesser guesser;
    String nickname = "nickname";

    @BeforeEach
    void init() {
        guesser = new Guesser(nickname);
    }

    @Test
    void startGameShouldAddGuesserToGame() {
        // Arrange
        Game game = new Game();

        // Act
        guesser.requestStartGame(game);

        // Assert
        assertEquals(GameStatus.NOT_STARTED, game.getGameStatus());
        assertNotNull(game.getGuesser());
        assertEquals(guesser, game.getGuesser());
    }
}
