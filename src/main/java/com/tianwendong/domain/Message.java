package com.tianwendong.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tianwendong.domain.util.CustomDateTimeDeserializer;
import com.tianwendong.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Message.
 */
@Entity
@Table(name = "T_MESSAGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "content")
    private String content;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "created_time", nullable = false)
    private DateTime created_time;

    @Column(name = "ipv4")
    private Integer ipv4;

    @Column(name = "user_agent")
    private String user_agent;

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

    public DateTime getCreated_time() {
        return created_time;
    }

    public void setCreated_time(DateTime created_time) {
        this.created_time = created_time;
    }

    public Integer getIpv4() {
        return ipv4;
    }

    public void setIpv4(Integer ipv4) {
        this.ipv4 = ipv4;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
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
                ", created_time='" + created_time + "'" +
                ", ipv4='" + ipv4 + "'" +
                ", user_agent='" + user_agent + "'" +
                '}';
    }
}
