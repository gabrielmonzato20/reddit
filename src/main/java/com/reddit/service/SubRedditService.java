package com.reddit.service;

import com.reddit.dto.SubRedditDto;
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
    @Transactional
    public SubRedditDto save(SubRedditDto dto){
      SubReddit subReddit= subRedditRepository.save(mapSubReddit(dto));
      dto.setId(subReddit.getId());
      return dto;


    }

    private SubReddit mapSubReddit(SubRedditDto dto) {
     return   SubReddit.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
    @Transactional
    public Iterable<SubRedditDto> getAll() {
    return subRedditRepository.findAll()
            .stream()
            .map(this::mapToDto)
            .collect(Collectors.toList());
    }

    private SubRedditDto mapToDto(SubReddit subReddit) {
        return   SubRedditDto.builder()
                .name(subReddit.getName())
                .description(subReddit.getDescription())
                .id(subReddit.getId())
                .build();
    }
}
