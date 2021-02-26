package com.reddit.controller;

import com.reddit.dto.CommentsDto;
import com.reddit.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentsService commentsService;
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CommentsDto commentsDto){
            commentsService.save(commentsDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("by-post/{postId}")
    public ResponseEntity<Iterable<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentsService.getAllCommentsForPost(postId));
    }
    @GetMapping("by-user/{userName}")
    public ResponseEntity<Iterable<CommentsDto>> getAllCommentsForUser(
            @PathVariable String userName){
        return ResponseEntity.status(HttpStatus.OK).body(commentsService
        .getAllCommentsForUser(userName));
    }
}
