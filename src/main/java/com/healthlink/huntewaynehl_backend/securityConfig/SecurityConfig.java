package com.healthlink.huntewaynehl_backend.securityConfig;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:3000")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*");
//            }
//        };
//    }
//}
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for testing
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // Allow all requests
//        return http.build();
//    }
//}

//import org.springframework.context.annotation.Configuration;
//import org.springframework.lang.NonNull;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(@NonNull CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000") // Update this to match your frontend URL
//                .allowedMethods("GET", "POST", "PUT", "DELETE");
//    }
//}



//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import java.io.IOException;
//
//@ComponentScan
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/home", "/Contact", "aboutUs").permitAll()
//                        .requestMatchers("/nurses/**").hasRole("NURSE")
//                        .requestMatchers("/providers/**").hasRole("PROVIDER")
//                        .requestMatchers("/patients/**").hasRole("PATIENT")
//                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers("/public/**", "/login", "/signup/**", "/css/**", "/js/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                .formLogin(login -> login
//                        .loginPage("/login").permitAll() // Custom login page
//                        .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login?logout=true").permitAll() // Redirect after logout
//                )
//                .csrf()
//                .disable(); // CSRF disabled (consider enabling in production with token-based authentication)
//
//        return http.build();
//    }
//
//    // Custom Authentication Success Handler
//    @Bean
//    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest request,
//                                                HttpServletResponse response,
//                                                Authentication authentication) throws IOException, ServletException {
//                // Redirect to patient dashboard if user has the PATIENT role
//                if (authentication.getAuthorities().stream()
//                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_PATIENT"))) {
//                    response.sendRedirect("/patientDashboard");
//                }
//                // Redirect to nurse dashboard if user has the NURSE role
//                else if (authentication.getAuthorities().stream()
//                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_NURSE"))) {
//                    response.sendRedirect("/nurse_dashboard/nurse");
//                }
//                // Redirect to provider dashboard if user has the PROVIDER role
//                else if (authentication.getAuthorities().stream()
//                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_PROVIDER"))) {
//                    response.sendRedirect("/provider_dashboard/provider");
//                }
//                // Default fallback if no roles match
//                else {
//                    response.sendRedirect("/");
//                }
//            }
//        };
//    }
//}




//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@Configuration
//public class SecurityConfig {
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/signup/**", "/css/**", "/js/**").permitAll() // Public access for signup and static resources
//                        .requestMatchers("/patientDashboard/patient").hasRole("PATIENT") // Restrict dashboards to appropriate roles
//                        .requestMatchers("/nurse_dashboard/nurse").hasRole("NURSE")
//                        .requestMatchers("/provider_dashboard/provider").hasRole("PROVIDER")
//                        .anyRequest().authenticated() // All other requests require authentication
//                )
//                .formLogin(login -> login
//                        .loginPage("/login") // Custom login page
//                        .successHandler(customAuthenticationSuccessHandler()) // Custom success handler
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout").permitAll()
//                );
//
//        return http.build();
//    }
//
//    // Custom Authentication Success Handler
//    @Bean
//    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest request,
//                                                HttpServletResponse response,
//                                                Authentication authentication) throws IOException, ServletException {
//                // Redirect to patient dashboard if user has the PATIENT role
//                if (authentication.getAuthorities().stream()
//                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_PATIENT"))) {
//                    response.sendRedirect("/patientDashboard/patient");
//                }
//                // Redirect to nurse dashboard if user has the NURSE role
//                else if (authentication.getAuthorities().stream()
//                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_NURSE"))) {
//                    response.sendRedirect("/nurse_dashboard/nurse");
//                }
//                // Redirect to provider dashboard if user has the PROVIDER role
//                else if (authentication.getAuthorities().stream()
//                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_PROVIDER"))) {
//                    response.sendRedirect("/provider_dashboard/provider");
//                }
//                // Default fallback if no roles match
//                else {
//                    response.sendRedirect("/");
//                }
//            }
//        };
//    }
//}

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/signup/**", "/login", "/css/**", "/js/**").permitAll() // Public routes
//                        .requestMatchers("/patientDashboard/patient").hasAuthority("PATIENT") // Roles from DB
//                        .requestMatchers("/nurse_dashboard/nurse").hasAuthority("NURSE")
//                        .requestMatchers("/provider_dashboard/provider").hasAuthority("PROVIDER")
//                        .anyRequest().authenticated() // Everything else requires login
//                )
//                .formLogin(login -> login
//                        .loginPage("/login") // Custom login page
//                        .loginProcessingUrl("/login") // Form action URL
//                        .successHandler(customAuthenticationSuccessHandler()) // Redirect based on role
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return (request, response, authentication) -> {
//            String role = authentication.getAuthorities().iterator().next().getAuthority();
//            switch (role) {
//                case "PATIENT":
//                    response.sendRedirect("/patientDashboard/patient");
//                    break;
//                case "NURSE":
//                    response.sendRedirect("/nurse_dashboard/nurse");
//                    break;
//                case "PROVIDER":
//                    response.sendRedirect("/provider_dashboard/provider");
//                    break;
//                default:
//                    response.sendRedirect("/");
//            }
//        };
//    }
//}

import com.healthlink.huntewaynehl_backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/Contact", "/aboutUs").permitAll()
                        .requestMatchers("/patient_dashboard/patient").hasAuthority("ROLE_PATIENT") // ✅ Ensure correct role
                        .requestMatchers("/nurse_dashboard/nurse").hasAuthority("ROLE_NURSE")
                        .requestMatchers("/provider_dashboard/provider").hasRole("PROVIDER")
                        .requestMatchers("/admin_dashboard/**").hasRole("ADMIN")  // ✅ Admin access

                        .requestMatchers("/public/**", "/login", "/signup/**", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")  // Redirect to login page after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")  // Ensure session is cleared
                        .permitAll()
                )

                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = "/";

            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                redirectUrl = "/admin_dashboard";  // ✅ Admin Redirect
            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_PATIENT"))) {
                redirectUrl = "/patient_dashboard/patient";
            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_NURSE"))) {
                redirectUrl = "/nurse_dashboard/nurse";
            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_PROVIDER"))) {
                redirectUrl = "/provider_dashboard/provider";
            }

            System.out.println("🔄 Redirecting to: " + redirectUrl);
            response.sendRedirect(redirectUrl);
        };
    }

}
