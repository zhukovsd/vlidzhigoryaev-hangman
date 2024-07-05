package com.amoureflare.view;

import com.amoureflare.enums.GameStatus;
import com.amoureflare.enums.HangmanStatus;
import com.amoureflare.model.Game;

import java.util.NoSuchElementException;

public class RenderingEngine {

    public void render(Game game) {
        System.out.println("\n\n");
        System.out.println("Состояние игры: " + getGameStatusStringRepresent(game.getGameStatus()));
        System.out.println("Слово: " + game.getGuessedWordPart());
        System.out.println("Состояние виселицы: ");
        System.out.println(getHangmanStatusStringRepresent(game.getHangmanStatus()));
        System.out.println("\n\n\n\n\n");
    }

    private String getGameStatusStringRepresent(GameStatus gameStatus) {
        return switch (gameStatus) {
            case NOT_STARTED -> "Не началась";
            case CONTINUING -> "Продолжается";
            case WISHER_WON -> "Выиграл загадывающий слово!";
            case GUESSER_WON -> "Выиграл отгадывающий слово!";
            default -> throw new NoSuchElementException("Unknown game state");
        };
    }

    private String getHangmanStatusStringRepresent(HangmanStatus hangmanStatus) {
        return switch (hangmanStatus) {
            case EMPTY -> "";
            case HEAD -> "o";
            case LEFT_HAND -> """
                    o
                   /""";
            case RIGHT_HAND -> """
                    o
                   / \\""";
            case BODY -> """
                    o
                   /|\\""";
            case LEFT_LEG -> """
                    o
                   /|\\
                   /""";
            case RIGHT_LEG -> """
                    o
                   /|\\
                   / \\""";
            default -> throw new NoSuchElementException("Unknown hangman state");
        };
    }

}
