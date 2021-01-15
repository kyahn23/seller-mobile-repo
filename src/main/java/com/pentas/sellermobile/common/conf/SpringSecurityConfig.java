package com.pentas.sellermobile.common.conf;

import com.pentas.sellermobile.common.springsecurity.AuthFailureHandler;
import com.pentas.sellermobile.common.springsecurity.AuthSuccessHandler;
import com.pentas.sellermobile.common.springsecurity.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomAuthenticationProvider authenticationProvider;

    @Autowired
	AuthFailureHandler authFailureHandler;

    @Autowired
	AuthSuccessHandler authSuccessHandler;

    @Override
	public void configure(WebSecurity web) throws Exception {
		// 허용되어야 할 경로들
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {

    	http.csrf().disable()
			.headers()
				.frameOptions()
				.sameOrigin()
				.and()
            .formLogin() // 로그인 페이지 및 성공 url, handler 그리고 로그인시 사용되는 id, password 파라미터 정의
	            .loginPage("/#/login") // 밑의 authorizeRequests 에 설정한 규칙에 어긋난경우 로그인페이지 호출 URL
	            .loginProcessingUrl("/loginProc") // 로그인처리시 호출할 URL
	            .defaultSuccessUrl("/afterLoginProc", true) // 로그인 성공후 이동할 URL
	            .failureUrl("/loginFail")  // 로그인 실패후 이동할 URL
	            .usernameParameter("userId") // 로그인페이지에서 로그인 ID 파라메타 명
	            .passwordParameter("userPw") // 로그인페이지에서 로그인 password 파라메타 명
	            .and()
            .logout()
            	.logoutSuccessUrl("/#/login?loginStat=LOGOUT") // 로그아웃후 이동할 URL
            	.invalidateHttpSession(true)
            	.and()
            .authorizeRequests()
				.antMatchers("/", "/**").permitAll()
            	.and()
			.cors()
				.and()
            .authenticationProvider(authenticationProvider);
	}

	@Override
	protected void  configure(AuthenticationManagerBuilder auth) {
		auth.eraseCredentials(false);
	}

}
