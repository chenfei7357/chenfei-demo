package com.chenfei.securitydemo.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

@Configuration
public class securityConfig  extends WebSecurityConfigurerAdapter {


	/**
	 * 密码加密管理实例
	 * @return
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	/**
	 * 角色继承
	 * @return
	 */
	@Bean
	RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
		hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
		return hierarchy;
	}

	/**
	 * 用户名、权限相关配置
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("admin")
				.password("123")
				.roles("ADMIN")
				.and()
				.withUser("user")
				.password("123")
				.roles("USER");

	}

	/**
	 * web.ignoring() 用来配置忽略掉的 URL 地址，一般对于静态文件，我们可以采用此操作。
	 * @param web
	 * @throws Exception
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**", "/css/**","/images/**");;
	}

	/**
	 * 需要鉴权的请求
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/user/**").hasRole("USER")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginProcessingUrl("/doLogin")//设置登陆接口地址
				.permitAll()
				.successHandler((req, res, authentication) -> {
					res.setContentType("application/json;charset=utf-8");
					PrintWriter writer = res.getWriter();
					writer.write(JSON.toJSONString(authentication));
					writer.flush();
					writer.close();
				})
				.failureHandler((req, res, e) -> {
					res.setContentType("application/json;charset=utf-8");
					PrintWriter writer = res.getWriter();
					writer.write(JSON.toJSONString(this.dealException(e)));
					writer.flush();
					writer.close();
				})
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessHandler((req, res, authentication) -> {
					res.setContentType("application/json;charset=utf-8");
					PrintWriter writer = res.getWriter();
					writer.write(JSON.toJSONString("注销成功"));
					writer.flush();
					writer.close();
				})
				.and()
				.csrf()
				.disable()
				.exceptionHandling()
				.authenticationEntryPoint((req, res, e) -> {
					res.setContentType("application/json;charset=utf-8");
					PrintWriter writer = res.getWriter();
					writer.write(JSON.toJSONString("未查到用户信息，还未登陆"));
					writer.flush();
					writer.close();
				})
		;
	}

	private String dealException(AuthenticationException e) {
		String msg="";
		if (e instanceof LockedException) {
			msg="账户被锁定，请联系管理员!";
		} else if (e instanceof CredentialsExpiredException) {
			msg="密码过期，请联系管理员!";
		} else if (e instanceof AccountExpiredException) {
			msg="账户过期，请联系管理员!";
		} else if (e instanceof DisabledException) {
			msg="账户被禁用，请联系管理员!";
		} else if (e instanceof BadCredentialsException) {
			msg="用户名或者密码输入错误，请重新输入!";
		}
		System.out.println(e.getMessage());
		return "登陆异常，错误信息为:"+msg;
	}
}
