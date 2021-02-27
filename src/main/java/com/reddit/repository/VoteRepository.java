package com.reddit.repository;

import com.reddit.model.Post;
import com.reddit.model.Usera;
import com.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findByPostAndUserOrderByVoteIdDesc(Post post, Usera user);
}
