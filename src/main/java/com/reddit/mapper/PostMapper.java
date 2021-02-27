package com.reddit.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.reddit.dto.PostRequest;
import com.reddit.dto.PostResponse;
import com.reddit.model.Post;
import com.reddit.model.SubReddit;
import com.reddit.model.Usera;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.VoteRepository;
import com.reddit.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit",source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostRequest postRequest, SubReddit subreddit, Usera user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target ="commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration" , expression = "java(duration(post))")
    public abstract PostResponse mapToDto(Post post);

    public Integer commentCount(Post post){
        return commentRepository.findBypost(post).size();
    }
    public String duration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
