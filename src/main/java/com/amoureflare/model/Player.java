package com.amoureflare.model;

import lombok.*;

public abstract class Player {
    @Getter
    @Setter
    protected String nickname;

    @Getter
    protected Game currentGame;

    public Player(String nickname) {
        this.nickname = nickname;
    }
}
