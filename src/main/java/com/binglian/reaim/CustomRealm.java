package com.binglian.reaim;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomRealm extends AuthorizingRealm{

	Map<String, String> userMap=new HashMap<String, String>(16);
	
	{
		userMap.put("binglian","fb9ddc41475f1715d9bedb4eef43e9be");
		super.setName("customRealm");
	}
	
	/**
	 * 认证授权管理
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName=(String)principals.getPrimaryPrincipal();
		
		//从数据库或缓存中获取角色数据
		Set<String> roles=getRolesByUserName(userName);
		
		Set<String> permissions=getPermissionsByUserName(userName);

		SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setStringPermissions(permissions);
		simpleAuthorizationInfo.setRoles(roles);
		return simpleAuthorizationInfo;
	}
	

	/**
	 * 模拟数据库授权功能
	 * @param userName
	 * @return
	 */
	private Set<String> getPermissionsByUserName(String userName) {
		Set<String> sets=new HashSet<>();
		sets.add("user:delete");
		sets.add("user:add");
		return sets;
	}


	/**
	 * 模拟数据库角色
	 * @param userName
	 * @return
	 */
	private Set<String> getRolesByUserName(String userName) {
		Set<String> sets=new HashSet<>();
		sets.add("admin");
		sets.add("user");
		return sets;
	}


	/**
	 * 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.从主体传过来的认证信息中，获得用户名
		String userName=(String)token.getPrincipal();
			
		//2.通过用户名到数据库中获取凭证
		String password=getPasswordByUserName(userName);
		
		if(password ==null){
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo("binglian",password,"customRealm");
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("binglian"));
		return authenticationInfo;
	}


	/**
	 * 模拟数据库查询凭证
	 * @param userName
	 * @return
	 */
	private String getPasswordByUserName(String userName) {
		return userMap.get(userName);
	}
	
	public static void main(String[] args){
		Md5Hash md5Hash=new Md5Hash("123456","binglian");
		System.out.println(md5Hash.toString());
	}

}
