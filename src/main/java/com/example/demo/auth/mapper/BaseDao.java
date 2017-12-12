package com.example.demo.auth.mapper;

import java.util.Collection;

/**
 * Created by muyz on 2017/11/30.
 */
public interface BaseDao<T> {
        int insert(T source);
        int delete(T source);
        Collection<T> getAll();
        int update(T source);
}

