package com.reddit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data

public class RefrashTokenDto {
    @NotBlank
    private String refrashToken;
    private String userName;

}
