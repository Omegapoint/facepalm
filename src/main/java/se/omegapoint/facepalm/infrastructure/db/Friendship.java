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

package se.omegapoint.facepalm.infrastructure.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Entity
@Table(name = "FRIENDSHIPS")
public class Friendship {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public Long id;

    @Column(name = "USER_ID")
    public String user;

    @Column(name = "FRIEND_ID")
    public String friend;

    @Temporal(TemporalType.DATE)
    @Column(name = "FRIENDS_SINCE")
    private Date date;

    public Friendship() {
    }

    public Friendship(final String user, final String friend, final Date date) {
        this.user = notBlank(user);
        this.friend = notBlank(friend);
        this.date = notNull(date);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(final String friend) {
        this.friend = friend;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }
}
