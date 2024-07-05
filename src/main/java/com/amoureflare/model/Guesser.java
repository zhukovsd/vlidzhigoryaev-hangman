package com.amoureflare.model;

public class Guesser extends Player {
    public Guesser(String nickname) {
        super(nickname);
    }

    public void nameLetter(Character letter) {
        currentGame.guessLetter(letter);
    }

    public void requestStartGame(Game game) {
        game.addGuesser(this);
        game.requestStart(this);
        currentGame = game;
    }
}
