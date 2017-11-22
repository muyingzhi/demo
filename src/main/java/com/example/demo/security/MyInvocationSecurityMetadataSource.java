package com.example.demo.security;

import com.example.demo.auth.ResourceRoles;
import com.example.demo.auth.service.ResourceRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by muyz on 2017/11/20.
 */
@Component("metadataSource")
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private ResourceRoleService resourceRoleService;
    public MyInvocationSecurityMetadataSource(){
    }
    private Collection<ResourceRoles> loadSourceDefine(){
        Collection<ResourceRoles> rrs = resourceRoleService.getAll();

        return rrs;
    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url = ((FilterInvocation)object).getRequestUrl();
        //-----取出受保护的资源与角色的对照
        Iterator<ResourceRoles> ite = loadSourceDefine().iterator();
        //匹配所有的url，并对角色去重
        Set<String> roles = new HashSet<String>();
        while ( ite.hasNext()) {
            ResourceRoles rr = ite.next();
            RequestMatcher urlMather = new AntPathRequestMatcher(rr.getUrlPattern());
            //-----当前请求url是否是受保护的
            if (urlMather.matches(((FilterInvocation)object).getRequest())) {
                String[] ss = rr.getRoles().split(",");
                for (String one: ss) {
                    roles.add(one);
                }
            }
        }

        Collection<ConfigAttribute> cas = new ArrayList<ConfigAttribute>();
        for (String role: roles){
            ConfigAttribute ca = new SecurityConfig(role);
            cas.add(ca);
        }
        return cas;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
