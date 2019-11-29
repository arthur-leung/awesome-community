package com.arthur.awesome.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private long parentId;
    private String comment;
    private Byte type;
}
