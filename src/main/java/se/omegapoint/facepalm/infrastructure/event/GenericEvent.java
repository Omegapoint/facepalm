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

package se.omegapoint.facepalm.infrastructure.event;

import se.omegapoint.facepalm.domain.event.ApplicationEvent;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

public class GenericEvent implements ApplicationEvent {
    private final Object data;

    public GenericEvent(final Object data) {
        this.data = notNull(data);
    }

    @Override
    public String message() {
        return data.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GenericEvent that = (GenericEvent) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
