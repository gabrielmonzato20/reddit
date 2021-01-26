package com.reddit.repository;

import com.reddit.model.PercistentToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PercistentTokenRepository extends JpaRepository<PercistentToken,Long> {
}
