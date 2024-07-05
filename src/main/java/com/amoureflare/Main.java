package com.amoureflare;

import com.amoureflare.model.Game;
import com.amoureflare.model.Guesser;
import com.amoureflare.model.Wisher;
import com.amoureflare.model.Word;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Wisher wisher = new Wisher("spectre");
        Guesser guesser = new Guesser("amoureflare");

        Game game = new Game();

        String wishedWordString = "";
        while (StringUtils.isEmpty(wishedWordString)) {
            System.out.print(wisher.getNickname() + ": Введите загадываемое слово - ");
            wishedWordString = scanner.nextLine();
        }

        Word wished = new Word(wishedWordString);
        wisher.requestStartGame(game, wished);
        guesser.requestStartGame(game);
        game.start();

        while (!game.isEnded()) {
            System.out.print(guesser.getNickname() + ": Введите букву слова - ");
            guesser.nameLetter(scanner.next().charAt(0));
        }
    }
}