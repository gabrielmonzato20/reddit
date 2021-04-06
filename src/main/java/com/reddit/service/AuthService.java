package com.reddit.service;

import com.reddit.dto.AuthenticateDto;
import com.reddit.dto.LoginRequest;
import com.reddit.dto.RefrashTokenDto;
import com.reddit.dto.UserAuthRequest;
import com.reddit.exceptions.AuthRedditException;
import com.reddit.exceptions.RedditException;
import com.reddit.model.NotificationEmail;
import com.reddit.model.PercistentToken;
import com.reddit.model.Usera;
import com.reddit.repository.PercistentTokenRepository;
import com.reddit.repository.UseraRepository;
import com.reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {
    @Value("${server.port}")
    private  String port;
    private final PasswordEncoder passwordEncoder;
    private final UseraRepository userRepository;
    private final PercistentTokenRepository percistentTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefrashTokenService refrashTokenService;

@Transactional
    public void signup(UserAuthRequest req){
    Usera user = new Usera();
    user.setUsername(req.getUserName());
    user.setPassword(passwordEncoder.encode(req.getPassWorld()));
    user.setEmail(req.getEmail());
    user.setEnabled(false);
    user.setCreated(Instant.now());
    userRepository.save(user);
    String token = generateUserToken(user);
    mailService.sendEmail(new NotificationEmail("Please enter witch your email:",user.getEmail()
    ,"Thank you"+user.getUsername()+" for signing up to  Reddit Client , " +
    "please click on the below url to activate your account : " +
            "<a href='http://localhost:"+this.port+"/api/auth/accountVerification/"+token+"'>Click Hear</a>"));
}
    @Transactional
    public Usera getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        System.out.println(principal.toString());
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new RedditException("User name not found - " + principal.getUsername()));
    }

    private String generateUserToken(Usera user) {
       String verifyToekn =  UUID.randomUUID().toString();
        PercistentToken tokenModel = new PercistentToken();
        tokenModel.setToken(verifyToekn);
        tokenModel.setUser(user);
        percistentTokenRepository.save(tokenModel);
return verifyToekn;
}

    public void verifyToken(String token) {
    Optional<PercistentToken> verifyToken = percistentTokenRepository.findByToken(token);
    verifyToken.orElseThrow(() -> new AuthRedditException("not found token"));
    fetchUser(verifyToken.get());
    }

    private void fetchUser(PercistentToken verifyToken) {
    String username = verifyToken.getUser().getUsername();
    Usera user = userRepository.findByUsername(username).
            orElseThrow(() -> new AuthRedditException("User not found"));
    user.setEnabled(Boolean.TRUE);
    userRepository.save(user);
    }

    public AuthenticateDto login(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUserName(),
                    loginRequest.getPassworld()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtProvider.generateToken(auth);
        return AuthenticateDto.builder()
                .userName(loginRequest.getUserName())
                .userToken(token)
                .refreshToken(refrashTokenService.generatedRefrashToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getTimeToken()))
                .build();
    }

    public AuthenticateDto refrashToken(RefrashTokenDto refrashTokenDto) {
                    refrashTokenService.validateRefrashToken(refrashTokenDto.getRefrashToken());
                    String token = jwtProvider.generateTokenWitchUserName(refrashTokenDto.getUserName());
    return AuthenticateDto.builder()
            .expiresAt(Instant.now().plusMillis(jwtProvider.getTimeToken()))
            .refreshToken(refrashTokenDto.getRefrashToken())
            .userName(refrashTokenDto.getUserName())
            .userToken(token).build();
}
}
