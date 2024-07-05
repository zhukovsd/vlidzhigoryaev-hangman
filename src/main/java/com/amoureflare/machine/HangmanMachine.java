package com.amoureflare.machine;

import com.amoureflare.enums.HangmanStatus;

public class HangmanMachine extends Machine<HangmanStatus, Boolean> {

    public HangmanMachine(HangmanStatus initState) {
        // TODO: create transition function
        super(initState);
        transitionFunction = isLastLetterGuessed -> {
            if (isLastLetterGuessed) {
                return currentState;
            } else {
                currentState = switch (currentState) {
                    case EMPTY -> HangmanStatus.HEAD;
                    case HEAD -> HangmanStatus.LEFT_HAND;
                    case LEFT_HAND -> HangmanStatus.RIGHT_HAND;
                    case RIGHT_HAND -> HangmanStatus.BODY;
                    case BODY -> HangmanStatus.LEFT_LEG;
                    case LEFT_LEG, RIGHT_LEG -> HangmanStatus.RIGHT_LEG;
                    default -> throw new IllegalStateException("Unknown hangman state to transit to new state");
                };
                return currentState;
            }
        };
    }

    @Override
    public HangmanStatus transit(Boolean isLastLetterGuessed) {
        return transitionFunction.apply(isLastLetterGuessed);
    }
}
