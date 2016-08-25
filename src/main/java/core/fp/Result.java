package core.fp;

import java.util.function.Function;

public interface Result<O, E> {
    static <O, E> Result<O, E> error(final E value) {
        return new Result<O, E>() {
            public <R> Result<R, E> mapOk(Function<O, R> f) {
                return error(value);
            }

            public O orElse(Function<E, O > f) {
                return f.apply(value);
            }

            public <R> R either(Function<O, R> f, Function<E, R> g) {
                return g.apply(value);
            }

            @Override
            public <R> Result<R, E> andThen(Function<O, Result<R, E>> f) {
                return error(value);
            }
        };
    }

    static <O, E> Result<O, E> ok(final O value) {
        return new Result<O, E>() {
            public <R> Result<R, E> mapOk(Function<O, R> f) {
                return ok(f.apply(value));
            }

            public O orElse(Function<E, O> f) {
                return value;
            }

            public <R> R either(Function<O, R> f, Function<E, R> g) {
                return f.apply(value);
            }

            @Override
            public <R> Result<R, E> andThen(Function<O, Result<R, E>> f) {
                return f.apply(value);
            }
        };
    }

    <R> Result<R, E> mapOk(Function<O, R> f);

    O orElse(Function<E, O> f);

    <R> R either(Function<O, R> f, Function<E, R> g);

    <R> Result<R, E> andThen(Function<O, Result<R, E>> f);
}
