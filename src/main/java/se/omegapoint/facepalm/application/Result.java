/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    public boolean isFailure() {
        return success == null;
    }

    public S success() {
        return Optional.ofNullable(success).orElseThrow(() -> new IllegalStateException("Cannot retrieve success from failure Result"));
    }

    public F failure() {
        return Optional.ofNullable(failure).orElseThrow(() -> new IllegalStateException("Cannot retrieve failure from success Result"));
    }
}
