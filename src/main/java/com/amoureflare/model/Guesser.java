package com.amoureflare.model;

public class Guesser extends Player {
    public Guesser(String nickname) {
        super(nickname);
    }

    public void nameLetter(Letter letter) {
        // TODO: check game status and name only if can
    }

    public void requestStartGame(Game game) {
        game.addGuesser(this);
        game.requestStart(this);
    }
}
