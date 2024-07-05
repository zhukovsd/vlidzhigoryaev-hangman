package com.amoureflare.model;

import com.amoureflare.enums.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WisherTest {

    Wisher wisher;
    String nickname = "nickname";

    @BeforeEach
    void init() {
        wisher = new Wisher(nickname);
    }

    @Test
    void startGameShouldAddWisherToGameWithInitWord() {
        // Arrange
        String initWord = "слово";
        Game game = new Game();

        // Act
        wisher.requestStartGame(game, new Word(initWord));

        // Assert
        assertEquals(GameStatus.NOT_STARTED, game.getGameStatus());
        assertEquals(wisher, game.getWisher());
        assertNotNull(game.getHiddenWord());
        assertEquals(initWord, game.getHiddenWord().toString());
    }
}
