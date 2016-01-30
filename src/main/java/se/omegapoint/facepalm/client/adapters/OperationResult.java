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

package se.omegapoint.facepalm.client.adapters;

public class OperationResult {
    private final String message;
    private final boolean success;

    private OperationResult(final boolean success, final String message) {
        this.message = message;
        this.success = success;
    }

    public static OperationResult success() {
        return new OperationResult(true, null);
    }

    public static OperationResult failure(final String message) {
        return new OperationResult(false, message);
    }

    public String message() {
        if (success) {
            throw new IllegalStateException("No message available for successful operations");
        }
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
