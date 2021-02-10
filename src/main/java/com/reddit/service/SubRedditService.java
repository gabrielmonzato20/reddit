package com.reddit.service;

import com.reddit.dto.SubRedditDto;
import com.reddit.exceptions.RedditException;
import com.reddit.mapper.SubRedditMapper;
import com.reddit.model.SubReddit;
import com.reddit.repository.SubRedditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SubRedditService {

    private final SubRedditRepository subRedditRepository;
    private final SubRedditMapper subRedditMapper;
    @Transactional
    public SubRedditDto save(SubRedditDto dto){
      SubReddit subReddit= subRedditRepository.save(subRedditMapper.mapDtoToSubrredit(dto));
      dto.setId(subReddit.getId());
      return dto;


    }

    @Transactional
    public Iterable<SubRedditDto> getAll() {
    return subRedditRepository.findAll()
            .stream()
            .map(subRedditMapper::mapSubrreditToDto)
            .collect(Collectors.toList());
    }
    @Transactional
    public SubRedditDto getById(long id){
        SubReddit subReddit =subRedditRepository.findById(id)
                .orElseThrow(() -> new RedditException("Subreddit not found"));
        return subRedditMapper.mapSubrreditToDto(subReddit);
    }


}
