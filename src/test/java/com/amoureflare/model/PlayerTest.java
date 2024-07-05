package com.amoureflare.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    private Player player;
    private final String nickname = "nickname";

    @BeforeEach
    void init() {
        player = new Wisher(nickname);
    }

    @Test
    public void getNickname() {
        Assertions.assertEquals(nickname, player.getNickname());
    }


}
