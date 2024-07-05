package com.amoureflare.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

public class Word {
    @Getter
    private final String word;

    public Word(String word) {
        if (StringUtils.isEmpty(word.trim())) {
            throw new IllegalArgumentException("Empty word");
        } else {
            this.word = word;
        }
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(word);
    }

    public boolean containsLetter(Character letter) {
        return word.contains(String.valueOf(letter));
    }
    @Override
    public java.lang.String toString() {
        return word;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Word) {
            return word.equals(((Word) obj).word);
        }
        return super.equals(obj);
    }
}
