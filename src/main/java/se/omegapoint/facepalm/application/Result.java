package se.omegapoint.facepalm.application;

import java.util.Optional;

public class Result<S, F> {

    private final S success;
    private final F failure;

    private Result(final S success, final F failure) {
        this.success = success;
        this.failure = failure;
    }

    public static <S, F> Result<S, F> success(final S success) {
        return new Result<>(success, null);
    }

    public static <S, F> Result<S, F> failure(final F failure) {
        return new Result<>(null, failure);
    }

    public boolean isSuccess() {
        return success != null;
    }

    public S success() {
        return Optional.ofNullable(success).orElseThrow(() -> new IllegalStateException("Cannot retrieve success from failure Result"));
    }

    public F failure() {
        return Optional.ofNullable(failure).orElseThrow(() -> new IllegalStateException("Cannot retrieve failure from success Result"));
    }
}
