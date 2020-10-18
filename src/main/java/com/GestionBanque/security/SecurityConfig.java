package com.GestionBanque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;

@Configuration   // c'est une classe de configuration de securité (configuration de spring security)
@EnableWebSecurity  //  pour activer la securité web 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private javax.sql.DataSource dataSource;
	// redéfinir les méthodes
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		/*
		 * auth.inMemoryAuthentication()
		 * .withUser("admin").password("{noop}1234").roles("ADMIN","USER") .and()
		 * .withUser("user").password("{noop}123").roles("USER");
		 */
		
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		// la requete que Spring Security devrait exécuter pour récupérer les utilisateurs
		.usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")  
		// ça veut dire que spring security récupére username saisie par l'utilisateur, il se connécte à la base de donnée
		// pour savoir si l'utlisateur existe et si existe il va comparer le mot de passe, si si le cas, aprés il aura besoin 
		// de savoir les roles de cet utlisateur:
		// il récupére le role de l'utilisateur
		.authoritiesByUsernameQuery("select username as principal, roles as role from users_roles where username=?")	
		// il indique à spring security quand il récupére un role il va lui ajouter un préfixe
		.rolePrefix("ROLE_")
		.passwordEncoder(new MessageDigestPasswordEncoder("MD5"));
		}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//      ça veut dire entrain d'indiquer à spring security que l'opération d'authentification passe d'abord par un formulaire d'authentification 
		http.formLogin().loginPage("/login"); 
//		sécuriser les ressources de l'application (/operation et /consultercompte nécessite d'avoir le role User.
		http.authorizeRequests().antMatchers("/operation","/consultercompte").hasRole("USER");
//		sécuriser les ressources de l'application (/saveoperation nécessite d'avoir le role Admin.
		http.authorizeRequests().antMatchers("/saveOperation").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403"); // ça veut dire si n'a pas le droit d'accéder il va vers une action qui
														   // s'appelle 403
	}
	
	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 */

}
