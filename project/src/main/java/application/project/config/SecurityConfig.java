package application.project.config;

import java.util.Collection;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;

import application.project.domain.UserDetailsCustom;
import application.project.domain.dto.response.ResLoginDTO.UserLoginInfo;
import application.project.util.SecurityUtil.SecurityUtil;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Value("${security.authentication.jwt.base64-secret}")
    private String jwtKey;

    @Value("${basepath.baseApi}")
    private String basePath;
    
    // used to grant user's authority
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("");
        grantedAuthoritiesConverter.setAuthoritiesClaimName(SecurityUtil.AUTHORITIES_KEY);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
                getSecretKey()).macAlgorithm(SecurityUtil.JWT_ALGORITHM).build();
        return token -> {
            try {
                return jwtDecoder.decode(token);
            } catch (Exception e) {
                System.out.println(">>> JWT error: " + e.getMessage());
                throw e;
            }
        };
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, SecurityUtil.JWT_ALGORITHM.getName());
    }

    @Bean
    public SecurityFilterChain filterChain(
        HttpSecurity http,
        CustomAuthenticationEntryPoint  customAuthenticationEntryPoint
        
        ) throws Exception {
        http
                .csrf(c -> c.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/", 
                        this.basePath + "auth/login", 
                        this.basePath + "auth/refresh", 
                        this.basePath + "users/register")
                        .permitAll()
                    .anyRequest().authenticated())

                .oauth2ResourceServer((oauth2) 
                    -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtToUserPrincipalConverter()))
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                    
                // .exceptionHandling(exceptions -> exceptions
                //     .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()) //401
                //     .accessDeniedHandler(new BearerTokenAccessDeniedHandler())) //403

                .formLogin(f -> f.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtToUserPrincipalConverter() {
    return jwt -> { 
        ObjectMapper mapper =  new ObjectMapper();

        Map<String, Object> rawMap  = jwt.getClaim(SecurityUtil.USER_KEY);

        UserLoginInfo userInfo = mapper.convertValue(rawMap, UserLoginInfo.class);

        String username = jwt.getSubject();
        Collection<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(
            jwt.getClaimAsString("scope")
        );
        // build a lightweight principal—no DB needed
        UserDetailsCustom principal = new UserDetailsCustom(userInfo.getId(), username, auths);
        return new UsernamePasswordAuthenticationToken(principal, jwt, auths);
    };
}
}
    

   

