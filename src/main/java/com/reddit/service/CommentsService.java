package com.reddit.service;

import com.reddit.dto.CommentsDto;
import com.reddit.exceptions.RedditException;
import com.reddit.mapper.CommentMapper;
import com.reddit.model.Comment;
import com.reddit.model.NotificationEmail;
import com.reddit.model.Post;
import com.reddit.model.Usera;
import com.reddit.repository.CommentRepository;
import com.reddit.repository.PostRepository;
import com.reddit.repository.UseraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentsService {

private final static String POST_URL= "";
private final CommentRepository commentRepository;
private final PostRepository postRepository;
private final UseraRepository userRepository;
private final AuthService authService;
private final CommentMapper commentMapper;
private final MailContentBuilder mailContentBuilder;
private final MailService mailService;

public void save(CommentsDto commentsDto){
   Post post = postRepository.findById(commentsDto.getPostId())
            .orElseThrow(() -> {
                return new RedditException("Post"+commentsDto.getId()
                        +" not found");
            });
 Comment comment =  commentMapper.map(commentsDto,
         post,
         authService.getCurrentUser());
    System.out.println(post.toString());
 String mailMsg = mailContentBuilder.build(post.getUser().getUsername()
         +" post  a comment on you post "+ POST_URL);
 sendNotificationEmail( mailMsg , post.getUser());
 commentRepository.save(comment);


}

    private void sendNotificationEmail(String mailMsg, Usera user) {
    mailService.sendEmail(new NotificationEmail(user.getUsername()+" comment on oyu post"
    ,user.getEmail(),mailMsg));
    }

    public Iterable<CommentsDto> getAllCommentsForPost(Long postId) {
    Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RedditException("Post "+postId+"not found"));
    return commentRepository.findBypost(post).parallelStream()
            .map(comment -> {
               return commentMapper.mapToDto(comment);
            }).collect(Collectors.toList());

}
public Iterable<CommentsDto> getAllCommentsForUser(String userName){
    Usera user = userRepository.findByUsername(userName)
            .orElseThrow(() ->{
                return new RedditException("User "+userName+" not found");
            });
    return commentRepository.findByuser(user).parallelStream()
            .map(commentMapper::mapToDto).collect(Collectors.toList());
}
}
