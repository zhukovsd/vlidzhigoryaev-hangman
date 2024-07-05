package com.amoureflare.machine;

import java.util.function.Function;

/**
 * Детерминированный конечный автомат
 * @param <S> тип состояния
 * @param <A> тип перехода
 */
public abstract class Machine<S, A> {
    protected S currentState;
    protected Function<A, S> transitionFunction;

    public Machine(S initState) {
        currentState = initState;
    }

    public abstract S transit(A transition);
}
