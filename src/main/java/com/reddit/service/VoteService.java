package com.reddit.service;

import com.reddit.dto.VoteDto;
import com.reddit.exceptions.RedditException;
import com.reddit.model.Post;
import com.reddit.model.Vote;
import com.reddit.repository.PostRepository;
import com.reddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.reddit.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
@Builder
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new RedditException(
                        "Post id " + voteDto.getPostId() + " not found"));
        Optional<Vote> vote = voteRepository.findByPostAndUserOrderByVoteIdDesc(
                post, authService.getCurrentUser()
        );

        if (vote.isPresent() &&
                vote.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new RedditException("Vote arredi exists");
        }

        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);

        }
        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);

    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
