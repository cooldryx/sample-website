package com.tianwendong.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Message.
 */
@Entity
@Table(name = "T_MESSAGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message implements Serializable {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("hh:mm d MMMM yyyy");

    private static final int MAX_USER_AGENT_LEN = 255;

    @Id
    @GeneratedValue(generator="increment") //todo: due to hibernate validation check, changed to generator annotation
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Column(name = "email", length = 90)
    @Size(min = 0, max = 90)
    private String email;

    @Column(name = "nickname", length = 30)
    @Size(min = 0, max = 30)
    private String nickname;

    @NotNull
    @Column(name = "content", nullable = false, updatable = false)
    private String content;

    @CreatedDate
    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "created_time", nullable = false, updatable = false)
    private DateTime createdTime = DateTime.now();

    @Size(min = 0, max = 40)
    @Column(name = "ip_address", length = 40)
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }

    @JsonGetter
    public String getFormattedCreatedTime() {
        return DATE_TIME_FORMATTER.print(this.createdTime);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        if (userAgent.length() >= MAX_USER_AGENT_LEN) {
            this.userAgent = userAgent.substring(0, MAX_USER_AGENT_LEN - 1);
        } else {
            this.userAgent = userAgent;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Message message = (Message) o;

        if (id != null ? !id.equals(message.id) : message.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", email='" + email + "'" +
                ", nickname='" + nickname + "'" +
                ", content='" + content + "'" +
                ", createdTime='" + createdTime + "'" +
                ", ipAddress='" + ipAddress + "'" +
                ", userAgent='" + userAgent + "'" +
                '}';
    }
}
