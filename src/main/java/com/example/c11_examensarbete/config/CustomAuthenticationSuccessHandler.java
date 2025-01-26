package com.example.c11_examensarbete.config;

import com.example.c11_examensarbete.entities.User;
import com.example.c11_examensarbete.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public CustomAuthenticationSuccessHandler(UserRepository userRepository, OAuth2AuthorizedClientService authorizedClientService) {
        this.userRepository = userRepository;
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String clientRegistrationId = authToken.getAuthorizedClientRegistrationId();

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(clientRegistrationId, authentication.getName());
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        OAuth2User oAuth2User = authToken.getPrincipal();

        // Extract user details
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("avatar_url");
        Integer oAuthId = oAuth2User.getAttribute("id");

        assert oAuthId != null;
        userRepository.findByOauthProviderId(oAuthId.toString()).ifPresentOrElse(
                user -> {
                    // Update existing user details ADD EMAIL HERE
                    user.setUsername(name);
                    user.setProfilePictureUrl(picture);
                    user.setAccessToken(accessToken);
                    user.setOauthProvider(clientRegistrationId);
                    user.setOauthProviderId(String.valueOf(oAuthId));
                    user.setUpdatedAt(Instant.now());
                    if(user.getRole().isEmpty()) {
                        user.setRole("USER");
                    }
                    userRepository.save(user);
                },
                () -> {
                    // Create a new user entry
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setUsername(name);
                    newUser.setOauthProvider(clientRegistrationId);
                    newUser.setOauthProviderId(String.valueOf(oAuthId));
                    newUser.setProfilePictureUrl(picture);
                    newUser.setAccessToken(accessToken);
                    newUser.setCreatedAt(Instant.now());
                    newUser.setRole("USER");
                    userRepository.save(newUser);
                }
        );

        if(email == null || name == null) response.sendRedirect("http://localhost:5173/profile");
        else{

            // Redirect the user to the frontend application
            response.sendRedirect("http://localhost:5173/discovery");
        }

    }
}
