package com.example.demo.auth;

import java.io.Serializable;

/**
 * Created by muyz on 2017/11/20.
 */
public class ResourceRoles implements Serializable {
    private Long id;
    private String urlPattern;
    private String roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
