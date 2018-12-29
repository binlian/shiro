package com.binglian.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthentiacationTest {

	SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();
	
	@Before
	public void addUser(){
		simpleAccountRealm.addAccount("binglian", "123456","admin");
	}
	@Test
	public void testAuthentication(){
		
		//1.构建securityManage环境
		DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
		defaultSecurityManager.setRealm(simpleAccountRealm);
		
		//2.主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject=SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("binglian","123456");
		subject.login(token);
		
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
		subject.logout();
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
		
		subject.checkRole("admin");
	}
}
