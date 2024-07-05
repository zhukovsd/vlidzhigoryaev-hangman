package com.amoureflare.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Wisher extends Player {
    public Wisher(String nickname) {
        super(nickname);
    }

    public void requestStartGame(Game game, Word wishedWord) {
        game.addWisher(this);
        game.initWord(wishedWord);
        game.requestStart(this);
        currentGame = game;
    }
}
