package project.vilsoncake.botadminpanel.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AdminServerProperties adminServer;

    @Value("${app.cookie-secret-key}")
    private String cookieSecretKey;

    private static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 7; // 7 Days

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");

        http.authorizeHttpRequests(request -> request.requestMatchers(this.adminServer.getContextPath() + "/assets/**")
                        .permitAll()
                        .requestMatchers(this.adminServer.getContextPath() + "/login")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin(formLogin -> formLogin.loginPage(this.adminServer.getContextPath() + "/login")
                        .successHandler(successHandler))
                .logout((logout) -> logout.logoutUrl(this.adminServer.getContextPath() + "/logout"))
                .httpBasic(Customizer.withDefaults())
                .rememberMe(rememberMe -> rememberMe
                        .key(cookieSecretKey)
                        .tokenValiditySeconds(TOKEN_VALIDITY_SECONDS)
                        .useSecureCookie(true))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
