package com.example.demo.auth;

import java.io.Serializable;

/**
 * Created by muyz on 2017/11/30.
 */
public class BaseSource implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
