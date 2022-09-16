package com.vsu.myblog.dto.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vsu.myblog.dto.comment.CommentDto;
import com.vsu.myblog.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PostDto {

    private Long id;
    private String content;
    private String privacy;
    private List<LikeDto> likes;
    private List<CommentDto> comments;
    private Long userId;
}
