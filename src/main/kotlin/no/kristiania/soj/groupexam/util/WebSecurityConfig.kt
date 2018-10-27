package no.kristiania.soj.groupexam.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource


/**
 * Security configurations are extremely important.
 * Even a small mistake could have catastrophic consequences.
 * It is hence advisable to have all security-related configurations
 * in a single place (eg, a class or a configuration file), as it
 * makes it easier to audit it.
 *
 * Created by arcuri82 on 21-Sep-17.
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
/*
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
*/
    /*
     * This is where we define all the access rules,
     * ie who is authorized to access what
     */
    override fun configure(http: HttpSecurity) {

        http.csrf().disable()
        http.authorizeRequests()
                /*
                    these rules are matched one at a time, in their order.
                    this is important to keep in mind if different URL templates
                    can match the same URLs

                    commented out hasrole USER for now so we can access everything from /api/
                    at a later date we will choose who can access different pages and resources
                 */
                .antMatchers("/", "/login", "/signup",
                        "/index.html", "/api/movie", "/api/movie/**",
                        "/api/ticket", "/api/ticket/**", "/api/users",
                        "/api/users/**", "/api/test").permitAll()
                .antMatchers("/api/testUser").hasRole("USER")
                .antMatchers("/api/testAdmin").hasRole("ADMIN")
                /*
                    whitelisting: deny everything by default,
                    unless it was explicitly allowed in the rules
                    above.
                 */
                .anyRequest().hasRole("ADMIN")
                .and()
                /*
                    there are many different ways to define
                    how login is done.
                    So here we need to configure it.
                    We start from looking at "Basic" HTTP,
                    which is the simplest form of authentication
                  */
                .httpBasic()
                /*
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/index")
                .and()
                .logout()
                .logoutSuccessUrl("/index")
                */
    }


    /*
        Here we configure how users are authenticated.
        For simplicity here we just create some pre-defined users.
     */
    override fun configure(auth: AuthenticationManagerBuilder) {
/*
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "SELECT username, password, enabled " +
                                "FROM users " +
                                "WHERE username = ?"
                )
                .authoritiesByUsernameQuery(
                        "SELECT x.username, y.roles " +
                                "FROM users x, user_roles y " +
                                "WHERE x.username = ? and y.user_username = x.username "
                )
                .passwordEncoder(passwordEncoder)
        */
        //{noop} is just there to say we want the password as plaintext and not encoded
        auth.inMemoryAuthentication()
                .withUser("foo").password("{noop}bar123").roles("USER").and()
                .withUser("admin").password("{noop}admin").roles("ADMIN", "USER")
    }
}