package ma.youcode.demo.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ma.youcode.demo.models.User;
import ma.youcode.demo.services.UserService;

/* WebSecurityConfigurerAdapter =>   désigne la classe SpringSecurityConfig en tant que configuration Spring Security*/

//Activer la sécurité => Activer la configuration Spring Security personnalisée
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;


	public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		User user = new User();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRoles().toString());
		return Arrays.asList(authority);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());

		return authProvider;
	}

	/***************************************************************************************************************
	 *************************** Exécuter la chaîne de filtres de sécurité sur les requêtes HTTP
	 * *******************
	 **************************************************************************************************************/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and();
		http.csrf().disable();
		http.authorizeRequests((requests) -> {
			try {
				requests.antMatchers(HttpMethod.POST, "/users")
				.permitAll().anyRequest().authenticated().and()
						.addFilter(getAuthenticationFilter())
						.addFilter(new AuthorizationFilter(authenticationManager()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// STATELESS => chaque session lier à un token
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.formLogin();
		http.httpBasic();
	}

	// changer url d'authentification
	protected AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/users/login");
		return filter;
	}

	/************************************************************************************************************
	 * ********************************* Gérer l'ensemble de règles
	 * d'authentification **************************
	 *********************************************************************************************************/

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}
}
