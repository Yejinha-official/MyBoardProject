package com.yejin.official.board.web.dto;

import com.yejin.official.board.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsResponseDto {
    private String title;
    private String content;
    private String author;
    private Long id;

    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title =entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
