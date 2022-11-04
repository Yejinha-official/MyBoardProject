package com.yejin.official.board.web.dto;

import com.yejin.official.board.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    public PostsUpdateRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }

}
