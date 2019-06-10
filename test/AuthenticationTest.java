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
 * @Description : 案例一  认证
 */
public class AuthenticationTest {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
    @Before//在方法开始之前添加以一个用户
    public void addUser(){
        simpleAccountRealm.addAccount("admin","admin");
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

        //5.登出
        subject.logout();
        System.out.println("isAuthenticated: "+subject.isAuthenticated());//输出false
    }
}
