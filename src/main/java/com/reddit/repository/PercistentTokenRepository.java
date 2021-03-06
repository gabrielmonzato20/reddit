package com.reddit.repository;

import com.reddit.model.PercistentToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PercistentTokenRepository extends JpaRepository<PercistentToken,Long> {
    Optional<PercistentToken> findByToken(String token);
}
