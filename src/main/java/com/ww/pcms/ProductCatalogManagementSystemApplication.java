package com.ww.pcms;

import com.ww.pcms.filter.APIKeyRequestFilter;
import com.ww.pcms.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@SpringBootApplication

public class ProductCatalogManagementSystemApplication {
	Logger logger = LoggerFactory.getLogger(ProductCatalogManagementSystemApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ProductCatalogManagementSystemApplication.class, args);
	}

	@Configuration
	@EnableWebSecurity
	@Order(1)
	public class SecurityConfig extends WebSecurityConfigurerAdapter {

		private String principalRequestHeader = "x-api-key";

		private String principalRequestValue = "vasu";

	    @Override
	    protected void configure(HttpSecurity httpSecurity) throws Exception {
			APIKeyRequestFilter filter = new APIKeyRequestFilter(principalRequestHeader);
			filter.setAuthenticationManager(new AuthenticationManager() {

				@Override
				public Authentication authenticate(Authentication authentication) throws AuthenticationException {
					String principal = (String) authentication.getPrincipal();
					if (!principalRequestValue.equals(principal))
					{
						throw new BadCredentialsException("The API key was not found or not the expected value.");
					}
					authentication.setAuthenticated(true);
					logger.info("Authentication is successful");
					return authentication;
				}
			});
			httpSecurity.
					antMatcher("/**").
					csrf().disable().
					sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
					and().addFilter(filter).authorizeRequests().anyRequest().authenticated();

		}
	}
}
