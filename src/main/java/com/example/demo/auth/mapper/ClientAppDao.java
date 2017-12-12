package com.example.demo.auth.mapper;

import com.example.demo.auth.ClientApp;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by muyz on 2017/11/30.
 */
@Mapper
public interface ClientAppDao extends BaseDao<ClientApp>{

    public ClientApp getByClientId(String clientId);
}
