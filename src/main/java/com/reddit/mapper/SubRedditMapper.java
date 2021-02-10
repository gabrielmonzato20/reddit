package com.reddit.mapper;

import com.reddit.dto.SubRedditDto;
import com.reddit.model.Post;
import com.reddit.model.SubReddit;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface SubRedditMapper {
    @Mapping(target="numberofPosts", expression="java(mapPosts(subReddit.getPosts()))")
    SubRedditDto mapSubrreditToDto(SubReddit subReddit);

    default Integer mapPosts(List<Post> numberOfPosts){return numberOfPosts.size();
    }
    @InheritInverseConfiguration
    @Mapping(target="posts", ignore = true)
    SubReddit mapDtoToSubrredit(SubRedditDto subRedditDto);
}
