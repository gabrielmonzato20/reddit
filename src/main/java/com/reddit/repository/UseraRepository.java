package com.reddit.repository;

import com.reddit.model.Usera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UseraRepository extends JpaRepository<Usera,Long> {
    Optional<Usera> findByUsername(String name);
}
