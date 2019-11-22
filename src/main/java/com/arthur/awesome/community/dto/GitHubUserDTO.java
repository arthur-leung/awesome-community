package com.arthur.awesome.community.dto;

import lombok.Data;

@Data
public class GitHubUserDTO {
    private String name;
    private long id;
    private String bio;
    private String avatar_url;
}
