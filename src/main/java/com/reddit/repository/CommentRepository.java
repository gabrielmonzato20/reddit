package com.reddit.repository;

import com.reddit.dto.CommentsDto;
import com.reddit.model.Comment;
import com.reddit.model.Post;
import com.reddit.model.Usera;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findBypost(Post post);

    List<Comment> findByuser(Usera user);
}
