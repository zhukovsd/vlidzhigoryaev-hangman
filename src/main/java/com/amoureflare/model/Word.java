package com.amoureflare.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class Word {
    @Getter
    private final String word;

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
