package tacos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				.antMatchers("/order", "/design").hasAnyAuthority("ROLE_USER")
				.antMatchers("/**").permitAll()
			.and()
				.formLogin()
					.loginPage("/tacologin")
					.loginProcessingUrl("/authenticate")
					//.loginProcessingUrl("/login")
					.usernameParameter("username")
					.passwordParameter("password")
					.defaultSuccessUrl("/")
			.and()
				.logout()
					.logoutSuccessUrl("/");
	}

}
