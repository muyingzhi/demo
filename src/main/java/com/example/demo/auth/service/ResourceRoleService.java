package com.example.demo.auth.service;

import com.example.demo.auth.ResourceRoles;
import com.example.demo.auth.mapper.ResourceRolesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.serializer.Serializer;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by muyz on 2017/11/20.
 */
@Service
public class ResourceRoleService implements Serializable {
    @Autowired
    private ResourceRolesDao resourceRolesDao;

//    private Map<String, Object> rrsMap = null;
    public int insert(ResourceRoles rr){
        return resourceRolesDao.insert(rr);
    }

    public int update(Long id, String urlPattern, String ... roles){
        String s="";
        for (int i=0; i< roles.length; i++){
            s += roles[i];
        }
        ResourceRoles rrs = new ResourceRoles();
        rrs.setId(Long.valueOf(id));
        rrs.setRoles(s);
        rrs.setUrlPattern(urlPattern);
        return resourceRolesDao.update(rrs);
    }
    public Collection<ResourceRoles> getAll() {
//        if (rrsMap == null){
//            rrsMap = new HashMap<String,Object>();
//        }
//        Collection<ResourceRoles> all = (Collection<ResourceRoles>) rrsMap.get("all");
//        if (all == null){
//            all = resourceRolesDao.getAll();
//            rrsMap.put("all",all);
//        }
//        return all;
        return resourceRolesDao.getAll();
    }

    public int deleteById(Long id){
        return resourceRolesDao.deleteById(id);
    }
}
