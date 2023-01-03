package com.yejin.official.board.service;

import com.yejin.official.board.domain.posts.Posts;
import com.yejin.official.board.domain.posts.PostsRepository;
import com.yejin.official.board.web.dto.PostsListResponseDto;
import com.yejin.official.board.web.dto.PostsResponseDto;
import com.yejin.official.board.web.dto.PostsSaveRequestDto;
import com.yejin.official.board.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("There's no post on that id : "+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById (Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("There's no post on that id : "+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("There's no board item : index "+id));
        postsRepository.delete(posts);
    }
}
