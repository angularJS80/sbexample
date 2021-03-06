package com.example.demo.security.jwt;

import com.example.demo.security.model.BaseModel;

/**
 * @modify Yongbeom Cho
 */
public class JwtAuthRequest extends BaseModel {

    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;

    public JwtAuthRequest() {
        super();
    }

    public JwtAuthRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}