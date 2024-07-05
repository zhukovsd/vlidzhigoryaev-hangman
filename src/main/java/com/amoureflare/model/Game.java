package com.amoureflare.model;

import com.amoureflare.enums.GameStatus;
import com.amoureflare.enums.HangmanStatus;
import com.amoureflare.machine.GameMachine;
import com.amoureflare.machine.HangmanMachine;
import com.amoureflare.view.RenderingEngine;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class Game {
    @Getter
    private GameStatus gameStatus = GameStatus.NOT_STARTED;
    @Getter
    private HangmanStatus hangmanStatus = HangmanStatus.EMPTY;

    /*
     TODO:  Мб вынести все поля и логику, связанные со словами и буквами в отдельный класс-обработчик, который будет знать о внутрянке слова в том числе,
            потому что сейчас Game делает много чего и знает как устроено слово внутри
    */
    public static final Character MISS_SYMBOL = '_';

    @Getter
    private Word hiddenWord;
    @Getter
    private Word guessedWordPart;
    @Getter
    private Character lastLetter;
    @Getter
    private boolean isLastLetterGuessed = false;

    @Getter
    private Guesser guesser;
    private boolean isGuesserStartedGame = false;
    @Getter
    private Wisher wisher;
    private boolean isWisherStartedGame = false;

    private RenderingEngine renderingEngine = new RenderingEngine();
    private HangmanMachine hangmanMachine = new HangmanMachine(hangmanStatus);
    private GameMachine gameMachine = new GameMachine(gameStatus);

    public void addWisher(Wisher wisher) {
        checkGameIsNotStarted();
        this.wisher = wisher;
    }

    public void addGuesser(Guesser guesser) {
        checkGameIsNotStarted();
        this.guesser = guesser;
    }

    public void initWord(Word initWord) {
        checkGameIsNotStarted();
        hiddenWord = initWord;
        guessedWordPart = new Word(String.valueOf(MISS_SYMBOL).repeat(hiddenWord.getWord().length()));
    }

    public void guessLetter(Character letter) {
        if (!isEnded() && isStarted()) {
            lastLetter = letter;
            if (hiddenWord.containsLetter(letter) && !guessedWordPart.containsLetter(letter)) {
                updateGuessedWordPart();
                isLastLetterGuessed = true;
            } else {
                isLastLetterGuessed = false;
            }
            hangmanStatus = hangmanMachine.transit(isLastLetterGuessed);
            gameStatus = gameMachine.transit(this);
            renderingEngine.render(this);
        } else {
            throw new IllegalStateException("Game is already ended or not started");
        }
    }

    public void requestStart(Player player) {
        checkGameIsNotStarted();
        tryStartByPlayer(player);
    }

    public void start() {
        checkGameIsNotStarted();
        if (isReadyToStart()) {
            gameStatus = GameStatus.CONTINUING;
        } else {
            throw new IllegalStateException("Game is not ready to start");
        }
    }

    private void checkGameIsNotStarted() {
        if (!isNotStarted()) {
            throw new IllegalStateException("Game already started");
        }
    }

    public boolean isNotStarted() {
        return gameStatus == GameStatus.NOT_STARTED;
    }

    public boolean isStarted() {
        return gameStatus != GameStatus.NOT_STARTED;
    }

    private void tryStartByPlayer(Player player) {
        if (wisher == player) {
            isWisherStartedGame = true;
        } else if (guesser == player) {
            isGuesserStartedGame = true;
        } else {
            throw new IllegalArgumentException("Unknown player");
        }
    }

    private boolean isReadyToStart() {
        return gotAllRequestsToStart() && !hiddenWord.isEmpty();
    }

    private boolean gotAllRequestsToStart() {
        return isGuesserStartedGame && isWisherStartedGame;
    }

    private void updateGuessedWordPart() {
        StringBuilder guessWordBuilder = new StringBuilder();
        for (int i = 0; i < hiddenWord.getWord().length(); i++) {
            Character letterFromHiddenWord = hiddenWord.getWord().charAt(i);
            Character letterFromGuessedWord = guessedWordPart.getWord().charAt(i);

            if (letterFromGuessedWord == MISS_SYMBOL &&
                letterFromHiddenWord == lastLetter) {
                guessWordBuilder.append(letterFromHiddenWord);
            } else {
                guessWordBuilder.append(letterFromGuessedWord);
            }
        }

        guessedWordPart = new Word(guessWordBuilder.toString());
    }

    public boolean isEnded() {
        return gameStatus == GameStatus.WISHER_WON || gameStatus == GameStatus.GUESSER_WON;
    }
}
