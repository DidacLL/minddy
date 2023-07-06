package cat.itacademy.minddy.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${minddy.auth.github-id}")
    private String githubClientId;
    @Value("${minddy.auth.github-secret}")
    private String githubClientSecret;

    @Value("${minddy.auth.google-id}")
    private String googleClientId;
    @Value("${minddy.auth.google-secret}")
    private String googleClientSecret;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/docs/**").permitAll()
                        .requestMatchers("/demo/**").permitAll()
                        .requestMatchers("/test/**").permitAll()
                        .requestMatchers("/dev/**").hasRole("developer")
                        .requestMatchers("/admin/**").hasRole("administrator")
                        .requestMatchers("/public/**").hasRole("user")
                        .anyRequest().authenticated())
                .oauth2Login((oauth2Login) -> oauth2Login
                        .userInfoEndpoint((userInfoEndpoint) -> userInfoEndpoint
                                .userAuthoritiesMapper(userAuthoritiesMapper())
                        )
                );
        return http.build();
    }
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return (userRequest) -> {
            OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
            OAuth2User oauth2User = delegate.loadUser(userRequest);

            // Extract userId and username from the oauth2User
            String userId = (String) oauth2User.getAttribute("sub");
            String username = (String) oauth2User.getAttribute("preferred_username");

            // Create a custom User object with userId and username
            return new DefaultOAuth2User(
                    oauth2User.getAuthorities(),
                    oauth2User.getAttributes(),
                    "sub"
            );
        };
    }
    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach(authority -> {
                if (authority instanceof OAuth2UserAuthority oauth2UserAuthority) {

                    // Get the OAuth2 provider's name (e.g., google, github)
                    String providerName = oauth2UserAuthority.getAuthority();

                    // Assign roles based on the provider
                    if (providerName.equals("google")) {
                        mappedAuthorities.add(new SimpleGrantedAuthority("user"));
                    } else if (providerName.equals("github")) {
                        mappedAuthorities.add(new SimpleGrantedAuthority("user"));
                    }
                }
            });

            return mappedAuthorities;
        };
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                this.googleClientRegistration(),
                this.githubClientRegistration(),
                this.keycloakClientRegistration());
    }

    private ClientRegistration keycloakClientRegistration() {
        //FIXME Fill with Keycloak provider data when defined
        return ClientRegistration.withRegistrationId("keycloak")
                // Define el ID de registro del cliente OAuth2.
                // Este valor se usa para identificar al cliente en tu aplicación.
                .clientId("keycloak-client-id")
                // Define el ID de cliente de tu aplicación en Keycloak.
                .clientSecret("keycloak-client-secret")
                // Define el secreto de cliente de tu aplicación en Keycloak.
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                // Define el tipo de concesión de autorización que se utilizará.
                // En este caso, se utiliza el flujo de concesión de código de autorización.
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                // Define la URI de redireccionamiento que se utilizará después de que el usuario haya iniciado sesión en Keycloak.
                // Spring Security manejará automáticamente el intercambio de código por un token de acceso en esta URI.
                .scope("openid", "profile", "email")
                // Define los ámbitos que se solicitarán al usuario durante el inicio de sesión.
                // Estos ámbitos determinan qué información se puede acceder sobre el usuario.
                .authorizationUri("https://your-keycloak-server/auth/realms/your-realm/protocol/openid-connect/auth")
                // Define la URI de autorización de Keycloak.
                // Esta es la URI a la que se redirigirá al usuario para iniciar sesión en Keycloak.
                .tokenUri("https://your-keycloak-server/auth/realms/your-realm/protocol/openid-connect/token")
                // Define la URI del token de Keycloak.
                // Esta es la URI a la que Spring Security enviará una solicitud para intercambiar un código de autorización por un token de acceso.
                .userInfoUri("https://your-keycloak-server/auth/realms/your-realm/protocol/openid-connect/userinfo")
                // Define la URI de información del usuario de Keycloak.
                // Esta es la URI a la que Spring Security enviará una solicitud para obtener información sobre el usuario que ha iniciado sesión.
                .userNameAttributeName("preferred_username")
                // Define el atributo del nombre de usuario.
                // Este es el atributo que Spring Security utilizará para obtener el nombre de usuario del usuario que ha iniciado sesión.
                .jwkSetUri("https://your-keycloak-server/auth/realms/your-realm/protocol/openid-connect/certs")
                // Define la URI del conjunto JWK (JSON Web Key) de Keycloak.
                // Esta es la URI a la que Spring Security enviará una solicitud para obtener las claves públicas
                // utilizadas para verificar las firmas de los tokens JWT emitidos por Keycloak.
                .clientName("Keycloak")
                // Define el nombre del cliente.
                // Este es el nombre que se mostrará al usuario durante el inicio de sesión para identificar al cliente OAuth2.
                .build();
        // Construye el objeto ClientRegistration.
    }

    private ClientRegistration googleClientRegistration() {
        return CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .build();
    }

    private ClientRegistration githubClientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId(githubClientId)
                .clientSecret(githubClientSecret)
                .build();
    }
}
