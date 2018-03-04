package be.joelv.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String USER = "user";
	private static final String USERS_BY_USERNAME = 
			"select username, password, enabled from users where username = ?";
	private static final String AUTHORITIES_BY_USERNAME = 
			"select users.username, authorities.authority from users" + 
			" inner join authorities on users.id = authorities.userId" + 
			" where users.username = ?";
	private final DataSource dataSource;
	public SecurityConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery(USERS_BY_USERNAME)
			.authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME);
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.mvcMatchers("/images/**")
			.mvcMatchers("/styles/**")
			.mvcMatchers("/scripts/**");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").and().logout().and().authorizeRequests()
			.mvcMatchers("/books/**").hasAuthority(USER);
	}
}
