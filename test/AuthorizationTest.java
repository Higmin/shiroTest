package com.zdhs.atw.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @Auther : guojianmin
 * @Date : 2019/5/31 13:34
 * @Description : 案例二  认证和授权（其中授权  在这里只演示是否拥有角色，权限的在下一个案例中，需要自定义Realm）
 */
public class AuthorizationTest {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
    @Before//在方法开始之前添加以一个用户，让他具备admin和user两个角色  分别为 角色admin 和 角色user
    public void addUser(){
        simpleAccountRealm.addAccount("admin","admin","admin","user");
    }
    @Test
    public void testAuthentication(){
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);//设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject();//获取当前主体

        //3.生成token
        UsernamePasswordToken token = new UsernamePasswordToken("admin","admin");

        //4.登录
        subject.login(token);
        //subject.isAuthenticated()方法返回一个boolean值，用于判断是否认证成功
        System.out.println("isAuthenticated: "+subject.isAuthenticated());//输出true
        //判断subject是否具有admin和user两个角色权限，如没有则会报错 org.apache.shiro.authz.UnauthorizedException: Subject does not have role [xxxxxx]
        subject.checkRoles("admin","user");//没有报错的话，就是拥有这两个角色

        //5.登出
        subject.logout();
        System.out.println("isAuthenticated: "+subject.isAuthenticated());//输出false
    }
}
