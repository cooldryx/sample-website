package com.tianwendong.web.rest.dto;

import javax.validation.constraints.Pattern;

public class MessageDTO {

    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String nickname;

    private String content;

    public MessageDTO() {
    }

    public MessageDTO(String email, String nickname, String content) {
        this.email = email;
        this.nickname = nickname;
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageDTO{");
        sb.append("email='").append(email).append('\'');
        sb.append("nickname='").append(nickname).append('\'');
        sb.append("content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
