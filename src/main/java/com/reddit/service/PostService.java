package com.reddit.service;


import com.reddit.dto.PostRequest;
import com.reddit.dto.PostResponse;
import com.reddit.exceptions.RedditException;
import com.reddit.mapper.PostMapper;
import com.reddit.model.Post;
import com.reddit.model.SubReddit;
import com.reddit.model.Usera;
import com.reddit.repository.PostRepository;
import com.reddit.repository.SubRedditRepository;
import com.reddit.repository.UseraRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubRedditRepository subredditRepository;
    private final UseraRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        SubReddit subreddit = subredditRepository.findByName(postRequest.getSubrreditName())
                .orElseThrow(() -> new RedditException(postRequest.getSubrreditName()));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RedditException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        SubReddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new RedditException(subredditId.toString()));
        List<Post> posts = postRepository.findBysubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        Usera user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByuser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}