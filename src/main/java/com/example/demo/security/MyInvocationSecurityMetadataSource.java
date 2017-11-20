package com.example.demo.security;

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

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    public MyInvocationSecurityMetadataSource(){
        loadSourceDefine();
    }
    private void loadSourceDefine(){
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
        for (int i=0;i<2;i++) {
            String roleName = "";
            if( i==0){
                roleName = "ROLE_USER";
            } else {
                roleName = "ROLE_ADMIN";
            }
            ConfigAttribute ca = new SecurityConfig(roleName);
            atts.add(ca);
        }
        resourceMap.put("/user/**", atts);

    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url = ((FilterInvocation)object).getRequestUrl();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()){
            String resURL = ite.next();
            RequestMatcher urlMather = new AntPathRequestMatcher(resURL);
            if (urlMather.matches(((FilterInvocation)object).getRequest())){
                return resourceMap.get(resURL);
            }
        }
        return null;
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
