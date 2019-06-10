package com.zdhs.atw.test.shiroTest;

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
 * @Description : 案例三  认证和授权（其中授权 包括权限和角色）
 */
public class AuthorizationTest {

    @Test
    public void testAuthentication(){

        //0.创建自己实现的Realm实例
        MyRealm myRealm = new MyRealm();

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(myRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);//设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject();//获取当前主体

        //3.生成token
        UsernamePasswordToken token = new UsernamePasswordToken("admin","admin");

        //4.登录
        subject.login(token);
        //subject.isAuthenticated()方法返回一个boolean值，用于判断是否认证成功
        System.out.println("isAuthenticated: "+subject.isAuthenticated());//输出true

        //5.判断subject是否具有admin和user两个角色，如没有则会报错 org.apache.shiro.authz.UnauthorizedException: Subject does not have role [xxx]
        subject.checkRoles("admin","user");//没有报错的话，就是拥有这两个角色

        //6.判断subject是否具有 user:add 权限，如没有则会报错 org.apache.shiro.authz.UnauthorizedException: Subject does not have permission [xxx]
        subject.checkPermission("user:adsd");//如果没有报错的话，就是有这个权限

        //7.登出
        subject.logout();
        System.out.println("isAuthenticated: "+subject.isAuthenticated());//输出false
    }
}
