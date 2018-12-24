/**
 * 
 */
package com.zhsl.server;

import com.zhsl.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * 资源服务器配置
 * 
 * @author zhailiang
 *
 */
@Configuration
@EnableResourceServer
public class SsoResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private SimpleUrlAuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.formLogin()
				.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
				.loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
					.and()
				.authorizeRequests().anyRequest().authenticated()
					.and()
				.csrf().disable();
        //http.formLogin().and().authorizeRequests().anyRequest().authenticated();
	}
	
}