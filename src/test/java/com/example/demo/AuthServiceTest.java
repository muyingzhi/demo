package com.example.demo;

import com.example.demo.auth.ResourceRoles;
import com.example.demo.auth.Role;
import com.example.demo.auth.service.ResourceRoleService;
import com.example.demo.auth.service.RoleService;
import com.example.demo.auth.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by muyz on 2017/11/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceRoleService resourceRoleService;
    @Autowired
    private UserService userService;
    @Test
    public void start() {
        assertTrue("start is ok", true);
    }
    @Test
    public void roleTest(){
        Role role = new Role();
        role.setRoleName("ROLE_ADMIN");
        int i = roleService.insert(role);

        Collection<Role> roles = roleService.getAll();

        assertTrue(i>0);
        roleService.delete(role);

        roleService.deleteById(role.getId());
    }

    @Test
    public void rrsTest(){
        ResourceRoles rrs = new ResourceRoles();
        rrs.setRoles("ROLE_ADMIN");
        rrs.setUrlPattern("/user/**");
        resourceRoleService.insert(rrs);

        rrs = new ResourceRoles();
        rrs.setRoles("ROLE_USER");
        rrs.setUrlPattern("/user/get/**");
        resourceRoleService.insert(rrs);


        Collection<ResourceRoles> all = resourceRoleService.getAll();

        assertTrue(!all.isEmpty());

//        int i = resourceRoleService.update(rrs.getId(),rrs.getUrlPattern(),"USER_ADD");
//        assertTrue(i>0);
//
//        resourceRoleService.deleteById(rrs.getId());
    }
    @Test
    public void userRolesTest(){
        userService.addRoles("muyz","ROLE_ADMIN","ROLE_USER");
        userService.removeRole("muyz","ROLE_ADMIN");

        UserDetails ud = userService.loadUserByUsername("muyz");
//        for (GrantedAuthority ga : gas) {
//
//        }
    }
}
