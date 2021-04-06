package com.reddit.service;

import com.reddit.exceptions.RedditException;
import com.reddit.model.Token;
import com.reddit.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefrashTokenService {

    public final TokenRepository tokenRepository;


    public Token generatedRefrashToken(){
        Token token = new Token();
        token.setCreatedDate(Instant.now());
        token.setToken(UUID.randomUUID().toString());
        return tokenRepository.save(token);

 }

 public void validateRefrashToken(String token){
        tokenRepository.findByToken(token)
                .orElseThrow(()->new RedditException("error on token not found  "+ token ));
 }
public void deleteRefrashToken(String token){
        tokenRepository.deleteByToken(token);
}
}
