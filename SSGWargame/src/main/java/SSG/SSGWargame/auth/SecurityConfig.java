package SSG.SSGWargame.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//WebSecurityConfigurerAdapter : spring security의 filter chain을 적용한
// WebSecurity 클래스를 Custom할 수 있는 WebSecurityConfigurer클래스를 편리하게
// 생성할 수 있는 클래스이다.
// WebSecurityConfigurerAdapter -> WebSecurityConfigurer -> WebSecurity순대로
// 클래스를 사용한다. 우리는 이 중 WebSecurityConfigurerAdapter를 사용해 Spring Security
// 를 설정하려고 한다.
//filter chain : 인증을 처리하는 여러개의 시큐리티 필터를 담는 것.
@EnableWebSecurity // spring security를 적용한다는 Annotation
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ExService exService;

    /**
     * Using the HttpSecurity, each request can be intercepted to perform various processes such as
     * authentication by URL, login, logout by this Method
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/userAccess").hasRole("USER") // USER라는 권한을 가진 유저만 접근 가능
                .antMatchers("/signUp").anonymous() // 인증되지 않은, 로그인 되지 않은 사용자만 접근 가능.
                .and()
                .formLogin() //Use Login Form provided by Spring Security. When Success, Redirect to '/'
                .and()
                .csrf().disable();
    }

    @Bean // password encoder : Register to Bean.
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * This method provide `authentication object creation` using AuthenticationManagerBuilder Object.
     * exService is object that inherits UserDetails Interface. Using exService, We manage logged-in users' data.
     * passwordEncoder encrypts text entered at login.
     * @param auth
     * @throws Exception
     */
    /**
     * AuthenticationManagerBuilder : tool to customizing authentication managers.
     * if autowired -> global(parent)
     *
     * if @override -> local
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(exService).passwordEncoder(new BCryptPasswordEncoder());
        String password = passwordEncoder().encode("1111");
        auth.inMemoryAuthentication().withUser("user").password(password).roles("USER");
        auth.inMemoryAuthentication().withUser("manager").password(password).roles("MANAGER");
        auth.inMemoryAuthentication().withUser("admin").password(password).roles("ADMIN");
    }
}
