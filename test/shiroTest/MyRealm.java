package com.zdhs.atw.test.shiroTest;

import com.zdhs.atw.entity.SysPermission;
import com.zdhs.atw.entity.SysRole;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Auther : guojianmin
 * @Date : 2019/5/31 14:09
 * @Description : TODO用一句话描述此类的作用
 */
public class MyRealm extends AuthorizingRealm {

    /**
     * 模拟数据库数据
     */
    Map<String,String> userMap =  new HashMap<>(16);
    {
        userMap.put("admin","admin");
        super.setName("myRealm");//设置自定义Realm的名称，取什么无所谓
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userName = (String) principalCollection.getPrimaryPrincipal();
        //从数据库获取角色和权限数据
        Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPermissionByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 模拟从是数据库获取角色数据
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        HashSet<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    /**
     * 模拟从数据库获取权限数据
     * @param userName
     * @return
     */
    private Set<String> getPermissionByUserName(String userName) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:delete");
        permissions.add("user:add");
        return permissions;
    }

    /**
     * 认证
     * @param authenticationToken 主题传过来的认证信息
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体传过来的认证信息中，获取用户名
        String username = (String) authenticationToken.getPrincipal();

        //2.通过用户名从数据库获取凭证
        String password = getPasswordByUserName(username);
        if (password == null) return null;
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("admin", password, "myRealm");
        return authenticationInfo;
    }

    /**
     * 模拟从数据库获取凭证的过程
     * @param username
     * @return
     */
    private String getPasswordByUserName(String username) {
        return userMap.get(username);
    }
}
