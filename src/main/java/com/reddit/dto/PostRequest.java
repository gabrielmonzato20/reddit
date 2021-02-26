package com.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {
private long postid;
    private String subrreditName;
    private String postName;
    private String url ;
    private String description;


}
