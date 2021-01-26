package com.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubReddit extends JpaRepository<SubReddit,Long> {
}
