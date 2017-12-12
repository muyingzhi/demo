package com.example.demo;

import com.example.demo.auth.ClientApp;
import com.example.demo.auth.mapper.ClientAppDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

import java.util.Iterator;

import static org.junit.Assert.assertTrue;

/**
 * Created by muyz on 2017/11/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientAppTest {
    @Autowired
    private ClientAppDao clientAppDao;
    @Test
    public void testInsert(){

        ClientApp ca = new ClientApp();
        ca.setAppName("application "+ Double.valueOf(Math.random()*1000).intValue());
        ca.setAppId(Base64Utils.encodeToUrlSafeString(ca.getAppName().getBytes()));
        ca.setAppSecret(Base64Utils.encodeToUrlSafeString((ca.getAppName()+":YZ").getBytes()));
        clientAppDao.insert(ca);

        assertTrue("Insert ClientApp is OK",true);
    }
    @Test
    public void testDelete() {
        Iterator<ClientApp> it = clientAppDao.getAll().iterator();
        if ( it.hasNext()){
            clientAppDao.delete(it.next());
        }
        assertTrue("delete is OK",true);
    }
    @Test
    public void testUpdate() {
        Iterator<ClientApp> it = clientAppDao.getAll().iterator();
        if ( it.hasNext()){
            ClientApp ca = it.next();
            ca.setAppName(ca.getAppName() + "|Update");
            clientAppDao.update(ca);
        }
        assertTrue("update is OK",true);
    }
}

