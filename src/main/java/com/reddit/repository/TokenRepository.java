package com.reddit.repository;

import com.reddit.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
Optional<Token> findByToken(String token);
void deleteByToken(String token);
}
