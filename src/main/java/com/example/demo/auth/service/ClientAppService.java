package com.example.demo.auth.service;

import com.example.demo.auth.ClientApp;
import com.example.demo.auth.mapper.ClientAppDao;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muyz on 2017/12/11.
 */
@Service
public class ClientAppService {
    @Autowired
    private ClientAppDao appDao;

    public boolean isValid(String clientId){
        ClientApp app = appDao.getByClientId(clientId);
        if (app!=null){
            return true;
        }
        return false;
    }
    public String getAccessToken(String code,String clientId,String clientSecret) throws Exception {
        String accessToken="";
        Map<String,Object> map = new HashMap<String,Object>();
        ClientApp app = appDao.getByClientId(clientId);
        if (clientSecret.equals(app.getAppSecret())){
            byte[] bytes = Base64.encodeBase64((code + ":" + clientId + ":" + clientSecret).getBytes());
            accessToken = new String(bytes);
        } else {
            throw new Exception("client secret is error");
        }
        return accessToken;
    }
}
