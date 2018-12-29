package com.binglian.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoFramework;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.binglian.reaim.CustomRealm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomRealmTest {

	@Test
	public void testAuthentication(){
		
		CustomRealm customRealm=new CustomRealm();
		
		//1.构建securityManager环境
		DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
		defaultSecurityManager.setRealm(customRealm);
		
		HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();//加密配置
		matcher.setHashAlgorithmName("MD5");//使用算法md5
		matcher.setHashIterations(1);//加密次数
		
		customRealm.setCredentialsMatcher(matcher);//对自定义Realm进行加密配置
		
		
		//2.主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject=SecurityUtils.getSubject();
		
		
		UsernamePasswordToken token=new UsernamePasswordToken("binglian","123456");
		
		subject.login(token);
		
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
		
		subject.checkRole("admin");//检查角色是否admin
		subject.checkPermissions("user:add","user:delete");//检查是否能能使用授权功能

		
		
	}
}
