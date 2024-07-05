package com.amoureflare.machine;

import com.amoureflare.enums.GameStatus;
import com.amoureflare.enums.HangmanStatus;
import com.amoureflare.model.Game;

public class GameMachine extends Machine<GameStatus, Game> {

    public GameMachine(GameStatus initState) {
        // TODO: create transition function
        super(initState);

        transitionFunction = game -> {
            currentState = game.getGameStatus();
            if (currentState == GameStatus.NOT_STARTED && !game.isNotStarted()) {
                return GameStatus.CONTINUING;
            } else if (currentState == GameStatus.CONTINUING && game.getHangmanStatus() == HangmanStatus.RIGHT_LEG) {
                return GameStatus.WISHER_WON;
            } else if (currentState == GameStatus.CONTINUING && game.getHiddenWord().equals(game.getGuessedWordPart())) {
                return GameStatus.GUESSER_WON;
            } else {
                return currentState;
            }
        };
    }

    @Override
    public GameStatus transit(Game transition) {
        return transitionFunction.apply(transition);
    }
}
