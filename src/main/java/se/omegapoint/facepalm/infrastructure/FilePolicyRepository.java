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

package se.omegapoint.facepalm.infrastructure;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.omegapoint.facepalm.domain.Policy;
import se.omegapoint.facepalm.domain.event.EventService;
import se.omegapoint.facepalm.domain.repository.PolicyRepository;
import se.omegapoint.facepalm.infrastructure.event.GenericEvent;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class FilePolicyRepository implements PolicyRepository {

    private static final int TIMEOUT = 5;
    private final EventService eventService;

    @Autowired
    public FilePolicyRepository(final EventService eventService) {
        this.eventService = notNull(eventService);
    }

    @Override
    public Optional<Policy> retrievePolicyWith(final String filename) {
        notBlank(filename);

        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Command command = commandBasedOnOperatingSystem(filename);
        final Future<String> future = executorService.submit(command);

        try {
            eventService.publish(new GenericEvent(format("About to execute command[%s]", command.command)));
            return Optional.of(new Policy(future.get(TIMEOUT, TimeUnit.SECONDS)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Command commandBasedOnOperatingSystem(final String filename) {
        final String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        return os.contains("win") ? new WindowsCommand(filename) : new UnixCommand(filename);
    }

    abstract class Command implements Callable<String> {
        public final String command;

        protected Command(final String command) {
            this.command = notBlank(command);
        }
    }

    class WindowsCommand extends Command {

        public WindowsCommand(final String filename) {
            super("cmd /K \"cd docs && type " + filename + " && exit\"");
        }

        @Override
        public String call() throws Exception {
            final Process exec = Runtime.getRuntime().exec(command);
            return IOUtils.toString(exec.getInputStream(), Charset.forName("UTF-8"));
        }
    }

    class UnixCommand extends Command {

        public UnixCommand(final String filename) {
            super("cat ./docs/" + filename);
        }

        @Override
        public String call() throws Exception {
            final Process exec = Runtime.getRuntime().exec(command);
            return IOUtils.toString(exec.getInputStream(), Charset.forName("UTF-8"));
        }
    }
}
