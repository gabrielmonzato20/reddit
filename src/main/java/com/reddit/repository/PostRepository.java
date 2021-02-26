package com.reddit.repository;


import com.reddit.model.Post;
import com.reddit.model.SubReddit;
import com.reddit.model.Usera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findBysubreddit(SubReddit subReddit);
    List<Post> findByuser(Usera user);

}
