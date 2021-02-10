package com.reddit.controller;

import com.reddit.dto.SubRedditDto;
import com.reddit.service.SubRedditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {
    private final SubRedditService subRedditService;
    @PostMapping
        public ResponseEntity<SubRedditDto> createSubreddit(@RequestBody SubRedditDto dto){

        return ResponseEntity.status(HttpStatus.CREATED).body(subRedditService
        .save(dto));

    }

    @GetMapping
    public ResponseEntity<Iterable<SubRedditDto>> getAll(){
           return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                   subRedditService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SubRedditDto> getById(@PathVariable long id){
        SubRedditDto subRedditDto= subRedditService.getById(id);
        return ResponseEntity.ok(subRedditDto);
    }
}
