package com.binglian.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.druid.pool.DruidDataSource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JDBCRealmTest {

	DruidDataSource dataSource=new DruidDataSource();
	
	{
		dataSource.setUrl("jdbc:mysql://localhost:3306/test");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
	}
	
	@Test 
	public void testAuthentication(){
		
		JdbcRealm jdbcRealm=new JdbcRealm();
		
		jdbcRealm.setDataSource(dataSource);
		jdbcRealm.setPermissionsLookupEnabled(true);//如果要使用权限管理 要打开 因为它默认是关闭的
		
		String sql="select password from users where username=?";
		jdbcRealm.setAuthenticationQuery(sql);
		
		String roleSql="select name from test_rest_user where role_name=?";
		jdbcRealm.setUserRolesQuery(roleSql);
		
		//1.构建securityManager环境
		DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
		defaultSecurityManager.setRealm(jdbcRealm);
		
		//2.主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject=SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("binglian","123456");
		
		subject.login(token);
		
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
		
		subject.checkRole("admin");
	}
}
