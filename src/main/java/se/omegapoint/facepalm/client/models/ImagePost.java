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

package se.omegapoint.facepalm.client.models;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class ImagePost {
    public final Long id;
    public final String title;
    public final Long numPoints;
    public final Long numComments;

    public ImagePost(final Long id, final String title, final Long points, final Long numComments) {
        this.id = notNull(id);
        this.title = notBlank(title);
        this.numPoints = notNull(points);
        this.numComments = notNull(numComments);
    }
}
