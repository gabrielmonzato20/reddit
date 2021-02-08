package com.reddit.service;

import com.reddit.dto.AuthenticateDto;
import com.reddit.dto.LoginRequest;
import com.reddit.dto.UserAuthRequest;
import com.reddit.exceptions.AuthRedditException;
import com.reddit.model.NotificationEmail;
import com.reddit.model.PercistentToken;
import com.reddit.model.Usera;
import com.reddit.repository.PercistentTokenRepository;
import com.reddit.repository.UseraRepository;
import com.reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@Service
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UseraRepository userRepository;
    private final PercistentTokenRepository percistentTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

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
            "http://localhost:8080/api/auth/accountVerification/" + token));
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
        return new AuthenticateDto(loginRequest.getPassworld(),token);
    }
}
